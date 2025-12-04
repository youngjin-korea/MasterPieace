package com.sunmight.erp.domain.utils.offer.service;

import com.sunmight.erp.domain.utils.offer.dto.request.OfferDetailRequest;
import com.sunmight.erp.domain.utils.offer.dto.request.OfferSaveRequest;
import com.sunmight.erp.domain.utils.offer.dto.request.OfferSearchCondition;
import com.sunmight.erp.domain.utils.offer.dto.response.OfferDetailResponse;
import com.sunmight.erp.domain.utils.offer.dto.response.OfferResponse;
import com.sunmight.erp.domain.utils.offer.entity.UofferDetailEntity;
import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import com.sunmight.erp.domain.utils.offer.repository.UofferDetailRepository;
import com.sunmight.erp.domain.utils.offer.repository.UofferRepository;
import com.sunmight.erp.global.exception.EntityNotFoundBusinessException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * readOnly = true면 JPA가 Dirty Checking(변경 감지)을 비활성화함 → 성능 ↑ && 실수로 조회 로직에서 엔티티 수정해도 JPA가 flush하지
 * 않음
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UofferService {

    private final UofferRepository uofferRepository;
    private final UofferDetailRepository uofferDetailRepository;

    // Offer(견적) + OfferDetail(견적상세) 리스트 함께 저장
    @Transactional
    public Long createOffer(OfferSaveRequest request) {

        UofferEntity offer = request.toEntity();

        // 1) 공통정보 먼저 저장
        uofferRepository.save(offer);

        // 2) 상세 내역 저장
        if (request.details() != null) {
            for (OfferDetailRequest d : request.details()) {
                UofferDetailEntity detail = d.toEntity();
                detail.setOffer(offer); // 연관관계 설정
                uofferDetailRepository.save(detail);
            }
        }
        return offer.getId();
    }

    // 기존 Offer 수정 + OfferDetail 전체 수정 (OfferDetail 추가(id 없는것)/수정(id 있는것)/삭제(기존에 있지만 요청엔 없는 id)) - 실무 난이도 극상
    @Transactional
    public Long updateOffer(Long offerId, OfferSaveRequest request) {
        log.info("start 기존 엔티티 조회 uofferRepository.findById(offerId)");
        //1) 기존 엔티티 조회
        UofferEntity offer = uofferRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundBusinessException("Uoffer", offerId));

        log.info("end 기존 엔티티 조회 uofferRepository.findById(offerId)");

        //2)  offer(공통) 값 수정
        offer.update(
                request.companyName(),
                request.address(),
                request.phone(),
                request.fax(),
                request.managerName(),
                request.offerDate(),
                request.offerTitle(),
                request.receiver(),
                request.reference(),
                request.taxRate()
        );

        //3) 변경전 조회한 uOfferEntity의 uOfferDetails를 id 와 인스턴스로 매핑함
        log.info("end offer.getUofferDetails()");
        Map<Long, UofferDetailEntity> oldDetails = offer.getUofferDetails().stream()
                .collect(Collectors.toMap(UofferDetailEntity::getId, d -> d));
        log.info("end offer.getUofferDetails()");
        //4) 신규 request Details 순회하며 요청 처리
        List<OfferDetailRequest> detailRequests =
                request.details() != null ? request.details() : Collections.emptyList();

        for (OfferDetailRequest requestDetail : detailRequests) {
            // 신규 추가
            if (requestDetail.id() == null) {
                UofferDetailEntity newDetail = requestDetail.toEntity();
                // 외래키 지정
                newDetail.setOffer(offer);
                // insert 쿼리
                uofferDetailRepository.save(newDetail);
            } else {
                // requestDetail.id() != null 이므로 수정
                UofferDetailEntity old = oldDetails.get(requestDetail.id());
                if (old != null) {
                    old.update(
                            requestDetail.itemTitle(),
                            requestDetail.modelName(),
                            requestDetail.spec(),
                            requestDetail.quantity(),
                            requestDetail.supplyPrice(),
                            requestDetail.remark()
                    );
                    // 나머지 삭제를 위하여 oldDetail Map에서 제거
                    oldDetails.remove(requestDetail.id());
                }
            }
        }

        // 프론트에 삭제한 목록이 남은 oldDetails delete Detail의 레포지토리에서 삭제문을 날려도 offer list에 값이 있음 삭제가 안됨
        for (UofferDetailEntity detail : oldDetails.values()) {
            offer.getUofferDetails().remove(detail);
        }

        return offer.getId();
    }

    //deleteOffer	Offer 삭제하면 Detail도 cascade로 모두 삭제
    @Transactional
    public Long deleteOffer(Long offerId) {
        // id로 삭제 OnToMany Cascade로 details 함께 삭제
        UofferEntity findUoffer = uofferRepository.findById(offerId)
                .orElseThrow(() -> new EntityNotFoundBusinessException("Uoffer", offerId));
        uofferRepository.delete(findUoffer);

        return offerId;
    }

    // getOffer	Offer 단건 조회 + Detail 목록
    public OfferResponse getOffer(Long offerId) {
        // fetch join 으로 N+1 예방
        UofferEntity findOffer = uofferRepository.findOfferWithDetails(offerId)
                .orElseThrow(() -> new EntityNotFoundBusinessException("Uoffer", offerId));

        return OfferResponse.of(findOffer);
    }

    // getOfferList	목록 조회(검색 옵션 포함 - 견적상태 && 년도별 ) - 동적쿼리 QueryDSL
    public Page<OfferResponse> getOfferList(OfferSearchCondition cond, Pageable pageable) {

        Page<UofferEntity> uofferEntities = uofferRepository.searchOfferList(cond, pageable);

        List<OfferResponse> offerList = uofferEntities.getContent().stream()
                .map(uoffer -> OfferResponse.of(uoffer)).toList();

        return new PageImpl<>(offerList, pageable, uofferEntities.getTotalElements());
    }

    //TODO:: addDetail (옵션)	Offer에 detail을 추가하는 내부 헬퍼

    //TODO:: removeDetail (옵션)	Offer에서 detail을 삭제하는 내부 헬퍼
}

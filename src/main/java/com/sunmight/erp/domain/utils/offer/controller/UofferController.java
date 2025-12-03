package com.sunmight.erp.domain.utils.offer.controller;

import com.sunmight.erp.domain.utils.offer.dto.request.OfferSearchCondition;
import com.sunmight.erp.domain.utils.offer.dto.response.OfferResponse;
import com.sunmight.erp.domain.utils.offer.service.UofferService;
import com.sunmight.erp.domain.utils.offer.dto.request.OfferSaveRequest;
import com.sunmight.erp.domain.utils.offer.dto.response.OfferSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// 기본적인 JSON 필드 검증이라면 무조건 @Valid
import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UofferController {

    private final UofferService uofferService;

    /**
     * offerId 로 uoffer 단건 조회
     *
     * @param offerId
     * @return
     */
    @GetMapping("/api/uoffer")
    public ResponseEntity<OfferResponse> getOffer(@RequestParam("offerId") Long offerId) {
        return ResponseEntity
                .ok(uofferService.getOffer(offerId));
    }

    /**
     * 동적 쿼리, 페이징 조회
     * <p>
     * 문제 : cond를 @RequestBody로 받았을때 조건 없이 검색하면 Body가 없는데 바인딩 시도하여 예외발생 해결 : @ModelAttribute로 생성후
     * set하는 방식으로 값이 없으면 null 이 되어 예외가 발생하지 않음
     * <p>
     * 문제 : cond 에서 아무 날짜 포맷을 지정하지 않으면 LocalDate는 스프링 기본 컨버터로 년-월-일 패턴만 인식을 함 해결 : cond LocalDate
     * type field에 @DateTimeFormat(pattern="yyyyMMdd") 지정한 패턴으로 변경 가능
     *
     * @param cond
     * @param pageable
     * @return
     */
    @GetMapping("/api/uoffers/search")
    public Page<OfferResponse> search(@ModelAttribute OfferSearchCondition cond,
            Pageable pageable) {
        if (cond == null) {
            cond = new OfferSearchCondition();
        }
        return uofferService.getOfferList(cond, pageable);
    }

    //OfferSaveRequest @Valid 예외 발생시 컨트롤러 실행 전에 MethodArgumentNotValidException 발생 -> GlobalExceptionHandler 에서 예외처리
    @PostMapping("/api/uoffer")
    public ResponseEntity<OfferSaveResponse> saveOffer(
            @RequestBody @Valid OfferSaveRequest request) {
        Long savedOfferId = uofferService.createOffer(request);
        return ResponseEntity
                .ok(new OfferSaveResponse(savedOfferId, "견적서가 정상적으로 저장되었습니다."));
    }

    @PutMapping("/api/uoffer/{offerId}")
    public ResponseEntity<OfferSaveResponse> updateOffer(@PathVariable("offerId") Long offerId,
            @RequestBody @Valid OfferSaveRequest request) {
        Long updateOfferId = uofferService.updateOffer(offerId, request);
        // 수정 후에 리턴값 없이 마무리 하고, 따로 조회해서 응답을 주는 방식 선호
        OfferResponse findOfferResponse = uofferService.getOffer(offerId);
        return ResponseEntity
                .ok(new OfferSaveResponse(updateOfferId, "견적서가 정상적으로 수정되었습니다."));
    }

    @DeleteMapping("/api/uoffer/{offerId}")
    public ResponseEntity<OfferSaveResponse> deleteOffer(@PathVariable("offerId") Long offerId) {
        Long deletedOfferId = uofferService.deleteOffer(offerId);
        return ResponseEntity.ok(new OfferSaveResponse(deletedOfferId, "견적서가 정상적으로 삭제되었습니다."));
    }
}

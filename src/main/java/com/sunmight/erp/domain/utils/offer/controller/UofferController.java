package com.sunmight.erp.domain.utils.offer.controller;

import com.sunmight.erp.domain.utils.offer.dto.request.OfferSearchCondition;
import com.sunmight.erp.domain.utils.offer.dto.response.OfferResponse;
import com.sunmight.erp.domain.utils.offer.mybatis.dto.request.OfferDetailStatCondDto;
import com.sunmight.erp.domain.utils.offer.mybatis.dto.response.OfferDetailStatDto;
import com.sunmight.erp.domain.utils.offer.service.UofferExcelService;
import com.sunmight.erp.domain.utils.offer.service.UofferService;
import com.sunmight.erp.domain.utils.offer.dto.request.OfferSaveRequest;
import com.sunmight.erp.domain.utils.offer.dto.response.OfferSaveResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class UofferController {

    private final UofferService uofferService;
    private final UofferExcelService uofferExcelService;

    /**
     * offerId 로 uoffer 단건 조회
     *
     * @param offerId
     * @return
     */
    @GetMapping("/api/uoffer/{offerId}")
    public ResponseEntity<OfferResponse> getOffer(@PathVariable("offerId") Long offerId) {
        return ResponseEntity
                .ok(uofferService.getOffer(offerId));
    }

    /**
     * 동적 쿼리, 페이징 조회
     * <p>
     * 문제 : cond를 @RequestBody로 받았을때 조건 없이 검색하면 Body가 없는데 바인딩 시도하여 예외발생 해결 : @ModelAttribute로 생성후
     * set하는 방식으로 값이 없어도 cond에 널이 아니라 기본 생성자로 생성된 인스턴스가 들어감
     * <p>
     * 문제 : cond 에서 아무 날짜 포맷을 지정하지 않으면 LocalDate는 스프링 기본 컨버터로 년-월-일 패턴만 인식을 함 해결 : cond LocalDate
     * type field에 @DateTimeFormat(pattern="yyyyMMdd") 지정한 패턴으로 변경 가능
     *
     * @param cond
     * @param pageable -> 기본 값 page = 0, size = 20
     * @return
     */
    @GetMapping("/api/uoffers/search")
    public Page<OfferResponse> search(@ModelAttribute OfferSearchCondition cond,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return uofferService.getOfferList(cond, pageable);
    }

    /**
     * TODO :: 응답 스펙 객체로 변환
     *
     * @param cond
     * @return
     */
    @GetMapping("/api/uoffer")
    public ResponseEntity<List<OfferDetailStatDto>> getOfferDetailStats(
            @ModelAttribute @Valid OfferDetailStatCondDto cond) {
        return ResponseEntity
                .ok(uofferService.getOfferDetailStats(cond));
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

    // 엑셀 업로드로 u_offer_detail 대량 저장
    @PostMapping("/api/uoffer/{offerId}/details/excel")
    public ResponseEntity<?> uploadDetails(@PathVariable("offerId") Long offerId,
            @RequestParam("file") MultipartFile file) {
        uofferExcelService.uploadUofferDetails(offerId, file);
        return ResponseEntity.ok(new OfferSaveResponse(offerId, "견적서 내역 대량 업로드 저장 완료!"));
    }

    @GetMapping("/api/uoffer/details/excel-template")
    public ResponseEntity<byte[]> downloadTemplate() {
        byte[] fileBytes = uofferExcelService.generateTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"uoffer_detail_template.xlsx\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(fileBytes);
    }

}

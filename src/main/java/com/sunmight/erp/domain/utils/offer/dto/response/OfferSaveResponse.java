package com.sunmight.erp.domain.utils.offer.dto.response;

public class OfferSaveResponse {

    private Long offerId;      // 생성된 견적서 ID
    private String message;    // 처리 결과 메시지
    // 필요하면 추가 필드 가능 (예: 상태코드, 생성일 등)

    public OfferSaveResponse(Long offerId, String message) {
        this.offerId = offerId;
        this.message = message;
    }

    // Getter
    public Long getOfferId() {
        return offerId;
    }

    public String getMessage() {
        return message;
    }
}


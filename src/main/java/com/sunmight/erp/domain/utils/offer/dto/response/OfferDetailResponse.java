package com.sunmight.erp.domain.utils.offer.dto.response;

import com.sunmight.erp.domain.utils.offer.entity.UofferDetailEntity;
import java.math.BigDecimal;

/**
 * record = 생성자 + getter + equals + hashCode + toString을 자동 생성하는 불변 DTO 클래스.
 *
 * @param itemTitle
 * @param modelName
 * @param spec
 * @param quantity
 * @param supplyPrice
 * @param remark
 */
public record OfferDetailResponse(
        Long id,
        String itemTitle,
        String modelName,
        String spec,
        Integer quantity,
        BigDecimal supplyPrice,
        String remark) {

    // entity -> dto 변환 책임
    public static OfferDetailResponse of (UofferDetailEntity details) {
        return new OfferDetailResponse(
                details.getId(),
                details.getItemTitle(),
                details.getModelName(),
                details.getSpec(),
                details.getQuantity(),
                details.getSupplyPrice(),
                details.getRemark()
        );
    }
}

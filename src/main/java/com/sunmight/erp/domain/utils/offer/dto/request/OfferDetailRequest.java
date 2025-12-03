package com.sunmight.erp.domain.utils.offer.dto.request;

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
public record OfferDetailRequest(
        Long id,
        String itemTitle,
        String modelName,
        String spec,
        Integer quantity,
        BigDecimal supplyPrice,
        String remark) {

    // dto -> entity 변환 책임
    public UofferDetailEntity toEntity() {
        return UofferDetailEntity.builder()
                .itemTitle(itemTitle)
                .modelName(modelName)
                .spec(spec)
                .quantity(quantity)
                .supplyPrice(supplyPrice)
                .remark(remark).build();
    }
}

package com.sunmight.erp.domain.utils.offer.mybatis.dto.response;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * 견적상세의 품목별 수량 가격 합산 통계 응답
 */
@Getter
@Setter
public class OfferDetailStatDto {

    private String itemTitle;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
}

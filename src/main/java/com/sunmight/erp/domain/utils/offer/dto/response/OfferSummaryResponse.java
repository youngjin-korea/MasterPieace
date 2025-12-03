package com.sunmight.erp.domain.utils.offer.dto.response;

import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import java.math.BigDecimal;
import java.time.LocalDate;

public record OfferSummaryResponse(
        Long id,
        String companyName,
        String managerName,
        LocalDate offerDate,
        BigDecimal taxRate
) {

    public static OfferSummaryResponse of(UofferEntity entity) {
        return new OfferSummaryResponse(
                entity.getId(),
                entity.getCompanyName(),
                entity.getManagerName(),
                entity.getOfferDate(),
                entity.getTaxRate()
        );
    }
}

package com.sunmight.erp.domain.utils.offer.dto.response;

import com.sunmight.erp.domain.utils.offer.dto.request.OfferDetailRequest;
import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OfferResponse(
        String companyName,
        String address,
        String phone,
        String fax,
        String managerName,
        LocalDate offerDate,
        String offerTitle,
        String receiver,
        String reference,
        BigDecimal taxRate,
        List<OfferDetailResponse> details
) {

    public static OfferResponse of(UofferEntity uoffer) {
        List<OfferDetailResponse> details = uoffer.getUofferDetails().stream()
                .map(OfferDetailResponse::of)
                .toList();

        return new OfferResponse(
                uoffer.getCompanyName(),
                uoffer.getAddress(),
                uoffer.getPhone(),
                uoffer.getFax(),
                uoffer.getManagerName(),
                uoffer.getOfferDate(),
                uoffer.getOfferTitle(),
                uoffer.getReceiver(),
                uoffer.getReference(),
                uoffer.getTaxRate(),
                details
        );
    }
}

package com.sunmight.erp.domain.utils.offer.dto.request;

import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * record는 Getter Contructor 자동으로 만들어줌
 * @param companyName
 * @param address
 * @param phone
 * @param fax
 * @param managerName
 * @param offerDate
 * @param offerTitle
 * @param receiver
 * @param reference
 * @param taxRate
 * @param details
 */
public record OfferSaveRequest(
        @NotBlank(message = "회사명은 필수 입력값입니다.")
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

        List<OfferDetailRequest> details) {

    public UofferEntity toEntity() {
        return UofferEntity.builder()
                .companyName(companyName)
                .address(address)
                .phone(phone)
                .fax(fax)
                .managerName(managerName)
                .offerDate(offerDate)
                .offerTitle(offerTitle)
                .receiver(receiver)
                .reference(reference)
                .taxRate(taxRate).build();
    }
}

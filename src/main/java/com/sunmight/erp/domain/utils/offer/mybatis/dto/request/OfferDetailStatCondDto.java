package com.sunmight.erp.domain.utils.offer.mybatis.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class OfferDetailStatCondDto {

    @NotEmpty(message = "회사명은 필수 입력입니다.")
    private String company;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate endDate;
}

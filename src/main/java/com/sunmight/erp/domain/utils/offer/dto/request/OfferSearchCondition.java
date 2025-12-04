package com.sunmight.erp.domain.utils.offer.dto.request;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class OfferSearchCondition {

    private String companyName;     // 회사명 검색
    private String managerName;     // 담당자명

    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate startDate;     // 견적일 시작

    // spring 의 기본 컨버터 패턴 yyyy-MM-dd, 지정해준 yyyyMMdd 모두 컨버팅 가능해짐
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate endDate;       // 견적일 끝
    private String itemKeyword;     // Detail의 itemTitle, modelName 등 검색
}


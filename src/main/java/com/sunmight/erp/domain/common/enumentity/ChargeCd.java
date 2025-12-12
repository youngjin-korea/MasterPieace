package com.sunmight.erp.domain.common.enumentity;

import lombok.Getter;

@Getter
public enum ChargeCd {
    KRW("KRW", 410, "₩", "Korea", 0, "South Korean Won"),
    USD("USD", 840, "$", "United States", 2, "US Dollar"),
    EUR("EUR", 978, "€", "European Union", 2, "Euro"),
    JPY("JPY", 392, "¥", "Japan", 0, "Japanese Yen"),
    CNY("CNY", 156, "¥", "China", 2, "Chinese Yuan"),
    GBP("GBP", 826, "£", "United Kingdom", 2, "British Pound");

    private final String code;
    private final int numericCode;
    private final String symbol;
    private final String country;
    private final int defaultFractionDigits;
    private final String description;

    ChargeCd(String code, int numericCode, String symbol,
            String country, int defaultFractionDigits, String description) {
        this.code = code;
        this.numericCode = numericCode;
        this.symbol = symbol;
        this.country = country;
        this.defaultFractionDigits = defaultFractionDigits;
        this.description = description;
    }
}

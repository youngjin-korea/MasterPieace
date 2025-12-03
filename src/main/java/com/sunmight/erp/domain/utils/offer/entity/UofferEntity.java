package com.sunmight.erp.domain.utils.offer.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "U_OFFER")
@Entity
public class UofferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UofferDetailEntity> uofferDetails = new ArrayList<>();

    // 회사 정보(스냅샷) -------------------------
    @Column(name = "company_name", length = 200)
    private String companyName;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "fax", length = 50)
    private String fax;

    // 견적 정보 --------------------------------
    @Column(name = "manager_name", length = 100)
    private String managerName;

    @Column(name = "offer_date")
    private LocalDate offerDate;

    @Column(name = "offer_title", length = 300)
    private String offerTitle;

    @Column(name = "receiver", length = 100)
    private String receiver;

    @Column(name = "reference", length = 200)
    private String reference;

    // precision: 전체 자릿수, scale: 소수점 이하 자릿수
    @Column(name = "tax_rate", precision = 5, scale = 2)
    private BigDecimal taxRate;

    @Builder
    public UofferEntity(
            String companyName,
            String address,
            String phone,
            String fax,
            String managerName,
            LocalDate offerDate,
            String offerTitle,
            String receiver,
            String reference,
            BigDecimal taxRate) {
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.managerName = managerName;
        this.offerDate = offerDate;
        this.offerTitle = offerTitle;
        this.receiver = receiver;
        this.reference = reference;
        this.taxRate = taxRate;
    }

    // 수정 책임
    public void update(
            String companyName,
            String address,
            String phone,
            String fax,
            String managerName,
            LocalDate offerDate,
            String offerTitle,
            String receiver,
            String reference,
            BigDecimal taxRate
    ) {
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
        this.fax = fax;
        this.managerName = managerName;
        this.offerDate = offerDate;
        this.offerTitle = offerTitle;
        this.receiver = receiver;
        this.reference = reference;
        this.taxRate = taxRate;
    }

}

package com.sunmight.erp.domain.utils.offer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "U_OFFER_DETAIL")
@Entity
public class UofferDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ManyToOne 매핑 ( UofferEntity )
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "u_offer_id")
    private UofferEntity offer;

    @Column(name = "item_title", length = 200)
    private String itemTitle;

    @Column(name = "model_name", length = 200)
    private String modelName;

    @Column(name = "spec", length = 200)
    private String spec;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "supply_price")
    private BigDecimal supplyPrice;

    @Column(name = "remark")
    private String remark;

    public void setOffer(UofferEntity offer) {
        this.offer = offer;
    }

    @Builder
    public UofferDetailEntity(String itemTitle, String modelName, String spec, Integer quantity,
            BigDecimal supplyPrice, String remark) {
        this.itemTitle = itemTitle;
        this.modelName = modelName;
        this.spec = spec;
        this.quantity = quantity;
        this.supplyPrice = supplyPrice;
        this.remark = remark;
    }

    public void update(
            String itemTitle,
            String modelName,
            String spec,
            Integer quantity,
            BigDecimal supplyPrice,
            String remark) {
        this.itemTitle = itemTitle;
        this.modelName = modelName;
        this.spec = spec;
        this.quantity = quantity;
        this.supplyPrice = supplyPrice;
        this.remark = remark;
    }
}

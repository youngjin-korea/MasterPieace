package com.sunmight.erp.domain.sales.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MS3011")
@Entity
public class Ms3011 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "ms3010_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))  // 필요 시 FK 제약 끌 수도 있음
    @ManyToOne(fetch = FetchType.LAZY)
    private Ms3010 ms3010;
}

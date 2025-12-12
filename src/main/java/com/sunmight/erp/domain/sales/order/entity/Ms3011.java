package com.sunmight.erp.domain.sales.order.entity;

import com.sunmight.erp.domain.common.enumentity.JointType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;

/**
 * 주문상세 목록 테이블
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MS3011")
@Entity
public class Ms3011 {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ms3010_id",
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))  // 필요 시 FK 제약 끌 수도 있음
    private Ms3010 ms3010;

    // 외래키 매핑 제품

    // 폭 전체 18자리, 소수점 4자리 까지
    @Column(precision = 18, scale = 4)
    private BigDecimal width;

    // 트림폭
    @Column(precision = 18, scale = 4)
    private BigDecimal trimWidth;

    // 길이
    @Column(precision = 18, scale = 4)
    private BigDecimal len;

    // joint 수
    private Long jointCnt;

    // joint type
    @Enumerated(EnumType.STRING)
    private JointType jointType;
}

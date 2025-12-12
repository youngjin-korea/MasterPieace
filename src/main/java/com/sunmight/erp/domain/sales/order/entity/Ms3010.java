package com.sunmight.erp.domain.sales.order.entity;

import com.sunmight.erp.domain.common.enumentity.ChargeCd;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 테이블
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 필수 필요 (프록시 등으로 활용됨)
@Table(name = "MS3010") // 디비 테이블명
@Entity
public class Ms3010 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고아객체 발생시 주문상세 테이블에서 삭제됨
    @OneToMany(mappedBy = "ms3010", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ms3011> orders = new ArrayList<>();

    //주문 번호
    private Long soNo;

    //주문일자
    private LocalDate soYmd;

    //주문 대리점

    // 거래 화폐 종류
    @Enumerated(EnumType.STRING)
    private ChargeCd chargeCd;
}

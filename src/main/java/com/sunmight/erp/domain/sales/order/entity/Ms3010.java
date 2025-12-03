package com.sunmight.erp.domain.sales.order.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MS3010")
@Entity
public class Ms3010 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

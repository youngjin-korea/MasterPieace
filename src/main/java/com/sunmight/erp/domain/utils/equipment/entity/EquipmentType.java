package com.sunmight.erp.domain.utils.equipment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 장비 타입 코드 테이블
 */
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EQUIPMENT_TYPE")
public class EquipmentType {

    @Id
    @GeneratedValue
    private Long id;

    private String typeName;
    private String description;
}

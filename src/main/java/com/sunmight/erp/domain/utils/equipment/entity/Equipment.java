package com.sunmight.erp.domain.utils.equipment.entity;

import com.sunmight.erp.domain.common.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EQUIPMENT")
public class Equipment extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Column(name = "serial_no")
    private String serialNo;

    private Long price;

    private LocalDate purchaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_type_id")
    private EquipmentType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_status_id")
    private EquipmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;
}

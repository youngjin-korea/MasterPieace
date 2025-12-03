package com.sunmight.erp.domain.utils.equipment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String team;
    private String position;
    private String phone;
    private String email;
}

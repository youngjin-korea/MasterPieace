package com.sunmight.erp.domain.test;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TEST")
@Entity
public class TestEntity {
    @Id @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public static TestEntity saveDummy () {
        TestEntity testEntity = new TestEntity();
        testEntity.setName("test중");
        return testEntity;
    }
}

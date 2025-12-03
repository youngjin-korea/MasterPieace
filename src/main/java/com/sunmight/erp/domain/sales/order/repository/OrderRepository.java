package com.sunmight.erp.domain.sales.order.repository;

import com.sunmight.erp.domain.sales.order.entity.Ms3010;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Ms3010, Long> {
}

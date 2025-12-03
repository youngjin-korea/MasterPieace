package com.sunmight.erp.domain.sales.order.service;

import com.sunmight.erp.domain.sales.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
}

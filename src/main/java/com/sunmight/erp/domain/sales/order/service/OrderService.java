package com.sunmight.erp.domain.sales.order.service;

import com.sunmight.erp.domain.sales.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true) // default 읽기 트랜잭션으로 하여 조회에서 jpa 더티체킹 등 안함 성능 향상
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    // order + orderDetail 저장


    // order + orderDetail 수정

    // order 삭제

    // order 단건 조회 + orderDetail 목록

    // order 목록조회 + 검색조건 (주문날짜, 거래처, item, grit, brand)


}

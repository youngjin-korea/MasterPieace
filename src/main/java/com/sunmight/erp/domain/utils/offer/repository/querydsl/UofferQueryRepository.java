package com.sunmight.erp.domain.utils.offer.repository.querydsl;

import com.sunmight.erp.domain.utils.offer.dto.request.OfferSearchCondition;
import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UofferQueryRepository {

    Page<UofferEntity> searchOfferList(OfferSearchCondition cond, Pageable pageable);
}

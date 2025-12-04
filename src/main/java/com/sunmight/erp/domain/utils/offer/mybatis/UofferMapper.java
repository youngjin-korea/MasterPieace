package com.sunmight.erp.domain.utils.offer.mybatis;

import com.sunmight.erp.domain.utils.offer.mybatis.dto.request.OfferDetailStatCondDto;
import com.sunmight.erp.domain.utils.offer.mybatis.dto.response.OfferDetailStatDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UofferMapper {

    // 견적 상세의 품목별 합산 통계
    List<OfferDetailStatDto> getOfferDetailStats(OfferDetailStatCondDto cond);
}

package com.sunmight.erp.domain.utils.offer.repository.querydsl;

import static com.sunmight.erp.domain.utils.offer.entity.QUofferDetailEntity.*;
import static com.sunmight.erp.domain.utils.offer.entity.QUofferEntity.*;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sunmight.erp.domain.utils.offer.dto.request.OfferSearchCondition;
import com.sunmight.erp.domain.utils.offer.entity.QUofferDetailEntity;
import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class UofferQueryRepositoryImpl implements UofferQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<UofferEntity> searchOfferList(OfferSearchCondition cond, Pageable pageable) {

        List<UofferEntity> content = query
                .select(uofferEntity)
                .from(uofferEntity)
                .where(
                        companyNameContains(cond.getCompanyName()),
                        managerNameContains(cond.getManagerName()),
                        offerDateBetween(cond.getStartDate(), cond.getEndDate()),
                        hasItemKeyword(cond.getItemKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(uofferEntity.id.desc())
                .fetch();

        // total 쿼리
        Long total = query
                .select(uofferEntity.count())
                .from(uofferEntity)
                .where(
                        companyNameContains(cond.getCompanyName()),
                        managerNameContains(cond.getManagerName()),
                        offerDateBetween(cond.getStartDate(), cond.getEndDate()),
                        hasItemKeyword(cond.getItemKeyword())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    // 동적 조건
    // 회사명 포함여부
    private BooleanExpression companyNameContains(String name) {
        return StringUtils.hasText(name) ? uofferEntity.companyName.contains(name) : null;
    }

    // 매니저 명 포함여부
    private BooleanExpression managerNameContains(String name) {
        return StringUtils.hasText(name) ? uofferEntity.managerName.contains(name) : null;
    }

    // 날짜 범위 여부
    private BooleanExpression offerDateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null && endDate == null) {
            return null;
        }
        if (startDate != null && endDate == null) {
            return uofferEntity.offerDate.goe(startDate);
        }
        if (startDate == null && endDate != null) {
            return uofferEntity.offerDate.loe(endDate);
        }
        // 모두 값이 있는 경우
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            LocalDate tmp = startDate;
            startDate = endDate;
            endDate = tmp;
        }
        return uofferEntity.offerDate.between(startDate, endDate);
    }

    // Detail 기반 조건 존재 서브쿼리 -> exists(subquery)
    // 메인쿼리의 테이블에 행과 같은 아이디 이면서 키워드가 포함된 디테일 테이블을 갖는 메인쿼리 테이블 행만 남김
    private BooleanExpression hasItemKeyword(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return JPAExpressions
                    .selectOne()
                    .from(uofferDetailEntity)
                    .where(
                            uofferDetailEntity.offer.id.eq(uofferEntity.id),
                            uofferDetailEntity.itemTitle.contains(keyword)
                                    .or(uofferDetailEntity.modelName.contains(keyword))
                    ).exists();
        }
        return null;
    }

}

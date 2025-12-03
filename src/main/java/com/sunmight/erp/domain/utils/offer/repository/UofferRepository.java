package com.sunmight.erp.domain.utils.offer.repository;

import com.sunmight.erp.domain.utils.offer.entity.UofferEntity;
import com.sunmight.erp.domain.utils.offer.repository.querydsl.UofferQueryRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UofferRepository extends JpaRepository<UofferEntity, Long>, UofferQueryRepository {

    @Query("SELECT o FROM UofferEntity o LEFT JOIN FETCH o.uofferDetails WHERE o.id = :offerId")
    Optional<UofferEntity> findOfferWithDetails(@Param("offerId") Long offerId);
}

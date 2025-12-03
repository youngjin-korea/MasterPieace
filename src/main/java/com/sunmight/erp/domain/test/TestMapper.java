package com.sunmight.erp.domain.test;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    void delete (Long id);
}

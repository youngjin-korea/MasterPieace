package com.sunmight.erp.domain.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;

    public List<TestEntity> findAll() {
        return testRepository.findAll();
    }

    // 더미데이터 저장
    public TestEntity saveTest(TestEntity test) {
        return testRepository.save(test);
    }

    public void deleteMapper (Long id) {
        testMapper.delete(id);
    }
}

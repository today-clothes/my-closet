package com.oclothes;

import com.oclothes.infra.file.FileService;
import com.oclothes.infra.file.LocalFileServiceImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@TestConfiguration
public class BaseDataJpaTestConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(this.entityManager);
    }

    @Bean
    public FileService fileService() {
        return new LocalFileServiceImpl();
    }

}

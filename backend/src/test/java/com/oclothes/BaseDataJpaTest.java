package com.oclothes;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

@Import(BaseDataJpaTestConfig.class)
@DataJpaTest
public abstract class BaseDataJpaTest extends BaseTest {
}

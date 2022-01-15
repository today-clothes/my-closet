package com.oclothes;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
public abstract class BaseTest {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());
}

package com.connect_group.thymeleaf_extras.config;

import com.connect_group.thymeleaf.testing.config.ExtendableTestSpringContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ThymesheetExtrasConfig.class})
public class TestConfig extends ExtendableTestSpringContext {}

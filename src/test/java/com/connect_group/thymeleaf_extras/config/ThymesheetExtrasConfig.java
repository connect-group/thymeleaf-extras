package com.connect_group.thymeleaf_extras.config;

import com.connect_group.thymeleaf.testing.config.ThymesheetConfig;
import com.connect_group.thymeleaf_extras.ThymeleafExtrasDialect;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.dialect.IDialect;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ThymesheetExtrasConfig extends ThymesheetConfig {
    protected Set<IDialect> additionalDialects() {
        Set<IDialect> additionalDialects = new HashSet<>();
        additionalDialects.add(new ThymeleafExtrasDialect());
        return additionalDialects;
    }
}

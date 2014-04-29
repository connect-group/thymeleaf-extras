package com.connect_group.thymeleaf_extras;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class ThymeleafExtrasDialect extends AbstractDialect {
    public static final String DIALECT_PREFIX = "xtra";

    @Override
    public String getPrefix() {
        return DIALECT_PREFIX;
    }

    @Override
    public boolean isLenient() {
        return false;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new UTextBeforeModifierProcessor());
        processors.add(new UTextAfterModifierProcessor());
        processors.add(new InsertUTextBeforeModifierProcessor());
        processors.add(new StripWhitespaceProcessor());
        return processors;
    }
}

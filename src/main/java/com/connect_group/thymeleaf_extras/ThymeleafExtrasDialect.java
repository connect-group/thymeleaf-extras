package com.connect_group.thymeleaf_extras;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class ThymeleafExtrasDialect extends AbstractProcessorDialect {


    private static final String PREFIX = "xtra";
    private static final int PROCESSOR_PRECEDENCE = 1500;
    private static final String DIALECT_NAME = "extras-dialect";

    public ThymeleafExtrasDialect() {
        super(DIALECT_NAME, PREFIX, PROCESSOR_PRECEDENCE);
    }

    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final HashSet<IProcessor> processors = new HashSet<>();
        processors.add(new UTextAfterHTMLProcessor(PREFIX, getDialectProcessorPrecedence()));
        processors.add(new UTextBeforeHTMLProcessor(PREFIX, getDialectProcessorPrecedence()));
        processors.add(new StripWhiteSpaceHTMLProcessor(PREFIX, getDialectProcessorPrecedence()));
        return processors;
    }
}

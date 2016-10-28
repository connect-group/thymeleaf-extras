package com.connect_group.thymeleaf_extras;

import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

public class ThymeleafExtrasDialect extends AbstractProcessorDialect {


    public static final String PREFIX = "xtra";
    public static final int PROCESSOR_PRECEDENCE = 1500;

    protected ThymeleafExtrasDialect() {
        super("extras-dialect", PREFIX, PROCESSOR_PRECEDENCE);
    }

    public Set<IProcessor> getProcessors(String dialectPrefix) {
        final HashSet<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new UTextAfterModifierHTMLProcessor(PREFIX, PROCESSOR_PRECEDENCE));
        processors.add(new UTextBeforeModifierHTMLProcessor(PREFIX, PROCESSOR_PRECEDENCE));
        return processors;
    }
}

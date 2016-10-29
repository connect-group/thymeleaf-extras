package com.connect_group.thymeleaf_extras;

import org.junit.Test;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UUTextBeforeHTMLProcessorTest {

    @Test
    public void runTest() {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.setDialects(Arrays.asList(new StandardDialect(), new ThymeleafExtrasDialect()));
        testExecutor.execute("classpath:utextbefore.thtest");
        assertEquals(true, testExecutor.getReporter().isAllOK());
    }

}
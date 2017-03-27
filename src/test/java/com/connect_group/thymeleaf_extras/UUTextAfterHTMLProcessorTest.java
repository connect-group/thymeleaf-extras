package com.connect_group.thymeleaf_extras;

import org.junit.Test;
import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.testing.templateengine.engine.TestExecutor;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class UUTextAfterHTMLProcessorTest {

    @Test
    public void runTest() {
        TestExecutor testExecutor = new TestExecutor();
        testExecutor.setDialects(Arrays.asList(new StandardDialect(), new ThymeleafExtrasDialect()));
        testExecutor.execute("classpath:utextafter.thtest");
        assertEquals(true, testExecutor.getReporter().isAllOK());
    }

}
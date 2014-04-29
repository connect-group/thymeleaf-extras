package com.connect_group.thymeleaf_extras;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class ThymeleafExtrasDialectTest {

    @Test
    public void shouldNotBeLenient() {
        ThymeleafExtrasDialect dialect = new ThymeleafExtrasDialect();
        assertThat(dialect.isLenient(), equalTo(false));
    }
}

package com.connect_group.thymeleaf_extras;

import com.connect_group.thymeleaf.testing.ThymeleafTestEngine;
import com.connect_group.thymeleaf_extras.config.TestConfig;
import com.connect_group.thymesheet.query.HtmlElements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static com.connect_group.thymeleaf.testing.hamcrest.ThymeleafMatchers.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class UTextAfterModifierProcessorTest {
    public static final String COMPONENT_HTML_PATH = TestConfig.getTestResourcesHtmlPath("/TextAfterModifier.html");

//    static {
//        String path;
//        URL url = UTextAfterModifierProcessorTests.class.getResource("/TextAfterModifier.html");
//        try {
//            path = URLDecoder.decode(url.getPath(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            path="";
//            e.printStackTrace();
//        }
//
//        COMPONENT_HTML_PATH = path;
//
//        TestConfig.getTestResourcesHtmlPath("/TextAfterModifier.html");
//    }


    @Autowired
    private ThymeleafTestEngine selectorEngine;

    @Test
    public void shouldRemoveTheCustomAttribute() throws Exception {
        Map<String,Object> model = new HashMap<>();

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.icon-then-text"), everyItem(not(hasAttribute("data-xtra-text-after"))));
    }

    @Test
    public void shouldHaveNoText_WhenEmptyTextSpecified() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.icon-then-text").text().trim(), equalTo(""));
    }

    @Test
    public void shouldHaveExpectedText_WhenSpecified() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "expected text");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.icon-then-text"), isSingleElementThat(hasTextAfterElement("expected text")));
        assertThat(tags.matching("p.icon-then-text").text().trim(), equalTo("expected text"));
        assertThat(tags.matching("p.icon-then-text > i").text().trim(), equalTo(""));
    }

    @Test
    public void shouldNotChangeOrderOfNestedElements() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "expected text");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.mixedcontent").text().trim(), equalTo("span1span2expected text"));
    }

}

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

import static com.connect_group.thymeleaf.testing.hamcrest.ThymeleafMatchers.exists;
import static com.connect_group.thymeleaf.testing.hamcrest.ThymeleafMatchers.hasAttribute;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class UTextBeforeModifierProcessorTest {
    public static final String COMPONENT_HTML_PATH = TestConfig.getTestResourcesHtmlPath("/TextBeforeModifier.html");

//    static {
//        String path;
//        URL url = UTextBeforeModifierProcessorTests.class.getResource("/TextBeforeModifier.html");
//        try {
//            path = URLDecoder.decode(url.getPath(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            path="";
//            e.printStackTrace();
//        }
//
//        COMPONENT_HTML_PATH = path;	}


    @Autowired
    private ThymeleafTestEngine selectorEngine;

    @Test
    public void shouldRemoveTheCustomAttribute() throws Exception {
        Map<String,Object> model = new HashMap<>();

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p"), everyItem(not(hasAttribute("data-xtra-text-before"))));
    }

    @Test
    public void shouldKeepIconElement() throws Exception {
        Map<String,Object> model = new HashMap<>();

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.text-then-icon > i.icon"), exists());
    }

    @Test
    public void shouldHaveNoText_WhenEmptyTextSpecified() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.text-then-icon").text().trim(), equalTo(""));

    }

    @Test
    public void shouldSetTextAsExpected() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "expected text");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.text-then-icon").text().trim(), equalTo("expected text"));
    }

    @Test
    public void shouldSetTextAsExpected_WhenCommentsExist() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "expected text");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.text-with-comment").text().trim(), equalTo("expected text"));
    }

    @Test
    public void shouldSetTextAsExpected_WhenNoTextInSourceHtml() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "expected text");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.no-text-then-icon").text().trim(), equalTo("expected text"));
    }

    @Test
    public void shouldAllowTags_WhenSpecifiedInText() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "<span>expected text<span>");

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p.no-text-then-icon span").text().trim(), equalTo("expected text"));
    }
}


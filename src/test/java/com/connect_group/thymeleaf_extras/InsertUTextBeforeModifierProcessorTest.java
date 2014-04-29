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
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class InsertUTextBeforeModifierProcessorTest {
    public static final String COMPONENT_HTML_PATH = TestConfig.getTestResourcesHtmlPath("/InsertUTextBefore.html");

//    static {
//        String path;
//        URL url = InsertUTextBeforeModifierProcessorTest.class.getResource("/InsertUTextBefore.html");
//        try {
//            path = URLDecoder.decode(url.getPath(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            path="";
//            e.printStackTrace();
//        }
//
//        COMPONENT_HTML_PATH = path;
//    }

    @Autowired
    private ThymeleafTestEngine selectorEngine;

    @Test
    public void shouldRemoveTheCustomAttribute() throws Exception {
        Map<String,Object> model = new HashMap<>();

        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p"), everyItem(not(hasAttribute("data-xtra-insert-utext"))));
    }

    @Test
    public void shouldInsertTheTextBeforeTheIcon() throws Exception {
        Map<String,Object> model = new HashMap<>();
        model.put("newText", "expected text");
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        assertThat(tags.matching("p").text(), is(equalTo("expected textText after icon")));
        assertThat(tags.matching("p i"), exists());
    }


}

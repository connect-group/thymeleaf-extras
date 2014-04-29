package com.connect_group.thymeleaf_extras;

import com.connect_group.thymeleaf.testing.ThymeleafTestEngine;
import com.connect_group.thymeleaf_extras.config.TestConfig;
import com.connect_group.thymesheet.query.HtmlElements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;

import java.util.HashMap;
import java.util.List;

import static com.connect_group.thymeleaf.testing.hamcrest.ThymeleafMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class StripWhitespaceProcessorTest {
    public static final String COMPONENT_HTML_PATH = TestConfig.getTestResourcesHtmlPath("/StripWhitespace.html");

//    private static String getComponentHtmlPath(String filename) {
//        String path;
//        URL url = ResponsiveImageProcessorTests.class.getResource(filename);
//        try {
//            path = URLDecoder.decode(url.getPath(), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            path="";
//            e.printStackTrace();
//        }
//        return path;
//    }
//
//    static {
//        COMPONENT_HTML_PATH = getComponentHtmlPath("/strip-whitespace/strip.html");
//    }

    @Autowired
    private ThymeleafTestEngine selectorEngine;

    @Test
    public void shouldKeepTheElements() throws Exception {
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH);
        assertThat(tags.matching("section"), occursOnce());
        assertThat(tags.matching("div.el"), hasSize(2));
    }

    @Test
    public void shouldRemoveStripWhitespaceDataAttribute() throws Exception {
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH);
        assertThat(tags.matching("section"), isSingleElementThat(not(hasAttribute("data-xtra-strip-whitespace"))));
    }

    @Test
    public void shouldRemoveAllWhitespaceOnlyTextNodes_WhenDeep() throws Exception {
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH);
        assertThat(tags.matching("section").text(), equalTo("Some textSome more text"));
        assertThat(tags.matching("section div:first-child p"), isSingleElementThat(hasOnlyText("Some text")));
        assertThat(tags.matching("section div:last-child p"), isSingleElementThat(hasOnlyText("Some more text")));
    }

    @Test
    public void shouldRemoveWhiteSpaceNodesImmediatlyWithinArticle_WhenShallow() throws Exception {
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH);
        Element el = tags.matching("article").get(0).getElement();

        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<Object> list = (List)el.getChildren();

        assertThat(list, everyItem(not(instanceOf(Text.class))));
    }

    @Test
    public void shouldNotRemoveWhiteSpaceNodesWithinArticleDiv_WhenShallow() throws Exception {
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH);
        Element el = tags.matching("article > div").get(0).getElement();

        @SuppressWarnings({ "rawtypes", "unchecked" })
        List<Object> list = (List)el.getChildren();

        assertThat(list, hasItem(instanceOf(Text.class)));
    }

    @Test
    public void shouldRemoveAllWhitespaceOnlyTextNodes_WhenDeep_AndInLoop() throws Exception {
        HashMap<String,Object> model = new HashMap<String,Object>();
        model.put("list", new String[]{"A","B","C"});
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);

        assertThat(tags.matching("ul").text(), equalTo("ABC"));
    }

    //@Test
    public void displayOutput() throws Exception {
        HashMap<String,Object> model = new HashMap<String,Object>();
        model.put("list", new String[]{"A","B","C"});
        HtmlElements tags = selectorEngine.process(COMPONENT_HTML_PATH, model);
        System.err.println(tags);
    }
}

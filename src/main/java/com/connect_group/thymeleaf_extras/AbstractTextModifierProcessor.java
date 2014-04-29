package com.connect_group.thymeleaf_extras;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Macro;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.attr.AbstractChildrenModifierAttrProcessor;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressionExecutionContext;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.List;

public abstract class AbstractTextModifierProcessor extends AbstractChildrenModifierAttrProcessor {
    protected AbstractTextModifierProcessor(String attrName) {
        super(attrName);
    }

    @Override
    protected List<Node> getModifiedChildren(Arguments arguments, Element element, String attributeName) {
        final Node newText = getTextNode(arguments, element, attributeName);
        List<Node> originalChildNodes = element.getChildren();
        List<Node> newNodes = removeRequiredTextNodes(originalChildNodes);
        addNewTextNodeToList(newNodes, newText);
        return newNodes;
    }

    protected abstract void addNewTextNodeToList(List<Node> newNodes, Node newText);

    protected abstract List<Node> removeRequiredTextNodes(List<Node> originalChildNodes);

    protected final String getText(final Arguments arguments, final Element element, final String attributeName) {

        final String attributeValue = element.getAttributeValue(attributeName);

        final Configuration configuration = arguments.getConfiguration();
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);

        final IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, attributeValue);

        final Object result =
                expression.execute(configuration, arguments, StandardExpressionExecutionContext.UNESCAPED_EXPRESSION);

        return (result == null? "" : result.toString());

    }

    protected final Node getTextNode(final Arguments arguments, final Element element, final String attributeName) {
        final String text = getText(arguments, element, attributeName);

        final Macro node = new Macro(text);
        // Setting this allows avoiding text inliners processing already generated text,
        // which in turn avoids code injection.
        node.setProcessable(false);

        return node;
    }
}


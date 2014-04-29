package com.connect_group.thymeleaf_extras;

import org.thymeleaf.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Add text to the beginning of an element, without removing anything.
 */
public class InsertUTextBeforeModifierProcessor extends AbstractTextModifierProcessor {

    protected InsertUTextBeforeModifierProcessor() {
        super("insert-utext");
    }

    @Override
    protected void addNewTextNodeToList(List<Node> newNodes, Node newText) {
        newNodes.add(0, newText);
    }

    @Override
    protected List<Node> removeRequiredTextNodes(List<Node> originalChildNodes) {
        return new ArrayList<Node>(originalChildNodes);
    }

    @Override
    public int getPrecedence() {
        return 1500;
    }

}

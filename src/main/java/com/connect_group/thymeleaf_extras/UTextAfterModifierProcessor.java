package com.connect_group.thymeleaf_extras;

import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;

import java.util.LinkedList;
import java.util.List;

/**
 * Will replace any trailing text at the end of an element.
 */
public class UTextAfterModifierProcessor extends AbstractTextModifierProcessor {

    protected UTextAfterModifierProcessor() {
        super("utext-after");
    }

    @Override
    protected void addNewTextNodeToList(List<Node> newNodes, Node newText) {
        newNodes.add(newText);
    }

    @Override
    protected List<Node> removeRequiredTextNodes(List<Node> originalChildNodes) {
        return removeTrailingTextNodes(originalChildNodes);
    }

    private List<Node> removeTrailingTextNodes(List<Node> originalChildNodes) {
        LinkedList<Node> newChildNodes = new LinkedList<>();
        boolean ignoreTextNodes=true;

        for(int i=originalChildNodes.size()-1; i >= 0; i--) {
            Node node = originalChildNodes.get(i);
            ignoreTextNodes = ignoreTextNodes && !(node instanceof Element);

            if(!ignoreTextNodes || !(node instanceof Text)) {
                newChildNodes.add(0, node);
            }

        }

        return newChildNodes;
    }

    @Override
    public int getPrecedence() {
        return 1400;
    }

}
package com.connect_group.thymeleaf_extras;

import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Will replace any leading text prior to the first child element.
 * For example,
 *     <p te-utext-before="'hello'">Text<i class="icon"></i></p>
 *
 * ... will become
 *
 *     <p>hello<i class="icon"></i></p>
 */
public class UTextBeforeModifierProcessor extends AbstractTextModifierProcessor {


    public UTextBeforeModifierProcessor() {
        super("utext-before");
    }

    @Override
    protected void addNewTextNodeToList(List<Node> newNodes, Node newText) {
        newNodes.add(0, newText);
    }

    @Override
    protected List<Node> removeRequiredTextNodes(List<Node> originalChildNodes) {
        return removeLeadingTextNodes(originalChildNodes);
    }



    private List<Node> removeLeadingTextNodes(List<Node> originalChildNodes) {
        ArrayList<Node> newChildNodes = new ArrayList<>();
        boolean ignoreTextNodes=true;
        for(Node node : originalChildNodes) {
            ignoreTextNodes = ignoreTextNodes && !(node instanceof Element);

            if(!ignoreTextNodes || !(node instanceof Text)) {
                newChildNodes.add(node);
            }
        }

        return newChildNodes;
    }


    @Override
    public int getPrecedence() {
        return 1400;
    }
}

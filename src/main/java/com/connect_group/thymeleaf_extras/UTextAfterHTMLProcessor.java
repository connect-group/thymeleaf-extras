package com.connect_group.thymeleaf_extras;

import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IText;
import org.thymeleaf.templatemode.TemplateMode;

final class UTextAfterHTMLProcessor extends AbstractTextModifierProcessor {

    public static final String ATTRIBUTE_NAME = "utext-after";

    public UTextAfterHTMLProcessor(String dialectPrefix, int precedence) {
        super(TemplateMode.HTML, dialectPrefix, ATTRIBUTE_NAME, precedence);
    }

    @Override
    void doProcess(IModel model, IText attributeValue) {
        Integer position = getPositionOfReplaceableTextEvent(model);
        if (position != null) {
            model.replace(position, attributeValue);
        }
    }

    private Integer getPositionOfReplaceableTextEvent(IModel model) {
        Integer lastPosition = null;
        for (int i = 0; i < model.size(); i++) {
            if (model.get(i) instanceof IText) {
                lastPosition = i;
            }
        }
        return lastPosition;
    }
}

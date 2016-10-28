package com.connect_group.thymeleaf_extras;

import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IText;
import org.thymeleaf.templatemode.TemplateMode;

final class UTextBeforeModifierHTMLProcessor extends AbstractUTextModifierProcessor {

    public static final String ATTRIBUTE_NAME = "utext-before";

    public UTextBeforeModifierHTMLProcessor(String dialectPrefix, int precedence) {
        super(TemplateMode.HTML, dialectPrefix, ATTRIBUTE_NAME, precedence);
    }

    private Integer getPositionOfFirstTextEvent(IModel model) {
        for (int i = 0; i < model.size(); i++) {
            if (model.get(i) instanceof IText) {
                return i;
            }
        }
        return null;
    }

    @Override
    void doProcess(IModel model, IText attributeValue) {
        Integer positionOfFirstTextEvent = getPositionOfFirstTextEvent(model);
        if (positionOfFirstTextEvent != null) {
            model.replace(positionOfFirstTextEvent, attributeValue);
        } else {
            //nothing set, we need to add the value after the opened element that contained or attribute
            model.insert(1, attributeValue);
        }
    }
}

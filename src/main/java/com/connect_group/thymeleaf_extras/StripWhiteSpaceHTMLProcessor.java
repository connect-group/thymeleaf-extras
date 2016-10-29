package com.connect_group.thymeleaf_extras;

import org.thymeleaf.engine.EngineEventUtils;
import org.thymeleaf.model.*;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.ArrayList;
import java.util.List;

final class StripWhiteSpaceHTMLProcessor extends AbstractTextModifierProcessor {

    private static final String ATTRIBUTE_NAME = "strip-whitespace";
    private static final String DEEP_ATTRIBUTE_VALUE = "deep";

    protected StripWhiteSpaceHTMLProcessor(final String dialectPrefix, final int precedence) {
        super(TemplateMode.HTML, dialectPrefix, ATTRIBUTE_NAME, precedence);
    }

    @Override
    void doProcess(IModel model, IText attributeValue) {
        List<ITemplateEvent> validEvents;
        if (DEEP_ATTRIBUTE_VALUE.equals(attributeValue.getText())) {
            validEvents = getNonWhiteSpaceEvents(model, false);
        } else {
            validEvents = getNonWhiteSpaceEvents(model, true);
        }
        updateModel(model, validEvents);
    }

    private void updateModel(IModel model, List<ITemplateEvent> nonWhiteSpaceEvents) {
        model.reset();
        for (ITemplateEvent nonWhiteSpaceEvent : nonWhiteSpaceEvents) {
            model.add(nonWhiteSpaceEvent);
        }
    }

    private List<ITemplateEvent> getNonWhiteSpaceEvents(IModel model, boolean keepChildWhiteSpace) {
        List<ITemplateEvent> eventsToKeep = new ArrayList<>();
        int openElementCount = 0;
        for (int i = 0; i < model.size(); i++) {
            ITemplateEvent iTemplateEvent = model.get(i);
            if (iTemplateEvent instanceof IOpenElementTag) {
                openElementCount++;
            }
            if (iTemplateEvent instanceof ICloseElementTag) {
                openElementCount--;
            }
            if (isNotText(iTemplateEvent) || isNotWhiteSpace((IText) iTemplateEvent) ||
                    keepChildWhiteSpace(openElementCount, keepChildWhiteSpace)) {
                eventsToKeep.add(iTemplateEvent);
            }
        }
        return eventsToKeep;
    }

    private boolean keepChildWhiteSpace(int elementCount, boolean keepChildEmptyText) {
        return keepChildEmptyText && elementCount > 1;
    }

    private boolean isNotWhiteSpace(IText iTemplateEvent) {
        return !EngineEventUtils.isWhitespace(iTemplateEvent);
    }

    private boolean isNotText(ITemplateEvent iTemplateEvent) {
        return !(iTemplateEvent instanceof IText);
    }


}

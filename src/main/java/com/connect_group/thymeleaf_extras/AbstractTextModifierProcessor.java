package com.connect_group.thymeleaf_extras;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.engine.StandardModelFactory;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IText;
import org.thymeleaf.processor.element.AbstractAttributeModelProcessor;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.templatemode.TemplateMode;

abstract class AbstractTextModifierProcessor extends AbstractAttributeModelProcessor {

    private final TemplateMode templateMode;

    protected AbstractTextModifierProcessor(final TemplateMode templateMode, final String dialectPrefix, final String attributeName, final int precedence) {
        super(templateMode, dialectPrefix, null, false, attributeName, true, precedence, true);
        this.templateMode = templateMode;
    }

    @Override
    protected void doProcess(ITemplateContext context, IModel model, AttributeName attributeName, String attributeValue, IElementModelStructureHandler structureHandler) {
        doProcess(model, getAttributeValueAsEvent(context, attributeValue));
    }

    abstract void doProcess(IModel model, IText attributeValue);

    private IText getAttributeValueAsEvent(ITemplateContext context, String attributeValue) {
        String evaluatedAttributeValue = getEvaluatedAttributeValue(context, attributeValue);
        IModelFactory modelFactory = getModelFactory(context);
        return modelFactory.createText(evaluatedAttributeValue);
    }

    private String getEvaluatedAttributeValue(ITemplateContext context, String attributeValue) {
        IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(context.getConfiguration());
        IStandardExpression expression = expressionParser.parseExpression(context, attributeValue);
        return expression.execute(context).toString();
    }

    private IModelFactory getModelFactory(ITemplateContext context) {
        return new StandardModelFactory(context.getConfiguration(), templateMode);
    }

}

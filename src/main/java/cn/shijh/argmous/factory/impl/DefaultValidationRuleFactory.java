package cn.shijh.argmous.factory.impl;

import cn.shijh.argmous.annotation.ArrayParamCheck;
import cn.shijh.argmous.annotation.IsRule;
import cn.shijh.argmous.annotation.ParamCheck;
import cn.shijh.argmous.exception.RuleCreateException;
import cn.shijh.argmous.factory.ValidationRuleFactory;
import cn.shijh.argmous.model.ValidationRule;
import cn.shijh.argmous.util.AnnotationBeanUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class DefaultValidationRuleFactory implements ValidationRuleFactory {
    @Override
    public Collection<ValidationRule> createFromAnnotations(ParamCheck[] paramChecks, String defaultTargetName) {
        return Arrays.stream(paramChecks)
                .map(i->this.createFromAnnotation(i, defaultTargetName))
                .collect(Collectors.toList());
    }
    
    @Override
    public ValidationRule createFromAnnotation(ParamCheck paramCheck, String defaultTargetName) {
        ValidationRule validationRule = new ValidationRule();
        AnnotationBeanUtils.copyProperties(paramCheck, validationRule);
        if (validationRule.getTarget().isEmpty()) {
            validationRule.setTarget(defaultTargetName);
        }
        return validationRule;
    }

    @Override
    public Collection<ValidationRule> createFromAnnotation(ArrayParamCheck arrayParamCheck, String defaultTargetName) throws RuleCreateException {
        Collection<ValidationRule> fromAnnotations = createFromAnnotations(arrayParamCheck.value(), defaultTargetName);
        int count = 0;
        String targetName = arrayParamCheck.target().isEmpty() ? defaultTargetName : arrayParamCheck.target();
        for (ValidationRule r : fromAnnotations) {
            r.setTarget(targetName + "[" + (count++) + "]");
        }
        ValidationRule selfRule = createFromAnnotation(arrayParamCheck.self(), targetName);
        fromAnnotations.add(selfRule);
        return fromAnnotations;
    }

    @Override
    public Collection<ValidationRule> createFromBean(Class<?> type, String name) {
        Field[] fields = type.getDeclaredFields();
        Collection<ValidationRule> rules = new ArrayList<>(fields.length);
        AtomicBoolean shouldValid = new AtomicBoolean(false);
        for (Field field : fields) {
            ValidationRule rule = ValidationRule.empty();
            rule.setTarget(name);
            rule.addInclude(field.getName());
            shouldValid.set(false);
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                IsRule isRule = annotation.annotationType().getDeclaredAnnotation(IsRule.class);
                Optional.ofNullable(isRule).ifPresent(ir -> {
                    shouldValid.set(true);
                    AnnotationBeanUtils.copyProperties(annotation, rule,
                            Collections.singletonMap("value", ir.value()));
                });
            }
            if (shouldValid.get()) {
                rules.add(rule);
            }
        }
        return rules;
    }
}

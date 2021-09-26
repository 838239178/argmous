package cn.shijh.argmous.factory;

import cn.shijh.argmous.annotation.ParamCheck;
import cn.shijh.argmous.exception.RuleCreateException;
import cn.shijh.argmous.model.ValidationRule;

import java.util.Collection;

public interface ValidationRuleFactory {
    Collection<ValidationRule> createFromAnnotations(ParamCheck[] paramChecks, String defaultTargetName) throws RuleCreateException;

    ValidationRule createFromAnnotation(ParamCheck paramCheck, String defaultTargetName) throws RuleCreateException;

    Collection<ValidationRule> createFromBean(Object bean, String name) throws RuleCreateException;
}

package cn.shijh.argmous.factory.proxy;

import cn.shijh.argmous.factory.ArgmousProxyFactory;
import cn.shijh.argmous.factory.ArgumentInfoFactory;
import cn.shijh.argmous.factory.ValidationRuleFactory;
import cn.shijh.argmous.factory.arg.DefaultArgumentInfoFactory;
import cn.shijh.argmous.factory.rule.DefaultValidationRuleFactory;
import cn.shijh.argmous.handler.ParamCheckInvocationHandler;
import cn.shijh.argmous.service.ArgmousService;
import cn.shijh.argmous.service.impl.ArgmousServiceImpl;
import lombok.AllArgsConstructor;

import java.lang.reflect.Proxy;

@AllArgsConstructor
public class JDKProxyFactory implements ArgmousProxyFactory {
    private final ValidationRuleFactory validationRuleFactory;
    private final ArgumentInfoFactory argumentInfoFactory;
    private final ArgmousService argmousService;

    public JDKProxyFactory() {
        validationRuleFactory = new DefaultValidationRuleFactory();
        argumentInfoFactory = new DefaultArgumentInfoFactory();
        argmousService = new ArgmousServiceImpl();
    }

    @Override
    public Object proxy(Object target) {
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                new ParamCheckInvocationHandler(validationRuleFactory,argumentInfoFactory, argmousService, target));
    }
}

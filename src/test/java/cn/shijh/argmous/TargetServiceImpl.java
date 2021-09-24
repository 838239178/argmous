package cn.shijh.argmous;

import cn.shijh.argmous.annotation.ArrayParamCheck;
import cn.shijh.argmous.annotation.ParamCheck;
import cn.shijh.argmous.annotation.ParamChecks;
import cn.shijh.argmous.annotation.Valid;

import java.util.Collection;

public class TargetServiceImpl implements TargetService {

    @Override
    @ParamCheck(size = {-1,3}, regexp = "^a+.*")
    public void test1(@Valid("s") String  s) {
        System.out.println("pass");
    }

    @ParamCheck(include = "s", size = {-1, 2})
    @Override
    public void testBean2(TestBean bean, @Valid("s") String s) {
        System.out.println("success");
    }

    @Override
    @ParamChecks({
            @ParamCheck(include = "a", size = {2,-1}),
            @ParamCheck(include = "b", range = {"1","3"})
    })
    public void test2(@Valid("a") String a, @Valid("b") Integer b) {
        System.out.println("pass");
    }

    @ArrayParamCheck(target = "ss", value = {
            @ParamCheck(size = {2,-1}),
            @ParamCheck(regexp = "^a+.*")
    })
    public void test3(@Valid("ss") Collection<String> ss) {
        System.out.println("pass");
    }

    @Override
    public void testBean(@Valid("bean") TestBean bean) {
        System.out.println(bean);
    }

    @ParamCheck(include = "name", regexp = "b.*")
    @Override
    public void testBeanOverride(@Valid("bean") TestBean bean) {
        System.out.println(bean);
    }
}

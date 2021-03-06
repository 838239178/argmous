package top.pressed.argmous.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 'exclude'、'include'只能使用其中一个,如果两个都用，那么只使用’include‘ <br/>
 *
 * @author shijh
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {
    //language=regexp
    String regexp() default "";

    /**
     * use a char to split a string
     */
    String split() default "";

    /**
     * 前闭后开 -1 为不限制 只填一个相当于 [n,+∞）
     */
    int[] size() default {};

    /**
     * 前闭后开 空字符 为不限制 只填一个相当于 [n,+∞)
     */
    String[] range() default {};

    boolean required() default true;

    String target() default "";

    String[] include() default {};

    String[] custom() default {};
}

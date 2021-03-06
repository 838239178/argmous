package top.pressed.argmous.util;

import top.pressed.argmous.model.ValidationRule;

import java.util.*;

public class CustomizeUtils {

    /**
     * check present
     * @param rule rule
     * @param pattern regexp
     * @return true if present
     */
    public static boolean isContained(ValidationRule rule, String pattern) {
        return findOptional(rule,pattern).isPresent();
    }

    /**
     * if key and value both present, like 'a=b'
     * @param rule rule
     * @param key the key like 'a'
     * @return true if present
     */
    public static boolean hasKeyValue(ValidationRule rule, String key) {
        return findOptional(rule,"^" + key + "=.+$").isPresent();
    }

    /**
     * search element in custom
     * @param rule rule
     * @param pattern regexp
     * @return an optional string
     */
    public static Optional<String> findOptional(ValidationRule rule, String pattern) {
        return rule.getCustom().stream()
                .filter(s -> s.matches(pattern))
                .findFirst();
    }

    /**
     * search element in custom
     * @param rule rule
     * @param pattern regexp
     * @return null if not found else the element
     */
    public static String find(ValidationRule rule, String pattern) {
        Optional<String> res = findOptional(rule, pattern);
        return res.orElse(null);
    }

    /**
     * if your custom element like 'a=b,c,d' you can get the collection of [b,c,d]
     * @param rule rule
     * @param key like 'a'
     * @return collection of values
     */
    public static Collection<String> getValues(ValidationRule rule, String key) {
        final Collection<String> values = new ArrayList<>(2);
        findOptional(rule, "^" + key + "=.+$").ifPresent(s -> {
            String value = s.split("=")[1];
            values.addAll(Arrays.asList(value.split(",")));
        });
        return values;
    }

    /**
     * if your custom element like 'a=b' you can get the 'b'
     * @param rule rule
     * @param key like 'a'
     * @return null if not found or none value else value like 'b'
     */
    public static String getValue(ValidationRule rule, String key) {
        final String[] value = new String[1];
        value[0] = null;
        findOptional(rule, "^" + key + "=.+$").ifPresent(
                s -> value[0] = s.split("=")[1]
        );
        return value[0];
    }
}

package asalty.fish.iotbigdata.proxy.statistics;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 13090
 * @version 1.0
 * @description: TODO
 * @date 2022/3/17 15:48
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StatisticsProxy {
    String value() default "";

    Class<?> esDocClass() default Object.class;

    String propertyName() default "";

    ResultType resultType() default ResultType.PLAIN;

    enum ResultType {
        PLAIN,
        MIN,
        MAX,
        AVG,
        SUM,
    }
}

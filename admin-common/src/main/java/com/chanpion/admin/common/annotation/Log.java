package com.chanpion.admin.common.annotation;

import java.lang.annotation.*;

/**
 * 记录日志
 *
 * @author April Chen
 * @date 2019/10/14 9:12
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value() default "";
}

package com.chanpion.admin.common.anno;

import java.lang.annotation.*;

/**
 * 记录业务日志
 *
 * @author April Chen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    String value();
}

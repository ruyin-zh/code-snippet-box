package org.code.ruyin.util.snippet.ds.annotation;

import java.lang.annotation.*;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc: 该注解用于标识数据源是否使用主库
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Master {
}

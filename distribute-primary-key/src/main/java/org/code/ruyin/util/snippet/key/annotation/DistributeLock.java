package org.code.ruyin.util.snippet.key.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xzwang080
 * @desc
 * @date 2021/4/7
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributeLock {


    /**
     *
     * 锁名称
     *
     * */
    String lockName() default "";


    /**
     *
     * 锁名称前缀
     *
     * */
    String lockNamePrefix() default "";


    /**
     *
     * 锁名称后缀
     *
     * */
    String lockNameSuffix() default "";


    /**
     *
     * 锁名称拼接时使用的分隔符
     *
     * */
    String separator() default ".";


    /**
     *
     * 获取注解的方法参数列表的某个属性值来作为lockName,因为有时lockName不固定
     * 当param不为空时,可以通过argIndex参数来设置具体是参数列表的第几个参数,默认取第一个
     *
     * */
    String param() default "";


    /**
     *
     * 将方法第argIndex参数作为锁
     *
     * */
    int argIndex() default 0;


    /**
     *
     * 是否使用公平锁
     *
     * */
    boolean fairLock() default false;


    /**
     *
     * 获取锁是否进行尝试,否则一致等待
     *
     * */
    boolean tryLock() default false;


    /**
     *
     * 锁最长等待时间
     *
     * */
    long waitTime() default 30L;


    /**
     *
     * 锁超时时间
     *
     * */
    long leaseTime() default 5L;

    /**
     *
     * 时间单位
     *
     * */
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}

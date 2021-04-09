package org.code.ruyin.util.snippet.key.template;

import org.code.ruyin.util.snippet.key.callback.DistributeLockCallback;

import java.util.concurrent.TimeUnit;

/**
 * @author hjxz
 * @desc 定义模板方法
 * @date 2021/4/7
 */
public interface DistributeLockTemplate {

    long DEFAULT_WAIT_TIME = 30;
    long DEFAULT_TIMEOUT = 5;
    TimeUnit DEFAULT_TIMEUNIT = TimeUnit.SECONDS;



    <T> T lock(DistributeLockCallback<T> callback, boolean fairLock);

    <T> T lock(DistributeLockCallback<T> callback, boolean fairLock, long waitTime, long leaseTime, TimeUnit timeUnit);

    <T> T tryLock(DistributeLockCallback<T> callback, boolean fairLock);

    <T> T tryLock(DistributeLockCallback<T> callback, boolean fairLock, long waitTime, long leaseTime, TimeUnit timeUnit);

}

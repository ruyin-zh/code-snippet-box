package org.code.ruyin.util.snippet.key.callback;

/**
 * @author hjxz
 * @desc
 * @date 2021/4/7
 */
public interface DistributeLockCallback<T> {


    /**
     *
     * 业务逻辑处理
     *
     * */
    T process();


    /**
     *
     * 锁名称
     *
     * */
    String getLockName();

}

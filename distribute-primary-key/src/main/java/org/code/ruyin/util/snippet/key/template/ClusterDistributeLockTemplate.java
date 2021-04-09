package org.code.ruyin.util.snippet.key.template;

import lombok.extern.slf4j.Slf4j;
import org.code.ruyin.util.snippet.key.callback.DistributeLockCallback;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author xzwang080
 * @desc 集群方式实现,其他方式(single、master-slave、sentinel、replicate、proxy)类似
 * @date 2021/4/7
 */
@Slf4j
public class ClusterDistributeLockTemplate implements DistributeLockTemplate{


    private RedissonClient redissonClient;

    public ClusterDistributeLockTemplate(){}

    public ClusterDistributeLockTemplate(RedissonClient redissonClient){
        this.redissonClient = redissonClient;
    }


    @Override
    public <T> T lock(DistributeLockCallback<T> callback, boolean fairLock) {
        return lock(callback, fairLock, DEFAULT_WAIT_TIME, DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    @Override
    public <T> T lock(DistributeLockCallback<T> callback, boolean fairLock, long waitTime, long leaseTime, TimeUnit timeUnit) {
        RLock lock = getLock(callback.getLockName(),fairLock);
        try {
            lock.lock(leaseTime,timeUnit);
            return callback.process();
        }finally {
            if (lock != null && lock.isLocked()){
                lock.unlock();
            }
        }
    }

    @Override
    public <T> T tryLock(DistributeLockCallback<T> callback, boolean fairLock) {
        return null;
    }

    @Override
    public <T> T tryLock(DistributeLockCallback<T> callback, boolean fairLock, long waitTime, long leaseTime, TimeUnit timeUnit) {
        RLock lock = getLock(callback.getLockName(),fairLock);
        try {
            if (lock.tryLock(waitTime,leaseTime,timeUnit)){
                return callback.process();
            }

        }catch (InterruptedException e){
            log.warn("[redisson] 尝试获取分布式锁失败! lockName:{}",callback.getLockName());
        }finally {
            if (lock != null && lock.isLocked()){
                lock.unlock();
            }
        }
        return null;
    }


    private RLock getLock(String lockName, boolean fairLock){
        return fairLock ? redissonClient.getFairLock(lockName) : redissonClient.getLock(lockName);
    }


    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}

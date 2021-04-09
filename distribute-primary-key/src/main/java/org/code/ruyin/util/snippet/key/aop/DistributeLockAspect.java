package org.code.ruyin.util.snippet.key.aop;

import org.apache.commons.beanutils.PropertyUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.code.ruyin.util.snippet.key.annotation.DistributeLock;
import org.code.ruyin.util.snippet.key.callback.DistributeLockCallback;
import org.code.ruyin.util.snippet.key.template.DistributeLockTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * @author hjxz
 * @desc
 * @date 2021/4/9
 */
@Aspect
@Component
public class DistributeLockAspect {

    @Autowired
    private DistributeLockTemplate lockTemplate;


    @Pointcut("@annotation(org.code.ruyin.util.snippet.key.annotation.DistributeLock)")
    public void distributeLockAspect(){}


    @Around(value = "distributeLockAspect()")
    public Object doAround(ProceedingJoinPoint pjp) throws NoSuchMethodException {
        //切点所在类
        Class<?> targetClazz = pjp.getTarget().getClass();
        //注解所在方法签名
        String methodName = pjp.getSignature().getName();

        Class<?>[] parameterTypes = ((MethodSignature) pjp.getSignature()).getMethod().getParameterTypes();

        Method method = targetClazz.getMethod(methodName, parameterTypes);

        Object[] args = pjp.getArgs();

        String lockName = getLockName(method, args);
        return lock(pjp,method,lockName);
    }


    @AfterThrowing(value = "distributeLockAspect()")
    public void afterThrowing(Throwable ex){
        throw new RuntimeException(ex);
    }


    private Object lock(ProceedingJoinPoint pjp, Method method, String lockName){
        DistributeLock dl = method.getAnnotation(DistributeLock.class);

        boolean tryLock = dl.tryLock();
        if (tryLock){
            return tryLock(pjp, dl, lockName);
        }else {
            return lock(pjp, lockName, dl.fairLock());
        }
    }


    private String getLockName(Method method, Object[] args){
        Objects.requireNonNull(method);
        DistributeLock dl = method.getAnnotation(DistributeLock.class);

        String lockName = dl.lockName();
        String param = dl.param();

        if (isEmpty(lockName)){
            if (args.length > 0){
                if (isNotEmpty(param)){
                    Object arg;
                    if (dl.argIndex() > 0){
                        arg = args[dl.argIndex() - 1];
                    }else {
                        arg = args[0];
                    }

                    lockName = String.valueOf(getParam(arg, param));
                }else if (dl.argIndex() > 0){
                    lockName = args[dl.argIndex() - 1].toString();
                }
            }
        }

        if (isNotEmpty(lockName)){
            String namePrefix = dl.lockNamePrefix();
            String nameSuffix = dl.lockNameSuffix();
            String separator = dl.separator();

            StringJoiner sj = new StringJoiner(separator,namePrefix,nameSuffix);
            sj.add(lockName);
            return sj.toString();
        }

        throw new IllegalArgumentException("Can not get or generate lockName accurately!");
    }
    
    
    private Object getParam(Object arg, String param){
        if (isNotEmpty(param) && arg != null){
            Object result;
            try {
                result = PropertyUtils.getProperty(arg,param);
                return result;
            } catch (IllegalAccessException e) {
                throw new RuntimeException("invalid method access!");
            } catch (InvocationTargetException e) {
                throw new RuntimeException("method invoke fail!");
            } catch (NoSuchMethodException e) {
                throw new IllegalArgumentException(arg + " not have attr:" + param + " or not have get() method",e);
            }
        }

        return null;
    }
    
    
    private Object lock(ProceedingJoinPoint pjp, String lockName, boolean fairLock){
        return lockTemplate.lock(new DistributeLockCallback<Object>() {
            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return null;
            }
        },fairLock);
    }


    private Object tryLock(ProceedingJoinPoint pjp, DistributeLock dl, String lockName){
        boolean fairLock = dl.fairLock();
        long waitTime = dl.waitTime();
        long leaseTime = dl.leaseTime();
        TimeUnit timeUnit = dl.timeUnit();

        return lockTemplate.tryLock(new DistributeLockCallback<Object>() {

            @Override
            public Object process() {
                return proceed(pjp);
            }

            @Override
            public String getLockName() {
                return lockName;
            }
        }, fairLock, waitTime, leaseTime, timeUnit);
    }


    private Object proceed(ProceedingJoinPoint pjp){
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private boolean isEmpty(Object str){
        return str == null || "".equals(str);
    }

    private boolean isNotEmpty(Object str){
        return !isEmpty(str);
    }

}

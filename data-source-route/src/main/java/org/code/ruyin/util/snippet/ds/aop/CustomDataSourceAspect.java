package org.code.ruyin.util.snippet.ds.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.code.ruyin.util.snippet.ds.bean.DBContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc:
 */
@Aspect
@Component
public class CustomDataSourceAspect {


    @Pointcut("!@annotation(org.code.ruyin.util.snippet.ds.annotation.Master)" +
            "&& (execution(* org.code.ruyin.util.snippet.ds.service..*.select*(..))" +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.get*(..)))")
    public void readPointCut(){

    }


    @Pointcut("@annotation(org.code.ruyin.util.snippet.ds.annotation.Master) " +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.insert*(..))" +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.add*(..))" +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.create*(..))" +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.update*(..))" +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.edit*(..))" +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.delete*(..))" +
            "|| execution(* org.code.ruyin.util.snippet.ds.service..*.remove*(..))")
    public void writePointCut(){

    }


    @Before("readPointCut()")
    public void read(){
        DBContextHolder.slave();
    }


    @Before("writePointCut()")
    public void write(){
        DBContextHolder.master();
    }


}

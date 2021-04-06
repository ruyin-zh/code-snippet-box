package org.code.ruyin.util.snippet.ds.bean;

import org.code.ruyin.util.snippet.ds.enums.DBTypeEnum;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc:
 */
public class DBContextHolder {

    private static ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static AtomicInteger counter = new AtomicInteger(-1);


    public static DBTypeEnum get(){
        return contextHolder.get();
    }

    public static void set(DBTypeEnum dbType){
        contextHolder.set(dbType);
    }


    public static void master(){
        set(DBTypeEnum.MASTER);
        System.out.println("--------切换到master---------");
    }


    public static void slave(){
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999){
            counter.set(-1);
        }

        if (index == 0){
            set(DBTypeEnum.SLAVE1);
            System.out.println("--------切换到slave1---------");
        }else {
            set(DBTypeEnum.SLAVE2);
            System.out.println("--------切换到slave2---------");
        }
    }

}

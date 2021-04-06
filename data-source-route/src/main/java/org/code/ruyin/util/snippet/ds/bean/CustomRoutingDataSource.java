package org.code.ruyin.util.snippet.ds.bean;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc:
 */
public class CustomRoutingDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.get();
    }
}

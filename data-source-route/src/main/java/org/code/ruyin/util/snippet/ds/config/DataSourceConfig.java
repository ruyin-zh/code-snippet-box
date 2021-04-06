package org.code.ruyin.util.snippet.ds.config;

import org.code.ruyin.util.snippet.ds.bean.CustomRoutingDataSource;
import org.code.ruyin.util.snippet.ds.enums.DBTypeEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc:
 */
@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slave1DataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave2")
    public DataSource slave2DataSource(){
        return DataSourceBuilder.create().build();
    }


    @Bean
    public CustomRoutingDataSource customRoutingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                                           @Qualifier("slave1DataSource") DataSource slave1DataSource,
                                                           @Qualifier("slave2DataSource") DataSource slave2DataSource){
        Map<Object,Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DBTypeEnum.MASTER,masterDataSource);
        targetDataSource.put(DBTypeEnum.SLAVE1,slave1DataSource);
        targetDataSource.put(DBTypeEnum.SLAVE2,slave2DataSource);

        CustomRoutingDataSource routingDataSource = new CustomRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        routingDataSource.setTargetDataSources(targetDataSource);
        return routingDataSource;
    }

}

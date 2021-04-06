package org.code.ruyin.util.snippet.ds.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author: hjxz
 * @date: 2021/4/6
 * @desc:
 */
@EnableTransactionManagement
@Configuration
public class MybatisConfig {

    @Resource(name = "customRoutingDataSource")
    private DataSource routingDataSource;


    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(routingDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return factoryBean.getObject();
    }


    @Bean
    public PlatformTransactionManager platformTransactionManager(){
        return new DataSourceTransactionManager(routingDataSource);
    }

}

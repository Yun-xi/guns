package com.stylefeng.guns.rest.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.DataSources;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-11-13 15:47
 */
@Configuration
public class DatasourceConfig {

    @Primary
    @Bean(name = "datasource")
    @Qualifier("datasource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }

    @Bean(name = "tccDataSource")
    @Qualifier("tccDataSource")
    @ConfigurationProperties(prefix = "spring.tccDataSource")
    public DataSource tccDataSource() {
        return DataSourceBuilder.create().type(DruidDataSource.class).build();
    }
}

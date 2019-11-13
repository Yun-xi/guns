package com.stylefeng.guns.rest.config;

import com.mchange.v2.c3p0.DataSources;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @author xieyaqi
 * @mail xieyaqi11@gmail.com
 * @date 2019-11-13 15:59
 */
@Configuration
public class MybatisConfig {

    @Autowired
    @Qualifier("datasource")
    private DataSource dataSource;

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "mybatis-plus")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean;
    }
}

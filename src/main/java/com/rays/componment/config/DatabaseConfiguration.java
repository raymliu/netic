package com.rays.componment.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;

@Configuration
@EnableTransactionManagement
@Slf4j
@EnableConfigurationProperties({ DataSourceProperties.class, LiquibaseProperties.class})
public class DatabaseConfiguration {


    @Autowired
    private Environment env;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        log.debug("Configuring Datasource");
        if (dataSourceProperties.getUrl() == null) {
            log.error("Your database connection pool configuration is incorrect! The application" +
                            " cannot start. Please check your Spring profile, current profiles are: {}",
                    Arrays.toString(env.getActiveProfiles()));

            throw new ApplicationContextException("Database connection pool is not configured correctly");
        }
        DruidDataSource config = new DruidDataSource();
        config.setDriverClassName(dataSourceProperties.getDriverClassName());
        config.setUrl( dataSourceProperties.getUrl());
        if (dataSourceProperties.getUsername() != null) {
            config.setUsername( dataSourceProperties.getUsername());
        } else {
            config.setUsername("");
        }
        if (dataSourceProperties.getPassword() != null) {
            config.setPassword(dataSourceProperties.getPassword());
        } else {
            config.setPassword(""); // HikariCP doesn't allow null password
        }
        return config;
    }

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource, DataSourceProperties dataSourceProperties,
                                     LiquibaseProperties liquibaseProperties) {

        // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
        SpringLiquibase liquibase = new AsyncSpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:liquibase/master.xml");
        liquibase.setContexts(liquibaseProperties.getContexts());
        liquibase.setDropFirst(liquibaseProperties.isDropFirst());
        liquibase.setShouldRun(liquibaseProperties.isEnabled());
        log.debug("Configuring Liquibase");
        return liquibase;
    }


    @Bean
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(DataSourceProperties dataSourceProperties) {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(this.dataSource(dataSourceProperties));
        mybatisSqlSessionFactoryBean.setPlugins(getInterceptors());
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisSqlSessionFactoryBean.setConfiguration(mybatisConfiguration);
        mybatisConfiguration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mybatisSqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        GlobalConfiguration configuration = new GlobalConfiguration();
        configuration.setIdType(IdType.AUTO.getKey());
        mybatisSqlSessionFactoryBean.setGlobalConfig(configuration);
        mybatisConfiguration.setMapUnderscoreToCamelCase(true);

        return mybatisSqlSessionFactoryBean;
    }

    @Bean
    public Interceptor[] getInterceptors() {
        ArrayList interceptors = new ArrayList();
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        interceptors.add(paginationInterceptor);

        return  (Interceptor[])interceptors.toArray(new Interceptor[0]);
    }

}

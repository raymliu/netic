package com.rays.config;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;


public class AsyncSpringLiquibase extends SpringLiquibase {

    private final Logger log = LoggerFactory.getLogger(AsyncSpringLiquibase.class);

    @Autowired
    private Environment env;

    @Override
    public void afterPropertiesSet() throws LiquibaseException {


        log.debug("Starting Liquibase synchronously");
        initDb();

    }

    protected void initDb() throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();
        log.debug("Started Liquibase in {} ms", watch.getTotalTimeMillis());
    }
}

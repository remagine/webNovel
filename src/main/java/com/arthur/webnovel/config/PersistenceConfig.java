package com.arthur.webnovel.config;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {
    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");

        if (StringUtils.isNotEmpty(env.getProperty("database.server-name"))) {
            ds.addDataSourceProperty("serverName", env.getProperty("database.server-name"));
        }
        ds.addDataSourceProperty("databaseName", env.getProperty("database.database-name"));
        ds.addDataSourceProperty("user", env.getProperty("database.user"));
        if (StringUtils.isNotEmpty(env.getProperty("database.password"))) {
            ds.addDataSourceProperty("password", env.getProperty("database.password"));
        }
        return ds;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.arthur.webnovel.entity");
        builder.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");

        if (env.acceptsProfiles("dev")) {
            builder.setProperty("hibernate.show_sql", "true");
            builder.setProperty("hibernate.format_sql", "true");
        }

        return builder.buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
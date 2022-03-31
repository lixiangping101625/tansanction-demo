//package com.distribute.tansanctiondemo.config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration(proxyBeanMethods = false)
//public class MyDataSourcesConfiguration {
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("app.datasource.first")
//    public DataSourceProperties firstDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @Primary
//    @ConfigurationProperties("app.datasource.first.configuration")
//    public HikariDataSource firstDataSource(DataSourceProperties firstDataSourceProperties) {
//        return firstDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    @ConfigurationProperties("app.datasource.second")
//    public DataSourceProperties secondDataSourceProperties() {
//        return new DataSourceProperties();
//    }
//
//    @Bean
//    @ConfigurationProperties("app.datasource.second.configuration")
//    public HikariDataSource secondDataSource( @Qualifier("secondDataSourceProperties") DataSourceProperties secondDataSourceProperties) {
//        return secondDataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//    }
//
//}
//

package com.example.ykqh.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 杨昆
 * @date 2021/6/17 9:26
 * @describe 数据库连接池配置
 */
@Slf4j
@Configuration
@MapperScan(value = {"com.example.ykqh.dao"})
public class DataSourceConfig {
    private static Properties pro;

    /**
     * 读取配置文件
     */
    public DataSourceConfig() throws IOException {
        pro = new Properties();
        InputStream in = DataSourceConfig.class.getClassLoader().getResourceAsStream("application.properties");
        pro.load(in);
    }

    /**
     * 配置数据库连接池
     */
    @Bean(name = "ykDataSource")
    public DataSource dataSource(){
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(pro.getProperty("spring.datasource.hikari.driver-class-name"));
        hikariDataSource.setJdbcUrl(pro.getProperty("spring.datasource.hikari.jdbc-url"));
        hikariDataSource.setUsername(pro.getProperty("spring.datasource.hikari.username"));
        hikariDataSource.setPassword(pro.getProperty("spring.datasource.hikari.password"));
        hikariDataSource.setPoolName(pro.getProperty("spring.datasource.hikari.pool-name"));
        hikariDataSource.setMaximumPoolSize(10);
        hikariDataSource.setMinimumIdle(8);
        hikariDataSource.setConnectionTimeout(60000);
        hikariDataSource.setConnectionTestQuery("select 1");
        hikariDataSource.setMaxLifetime(1800000);
        hikariDataSource.setIdleTimeout(600000);
        return hikariDataSource;
    }

    /**
     * 配置数据库会话工厂
     */
    @Bean(name = "ykSqlSessionFactory")
    public SqlSessionFactoryBean ykSqlSessionFactory(@Qualifier("ykDataSource") DataSource dataSource){
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        // 设置数据库
        sessionFactoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 查询返回map时，不会忽略为空的字段，依然能get到这些key，不过value为null
        configuration.setCallSettersOnNulls(true);
        // 自动将以下画线方式命名的数据库列映射到 Java 对象的驼峰式命名属性中
        configuration.setMapUnderscoreToCamelCase(true);
        sessionFactoryBean.setConfiguration(configuration);
        return sessionFactoryBean;
    }

    /**
     * 配置事务管理（可自定义获取事务、提交、回滚节点配置）
     */
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("ykDataSource") DataSource dataSource){
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}
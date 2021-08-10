package com.example.database.config;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;

import com.example.database.dds.DynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.support.SimpleBeanDefinitionRegistry;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * Mybatis配置
 * @author Louis
 * @date Oct 31, 2018
 */
@Configuration
@MapperScan(basePackages = {"com.example.**.dao"}) // 扫描DAO
public class MybatisConfig implements ApplicationContextAware {

    ApplicationContext applicationContext;
//    @Bean("mysmart")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.mysmart")
//    public DataSource mysmart() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean("myresource")
//    @ConfigurationProperties(prefix = "spring.datasource.myresource")
//    public DataSource myresource() {
//        return DataSourceBuilder.create().build();
//    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
//        RootBeanDefinition definition = new RootBeanDefinition();
//        definition.setBeanClass();
//        SimpleBeanDefinitionRegistry beanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
//        beanDefinitionRegistry.registerBeanDefinition("mysmart", definition);
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        String[] beanNames = applicationContext.getBeanNamesForType(HikariDataSource.class);
        for (String beanName : beanNames) {
            dataSourceMap.put(beanName, applicationContext.getBean(beanName));
        }
        // 将 master 数据源作为默认指定的数据源
        dynamicDataSource.setDefaultDataSource(applicationContext.getBean(beanNames[0]));
        // 将 master 和 slave 数据源作为指定的数据源
        dynamicDataSource.setDataSources(dataSourceMap);
        return dynamicDataSource;
    }

//    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource());
        sessionFactory.setTypeAliasesPackage("com.example.database.entity");    // 扫描Model
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/dao/*.xml"));    // 扫描映射文件
        return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}
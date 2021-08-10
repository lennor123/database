package com.example.database.config;

import com.example.database.dao.LoginDao;
import com.example.database.dds.DynamicDataSource;
import com.example.database.entity.Repository;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Configuration
//@AutoConfigureOrder(1)
public class DatabaseConfig implements InitializingBean, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    ApplicationContext applicationContext;

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    LoginDao loginDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        SimpleBeanDefinitionRegistry simpleBeanDefinitionRegistry = new SimpleBeanDefinitionRegistry();
        List<Repository> repositorys = loginDao.findInfo();
        for (int i = 0; i < repositorys.size(); i++) {
            String host = repositorys.get(i).getHost();
            String port = repositorys.get(i).getPort();
            String databaseName = repositorys.get(i).getDatabaseName();
            String userName = repositorys.get(i).getUser();
            String password = repositorys.get(i).getKey();
            String databasekey = host + port + databaseName + userName + password;
            String jdbcurl = "jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition("com.zaxxer.hikari.HikariDataSource");
            if (i == 0) {
                rootBeanDefinition.setPrimary(true);
            }
            MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
            PropertyValue propertyValues1 = new PropertyValue("password", password);
            PropertyValue propertyValues2 = new PropertyValue("username", userName);
            PropertyValue propertyValues3 = new PropertyValue("jdbcUrl", jdbcurl);
            PropertyValue propertyValues4 = new PropertyValue("driverClassName", "com.mysql.cj.jdbc.Driver");
            mutablePropertyValues.addPropertyValue(propertyValues1);
            mutablePropertyValues.addPropertyValue(propertyValues2);
            mutablePropertyValues.addPropertyValue(propertyValues3);
            mutablePropertyValues.addPropertyValue(propertyValues4);
//            ConfigurableListableBeanFactory configurableListableBeanFactory = (ConfigurableListableBeanFactory) applicationContext;
//            configurableListableBeanFactory.registerSingleton(databasekey, "123");
            rootBeanDefinition.setPropertyValues(mutablePropertyValues);
            defaultListableBeanFactory.registerBeanDefinition(databaseName, rootBeanDefinition);
//            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(rootBeanDefinition, databasekey);
//            BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, simpleBeanDefinitionRegistry);
        }
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(2);
        String[] beanNames = applicationContext.getBeanNamesForType(HikariDataSource.class);
        for (String beanName : beanNames) {
            dataSourceMap.put(beanName, applicationContext.getBean(beanName));
        }
        dynamicDataSource.setDefaultDataSource(applicationContext.getBean(beanNames[0]));
        dynamicDataSource.setDataSources(dataSourceMap);
        defaultListableBeanFactory.registerSingleton("dynamicDataSource", dynamicDataSource);
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition("org.mybatis.spring.SqlSessionFactoryBean");
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        PropertyValue propertyValues1 = new PropertyValue("dataSource", dynamicDataSource);
        PropertyValue propertyValues2 = new PropertyValue("typeAliasesPackage", "com.example.database.entity");
        PropertyValue propertyValues3 = new PropertyValue("mapperLocations", resolver.getResources("classpath*:**/dao/*.xml"));
        mutablePropertyValues.addPropertyValue(propertyValues1);
        mutablePropertyValues.addPropertyValue(propertyValues2);
        mutablePropertyValues.addPropertyValue(propertyValues3);
        rootBeanDefinition.setPropertyValues(mutablePropertyValues);
        rootBeanDefinition.setPrimary(true);
        defaultListableBeanFactory.registerSingleton("sqlSessionFactoryBean", rootBeanDefinition);
        defaultListableBeanFactory.registerSingleton("transactionManager", new DataSourceTransactionManager(dynamicDataSource));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

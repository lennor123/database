package com.example.database.config;

import com.example.database.dao.LoginDao;
import com.example.database.dds.DynamicDataSource;
import com.example.database.entity.Repository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@AutoConfigureOrder(1)
public class DatabaseConfig2 implements BeanDefinitionRegistryPostProcessor {

    ApplicationContext applicationContext;

    @Autowired
    DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    LoginDao loginDao;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        List<Repository> repositorys = loginDao.findInfo();
        List<Repository> repositorys = new ArrayList<Repository>();
        Repository repository = new Repository();
        repository.setHost("192.168.1.209");
        repository.setPort("3306");
        repository.setDatabaseName("mysmart");
        repository.setUser("root");
        repository.setKey("root");
        Repository repository2 = new Repository();
        repository2.setHost("192.168.1.209");
        repository2.setPort("3306");
        repository2.setDatabaseName("myresource");
        repository2.setUser("root");
        repository2.setKey("root");
        repositorys.add(repository);
        repositorys.add(repository2);
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
            rootBeanDefinition.setPropertyValues(mutablePropertyValues);
            BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(rootBeanDefinition, databaseName);
            BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

}

package com.pms.qms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:persistence-sqlqms-db.properties"})
@EnableJpaRepositories(
        basePackages = {"com.pms.qms.company.repository"},
        entityManagerFactoryRef ="pmsEntityManager",
        transactionManagerRef = "pmsTransactionManager"
)
public class SqlQmsDBConfig {

    @Autowired
    private Environment env;

    public SqlQmsDBConfig() {
        super();
    }

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean pmsEntityManager(){
        LocalContainerEntityManagerFactoryBean em=new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(pmsDataSource());
        em.setPackagesToScan(new String[] {"com.pms.qms.company.entities"});

        HibernateJpaVendorAdapter vendorAdapter=new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String,Object> properties=new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Primary
    @Bean
    public DataSource pmsDataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("payroll.jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.user"));
        dataSource.setPassword(env.getProperty("jdbc.pass"));
        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager pmsTransactionManager(){
        JpaTransactionManager transactionManager=new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(pmsEntityManager().getObject());
        return transactionManager;
    }
}

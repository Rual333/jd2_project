package by.it.academy.cv.data;

import com.mysql.cj.jdbc.Driver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "by.it.academy.cv")
@EnableTransactionManagement
@PropertySource(value="classpath:datasource.properties")
public class DaoConfiguration {

    @Autowired
    Environment environment;

    @Bean
    public DataSource dataSource() {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(environment.getProperty("datasource.url"));
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUsername(environment.getProperty("datasource.username"));
        dataSource.setPassword(environment.getProperty("datasource.password"));
        dataSource.setInitialSize(20);
        dataSource.setMaxTotal(30);
        return dataSource;


//        HikariConfig config = new HikariConfig();
//        config.setJdbcUrl( "jdbc:mysql://localhost:3306/curriculum_vitaes?" +
//                "serverTimezone=UTC&" +
//                "createDatabaseIfNotExist=true&" +
//                "useUnicode=true&characterEncoding=UTF-8" );
//        config.setUsername( "root" );
//        config.setPassword( "root" );
//        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
//        HikariDataSource dataSource = new HikariDataSource(config);
//        dataSource.setDriverClassName(Driver.class.getName());
//        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("by.it.academy");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql","true");
        properties.setProperty("hibernate.format_sql","true");
        properties.setProperty("hibernate.enable_lazy_load_no_trans","true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }
}

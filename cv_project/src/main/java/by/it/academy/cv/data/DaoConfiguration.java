package by.it.academy.cv.data;

import by.it.academy.cv.model.Gender;
import by.it.academy.cv.model.JobCandidate;
import by.it.academy.cv.model.JobCandidateContact;
import by.it.academy.cv.model.Technology;
import by.it.academy.cv.service.JobCandidateService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
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

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl( "jdbc:mysql://localhost:3306/curriculum_vitaes?" +
                "serverTimezone=UTC&" +
                "createDatabaseIfNotExist=true&" +
                "useUnicode=true&characterEncoding=UTF-8" );
        config.setUsername( "root" );
        config.setPassword( "root" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        return new HikariDataSource(config);
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean localSessionFactoryBean =
                new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(dataSource);
        localSessionFactoryBean.setAnnotatedClasses(
                Gender.class,
                JobCandidate.class,
                JobCandidateContact.class,
                JobCandidateService.class,
                Technology.class
        );
        Properties properties = new Properties();
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.enable_lazy_load_no_trans","true");
        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }
}

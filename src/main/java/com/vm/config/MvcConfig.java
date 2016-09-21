package com.vm.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableWebMvc 
@Configuration
@EnableAsync
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.vm.controller","com.vm.dao","com.vm.service"
		})
@PropertySource(value={"classpath:jdbc.properties"})
public class MvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
    Environment env;
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();
        bean.setPrefix("/WEB-INF/views/");
        bean.setSuffix(".jsp");
        return bean;
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
        resource.setBasename("classpath:messages");
        resource.setDefaultEncoding("UTF-8");
        return resource;
    }

    @Bean(name={"dataSource"},destroyMethod="close")
	  public DataSource getDataSource() {
	    HikariConfig config = new HikariConfig();
	    config.setJdbcUrl(this.env.getRequiredProperty("db.url"));
	    config.setDriverClassName(this.env.getRequiredProperty("db.driver"));
	    config.setUsername(this.env.getRequiredProperty("db.username"));
	    config.setPassword(this.env.getRequiredProperty("db.password"));

	    config.setConnectionTestQuery(this.env.getRequiredProperty("db.hikari.jdbc.validationQuery"));
	    config.setAutoCommit(Boolean.getBoolean(this.env.getRequiredProperty("db.hikari.autoCommit")));
	    config.setMaximumPoolSize(Integer.parseInt(this.env.getRequiredProperty("db.hikari.maximumPoolSize")));
	    config.setMinimumIdle(Integer.parseInt(this.env.getRequiredProperty("db.hikari.minimumIdle")));
	    config.setIdleTimeout(Long.parseLong(this.env.getRequiredProperty("db.hikari.idleTimeout")));
	    config.setMaxLifetime(Long.parseLong(this.env.getRequiredProperty("db.hikari.maxLifetime")));
	    config.setJdbc4ConnectionTest(Boolean.getBoolean(this.env.getRequiredProperty("db.hikari.jdbc4ConnectionTest")));
	    config.setPoolName(this.env.getRequiredProperty("db.hikari.poolName"));
	    config.addDataSourceProperty("cachePrepStmts", true);
	    config.addDataSourceProperty("useServerPrepStmts", true);
	    
	    return new HikariDataSource(config);
	  }

	  private Properties getHibernateProperties() {
	    Properties properties = new Properties();
	    properties.put("hibernate.show_sql", this.env.getRequiredProperty("hibernate.show_sql"));
	    properties.put("hibernate.dialect", this.env.getRequiredProperty("hibernate.dialect"));
	    properties.put("hibernate.hbm2ddl.auto", this.env.getRequiredProperty("hibernate.hbm2ddl.auto"));
	    properties.put("hibernate.cache.provider_class", this.env.getRequiredProperty("hibernate.cache.provider_class"));
	    properties.put("hibernate.cache.region.factory_class", this.env.getRequiredProperty("hibernate.cache.region.factory_class"));
	    properties.put("hibernate.cache.use_second_level_cache", this.env.getRequiredProperty("second_level"));
	    properties.put("hibernate.cache.use_query_cache", true);
	    return properties;
	  }
	  
	  @Autowired
	  @Bean(name={"sessionFactory"})//,destroyMethod="close"
	  public SessionFactory getSessionFactory(DataSource dataSource) {
	    LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
	    sessionBuilder.addProperties(getHibernateProperties());
        sessionBuilder.scanPackages("com.vm.domain");
	    return sessionBuilder.buildSessionFactory();
	  }

	  @Autowired
	  @Bean(name={"transactionManager"})//,destroyMethod="close"
	  public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
	    HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
	    return transactionManager;
	  }
 
	 
}

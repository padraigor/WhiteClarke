package org.porourke.springinaction.firstproject.ch11hibernate;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

public class Chapter11 {
	
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan(new String[] {"com.porourke.springinaction.firstproject.ch11hibernate"});
		
		Properties props = new Properties();
		props.setProperty("dialect", "org.hibernate.dialeact.H2Dialect");
		sfb.setHibernateProperties(props);
		
		return sfb;
	}
	
	@Bean
	public HibernateSpitterRespository(SessionFactory sessionFactory) {
		
	}
}

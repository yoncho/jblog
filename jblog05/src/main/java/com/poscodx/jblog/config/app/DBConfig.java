package com.poscodx.jblog.config.app;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DBConfig {
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mariadb://192.168.0.181:3307/jblog?chraset=utf8");
		dataSource.setUsername("jblog1");
		dataSource.setPassword("jblog");
		return dataSource;
	}
}

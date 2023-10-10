package com.poscodx.jblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.poscodx.jblog.config.app.DBConfig;
import com.poscodx.jblog.config.app.MyBatisConfig;
import com.poscodx.jblog.event.SpringContextEventListener;

@Configuration
@EnableAspectJAutoProxy //<aop:aspectj-autoproxy/>
@ComponentScan(basePackages={"com.poscodx.jblog.repository","com.poscodx.jblog.service","com.poscodx.jblog.security" })
@Import({DBConfig.class, MyBatisConfig.class})
public class AppConfig {
	
	//<bean class="com.poscodx.jblog.event.SpringContextEventListener" />
	@Bean
	public SpringContextEventListener springContextEventListener() {
		return new SpringContextEventListener();
	}
}

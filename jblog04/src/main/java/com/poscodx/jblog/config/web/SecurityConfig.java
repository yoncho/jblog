package com.poscodx.jblog.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.poscodx.jblog.interceptor.AdminInterceptor;
import com.poscodx.jblog.security.AuthUserHandlerMethodArgumentResolver;
import com.poscodx.jblog.security.LoginInterceptor;
import com.poscodx.jblog.security.LogoutInterceptor;

@Configuration
@EnableWebMvc
public class SecurityConfig implements WebMvcConfigurer {
/*
 * 1) argument resolver 
 * 2) addArgumentResolvers
 * 
 * 3) interceptor
 * 4) addIntercpetors
 * */
	
	//1
	@Bean
	public HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}
	//2
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(handlerMethodArgumentResolver());
	}
	
	@Bean
	public HandlerInterceptor loginInterceptor() {
		return new LoginInterceptor();
	}
	@Bean
	public HandlerInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}
	@Bean
	public HandlerInterceptor adminInterceptor() {
		return new AdminInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor())
			.addPathPatterns("/user/auth");
		registry.addInterceptor(logoutInterceptor())
			.addPathPatterns("/user/logout");
		registry.addInterceptor(adminInterceptor())
			.addPathPatterns("/**/admin/**");
	}
	
}

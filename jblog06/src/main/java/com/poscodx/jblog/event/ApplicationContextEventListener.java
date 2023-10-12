package com.poscodx.jblog.event;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import com.poscodx.jblog.vo.BlogVo;

public class ApplicationContextEventListener {
	@Autowired
	private ApplicationContext applicationContext;
	
	@EventListener({ContextRefreshedEvent.class})
	public void handlerContextRefreshedEvent() {
		System.out.println("--- Context Refreshed Event Received : ---" + applicationContext);
		
		
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(BlogVo.class);
		
		AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry)factory;
		
		registry.registerBeanDefinition("blog", beanDefinition);		
	}
	
}

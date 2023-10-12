package com.poscodx.jblog.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

import com.poscodx.jblog.vo.BlogVo;

public class SpringContextEventListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() != null) {
			System.out.println("SpringContext Refreshed [sub]");
			ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) event.getApplicationContext();
			
			BlogVo blog = new BlogVo();
			applicationContext.getBeanFactory().registerSingleton("blog", blog);
		}else {
			System.out.println("SpringContext Refreshed [root]");
		}
	}

}

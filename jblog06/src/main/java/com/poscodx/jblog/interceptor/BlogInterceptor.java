package com.poscodx.jblog.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.service.BlogService;

public class BlogInterceptor implements HandlerInterceptor {
	@Autowired
	private BlogService blogService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		String blogId = uri.split("/")[2];
		if(!blogService.checkBlogExist(blogId)) {
			response.sendRedirect(request.getContextPath());
			return false;
		}
		return true;
	}
	
}

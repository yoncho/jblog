package com.poscodx.jblog.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;

import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationContext applicaionContext;
	
	/*
	 * LoginInterceptor는 사용자가 /user/login 으로 접속 시 Interceptor로 먼저 들어옴.
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		UserVo authUser = userService.getUser(id, password);
		System.out.println(authUser);
		
		//사용자가 입력한 정보가 없을 경우..
		if(authUser==null) {
			request.setAttribute("id", id);
			request
				.getRequestDispatcher("/WEB-INF/views/user/login.jsp")
				.forward(request, response);
			return false;
		}
		
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		return false;
	}
	
	
	 
	
	
}

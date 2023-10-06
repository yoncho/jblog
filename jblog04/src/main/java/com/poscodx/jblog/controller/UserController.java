package com.poscodx.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.poscodx.jblog.security.LoginValidator;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.UserService;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private LoginValidator loginValidator;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}
	//Login Post는 Interceptor에서 진행
	
	@RequestMapping("/join")
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}
	
	@RequestMapping("/joinsuccess")
	public String joinsuccess(@ModelAttribute @Valid UserVo vo, BindingResult result, Model model) {
		System.out.println(vo);
		loginValidator.validate(vo,result);  
		if(result.hasErrors()) {  
			model.addAllAttributes(result.getModel());
			return "user/join";  
		}  
		
		//user join & create blog & default category
		userService.join(vo);
		blogService.insertById(vo.getId());
		CategoryVo category = new CategoryVo("(미지정)","기본 카테고리입니다. (삭제 불가능)",vo.getId());
		categoryService.insert(category);
		return "user/joinsuccess";
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
}

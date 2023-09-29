package com.poscodx.jblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
public class MainController {
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/")
	public String main(
			@AuthUser UserVo vo, 
			Model model) {
		List<BlogVo> list = blogService.findAll();
		model.addAttribute("list", list);
		return "main/index";
	}
}

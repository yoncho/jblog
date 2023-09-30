package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
			String keyword,
			String which,
			Model model) {
		System.out.println("##################################");
		System.out.println(keyword +"|"+which);
		List<BlogVo> list = new ArrayList();

		if(keyword != null && !keyword.isEmpty()) {
			//keyword search
			switch(which) {
			case "blog-title":
				list = blogService.findByTitleKeyword(keyword);
				break;
			case "tag":
				list = null;
				break;
			case "blog-user":
				list = blogService.findByUserKeyword(keyword);
				break;
			}
		}else {
			//default 
			list = blogService.findAll();
		}
		
		
		model.addAttribute("list", list);
		return "main/index";
	}
}

package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
public class MainController {
	private String DEFAULT_CHECKED_RADIO = "blog-title";
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping("/")
	public String main(
			@AuthUser UserVo vo, 
			@RequestParam Optional<String> keyword,
			@RequestParam Optional<String> which,
			Model model) {
		List<BlogVo> list = new ArrayList<BlogVo>();
		
		if(keyword.isPresent() && which.isPresent()) {
			//keyword search
			switch(which.get()) {
			case "blog-title":
				list = blogService.findByTitleKeyword(keyword.get());
				break;
			case "tag":
				list = null;
				break;
			case "blog-user":
				list = blogService.findByUserKeyword(keyword.get());
				break;
			}
		}else {
			//default 
			list = blogService.findAll();
			which = Optional.of(DEFAULT_CHECKED_RADIO);
			keyword = Optional.of("");
		}
		
		model.addAttribute("which", which.get());
		model.addAttribute("keyword", keyword.get());
		model.addAttribute("list", list);
		return "main/index";
	}
}

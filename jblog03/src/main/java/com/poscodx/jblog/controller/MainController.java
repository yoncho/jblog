package com.poscodx.jblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.security.AuthUser;
import com.poscodx.jblog.vo.UserVo;

@Controller
public class MainController {
	@RequestMapping("/")
	public String main(@AuthUser UserVo vo, Model model) {
		return "main/index";
	}
}

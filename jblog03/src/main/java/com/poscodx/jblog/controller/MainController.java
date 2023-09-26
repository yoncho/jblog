package com.poscodx.jblog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.vo.UserVo;

@Controller
public class MainController {
	@RequestMapping("/")
	public String main(HttpSession session, Model model) {
		UserVo userVo = (UserVo)session.getAttribute("authUser");
		model.addAttribute("authUser", userVo);
		return "main/index";
	}
}

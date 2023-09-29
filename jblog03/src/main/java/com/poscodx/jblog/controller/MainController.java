package com.poscodx.jblog.controller;

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
	public String main(@AuthUser UserVo vo, Model model) {
		List<BlogVo> list = blogService.findAll();
		BlogVo[][] blogArray = listToTDArray(list);
		model.addAttribute("blogarray", blogArray);
		model.addAttribute("list", list);
		return "main/index";
	}
	
	private BlogVo[][] listToTDArray(List<BlogVo> list){
		int col = 3;
		int totalCount = list.size();
		int row = (int) Math.ceil((double)totalCount / (double)col);
		System.out.println("##############################");
		System.out.println(totalCount + "|"+row);
		int startIndex = 0;
		BlogVo[][] result = new BlogVo[row][col];
		
		for(int r = 0; r < row; r++) {
			for(int c = 0; c < col; c++) {
				result[r][c] = list.get(startIndex);
				startIndex++;
				if(startIndex == totalCount) {
					return result;
				}
			}
		}
		return result;
	}
}

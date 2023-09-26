package com.poscodx.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}") //assets이라 쳐도 들어오니까 정적 리소스 예외처리 해놓고,, 해야함 path variable 정규식 사용하기!
public class BlogController {
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	
	//path valiable 
	@RequestMapping({"", "/{categoryNo}", "/{categoryNo}/{postNo}"})
	public String index(
			@PathVariable("id") String blogId,
			@PathVariable("categoryNo") Optional<Long> categoryNo, //null일때 다루는 optional을 알아서...@Optional같은?게 있지 않을까?
			@PathVariable("postNo") Optional<Long> postNo,
			Model model) {
		System.out.println("#############################hello " + blogId);
		if(categoryNo.isPresent()) {
			System.out.println("categoryNo is " + categoryNo.get());
		}
		
		
		
		//category list
		List<CategoryVo> categoryList = categoryService.findAllById(blogId);
		//post list
		List<PostVo> postList = postService.findAllById(blogId);
				
				
		//model binding
		model.addAttribute("postlist", postList);
		model.addAttribute("categorylist", categoryList);
		
		return "blog/main";
	}
	
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId) {
		return "blog/admin-basic";
	}
}

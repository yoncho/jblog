package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}") // assets이라 쳐도 들어오니까 정적 리소스 예외처리 해놓고,, 해야함 path variable 정규식 사용하기!
public class BlogController {
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BlogService blogService;

	//MAIN START
	@RequestMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
			@PathVariable("id") String blogId, 
			@PathVariable("categoryNo") Optional<Long> categoryNo, // null일때																								// 않을까?
			@PathVariable("postNo") Optional<Long> postNo, Model model) {
		// category list
		List<CategoryVo> categoryList = categoryService.findAllById(blogId);
		// post list
		List<PostVo> postList = new ArrayList();
		PostVo currentPost = new PostVo();
		// blog
		BlogVo blog = blogService.findById(blogId);

		if (categoryNo.isPresent() && postNo.isPresent()) {
			// categoryNo와 postNo 둘다 있는 경우
			postList = postService.findAllByCategory(blogId, categoryNo.get());
			currentPost = postService.findByNo(postNo.get());
		} else if (categoryNo.isPresent() && !postNo.isPresent()) {
			// categoryNo만 있는 경우
			postList = postService.findAllByCategory(blogId, categoryNo.get());
			currentPost = postList.get(0);
			model.addAttribute("currentCategory", categoryNo.get());
		} else {
			// default
			postList = postService.findAllById(blogId);
			currentPost = postList.get(0);
		}

		// model binding
		model.addAttribute("postlist", postList);
		model.addAttribute("categorylist", categoryList);
		model.addAttribute("currentPost", currentPost);
		model.addAttribute("blogVo", blog);
		return "blog/main";
	}
	//MAIN END
	
	//BASIC START
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		BlogVo blog = blogService.findById(blogId);
		model.addAttribute("blogId", blogId);
		model.addAttribute("blogVo", blog);
		return "blog/admin-basic";
	}
	//BASIC END

	//CATEGORY START
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId, Model model) {
		// category list
		List<CategoryVo> categoryList = categoryService.findAllById(blogId);
		model.addAttribute("categorylist", categoryList);
		model.addAttribute("blogId", blogId);
		return "blog/admin-category";
	}
	
	@RequestMapping("/admin/category/write")
	public String addCategory(
			@PathVariable("id") String blogId,
			@ModelAttribute CategoryVo category) {
		System.out.println("###################################" + category);
		categoryService.insert(category);
		return "redirect:/"+blogId+"/admin/category";
	}
	
	@RequestMapping("/admin/category/delete/{no}")
	public String deleteCategory(
			@PathVariable("id") String blogId,
			@PathVariable("no") int categoryNo) {
		System.out.println("######################################" + categoryNo);
		
		//1. delete post by category
//		postService.deleteByCategoryNo();
		//2. delete category
		categoryService.deleteByNo(categoryNo);
		
		return "redirect:/"+blogId+"/admin/category";
	}
	//CATEGORY END
	
	
	@RequestMapping("/admin/write")
	public String adminWrite(
			@PathVariable("id") String blogId, 
			Model model) {
		
		
		model.addAttribute("blogId", blogId);
		
		
		return "redirect:/"+blogId+"/admin/write";
	}
}

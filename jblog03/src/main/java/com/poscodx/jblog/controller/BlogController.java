package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.poscodx.jblog.service.BlogService;
import com.poscodx.jblog.service.CategoryService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}") // assets이라 쳐도 들어오니까 정적 리소스 예외처리 해놓고,, 해야함 path variable 정규식 사용하기!
public class BlogController {
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BlogService blogService;
	// MAIN START
	@RequestMapping({ "", "/{categoryNo}", "/{categoryNo}/{postNo}" })
	public String index(
			@PathVariable("id") String blogId, 
			@PathVariable("categoryNo") Optional<Long> categoryNo,																								// 않을까?
			@PathVariable("postNo") Optional<Long> postNo,
			HttpSession session,
			Model model) {
		List<CategoryVo> categoryList = categoryService.findAllById(blogId);
		List<PostVo> postList = new ArrayList();
		PostVo currentPost = new PostVo();
		BlogVo blog = blogService.findById(blogId);
		boolean isAdmin = false;
		
		if (categoryNo.isPresent() && postNo.isPresent()) {
			// categoryNo와 postNo 둘다 있는 경우
			postList = postService.findAllByCategory(blogId, categoryNo.get());
			currentPost = postService.findByNo(postNo.get());
		} else if (categoryNo.isPresent() && !postNo.isPresent()) {
			// categoryNo만 있는 경우
			postList = postService.findAllByCategory(blogId, categoryNo.get());
			if(postList.size() > 0 ) {
				currentPost = postList.get(0);
			}
			model.addAttribute("currentCategory", categoryNo.get());
		} else {
			// default
			postList = postService.findAllById(blogId);
			System.out.println("##########################" + postList);
			if(postList.size() > 0) {
				currentPost = postList.get(0);
			}
		}
		// check admin
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser != null && (authUser.getId()).equals(blogId))
		{
			isAdmin = true;
		}
		
		// model binding
		model.addAttribute("postlist", postList);
		model.addAttribute("categorylist", categoryList);
		model.addAttribute("currentPost", currentPost);
		model.addAttribute("blogVo", blog);
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("authUser", authUser);
		return "blog/main";
	}
	// MAIN END

	// BASIC START
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		System.out.println("##################################");
		System.out.println("controller");
		BlogVo blog = blogService.findById(blogId);
		
		model.addAttribute("blogId", blogId);
		model.addAttribute("blogVo", blog);
		return "blog/admin-basic";
	}
	
	@RequestMapping("/admin/basic/update")
	public String adminBasciUpdate(
			@PathVariable("id") String blogId,
			@PathVariable("title") String title,
			@RequestParam("logo-file") MultipartFile file) {
		System.out.println("#################################" + file.getOriginalFilename());
		
		String imageUrl = "";
		//Blog Vo
		BlogVo blog = new BlogVo();
		blog.setBlogId(blogId);
		blog.setTitle(title);
		blog.setImage(imageUrl);
		
		
		return "redirect:/"+blogId+"/admin/basic";
	}
	// BASIC END

	// CATEGORY START
	@RequestMapping("/admin/category")
	public String adminCategory(@PathVariable("id") String blogId, Model model) {
		// category list
		List<CategoryVo> categoryList = categoryService.findAllById(blogId);
		model.addAttribute("categorylist", categoryList);
		model.addAttribute("blogId", blogId);
		return "blog/admin-category";
	}

	@RequestMapping("/admin/category/write")
	public String addCategory(@PathVariable("id") String blogId, @ModelAttribute CategoryVo category) {
		System.out.println("###################################" + category);
		categoryService.insert(category);
		return "redirect:/" + blogId + "/admin/category";
	}

	@RequestMapping("/admin/category/delete/{no}")
	public String deleteCategory(@PathVariable("id") String blogId, @PathVariable("no") int categoryNo) {
		System.out.println("######################################" + categoryNo);

		// 1. delete post by category
		postService.deleteAllByCategoryNo(categoryNo);
		// 2. delete category
		categoryService.deleteByNo(categoryNo);

		return "redirect:/" + blogId + "/admin/category";
	}
	// CATEGORY END

	// WRITE START
	@RequestMapping(value="/admin/write", method=RequestMethod.GET)
	public String adminWrite(
			@PathVariable("id") String blogId, 
			String category, 
			Model model) {

		List<CategoryVo> categoryList = categoryService.findAllById(blogId);
		System.out.println("#################################" + category);
		if (category == null) {
			category = categoryList.get(0).getName();
		}
		model.addAttribute("blogId", blogId);
		model.addAttribute("categorylist", categoryList);
		
		model.addAttribute("category", category);
		return "blog/admin-write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String adminWrite(
			@PathVariable("id") String blogId, 
			String category, 
			@ModelAttribute PostVo post, 
			Model model) {
		
		int categoryNo = categoryService.findNoByName(category);
		post.setCategoryNo(categoryNo);
		System.out.println("######################" + post);
		postService.insert(post);
		return "redirect:/" + blogId + "/"+ categoryNo;
	}
	// WRITE END
}

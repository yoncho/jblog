package com.poscodx.jblog.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import com.poscodx.jblog.service.FileUploadService;
import com.poscodx.jblog.service.PostService;
import com.poscodx.jblog.vo.BlogVo;
import com.poscodx.jblog.vo.CategoryVo;
import com.poscodx.jblog.vo.PostVo;
import com.poscodx.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}") // assets이라 쳐도 들어오니까 정적 리소스 예외처리 해놓고,, 해야함 path variable 정규식 사용하기!
public class BlogController {
	private String DEFAULT_IMAGE_PATH = "/assets/images/default_img.png";
	
	@Autowired
	private PostService postService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private ApplicationContext applicationContext;
	
	private boolean isCheckedCategory = false;
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
		//applicationBlog
		BlogVo applicationBlog = (BlogVo)applicationContext.getBean("blog");
		BlogVo userBlog = blogService.findById(blogId);
		BeanUtils.copyProperties(userBlog, applicationBlog);
		
		boolean isAdmin = false;
		
		//1. Category 선택되었는지 확인
		if (categoryNo.isPresent() && !postNo.isPresent()) {
			isCheckedCategory = true;
			model.addAttribute("selectedCategoryNo", categoryNo.get());
		} else if(!categoryNo.isPresent() && !postNo.isPresent()){
			isCheckedCategory = false;
		}
		
		//2. Category 선택 유무에 따라 PostList 표시 범위 변경
		if(isCheckedCategory) {
			postList = postService.findAllByCategory(blogId, categoryNo.get());
		}else {
			postList = postService.findAllById(blogId);
			
		}
		
		//3. postNo가 입력된 경우 해당 post를 main화면에 표시
		if(postNo.isPresent()) {
			currentPost = postService.findByNo(blogId, postNo.get());
		}else {
			currentPost = postList.size() > 0 ? postList.get(0):null;
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
		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("authUser", authUser);
		return "blog/main";
	}
	// MAIN END

	// BASIC START
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String blogId, Model model) {
		BlogVo blog = blogService.findById(blogId);
		
		model.addAttribute("blogId", blogId);
		model.addAttribute("blogVo", blog);
		return "blog/admin-basic";
	}
	
	@RequestMapping("/admin/basic/update")
	public String adminBasciUpdate(
			@PathVariable("id") String blogId,
			@RequestParam("title") String title,
			@RequestParam("logo-file") MultipartFile file) {	
		String imageUrl = DEFAULT_IMAGE_PATH;
		
		if(!file.isEmpty()) {
			imageUrl = fileUploadService.restore(file);
		}
		
		//Blog Vo
		BlogVo blog = new BlogVo();
		blog.setBlogId(blogId);
		blog.setTitle(title);
		blog.setImage(imageUrl);
		
		//applicationBlog
		BlogVo applicationBlog = (BlogVo)applicationContext.getBean("blog");
		BeanUtils.copyProperties(blog, applicationBlog);
		blogService.updateBlog(blog);
		
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
		categoryService.insert(category);
		return "redirect:/" + blogId + "/admin/category";
	}

	@RequestMapping("/admin/category/delete/{no}")
	public String deleteCategory(@PathVariable("id") String blogId, @PathVariable("no") int categoryNo) {
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
		
		int categoryNo = categoryService.findNoByNameAndBlogId(category, blogId);
		post.setCategoryNo(categoryNo);
		postService.insert(post);
		return "redirect:/" + blogId + "/"+ categoryNo;
	}
	// WRITE END
}

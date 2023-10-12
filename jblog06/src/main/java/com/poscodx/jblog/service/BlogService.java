package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	private String DEFAULT_IMAGE_PATH = "/assets/images/default_img.png";
	private String DEFAULT_BLOG_TITLE = "새 블로그";
	
	@Autowired
	private BlogRepository blogRepository;
	
	public BlogVo defaultBlog(String id) {
		BlogVo blog = new BlogVo();
		blog.setBlogId(id);
		blog.setImage(DEFAULT_IMAGE_PATH);
		blog.setTitle(DEFAULT_BLOG_TITLE);
		return blog;
	}
	
	public void insertById(String id) {
		blogRepository.insertById(defaultBlog(id));
	}

	public BlogVo findById(String blogId) {
		return blogRepository.findById(blogId);
	}

	public boolean checkBlogExist(String blogId) {
		return blogRepository.checkBlogExist(blogId);
	}

	public void updateBlog(BlogVo blog) {
		blogRepository.updateBlog(blog);
	}

	public List<BlogVo> findAll() {
		return blogRepository.findAll();
	}

	public List<BlogVo> findByTitleKeyword(String titleKeyword) {
		return blogRepository.findByTitleKeyword(titleKeyword);
	}

	public List<BlogVo> findByUserKeyword(String userKeyword) {
		return blogRepository.findByUserKeyword(userKeyword);
	}

}

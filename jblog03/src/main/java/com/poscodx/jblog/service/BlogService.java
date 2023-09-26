package com.poscodx.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.BlogRepository;
import com.poscodx.jblog.vo.BlogVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public BlogVo defaultBlog(String id) {
		BlogVo blog = new BlogVo();
		blog.setBlogId(id);
		blog.setImage("");
		blog.setTitle("Spring 이야기");
		return blog;
	}
	
	public void insertById(String id) {
		blogRepository.insertById(defaultBlog(id));
	}
	
	
	
}

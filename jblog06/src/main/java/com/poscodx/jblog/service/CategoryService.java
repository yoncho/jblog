package com.poscodx.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.CategoryRepository;
import com.poscodx.jblog.vo.CategoryVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public void insert(CategoryVo category) {
		categoryRepository.insert(category);
	}

	public List<CategoryVo> findAllById(String blogId) {
		return categoryRepository.findAllById(blogId);
	}

	public void deleteByNo(int categoryNo) {
		categoryRepository.deleteByNo(categoryNo);
	}

	public int findNoByNameAndBlogId(String category, String blogId) {
		return categoryRepository.findNoByNameAndBlogId(category, blogId);
	}
	
}

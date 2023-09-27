package com.poscodx.jblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poscodx.jblog.repository.PostRepository;
import com.poscodx.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;
	
	public List<PostVo> findAllById(String blogId) {
		return postRepository.findAllById(blogId);
	}

	public List<PostVo> findAllByCategory(String blogId, Long categoryNo) {
		return postRepository.findAllByCategory(blogId, categoryNo);
	}

	public PostVo findByNo(Long postNo) {
		return postRepository.findByNo(postNo);
	}

}

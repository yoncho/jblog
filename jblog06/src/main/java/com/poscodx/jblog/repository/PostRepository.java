package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.PostVo;

@Repository
public class PostRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public List<PostVo> findAllById(String blogId) {
		return sqlSession.selectList("post.findAllById", blogId);
	}

	public List<PostVo> findAllByCategory(String blogId, Long categoryNo) {
		Map<String, Object> map = new HashMap();
		map.put("id", blogId);
		map.put("category", categoryNo);
		return sqlSession.selectList("post.findAllByCategory", map);
	}

	public PostVo findByNo(String blogId, Long postNo) {
		Map<String, Object> map = new HashMap();
		map.put("id", blogId);
		map.put("postNo", postNo);
		return sqlSession.selectOne("post.findByNo", map);
	}

	public boolean deleteAllByCategoryNo(int categoryNo) {
		int count = sqlSession.delete("post.deleteAllByCategoryNo", categoryNo);
		return count == 1;
	}

	public boolean insert(PostVo post) {
		int count = sqlSession.insert("post.insert", post);
		return count == 1;
	}
}

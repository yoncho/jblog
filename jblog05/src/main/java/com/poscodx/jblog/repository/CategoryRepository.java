package com.poscodx.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	@Autowired
	private SqlSession sqlSession;

	public boolean insert(CategoryVo category) {
		int count = sqlSession.insert("category.insert", category);
		return count == 1;
	}

	public List<CategoryVo> findAllById(String blogId) {
		List<CategoryVo> list = sqlSession.selectList("category.findAllById", blogId);
		return list;
	}

	public boolean deleteByNo(int categoryNo) {
		int count = sqlSession.delete("category.deleteByNo", categoryNo);
		return count == 1;
	}

	public int findNoByNameAndBlogId(String categoryName, String blogId) {
		Map<String, Object> map = new HashMap();
		map.put("categoryName", categoryName);
		map.put("blogId", blogId);
		return sqlSession.selectOne("category.findNoByNameAndBlogId",map);
	}
	
	
}

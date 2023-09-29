package com.poscodx.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.poscodx.jblog.vo.BlogVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public boolean insertById(BlogVo blog) {
		int count = sqlSession.insert("blog.insertById", blog);
		return count == 1;
	}

	public BlogVo findById(String blogId) {
		return sqlSession.selectOne("blog.findById", blogId);
	}

	public boolean checkBlogExist(String blogId) {
		int count = sqlSession.selectOne("blog.checkBlogExist", blogId);
		return count > 0;
	}

	public void updateBlog(BlogVo blog) {
		sqlSession.update("blog.updateBlog", blog);
	}

	public List<BlogVo> findAll() {
		return sqlSession.selectList("blog.findAll");
	}

}

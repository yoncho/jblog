package com.poscodx.jblog.vo;

import javax.validation.constraints.NotEmpty;

public class CategoryVo {
	private Long no;
	@NotEmpty
	private String name;
	private String description;
	private String blogId;
	
	public CategoryVo(String name, String description, String BlogId) {
		this.name=name;
		this.description=description;
		this.blogId=blogId;
	}
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBlogId() {
		return blogId;
	}
	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", name=" + name + ", description=" + description + ", blogId=" + blogId + "]";
	}
}

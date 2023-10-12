<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<spring:eval expression="@blog" var="blog" />
<div id="header">
	<h1>${blog.title}</h1>
	<ul>
		<li><a href="${pageContext.request.contextPath}/${blog.blogId}">블로그로 이동</a></li>
	</ul>
</div>
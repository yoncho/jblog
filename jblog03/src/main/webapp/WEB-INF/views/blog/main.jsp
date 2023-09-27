<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%pageContext.setAttribute("newline", "/n");%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>${blogVo.title}</h1>
			<ul>
				<li><a href="">로그인</a></li>
				<li><a href="">로그아웃</a></li>
				<li><a href="${pageContext.request.contextPath}/${id}/admin/basic">블로그 관리</a></li>
			</ul>
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${currentPost.title}</h4>
					<p>${fn:replace(currentPost.contents, newline, "<br>")}<p>
				</div>
				<ul class="blog-list">
					<c:forEach items="${postlist}" var="post" varStatus="status">
						<li><a href="${pageContext.request.contextPath}/${id}/${post.categoryNo}/${post.no}">${post.title}</a> <span>2015/05/02</span>	</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.image}">
			</div>
		</div>

		<div id="navigation">
			<a href="${pageContext.request.contextPath}/${id}"><h2>카테고리</h2></a>
			<ul>
				<c:forEach items="${categorylist}" var="category">
					<li><a href="${pageContext.request.contextPath}/${id}/${category.no}">${category.name}</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>${blogVo.title}</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>
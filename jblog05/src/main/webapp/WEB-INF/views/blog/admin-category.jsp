<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<spring:eval expression="@blog" var="blog"/>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/blog/includes/header.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a
						href="${pageContext.request.contextPath}/${blogId}/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a
						href="${pageContext.request.contextPath}/${blogId}/admin/write">글작성</a></li>
				</ul>
				<table class="admin-cat">

					<tr>
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>
					<c:forEach items="${categorylist }" var="category"
						varStatus="status">
						<tr>
							<td>${fn:length(categorylist) - status.index}</td>
							<td>${category.name}</td>
							<td>${category.postCount }</td>
							<td>${category.description}</td>
							<td>
							<c:if test="${fn:length(categorylist) - status.index != '1'}">
								<a href="${pageContext.request.contextPath}/${blogId}/admin/category/delete/${category.no}">
								<img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a>
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<form action="${pageContext.request.contextPath}/${blogId}/admin/category/write" 
					method="post">
					<input type="hidden" name="blogId" value="${blogId}">
					<table id="admin-cat-add">
					<tr>
						<td class="t">카테고리명</td>
						<td><input type="text" name="name"> </td>
					</tr>
					<tr>
						<td class="t">설명</td>
						<td><input type="text" name="description"></td>
					</tr>
					<tr>
						<td class="s">&nbsp;</td>
						<td><input type="submit" value="카테고리 추가"></td>
					</tr>
					</table>
				</form>
			</div>
		</div>
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp"/>
	</div>
</body>
</html>
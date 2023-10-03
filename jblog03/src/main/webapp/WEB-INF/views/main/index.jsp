<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<!-- header & search -->
	<div class="center-content">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<form class="search-form" action="${pageContext.request.contextPath}">
			<fieldset>
				<input type="text" name="keyword" value="${keyword}"/> <input type="submit" value="검색" />
			</fieldset>
			<fieldset>
				<input type="radio" name="which" value="blog-title"
				<c:if test="${which == 'blog-title'}">checked</c:if>
				><label>블로그 제목</label> 
				<input type="radio" name="which" value="tag"
				<c:if test="${which == 'tag'}">checked</c:if>> <label>태그</label>
				<input type="radio" name="which" value="blog-user"
				<c:if test="${which == 'blog-user'}">checked</c:if>> <label>블로거</label>
			</fieldset>
		</form>
		
		<!-- post viewer -->
		<table>
		<c:forEach items="${list}" var="vo" varStatus="loop">
            <c:if test="${loop.index % 3 == 0}">
                <tr>
            </c:if>
           		<td style="margin:5px;padding:10px">
            	<a href="${pageContext.request.contextPath}/${vo.blogId}">
            	<img src="${pageContext.request.contextPath}${vo.image}" width="150px">    
            	</a>
            	<p>블로그 제목 : ${vo.title}</p>
            	<p>블로거 : ${vo.blogId}</p>           
            </td>
            <c:if test="${loop.index % 3 == 2 or loop.last}">
                </tr>
            </c:if>
        </c:forEach>
		</table>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="rows" value="${rows}"/>
<a href="<%=request.getContextPath()%>/listServlet" ><h2>중고장터</h2></a>
<br>
	<form action="<%=request.getContextPath() %>/searchServlet" method="post">
		검색할 단어 : <input type="text" name="word">
		<input type="submit" value="검색">
	</form>
	<hr>
	<c:forEach var="row" items="${rows }">
	<ul>
		<li>번호
			<ul>
				<li>${row.no}</li>
			</ul>
		</li>
		<li>작성자
			<ul>
				<li>${row.accountId}</li>
			</ul>
		</li>
		<li>제목
			<ul>
				<li>
					<a href="<%=request.getContextPath() %>/detailServlet?no=${row.no}">${row.title }</a>
				</li>
			</ul>
		</li>
		<li>작성일
			<ul>
				<li>${row.regist}</li>
			</ul>
		</li>
	</ul>
	<hr>
	</c:forEach>
	
	<input type="button" value="등록" onClick="location.href='/fleamarket/addServlet'">
	<input type="button" value="로그아웃" onClick="location.href='/fleamarket/logoutServlet'">
<br/>
<br/> 
Copyrightⓒ 2020 Yoon-dong-hwan, Park-ju-hyeon, Kim-Jun-Oh. All rights reserved.
</body>
</html>
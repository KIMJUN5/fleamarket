<%@page import="java.sql.Date"%>
<%@page import="java.time.LocalDate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>등록화면</title>
</head>
<body>
<a href="<%=request.getContextPath()%>/listServlet" ><h2>중고장터</h2></a>
<br>
<form action="<%= request.getContextPath() %>/addServlet" method="post">

	<c:set var="id" value="${id}"/>
		작성자 : ${id}
		<input type="hidden" name="id" value="${id}"/><br>
		제목 <input type="text" name="title"/><br>
		종류 <select name="type">
			  <option value="BUY">구매</option>
			  <option value="SELL">판매</option>
			</select><br>
		가격 <input type="text" name="price"/><br>
		<textarea rows="5" cols="40" name="note">${row.note}</textarea><br>
		작성일 : <%= LocalDate.now().toString() %>
		<input type="hidden" name="regist" value="<%= LocalDate.now().toString() %>"/>
		
		<input type="submit" value="등록"/><br>
</form>
<br/>
<br/> 
Copyrightⓒ 2020 Yoon-dong-hwan, Park-ju-hyeon, Kim-Jun-Oh. All rights reserved.
</body>
</html>
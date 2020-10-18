<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수정하기</title>
</head>
<body>
<a href="<%=request.getContextPath()%>/listServlet" ><h2>중고장터</h2></a>
<br>
<form action="<%= request.getContextPath() %>/editServlet" method="post">
	<c:set var="row" value="${row}"></c:set>
		작성자 ${row.accountId}
		<input type="hidden" name="no" value="${row.no}"/>
		<input type="hidden" name="accountId" value="${row.accountId}"/><br>
		제목 <input type="text" name="title" value="${row.title}"/><br>
		가격 <input type="text" name="price" value="${row.price}"/><br>
		<textarea rows="5" cols="40" name="note">${row.note}</textarea><br>
		<input type="submit" value="수정"/>&nbsp;&nbsp;
		<input type="button" value="목록으로" onclick="location.href='<%=request.getContextPath()%>/listServlet'">
</form>
<br/>
<br/> 
Copyrightⓒ 2020 Yoon-dong-hwan, Park-ju-hyeon, Kim-Jun-Oh. All rights reserved.
</body>
</html>
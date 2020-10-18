<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:set var="rows" value="${rows }"/>
<h2>검색한 결과</h2>
<hr>
	<c:forEach var="row" items="${rows }">
	<ul>
		<li>번호
			<ul>
				<li>${row.no }</li>
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
				<li>${row.regist }</li>
			</ul>
		</li>
	</ul>
	<hr>
	</c:forEach>
	<input type="button" value="목록으로" onclick="location.href='<%=request.getContextPath()%>/listServlet'">

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
</head>
<body>
<c:set var="row" value="${row }"/>
<a href="<%=request.getContextPath()%>/listServlet" ><h2>중고장터</h2></a>
<br>
	<hr>
	<ul>
		<li>번호
			<ul>
				<li>${row.no}</li>
			</ul>
		</li>
		<li>작성아이디
			<ul>
				<li>${row.accountId}</li>
			</ul>
		</li>
		<li>타입
			<ul>
				<li>${row.type}</li>
			</ul>
		</li>
		<li>제목
			<ul>
				<li>${row.title}</li>
			</ul>
		</li>
		<li>가격
			<ul>
				<li>${row.price}</li>
			</ul>
		</li>
		<li>내용
			<ul>
				<li>${row.note}</li>
			</ul>
		</li>
		<li>등록일
			<ul>
				<li>${row.regist }</li>
			</ul>
		</li>
	</ul>
	<input type="button" value="목록으로" onclick="location.href='<%=request.getContextPath()%>/listServlet'"/>
	<input type="button" value="수정" onclick="location.href='<%=request.getContextPath()%>/editServlet?no=${row.no}'"/>
	<input type="button" value="삭제" onclick="location.href='<%=request.getContextPath()%>/removeServlet?no=${row.no}'"/>
<br/>
<br/> 
Copyrightⓒ 2020 Yoon-dong-hwan, Park-ju-hyeon, Kim-Jun-Oh. All rights reserved.
</body>
</html>
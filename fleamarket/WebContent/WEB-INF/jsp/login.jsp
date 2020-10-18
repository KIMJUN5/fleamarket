<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<form action="<%=request.getContextPath()%>/loginServlet" method="POST">
		<h3>로그인</h3>
		아이디 : <input type="text" name="id" ><br>
		비밀번호 : <input type="password" name="password" ><br><br>
		<input type="submit" value="로그인" >
		<input type="button" value="회원가입" onClick="location.href='/fleamarket/registerServlet'">
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="linkForm" id="linkForm" method="post" style="visibility:hidden;"
	action="/member/login-processing" >
		<input type="hidden" name="userId" value="${id}" id="login-id" autocomplete="off">
		<input type="hidden" name="userPw" value="${pw}" id="login-pw"autocomplete="off">
	</form>
	<script>
		this.document.getElementById("linkForm").submit();
	</script>
</body>
</html>
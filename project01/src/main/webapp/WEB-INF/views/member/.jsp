<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
setTimeout(function() {
  	opener.location.reload();
    opener.location.href="http://localhost:8012/notice";
    self.close(); //현재창 닫기
    }, 2000); // 2초후 실행 1000당 1초
</script>
<body>
 <div class="padd" style="visibility: hidden;">
 	<h1>콜백페이지</h1>
 	<form name="callform" id="callform" method="post" action="/member/login-processing" >
 		<input type="text" name="userId" id="login-id" autocomplete="off" value="${id}">
 		<input type="password" name="userPw" id="login-pw"autocomplete="off" value="${pw}">
 	</form>
  </div>
  <script>
  this.document.getElementById("callform").submit();
  </script>
</body>
</html>
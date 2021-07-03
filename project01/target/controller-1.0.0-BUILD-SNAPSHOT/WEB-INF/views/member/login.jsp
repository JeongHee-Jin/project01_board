<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ page import= "java.io.PrintWriter"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BOARD PROJECT</title>
</head>
<link href="/resources/css/member.css" rel="stylesheet" />
<body>
<% 
String userId=null;
if(request.getAttribute("userId")!=null){
	userId=(String) request.getAttribute("userId");
}
if(userId!=null){
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('이미 로그인 되어있습니다.');");
	script.println("history.back();");
	script.println("</script>");
}
%>
	<div id="loginPage_container">
		<div id="login-form">
			<h1>로그인</h1>
			<form name="loginForm" method="post"
				action="/member/login-processing" onsubmit="login(document.loginForm); return false;">
				<div class="input_area">
					<div class="int-area login_In">
						<input type="text" name="userId" id="login-id" autocomplete="off">
						<label for="userId">USER ID</label>
					</div>
					<div class="int-area login_In">
						<input type="password" name="userPw" id="login-pw"
							autocomplete="off"> <label for="userPw">PASSWORD</label>
					</div>
					<div id="login_regul" class="regul_div">${requestScope.loginFailMsg}</div>
				</div>
				<div id="btn-area">
					<button type="submit" id="btnL" class="btn_login">로그인</button>
					<button type="button" id="btnJ" value="JOIN" onclick="location.href='join'" class="btn_join">회원가입</button>

				</div>
				</form>
				<div id="btn-area">
				</div>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			
			<div class="caption">
				<a href="/member/find_id">아이디 찾기</a> <em></em> 
				<a href="/member/find_pw">비밀번호 찾기</a>
			</div>
		</div>
	</div>
	<script src="/resources/js/member.js"></script>
</body>
</html>
<%@ include file="../include/footer.jsp"%>
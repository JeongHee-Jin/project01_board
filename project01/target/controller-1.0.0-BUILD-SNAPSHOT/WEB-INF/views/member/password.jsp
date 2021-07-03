<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ page session="false"%>
 <%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/member.js" type="text/javascript" ></script>
<link href="/resources/css/member.css" rel="stylesheet"/>
</head>
<body>
	<div id="password_container">
		<div id="password_area">	
		<sec:authentication property="principal" var="pinfo" />
			<div class="password_title">
				<h1>비밀번호 인증</h1>
			</div>
			<div class="password_box">
				<div class="text1">정보를 안전하게 보호하기 위해 <br/>
					<span class="text1 text2">비밀번호를 다시 한 번 확인</span>합니다.
				</div>
				<div class="text3">비밀번호가 타인에게 노출되지 않도록 항상 주의해주세요.</div>
				<div class="input_div">
					<dl>
						<dt class="dt1">아이디</dt>
						<dt class="dt2"><span id="userId">${pinfo.username}</span></dt>
					</dl>
					<dl>
						<dt class="dt1">비밀번호</dt>
						<dt class="dt2">
							<input type="password" id="userPw">
							<label id="passLabel"class="err_label" style="margin-left: 100px;"></label>
						</dt>						
					</dl>
				</div>
			</div>
			<div class="password_btn" >
				<button class="cancle pass_btn" onclick="goBack();">취소</button>
				<button id="pass_ok" class="ok pass_btn" type="submit" onclick="passwordCheck();">확인</button>
			</div>
		</div>
	</div>
</body>
</html>
<%@ include file="../include/footer.jsp"%>
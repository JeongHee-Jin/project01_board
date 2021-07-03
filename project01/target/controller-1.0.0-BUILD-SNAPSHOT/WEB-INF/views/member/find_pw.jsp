<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="/resources/css/member.css" rel="stylesheet"/>
<body>
	<div id="finePwPage_container">
		<div id="finePwPage_area">
			<div class="sub-border">
				<div class="sub-menu">
					<a href="/member/find_id" id="btn-fineF" class="fineBtn fineBtnId">아이디 찾기</a>
					<a href="/member/find_pw" id="btn-fineT" class="fineBtn fineBtnPw">비밀번호 찾기</a>
				</div>
			</div>
			<div id="fine-info">
				<h1>비밀번호 찾기</h1>
				<div class="fine-text">회원가입시 인증한 이메일 주소를 입력하세요.</div>
				<form name="finePwForm" method="post" action="/member/resultIdPw" 
						onsubmit="findPw(document.finePwForm); return false;">
					<input type='hidden' name='find' id="find" value="pw">
					<div class="input_area">
						<div class="name int-area">
							<input type="text" name="userId" id="userId" autocomplete="off">
							<label for="userName">ID</label>
						</div>
						<div class="email int-area">
							<input type="text" name="email" id="email" autocomplete="off"
									oninput="this.value = this.value.replace(/[^a-z0-9@\-_.]/gi,'');">
							<label for="userEmail" class="email_label">EMAIL</label>
							<div id="email_regul" class="regul_div"></div>
						</div>
					</div>
					<div id="btn-area">
		                <button type="submit" id="btnF" class="btn_FindId btn_FindNext">NEXT</button>
		           	</div>
				</form>
			</div>
		</div>
	</div>
</body>
<script src="/resources/js/member.js"></script>
</html>
<%@ include file="../include/footer.jsp"%>
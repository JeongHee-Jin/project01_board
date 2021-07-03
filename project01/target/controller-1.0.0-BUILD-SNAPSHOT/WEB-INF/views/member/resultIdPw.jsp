<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="/resources/css/member.css" rel="stylesheet"/>
<body>
	<div id="resultPage_container">
		<div id="resultPage_area">
			<div class="sub-border">
				<div class="sub-menu">
					<h2>아이디/비밀번호 찾기 결과</h2>
				</div>
			</div>
			<c:if test="${result eq 'id'}">
				<div>
					<h1 class="findTit">아이디 찾기</h1>
					<div class="findIdText">고객님 아이디 찾기가 완료 되었습니다.</div>
					<h2 class="resultId">ID : ${idText}</h2>
					<div id="btn-area">
						<button onclick="location.href='/member/login'" id="btnF" class="btn_login">로그인</button>
						<button onclick="location.href='/member/find_pw'" class="btn_FindNext">비밀번호 찾기</button>
			        </div>			
				</div>
			</c:if>
			<c:if test="${result eq 'pw'}">
				<div>
					<h1 class="findTit">임시비밀번호 발급</h1>
					<div class="findPwText">고객님 메일로 임시비밀번호가 발송되었습니다.</div>
	
					<div id="btn-area">
			           <button onclick="location.href='/member/login'" id="btnF" class="btn_login">로그인</button>
			        </div>			
				</div>
			</c:if>
		</div>		
	</div>
</body>
<script src="/resources/js/member.js"></script>
</html>
<%@ include file="../include/footer.jsp"%>
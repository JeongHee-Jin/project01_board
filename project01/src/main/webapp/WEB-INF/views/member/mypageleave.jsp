<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ page session="false"%>
 <%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴</title>
<link href="/resources/css/member.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/member.js" type="text/javascript"></script>
<script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
	<div id="mypageleave_container">
		<div id="mypageleave_area">
			<div id="mypageleave_title">
				<h1>회원탈퇴</h1>
			</div>
			<div id="mypageleave_content">
				<ul>
					<li>
						즉시 탈퇴 처리 및 기존 아이디 사용 불가
						<div class="text">회원 탈퇴 시, 즉시 탈퇴 처리되며 기존에 가입하셨던 아이디로 재가입(재사용)이 불가능합니다.</div>
					</li>
					<li>
						탈퇴 후 7일 이내 재가입 불가 및 정보보관
						<div class="text">회원 탈퇴 후 7일간 개인정보가 저장되며 동일 이메일로 사이트 가입이 불가능합니다.</div>
					</li>
					<li>
						작성된 게시글과 댓글 삭제
						<div class="text">게시글과 댓글은 탈퇴한 7일 후 자동 삭제됩니다.</div>
					</li>
				</ul>
				<div class="agree">
					<div class="_check">
						<input type="checkbox" id="agree" value="Y">
						<label for="agree">상기 사항을 모두 확인하였습니다.</label>
					</div>					
					<div style="font-size: 16px;">사이트 이용 종료 및 복구 불가능 및 기존 아이디 영구 재사용 및
					 <span style="color: #ef3e42;">7일이내 재가입 불가함</span>에 동의합니다.</div>
				</div>				
			</div>
			<div class="password_btn" >
					<button class="cancle pass_btn" onclick="goBack();">취소</button>
					<button id="pass_ok" class="ok pass_btn" type="submit" onclick="leave();">탈퇴</button>
				</div>
		</div>
	</div>
</body>
</html>
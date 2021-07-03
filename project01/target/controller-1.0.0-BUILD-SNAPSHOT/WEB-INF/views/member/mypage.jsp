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
	<div id="mypage_container">
		<div id="mypage_area">		
			<div class="mypage_title">
				<h1>회원정보</h1>
			</div>
			<div class="menubox">
				<div class="mypage_boxs">					
					<div class="infoModify" onClick="send('myInfoModify');" >
						<p>회원정보수정</p>
					</div>				
				</div>
				<div class="mypage_boxs">
					<div class="board" onClick="send('myBoardMng');" style="background-position: 0 -100px;">
						<p>게시물관리</p>
					</div>
				</div>
				<div class="mypage_boxs">
					<div class="reply" onClick="send('myReplyMng');" style="background-position: 0 -200px;">
						<p>댓글관리</p>
					</div>
				</div>
				<div class="mypage_boxs ">
					<div class="leave" onClick="send('memberLeave');" style="background-position: 0 -300px;">
						<p>회원탈퇴</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<%@ include file="../include/footer.jsp"%>
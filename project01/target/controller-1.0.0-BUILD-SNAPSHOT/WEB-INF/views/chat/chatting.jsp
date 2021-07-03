<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/resources/css/chat.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/chat.js" type="text/javascript"></script>
</head>
<body>
	<div id="chat_body">	
		<sec:authorize access="isAnonymous()">
			<div id="chat_login_container">
				<div class="text_div">회원에게만 제공되는 채팅입니다. <br/>로그인 후 이용해주세요.</div>
				<button id="chat_login" class="_btn" onclick="location.href='/member/login'">로그인</button>
				<button id="chat_join" class="_btn" onclick="location.href='/member/join'">회원가입</button>
			</div>
		</sec:authorize>
		<sec:authentication property="principal" var="pinfo" />
		<sec:authorize access="isAuthenticated()">
			<div id="chat_container">							
				<div id="nick_box">
					<input type="text" id="chat_nick" maxlength="10" placeholder="닉네임 "/>
					<button type="button" class="into_btn" onclick="openSocket();">채팅방 입장</button>
				</div>					
				<div id="chat_box" class="blind">
					<div id="chat_top">
						<div id="chat_title">채팅방인원(${total})</div>
						<button type="button" id="exit_btn" onclick="closeSocket();" >×</button>
					</div>				
					<div id="chat_messages"></div>
					<div id="chat_bottom">
						<span id="message_area">
							<textarea name="content" id="my_message"></textarea>						
						</span>
						<button type="button" onclick="sendMsg();" id="sand_btn" disabled="true" >보내기</button>								
					</div>
				</div>						
			</div>
		</sec:authorize>
	</div>
</body>
<script>
function scroll(){
	$('#chat_messages').scrollTop($('#chat_messages')[0].scrollHeight);
}
</script>
</html>
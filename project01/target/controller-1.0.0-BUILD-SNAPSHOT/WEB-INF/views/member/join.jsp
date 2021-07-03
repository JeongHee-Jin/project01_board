<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MYOWN CLOSET</title>
</head>
<link href="/resources/css/member.css" rel="stylesheet"/> 
<!-- 주소API -->
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<body onload="joinFunction()">
<div id="joinPage_container">
	<div id="join-form">
		<input type="hidden" id="msg" value="${msg}">
		<div><h1>회원가입</h1></div>
		<form name="joinForm" method="post" action="${pageContext.request.contextPath}/member/join" onsubmit="join(document.joinForm); return false;">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
			<div class="input_area">
				<div class="join_In int-area">
					<input type="text" name="userId" id="join_id" autocomplete="off" maxlength='10'>
					<label for="userId" class="id_label">아이디</label>
					<div id="id_regul" class="regul_div"></div>
	            </div>	           
	            <div class="join_In int-area">
					<input type="password" name="userPw" id="join_pw" autocomplete="off" maxlength='16'>
					<!-- oninput="this.value = this.value.replace(/[^a-z0-9!@#$%^&*()-_+=\.]/gi,'');"> -->
					<label for="userPw" class="pw1_label">비밀번호</label>
					<div id="pw_regul" class="regul_div"></div>
	            </div>             
	            <div class="join_In int-area">
					<input type="password" name="userRpw" id="join_rpw" autocomplete="off" maxlength='16'>
					<!-- oninput="this.value = this.value.replace(/[^a-z0-9!@#$%^&*()-_+=\.]/gi,'');"> -->
					<label for="userPw" class="pw2_label">비밀번호 확인</label>
					<div id="rpw_regul" class="regul_div"></div>
            	</div>
<!-- 	            <div class="join_In int-area">
					<input type="text" name="userName" id="name" autocomplete="off">
					<label for="userName" class="name_label">이름</label>
					<div id="name_regul" class="regul_div"></div>
	            </div> -->
	            <div class="join_In int-area">
					<input type="text" name="userNickName" id="nick" autocomplete="off" maxlength='10'>
					<label for="nick" class="nick_label">닉네임</label>
					<div id="nick_regul" class="regul_div"></div>
	            </div>
	            <div class="join_In int-area">
					<input type="text" name="email" id="email" autocomplete="off">
					<!-- oninput="this.value = this.value.replace(/[^a-z0-9@\-_.]/gi,'');"> -->
					<label for="userEmail" class="email_label">EMAIL</label>
					<div id="email_regul" class="regul_div"></div>
	            </div>
 	            <div class="join_In int-area">
					<input type="text" name="email_code" id="emailCode" autocomplete="off">
					<label for="email_code" class="emailCode_label">인증코드</label>
					<button type="button" id="sendCode" class="btn_emailCode" >인증코드 발송</button>
					<div id="code_regul" class="regul_div"></div>
	            </div>
	             <div class="join_In int-area">
	            	<input type="text" name="phoneNum" id="phoneNum" autocomplete="off" maxlength="13"
	            	oninput="this.value = this.value.replace(/[^0-9\-]/gi,'');" >
	            	<label for="phon" >휴대번호(선택)</label>
	            </div>
	            <div class="join_In int-area">
	            	<input type="text" name="postCode" id="postcode" autocomplete="off" >
	            	<label for="address">우편번호(선택)</label>
	            	<button type="button" class="btn_postcode" onclick="postCodeSearch();" >우편번호검색</button>
	            </div>
	            <div class="join_In int-area">
	            	<input type="text" name="roadAddr" id="roadAddress" autocomplete="off">
	            	<label for="address">기본주소(선택)</label>
	            </div>
	            <div class="join_In int-area">
					<input type="text" name="detailAddr" id="detailAddress" autocomplete="off" >
					<label for="address">상세주소(선택)</label>
	            </div>	             
            </div>
            <div id="btn-area">       
                 <button type="submit" id="btnJ" class="btn_join">회원가입</button>
            </div>
        </form>
    </div>
</div>
<script src="/resources/js/member.js" type="text/javascript" ></script>
<!-- <script type="text/javascript">
var msg=document.getElementById("msg");
if(msg.value!=""){
	var message=msg.value;
	alert(message);
	location.href="/";
}
</script> -->

</body>
</html>
<%@ include file="../include/footer.jsp"%>
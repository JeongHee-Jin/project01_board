<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ page session="false"%>
 <%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지</title>
<link href="/resources/css/member.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/member.js" type="text/javascript"></script>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body onload="infoFunction()">
	<div id="mypageInfo_container">
		<div id="mypageInfo_area">		
			<div class="mypageInfo_title">
				<h1>회원정보수정</h1>
			</div>
			<div class="mypageInfo_box">
				<dl class="my_dl">
					<dt class="dt" >아이디</dt>
					<dd class="dd" id="id_view">${myInfo.userId}</dd>
				</dl>
				<dl class="my_dl box">
					<dt class="dt">비밀번호</dt>
					<dd class="dd"> <span class="gray">주기적인 변경으로 내 정보를 보호하세요.</span> 
					<button class="dd_btn" id="pwOpen" onClick="jsHideArea(this,pw_modify);">수정</button>
					</dd>
					<dd id="pw_modify"class="modifyEnter" >
						<div>8~16자 영문, 숫자, 특수문자(!@#$%^&*()-_+=.)만 사용 가능합니다.</div>
						<div>
							<input id="pwInput" class="conInput" type="password" placeholder="새 비밀번호" autocomplete="off" maxlength='16'>
							<label id="pw_label" class="err_label"></label>	
							<input id="repwInput" class="conInput" type="password" placeholder="새 비밀번호 확인" autocomplete="off" maxlength='16'>
							<label id="repw_label" class="err_label"></label>	
							<button id="pw_btn" class="modify_btn" >변경하기</button>							
						</div>	
										
					</dd>
				</dl>
				<dl class="my_dl box">
					<dt class="dt">닉네임</dt>
					<dd class="dd"><span id="nick_view">${myInfo.userNickName}</span>
					<button class="dd_btn" id="nickOpen" onClick="jsHideArea(this,nick_modify);">수정</button>
					</dd>
					<dd id="nick_modify"class="modifyEnter" >
						<div>닉네임 변경을 위해 중복확인이 필요합니다.</div>
						<div>
							<input id="nickInput" class="conInput" type="text" placeholder="변경 닉네임(10글자 이내)" maxlength='10'>
							<label id="nick_label" class="err_label"></label>	
							<button id="nick_btn" class="modify_btn" >중복 확인</button>							
						</div>	
										
					</dd>
				</dl>
				<dl class="my_dl box">
					<dt class="dt">이메일</dt>
					<dd class="dd"><span id="email_view">${myInfo.email}</span>
						<button class="dd_btn" id="emailOpen" onClick="jsHideArea(this,email_modify);">수정</button>
					</dd>
					<dd id="email_modify"class="modifyEnter" >
						<div>이메일 변경을 위해 인증이 필요합니다.</div>
						<div>
							<input id="emailInput" class="conInput" type="text" placeholder="변경 이메일">
							<input id="codeInput" class="conInput" type="text" placeholder="코드입력" style="display:none;">
							<label id="email_label" class="err_label"></label>	
							<button id="email_btn" class="modify_btn">인증코드 전송</button>							
						</div>						
					</dd>
					
				</dl>
				<dl class="my_dl box">
					<dt class="dt" >전화번호</dt>					
					<dd class="dd" >
						<c:choose>
							<c:when test="${myInfo.phoneNum eq null}">
								<span id="phone_view" class="gray">전화번호를 등록하세요</span>	
							</c:when>
							<c:otherwise>
								<span id="phone_view">${myInfo.phoneNum}</span>	
							</c:otherwise>
						</c:choose>				
						<button class="dd_btn" id="phoneOpen" onClick="jsHideArea(this,phone_modify);">수정</button>
					</dd>
					<dd id="phone_modify"class="modifyEnter" >
						<div>
							<input id="phoneInput" class="conInput" type="text" placeholder="변경 휴대폰번호(-없이 입력)">
							<label id="phone_label" class="err_label"></label>	
							<button id="phone_btn" class="modify_btn" >전화번호 변경</button>							
						</div>						
					</dd>
				</dl>
				<dl class="my_dl box">
					<dt class="dt">주소</dt>						
					<dd class="dd">
						<c:choose>
							<c:when test="${myInfo.postCode eq null}">
								<span id="post_view" class="gray">주소를 등록하세요</span>	
							</c:when>
							<c:otherwise>
								<span id="post_view">${myInfo.postCode}</span>	
							</c:otherwise>
						</c:choose>				
						<button id="addrOpen" class="dd_btn" onClick="jsHideArea(this,addr_modify);">수정</button>
					</dd>
					<dd id="road_view" class="addrDD dd">${myInfo.roadAddr}</dd>
					<dd id="detail_view" class="addrDD dd">${myInfo.detailAddr}</dd>
					<dd id="addr_modify"class="modifyEnter" >
						<div>
							<input id="postcode" class="conInput" type="text" autocomplete="off" style="width:60%;" placeholder="우편번호">
	            			<button type="button" class="btn_postcode" onclick="postCodeSearch();" style="height:40px;float:right;">우편번호검색</button>
							<input id="roadAddress" class="conInput" type="text" id="road" autocomplete="off" placeholder="변경할 기본주소"> 
							<input id="detailInput" class="conInput" type="text" autocomplete="off" placeholder="변경할 상세주소">
							<label id="addr_label" class="err_label"></label>	
							<button id="addr_btn" class="modify_btn">주소 변경</button>							
						</div>						
					</dd>
				</dl>
			</div>
		</div>			
	</div>
</body>
</html>
<%@ include file="../include/footer.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">
<!-- base url: 주소 특정값 자동 추가 -->
<%--  <base href="" target="_blank"> --%>
 <!-- csp는  xss 공격을 막기 위해 만들어진 정책, 인라인 js,css 넣지 못하게 막음 -->
<!--  <meta http-equiv=" Content-Security-Policy-Report-Only" content="upgrade-insecure-requests"> -->
<!--  -->
<title>BOARD★BELL</title>
<!-- <link rel="shortcut icon" href="#"> -->
<!-- <script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js" ></script> -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<link href="/resources/css/header.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/header.js" type="text/javascript" ></script>
</head>
<body>
<div id="header_body">
	<div id="sideNav_container" class="blind">
		<div id="sideNav_area">
			<div><button id="sideNav_exit" onclick="sideNavOpen(sideNav_container,'normal2');">X</button></div>		
			<div id="sideTab_nav">
				<div class="_divtit">메인 메뉴</div>
				<div class="group_box _left" >			
					<div class="brd_name _underline"><a href="/notice">공지</a></div>
					<div class="brd_name _underline"><a href="/cmu/total">커뮤니티</a></div>											
				</div>
				<div class="group_box" >
					<div class="brd_name _cursor"><a href="/chatting">채팅</a></div>
					<!-- <div class="brd_name"><a href="/game">게임</a></div>	 -->
				</div>
			</div>
			<div id="sideBoard_nav">
				<div class="_divtit">커뮤니티 메뉴</div>
				<div id="cmu1" class="group_box _left" >
					<c:forEach items="${brdNameList}" var="brdName" begin="0" end="8">				
						<div class="brd_name _underline"><a href="/cmu/brd/${brdMenu.BRD_MENU_LINK}?mid=${brdName.BRD_IDX}">${brdName.BRD_NAME}</a></div>										
					</c:forEach>
				</div>
				<div id="cmu2" class="group_box" >
					<c:forEach items="${brdNameList}" var="brdName" begin="9" end="18">				
						<div class="brd_name _underline"><a href="/cmu/brd/${brdMenu.BRD_MENU_LINK}?mid=${brdName.BRD_IDX}">${brdName.BRD_NAME}</a></div>										
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
	<div id="header_container">
		<div id="header_tit" class="he_color">
			<div id="logo">
				<span>
					<a href="https://boardbell.shop/" > 
						<img src="/resources/img/header/logo.png" class="logo_img">
					</a>
				</span>
				<span class="header_menu">
					<a href="/notice" ><span class="_cursor">공지</span></a>
					<a href="/cmu/total"><span class="_cursor">커뮤니티</span></a>
					<a href="/chatting"><span class="_cursor">채팅</span></a> 
					<!-- <a href="/game"><span>게임</span></a> -->
				</span>
			</div>
			<div class="menu_bar" onclick="sideNavOpen(sideNav_container,'normal');">
				<span><img src="/resources/img/header/menu_bar.png"
					class="menu_bar">
				</span>
			</div>
		</div>
		<div id="header_login">
		<sec:authentication property="principal" var="pinfo" />
			<%-- <sec:authorize access="isAnonymous()"> --%>
			<c:if test="${empty pinfo || pinfo eq 'anonymousUser'}">
				<div class="customer_menu">
					<span class="join _underline"> <a href="/member/join">회원가입</a>
					</span> <span class="login _underline"> <a href="/member/login">로그인</a>
					</span>
				</div>
			</c:if>
			<%-- </sec:authorize > --%>
			<sec:authorize access="hasRole('ROLE_MEMBER')">
<%-- 			<c:if test="${empty pinfo || pinfo.getRole() eq ROLE_MEMBER}">--%>			
				<div class="member_menu">
					<span class="userId">
						<a href="/member/mypage" class="_underline">마이페이지</a>
					</span> 
					<span class="logout">
						<a href="/member/logout" class="logout_btn _underline">로그아웃</a>
					</span>
				</div>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<div class="member_menu">
					<span class="userId">
						<a href="/manage/admin?_method=user" class="_underline">관리자페이지</a>
					</span> 
					<span class="logout">
						<a href="/member/logout" class="logout_btn _underline">로그아웃</a>
					</span>
				</div>
			</sec:authorize>
		</div>
	</div>
	
</div>
</body>
</html>
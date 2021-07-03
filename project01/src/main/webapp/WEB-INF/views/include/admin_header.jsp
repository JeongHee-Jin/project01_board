<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Security-Policy" content="upgrade-insecure-requests">

<title>관리자페이지</title>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- <link rel="shortcut icon" href="#"> -->
<link href="/resources/css/header.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/header.js" type="text/javascript" ></script>
</head>
<body>
	<div id="admin_header_body">
		<div id="sideNav_container" class="blind">
			<div id="sideNav_area">
				<div><button id="sideNav_exit" onclick="sideNavOpen(sideNav_container,'admin');">X</button></div>
				<div id="sideAdmin_nav">
					<div class="_divtit">관리자 메뉴</div>		
						<div class="brd_name _underline">
							<a href="/manage/admin?_method=user">유저관리</a>
						</div>
						<div class="brd_name _underline">
							<a href="/manage/admin?_method=menu">메뉴관리</a>
						</div>											
					</div>		
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
			</div>
		</div>
		<div id="admin_header_container">
			<div id="header_tit" class="ad_he_color">
				<div id="logo">
					<a href="https://boardbell.shop/" > 
						<img src="/resources/img/header/logo.png" class="logo_img">
					</a>
					<span class="header_menu color">
						<a href="/manage/admin?_method=user"><span>유저관리</span></a>
						<a href="/manage/admin?_method=menu"><span>메뉴관리</span></a>
					</span>
				</div>
				<div class="menu_bar" onclick="sideNavOpen(sideNav_container,'admin');">
					<span><img src="/resources/img/header/menu_bar2.png"
						class="menu_bar">
					</span>
				</div>
			</div>
			<div id="header_mid">
			</div>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page session="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/resources/css/header.css" rel="stylesheet" type="text/css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>
<body>
	<div id="admin_header_container">
		<div id="header_tit" class="ad_he_color">
			<div id="logo">
				<a href="http://localhost:8012/" > 
					<img src="/resources/img/header/logo.png" class="logo_img">
				</a>
				<nav class="header_menu color">
					<a href="/manage/admin?_method=user"><span>유저 관리</span></a>
					<a href="/manage/admin?_method=menu"><span>메뉴 관리</span></a>
				</nav>
			</div>
		</div>
		<div id="header_mid">
<!-- 			<div id="admin_mem_nav" class="hidden">
				<span>전체 유저 관리</span>
				<span>활동정지 유지 관리</span>
			</div> -->
		</div>
	</div>
</body>
</html>
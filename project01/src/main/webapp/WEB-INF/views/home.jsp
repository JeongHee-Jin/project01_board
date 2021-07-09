<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<%@ include file="include/header.jsp"%>
<html>
<head>
<meta charset="UTF-8">
</head>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.6.0/Chart.js" type="text/javascript"></script> -->
<link href="/resources/css/main.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/mainhome.js" type="text/javascript"></script>
<body>
<div id="main_body">
	<div id="main_container">
		<div id="main_left">
			<div id="weather_div">
				<div>
					<h3 style="display: inline-block;padding-bottom: 10px;">오늘의 날씨</h3>
					<div id="city"></div>
				</div>			
				<div id="weather_back">
					<!-- style="overflow: hidden; position: relative;"  -->
					<div id="graph_area">
						<svg height="50" width="405" version="1.1"  xmlns="http://www.w3.org/2000/svg" id="weather_svg"
						xmlns:xlink="http://www.w3.org/1999/xlink">
						<path id="weather_graph" stroke="#7294eb" d="" stroke-width="2"></path>					
						</svg>
					</div>
					<ul id="weather_ul">
					</ul>
				</div>			
			</div>
			<div id="banner_ad">
				<h3 style="padding-bottom: 10px;">광고</h3>
				<ul id="slider">
					<li class="mySlides">
						<a href="https://www.whoisnerdy.com/" >
							<img src="/resources/img/upload_banner/8964383326908312582.png" class="banner_img">
						</a>
					</li>
					<li class="mySlides">
						<a href="https://fastcampus.co.kr/">
							<img src="/resources/img/upload_banner/7188125314189083025.png" class="banner_img">
						</a>
					</li>
					<li class="mySlides">
						<a href="https://www.uplussave.com/shop/cmmn/index.mhp">
							<img src="/resources/img/upload_banner/15048091432526678625.png" class="banner_img">
						</a>
					</li>
				</ul>
	      		<a class="prev" onclick="moveSlides(-1)">&#10094;</a>
	     	 	<a class="next" onclick="moveSlides(1)">&#10095;</a>
				<div id="counter"></div>
			    <div id="img_dots"></div>         	
	          </div>
			<div id="youtube">
				<h3 style="display: inline-block;padding-bottom: 10px;">이달의 추천 영상</h3>
				<iframe width="430" height="315" src="https://www.youtube.com/embed/san8TyNMBCs" 
					title="YouTube video player" frameborder="0" 
					allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
					allowfullscreen>
				</iframe>
			</div>
		</div>
		<div id="main_right">
			<div id="tab_nav">
				<div class="_divtit">메인 메뉴</div>
				<div class="group_box _left" >			
					<div class="brd_name _underline"><a href="/notice">공지</a></div>
					<div class="brd_name _underline"><a href="/cmu/total">커뮤니티</a></div>											
				</div>
				<div class="group_box" >
					<div class="brd_name _underline"><a href="/chatting">채팅</a></div>
					<!-- <div class="brd_name"><a href="/game">게임</a></div>	 -->
				</div>
			</div>
			<div id="board_nav">
				<div class="_divtit">커뮤니티 메뉴</div>
				<div class="group_box _left" >
					<c:forEach items="${brdNameList}" var="brdName" begin="0" end="7">				
						<div class="brd_name">
							<a href="/cmu/brd/${brdName.BRD_MENU_LINK}?mid=${brdName.BRD_IDX}" class=" _underline">${brdName.BRD_NAME}</a>
						</div>										
					</c:forEach>
				</div>
				<div class="group_box" >
					<c:forEach items="${brdNameList}" var="brdName" begin="8" end="18">				
						<div class="brd_name">
							<a href="/cmu/brd/${brdName.BRD_MENU_LINK}?mid=${brdName.BRD_IDX}" class=" _underline">${brdName.BRD_NAME}</a>
							</div>										
					</c:forEach>
				</div>
			</div>
			<div id="board_hot">
				<div class="_divtit">인기 게시판</div>
				<div class="brd_listbox">
					<c:forEach items="${list}" var="list" begin="0" end="4">		
							<div class="brd_name" onClick="location.href='/cmu/hot?mid=${list.bBRD_IDX}&post=${list.POST_IDX}'">
							${list.POST_TITLE}</div>									
					</c:forEach>
				</div>
			</div>
		</div>
		<div id="banner_ad_r">
			<a href="https://66girls.co.kr/">
				<img src="/resources/img/upload_banner/1283604037098765198.png" class="banner_img">
			</a>
		</div>
	</div>
</div>
<script>
var result='${joinMsg}';
if(result=="success"){
	alert("회원가입을 축하드립니다.");
}
</script>
</body>
</html>
<%@ include file="include/footer.jsp"%>
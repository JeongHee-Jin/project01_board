<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/admin_header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="project01/resources/js/admin.js" type="text/javascript" ></script>
<link href="project01/resources/css/admin.css?ver=1" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="admin_menu_container">
	<div id="admin_menu_area">
		<h1>메뉴 관리</h1>
		<div id="menu_div" class="top">
			<div class="menu_tit" style="line-height: 40px;"><h2>게시판 메뉴</h2> 
				<button type="button" id="menuAdd_btn" class="open_btn" onclick="divOpen(addMenu);">추가</button>
			</div>
			<div id="addMenu" class="add_menu div_box hidden">
				<div>
					<label><input type="radio" name="boardMenuVal" value="10000">공지</label>
					<label><input type="radio" name="boardMenuVal" value="20000" checked>커뮤니티</label>
				</div>
				<div class="_line">
					<label>추가할 메뉴명</label><input type="text" id="addBrdMenuTxt" class="_input">
				</div>
				<div class="_line">
					<label style="padding-left: 43px;">링크명</label>
					<input type="text" id="addBrdMenulink" class="_input" placeholder="영문만 입력" oninput="this.value = this.value.replace(/[^a-z]/gi,'');">
					<button type="button"class="add_btn set_btn" id="menu_add_btn" onclick="addBoardMenu_btn();">추가</button>
				</div>	
			</div>
			<div class="div_box">
				<ul id="menu_nav">
					<c:forEach items="${brdMenuList}" var="brdMenu">
						<li onclick="boardListOpen(${brdMenu.MENU_IDX},${brdMenu.BRD_MENU_IDX},'${brdMenu.BRD_MENU_NAME}');">${brdMenu.BRD_MENU_NAME}</li>
					</c:forEach>
				</ul>
				<div id="brdMenuSet" class="div_modify hidden">
					<label>메뉴명</label><input id="brdmenuTxt" type="text" class="_input">	
					<button type="button"class="add_btn set_btn" id="brdMenuMod_btn" >수정</button>
					<button type="button"class="del_btn set_btn" id="brdMenuDel_btn" >삭제</button>					
				</div>
			</div>
		</div>
		<div id="board_list" class="top" >
			<div class="menu_tit"><h2>게시판</h2> 
				<button type="button" id="board_btn" class="open_btn" onclick="divOpen(addBoard);">추가</button>
			</div>
			<div id="addBoard" class="add_menu div_box hidden">
				<label>추가할 게시판명</label>
				<input type="text" id="addBrdTxt" class="_input">
				<button type="button"class="add_btn set_btn" id="board_add_btn" onclick="addBoard_btn();">추가</button>
			</div>
			<div id="board_ListBox" class="div_box hidden">
				<ul id="board_nav" ></ul>
				<div id="brdSet" class="div_modify hidden">
					<label>게시판명</label><input id="boardTxt" type="text" class="_input">	
					<button type="button"class="add_btn set_btn" id="brdMod_btn" >수정</button>
					<button type="button"class="del_btn set_btn" id="brdDel_btn" >삭제</button>				
				</div>	
			</div>						
		</div>
		<div id="cate_list" class="top" >
			<div class="menu_tit"><h2>카테고리</h2> 
				<button type="button" id="board_btn" class="open_btn" onclick="divOpen(addCate);">추가</button>
			</div>
			<div id="addCate" class="add_menu div_box hidden">
				<label>추가할 카테고리명</label>
				<input type="text" id="addCateTxt" class="_input">
				<button type="button"class="add_btn set_btn" id="board_add_btn" onclick="addCate_btn();">추가</button>
			</div>
			<div id="cate_ListBox" class="div_box hidden">
				<ul id="cate_nav"></ul>
				<div id="cateSet" class="div_modify hidden">
					<label>카테고리명</label><input id="cateTxt" type="text" class="_input">	
					<button type="button"class="add_btn set_btn" id="cateMod_btn" >수정</button>
					<button type="button"class="del_btn set_btn" id="cateDel_btn" >삭제</button>			
				</div>
			</div>			
		</div>			
	</div>
</div>
</body>
</html>
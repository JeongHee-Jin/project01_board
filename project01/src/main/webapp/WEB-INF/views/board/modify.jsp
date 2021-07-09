<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Project01. BOARD</title>
<script src="/resources/js/register.js"></script>
<link href="/resources/css/register.css" rel="stylesheet" type="text/css" />
</head>
<body onload="modifyView()">
	<div id="write_container">
		<form role="form" method="post" name="modiftForm" onsubmit="insert(document.modiftForm); return false;">
 			<div class="blind">
	 			<input type="hidden" id="nav" name="nav" value="${nav}">
	 			<input type='hidden' name="postId" id="postId" value="${brdVO.postId}">
	 			<input type='hidden' id="notice" value="${brdVO.notice}">
	 			<sec:authentication property="principal" var="pinfo"/>
 			</div>			
			<div class="boardTit">
				<h1>글수정</h1>
			</div>
			<div id="editer_board">
				<div id="common_editer">
					<div id="check_div">
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<c:if test="${nav eq 10000 }">
								<div class="noticeCheck _check">
									<input type="checkbox" name="notice" id="chkNoticeTotal" value="T" /> 
									<label for="chkNoticeTotal">전체 공지</label>
								</div>	
							</c:if>					
							<div class="noticeCheck _check">
								<input type="checkbox" name="notice" id="chkNotice"  value="Y" /> 
								<label for="chkNotice">공지글</label>
							</div>
						</sec:authorize>
					</div>
					<div>
						<div class="selIdDiv">
							<select id="selBoard" class="menuId heightMg" name="brdId" onchange="chageBoard()">
							<!-- 해당 게시물에서 글쓰기 누를시 : selected 추가(자바스크립트)  -->
								<c:forEach items="${brd}" var="brd">
									<c:choose>
										<c:when test="${brdVO.brdId eq brd.BRD_IDX}">
											<option value="${brd.BRD_IDX}" selected>${brd.BRD_NAME}</option>
										</c:when>
										<c:otherwise>
											<option value="${brd.BRD_IDX}">${brd.BRD_NAME}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
						</div>
						<div class="selIdDiv">
							<select id="selBCate" class="menuId heightMg" name="cateId" >
							<!-- 해당 게시물에서 글쓰기 누를시 : selected 추가(자바스크립트)  -->
							<option value="" id="optBCate" >말머리선택</option>
								<c:forEach items="${cate}" var="cate">
									<c:choose>
										<c:when test="${brdVO.cateId eq cate.CATE_IDX}">
											<option value="${cate.CATE_IDX}" selected>${cate.CATE_NAME}</option>
										</c:when>
										<c:otherwise>
											<option value="${cate.CATE_IDX}">${cate.CATE_NAME}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							
							</select>
						</div>
					</div>					
					<div class="post_subject heightMg">
						<label class="item blind" for="subject">제목</label>
						<input type="text" id="title" name="title" value="${brdVO.title}" maxlength="18">
					</div>
					<div class="heightMg">
<!-- 						<button onclick="makeImg()">
							<span class="EditorImg"></span>
							<span>사진</span>
						</button> -->
					</div>
					<div class="myEditorPlugin heightMg">
						<span class="family"> 
							<select name="fontName" id="fontName">
								<option value="">글꼴</option>
								<option value="돋움">돋움</option>
								<option value="굴림">굴림</option>
								<option value="궁서">궁서</option>
								<option value="바탕">바탕</option>
								<option value="맑은고딕">맑은고딕</option>
							</select>
						</span> 
						<span class="fontSize heightMg"> 
							<select name="fontSize" id="fontSize">
								<option value="">크기</option>
								<option value="1">7pt</option>
								<option value="2">8pt</option>
								<option value="3">10pt</option>
								<option value="4">12pt</option>
								<option value="5">16pt</option>
								<option value="6">20pt</option>
								<option value="7">30pt</option>
							</select>
						</span> 
						<span class="make01 heightMg"> 
							<button title="굵게" type="button" class="editor_btn bold" id="bold"></button> 
							<button title="밑줄" type="button" class="editor_btn underline" id="underline"></button> 
							<button title="기울임" type="button" class="editor_btn italic" id="italic"></button>
							<button title="취소선" type="button" class="editor_btn StrikeThrough" id="StrikeThrough"></button> 
							
							<!-- <img src="/resources/img/sub_register/btn_n_color2.gif" class="editor_btn" id="ForeColor">
	       		 				<img src="/resources/img/sub_register/btn_n_bgcolor.gif" class="editor_btn" id="BackColor"> -->
						</span> 
						<span class="make02 heightMg"> 
							<img src="/resources/img/sub_register/btn_n_alignleft.gif" class="editor_btn" id="justifyleft"> 
							<img src="/resources/img/sub_register/btn_n_aligncenter.gif" class="editor_btn" id="justifycenter"> 
							<img src="/resources/img/sub_register/btn_n_alignright.gif" class="editor_btn" id="justifyright"> 
							<img src="/resources/img/sub_register/btn_n_alignjustify.gif" class="editor_btn" id="justifyFull"> 
							<img src="/resources/img/sub_register/btn_n_numberset.gif" class="editor_btn" id="insertOrderedList"> 
							<img src="/resources/img/sub_register/btn_n_markset.gif" class="editor_btn" id="insertUnorderedList">
						</span> 
						<span class="make03 heightMg">
							<span class="editor_btn atFile filebox">
								<input type="file" name="uploadFile" id="uploadFile" multiple>
							</span>
							<button title="url" type="button" class="editor_btn URL" id="URL" onclick="createLink();"></button> 
							<span class="editor_btn html_span">
								<input type="button" class="btn_html" onclick="showSource();">
							</span> 
						</span>
					</div>
					<div id="li_txt">
						<label class="blind">내용</label>
						<textarea id="content_text" name="content"></textarea>
						<iframe id="content_iframe" scrolling=yes border=0 width="95%" height=300 frameborder=0>${brdVO.content}</iframe>
						
					</div>
					<div class = 'uploadResult uploadModify'>
						<div class="attach-tit">첨부파일</div>
						<div class="attach-list">
							<ul></ul>
						</div>
						<div class="attach-input"></div>
					</div>
				</div>
				<div class="btn_post">
					<button type="submit" class="register_btn">
						<strong>등록</strong>
					</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
<%@ include file="../include/footer.jsp"%>
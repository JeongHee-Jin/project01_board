<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
 <%@ page session="false"%>
 <%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Portfolio.Board</title>
<link href="/resources/css/member.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/member.js" type="text/javascript"></script>
<script type="text/javascript">
//새로고침막기

/* window.onbeforeunload = function(e) {
    var dialogText = "/member/mypage?_method=memberFront";
    e.returnValue = dialogText;
    return dialogText;
}; */

</script>
</head>
<body onload="javascript:reloadBoard(1);">
	<div id="mypageBoard_container">
		<div id="mypageBoard_area">
			<div id="mypageBoard_title">
				<h1>게시물관리</h1>
			</div>
			<div id="mypageBoard_content">
				<div class="content_top">
					<button id="myboard_del" onclick="chkBoardDel()">선택 삭제</button>
				</div>
				<table id="myboard_table">
					<tr class="tr_line">
						<td class="chkBox"><input type="checkbox" class="iChek" onClick="allChk(this);"></td>
						<td class="num">번호</td>
						<td class="name">게시판</td>
						<td class="title">제목</td>
						<td class="date">작성일</td>
					</tr>
					<c:set var="num" value="${pageMk.totalCount-((pageMk.cri.page-1)*pageMk.cri.pageLen) }" />
					<c:forEach items="${board}" var="board">
						<tr class="tr_line">
							<td><input type="checkbox" class="iChek" value="${board.POST_IDX}"></td>
							<td style="font-size: 16px;">${num}</td>
							<td style="color:#34b12b;font-weight: 600;">${board.BRD_NAME}</td>
							<td class="title" >
								<a href="/cmu/brd/${board.BRD_MENU_LINK}?mid=${board.BRD_IDX}&post=${board.POST_IDX}" class="_underline">
									${board. POST_TITLE}</a>
							</td>
							<td style="font-size: 16px;"><fmt:formatDate pattern="yy.MM.dd" value='${board.POST_REGTIME}' /></td>
						</tr>
						<c:set var="num" value="${num-1}"></c:set>
					</c:forEach>
				</table>
				<div id="pageNav">
<%-- 			<c:choose>
				<c:when test="${empty sub}">	 --%>
					<c:if test="${pageMk.prev}">
						<span class="prev_btn">
							<a href="javascript:;" onClick="reloadBoard(${pageMk.startPage-1})">&#60; 이전</a>
							<%-- <a href="list${pageMk.makeQuery(pageMk.startPage-1,nav) }">&#60; 이전</a> --%>
						</span>
					</c:if>
					<c:forEach begin="${pageMk.startPage}" end="${pageMk.endPage}" var="pageNum">
						<c:if test="${pageNum!=0 }">
							<span <c:out value="${pageMk.cri.page==pageNum?'class=active':''}" />>
								<a href="javascript:;" class="_underline" onClick="reloadBoard(${pageNum})">${pageNum}</a>
							</span>
						</c:if>			
					</c:forEach>
					 	<c:if test="${pageMk.next && pageMk.endPage>0 }">
						 <span class="next_btn">
						 	<a href="javascript:;" onClick="reloadBoard(${pageMk.endPage+1})">다음&#62;</a>
						</span>
					</c:if> 
<%-- 				</c:when>
				</c:choose> --%>
		</div>
			</div>
		</div>
	</div>
</body>
</html>

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
</head>
<body onload="javascript:reloadReply(1);">
	<div id="mypageReply_container">
		<div id="mypageReply_area">
			<div id="mypageReply_title">
				<h1>댓글관리</h1>
			</div>
			<div id="mypageReply_content">
				<div class="content_top">
					<button id="myboard_del" onclick="chkReplyDel()">선택 삭제</button>
				</div>
				<table id="myreply_table">
					<tr class="tr_line">
						<td class="chkBox"><input type="checkbox" class="iChek" onClick="allChk(this);"></td>
						<td class="num">번호</td>
						<td class="title">제목</td>
						<td class="date">작성일</td>
					</tr>
					<c:set var="num" value="${pageMk.totalCount-((pageMk.cri.page-1)*pageMk.cri.pageLen) }" />
					<c:forEach items="${reply}" var="reply">
						<tr class="tr_line">
							<td><input type="checkbox" class="iChek" value="${reply.REPLY_IDX}"></td>
							<td style="font-size: 16px;">${num}</td>
<%-- 							<td style="color:#34b12b;font-weight: 600;">${reply.BRD_NAME}</td> --%>
							<td class="title titl_hgt">
								<div class="board_titl">
								<c:choose>
									<c:when test="${!empty reply.POST_TITLE}">
										${reply.BRD_NAME} : ${reply.POST_TITLE}
									</c:when >
									<c:otherwise>
										삭제된 게시물 입니다.
									</c:otherwise>
								</c:choose>
								</div>
								<a href="/cmu/brd/${reply.BRD_MENU_LINK}?mid=${reply.BRD_IDX}&post=${reply.POST_IDX}" class="_underline">
									${reply.REPLY_CONTENT}									
									</a>
							</td>
							<td style="font-size: 16px;"><fmt:formatDate pattern="yy.MM.dd" value='${reply.REPLY_UPDATE}' /></td>
						</tr>
						<c:set var="num" value="${num-1}"></c:set>
					</c:forEach>
				</table>
				<div id="pageNav">
					<c:if test="${pageMk.prev}">
						<span class="prev_btn">
							<a href="javascript:;" onClick="reloadReply(${pageMk.startPage-1})">&#60; 이전</a>
						</span>
					</c:if>
					<c:forEach begin="${pageMk.startPage}" end="${pageMk.endPage}" var="pageNum">
						<c:if test="${pageNum!=0 }">
							<span <c:out value="${pageMk.cri.page==pageNum?'class=active':''}" />>
								<a href="javascript:;" class="_underline" onClick="reloadReply(${pageNum})">${pageNum}</a>
							</span>
						</c:if>				
					</c:forEach>
					 	<c:if test="${pageMk.next && pageMk.endPage>0 }">
						 <span class="next_btn">
						 	<a href="javascript:;" onClick="reloadReply(${pageMk.endPage+1})">다음	 &#62;</a>
						</span>
					</c:if> 
				</div>
			</div>
		</div>
	</div>
</body>
</html>
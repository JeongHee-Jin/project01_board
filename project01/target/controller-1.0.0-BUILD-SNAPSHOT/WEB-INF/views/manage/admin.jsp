<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/admin_header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/resources/js/admin.js" type="text/javascript" ></script>
<link href="/resources/css/admin.css?ver=1" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="admin_mem_container">
	<div id="admin_area">
		<h1>전체 유저 관리</h1>
		<div id="mem_srch_div">
			<label>유저 검색</label>
			<select id="searchType">
				<option value="0">아이디</option>
				<option value="1">닉네임</option>
			</select>
			<input type="text" id="mem_srch" autocomplete="off">
			<button id="btn_mem_srch" onclick="searchUser('${pageMk.makeQuery(1)}');">검색</button>
		</div>
		<div id="mem_content">
			<div class="_text">유저 멤버 수<span class="mem_sum">${pageMk.totalCount}</span></div>
			<table id="mem_list" >
				<tr class="mem_list_title">
					<td class="chk" style="min-width:30px">
						<input type="checkbox" class="iChek" onClick="allChk(this);"></td>
					<td class="id" style="min-width:250px">아이디 (닉네임)</td>
					<td class="role" style="min-width:70px">상태</td>
					<td class="rig" style="min-width:120px">가입일</td>
					<td class="bnum" style="min-width:70px">게시글수</td>
					<td class="rnum" style="min-width:70px">댓글수</td>
				</tr>
 				<c:forEach items="${userList}" var="userList">
					<tr class="mem_list_tr">
						<td>
							<c:if test="${userList.MEM_ROLE!='ROLE_ADMIN'}">
								<input type="checkbox" class="iChek" value="${userList.MEM_ID}">
							</c:if>
						</td>
						<td class="id_view">${userList.MEM_ID} (${userList.MEM_NICKNAME})</td>						
						<c:choose>
							<c:when test="${userList.MEM_ROLE=='ROLE_MEMBER'}">
								<td style="color: #46a51f;">유저</td></c:when>
							<c:when test="${userList.MEM_ROLE=='ROLE_STOP'}">
								<td style="color: #e84242;">활동정지</td></c:when>
							<c:when test="${userList.MEM_ROLE=='ROLE_LEAVE'}">
								<td style="color: #f94497;">탈퇴</td></c:when>
							<c:when test="${userList.MEM_ROLE=='ROLE_ADMIN'}">
								<td style="color: #5280ff;">관리자</td></c:when>
						</c:choose>						
						<td class="rig">${userList.MEM_REGDATE}</td>
						<td class="bnum">${userList.BOARD_SUM}</td>
						<td class="rnum">${userList.REPLY_SUM}</td>
					</tr>
				</c:forEach>
				
			</table>
			<div class="btn_area">선택 멤버 
				<button id="btn_mem_stop" onclick="chkUser('stop');">활동정지</button>
				<button id="btn_mem_stop" onclick="chkUser('play');">활동</button>
			</div>
		</div>
		<div id="pageNav">		
			<c:if test="${pageMk.prev}">
				<span class="prev_btn">
					<a href="javascript:;" onClick="location.href='${pageMk.makeQuery(pageMk.startPage-1)}'">&#60; 이전</a>
					<%-- 	<a href="list${pageMk.makeQuery(pageMk.startPage-1,nav) }">&#60; 이전</a> --%>
				</span>
			</c:if>
			<c:forEach begin="${pageMk.startPage }" end="${pageMk.endPage }" var="pageNum">
				<c:if test="${pageNum!=0 }">
					<span <c:out value="${pageMk.cri.page==pageNum?'class=active':''}" />>
						<a href="javascript:;" onClick="location.href='${pageMk.makeSearch(pageNum)}'">${pageNum}</a>
					</span>	
				</c:if>			
			</c:forEach>
			 	<c:if test="${pageMk.next && pageMk.endPage>0 }">
				 <span class="next_btn">
				 	<a href="javascript:;" onClick="location.href='${pageMk.makeQuery(pageMk.startPage+1)}'">다음	 &#62;</a>
				</span>
			</c:if> 
		</div>
	</div>
</div>
</body>
</html>
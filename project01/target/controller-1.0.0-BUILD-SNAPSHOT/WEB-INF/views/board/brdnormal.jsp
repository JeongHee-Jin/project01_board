<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Project01.커뮤니티</title>
	<script src="/resources/js/read.js" type="text/javascript" ></script>
	<link href="/resources/css/list.css?ver=1" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="list_container">
		<sec:authentication property="principal" var="pinfo"/>
		<div id="btn_fixed">
			<div class="top _fixedbtn _cursor" onclick="var offset=$('#list_container').offset(); $('html,body').animate({scrollTop:offset.top});">∧</div>
			<div class="list _fixedbtn _cursor" onclick="var offset=$('#board_list').offset(); $('html,body').animate({scrollTop:offset.top});">∨</div>
		</div>
		<h1>${tabMenu}</h1>
		<div id="navbar-item">
			<ul id="navbar">				
				<c:forEach items="${brdMenuList}" var="brdMenu">							
					<c:choose>
						<c:when test="${brdMenu.BRD_MENU_IDX eq 1100}">
							<li class="nav_total" onClick="location.href='/cmu/${brdMenu.BRD_MENU_LINK}'">${brdMenu.BRD_MENU_NAME}		
						</c:when>
						<c:when test="${brdMenu.BRD_MENU_IDX eq 1200}">
							<li class="nav_list " onClick="location.href='/cmu/${brdMenu.BRD_MENU_LINK}'">${brdMenu.BRD_MENU_NAME}
						</c:when>
						<c:otherwise>
							<li class="nav_list">${brdMenu.BRD_MENU_NAME}
						</c:otherwise>
					</c:choose>
					<div class="nav_name">								
						<c:forEach items="${brdNameList}" var="brdName">							
							<c:if test="${brdMenu.BRD_MENU_IDX eq brdName.BRD_MENU_IDX}">
								<a href="/cmu/brd/${brdMenu.BRD_MENU_LINK}?mid=${brdName.BRD_IDX}">${brdName.BRD_NAME}</a>
							</c:if>									
						</c:forEach>	
					</div>		
				</c:forEach>
			</ul>
		</div>
		<div id="brd-name-div">
			<c:forEach items="${brdMenuName}" var="menu">
				<div class="brd-name _cursor">${menu.BRD_MENU_NAME} 게시판</div>
			</c:forEach>
		</div>
		<c:if test="${! empty brdVO || replyPrint==1}">
		<!-- 게시글읽기 -->
		<div id="read_container">			
			<input type='hidden' name='brdCla' id='brdCla' value="${brdVO.brdMenuId}" />
			<div class="articleTopBtns">
			<label class="blind">이전글/다음글</label>
				<c:forEach items="${brdMenuName}" var="menu">
					<c:if test="${brdVO.prev ne null}">
						<a href="${menu.BRD_MENU_LINK}?mid=${brdVO.brdId}&post=${brdVO.prev}" class="baseButton">이전글</a>
					</c:if>
					<c:if test="${brdVO.next ne null}">
						<a href="${menu.BRD_MENU_LINK}?mid=${brdVO.brdId}&post=${brdVO.next}" class="baseButton">다음글</a>
					</c:if>	
				</c:forEach>	
			</div>
			<div id="read_board">
				<form role="form" method="get" name="readForm">
					<sec:authorize access="isAuthenticated()">
						<input type='hidden' id='userId' value="${pinfo.username}">
					</sec:authorize>
					<input type="hidden" name="nav"  id="nav" value="${nav}">
					<input type='hidden' name='brdId' id='mid' value="${brdVO.brdId}">
		 			<input type='hidden' name='postId' id='postId' value="${brdVO.postId}">
					<div id="article_header">
						<div class="articleTitle">
							<h3 class="board_tit">${brdVO.title}</h3>
						</div>
						<div class="writerInfo">
							<div class="profile">
								<label class="blind">작성자이름</label>
								<div class="profile_name">${brdVO.memNickName}</div>
								<label class="blind">작성날짜</label>
								<div class="board_info">
									<span class="date"><fmt:formatDate pattern="yyyy.MM.dd. HH:mm"
										value="${brdVO.regtime}" />
									</span>
									<span class="count">조회수  ${brdVO.hit}</span>
								</div>
								<div class="articleTool">
									<a href="javascript:copyURL()" role="button" class="btn_url" onfocus="blur();">URL 복사</a>
								</div>
							</div>
						</div>
					</div>
					<div id="article_content">
						<label class="blind">글내용</label>
						<div id="contentRender" class="contentRender">
							<c:out value="${brdVO.content}" escapeXml="false" />
						</div>
					</div>
					<div id="article_loadFile" class="uploadResult">
					</div>
<%-- 					<div id="article_box">
						<span id="like_btn" class="span like_btn _off" onClick="likeUpdate(${brdVO.postId},like_btn)">
							<!-- <a href=""  aria-pressed="false"> -->
								<span class="like_icon"></span>
								<span>좋아요</span>
								<strong>${brdVO.likeIt}</strong>
						</span>
						<span class="span reply_id">댓글 <strong id="replyCnt">${pageMkRp.totalCount}</strong></span>
					</div> --%>				
			 		<div id="ReplyBox">
			 		<h3 class="reply_tit">댓글 <strong id="replyCnt">${replyCnt}</strong>개</h3>
					 	<c:if test="${! empty replyList}">							
							<div id="reply_list">
								<ul class="reply_list_ul">
								<c:forEach items="${replyList}" var="re">							
										<c:choose>
											<c:when test="${pinfo ne 'anonymousUser'}">
												<c:if test="${re.memId eq pinfo.username}">
													<li class="replyItem _mine">	
												</c:if>
											</c:when>
											<c:otherwise>
												<li class="replyItem">
											</c:otherwise>
										</c:choose>
										<div id="replyUpdate_${re.replyId}" class="blind">
											<div class="replyWriter">
												<div class="reply_inbox">
													<div class="reply_name" id="replymemNick" >${re.memNickName}</div>
													<textarea class="reply_text" id="replyContent_${re.replyId}">${re.rpContent}</textarea>
												</div>
												<div class="reply_attach">
													<div class="register_box">
													<button type="button" class="btn_cancel button" onClick="replyUpDiv(${re.replyId});" >취소</button>
														<button type="button" class="btn_register button" onClick="replyUpdate(${re.replyId});" >등록</button>
													</div>
												</div>
											</div>
										</div>
										<div id="reply_area_${re.replyId}" class="reply_area">
											<div>
												<span class="reply_nick">${re.memNickName}</span>
												<span class="reply_date"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${re.rpUpdatetime}" /></span>
											</div>											
											<div class="reply_text">${re.rpContent}</div>
											<div class="reply_info">
												<sec:authorize access="isAuthenticated()">
												<c:if test="${re.memId eq pinfo.username}">
													<span class="replyU" onClick="replyUpDiv('${re.replyId}')">수정</span>
													<span class="replyD" onClick="replyDelete('${re.replyId}')">삭제</span>
												</c:if>
												</sec:authorize>
											</div>
										</div>
									</li>
								</c:forEach>
								</ul>
							</div>
						</c:if>
						<div id="reply_pageNav">	
							<c:if test="${pageMkRp.prev}">
								<span class="prev_btn">
									<a href="javascript:;" onClick="replyList(${pageMkRp.startPage-1})">&#60; 이전</a>
								</span>
							</c:if>
							<c:forEach begin="${pageMkRp.startPage }" end="${pageMkRp.endPage }" var="pageNum">
								<c:if test="${pageNum!=0 }">
									<span <c:out value="${pageMkRp.cri.page==pageNum?'id=rpPageAct class=active':''}" />>
										<a href="javascript:;" class="_underline" onClick="replyList(${pageNum})">${pageNum}</a>
									</span>
								</c:if>				
							</c:forEach>
							 	<c:if test="${pageMkRp.next && pageMkRp.endPage>0 }">
								 <span class="next_btn">
								 	<a href="javascript:;" onClick="replyList(${pageMkRp.endPage+1})">다음	 &#62;</a>
								</span>
							</c:if> 
						</div>
						<sec:authorize access="isAuthenticated()">
							<div class="replyWriter">
								<div class="reply_inbox">
									<div class="reply_name" id="replyNick">${pinfo.userNickName}</div>
									<textarea class="reply_text" id="replyContent" placeholder="댓글을 입력하세요"></textarea>
								</div>
								<div class="reply_attach">
									<div class="register_box">
										<button type="button" class="btn_register button" onClick="replyInsert()" >등록</button>
									</div>
								</div>
							</div>
						</sec:authorize>
					</div>
				</form>			
			</div>	
			<div class="ArticleBottomBtns">
				<sec:authorize access="isAuthenticated()">
					<c:if test="${brdVO.notice eq'N' }">
						<div class="left_area">
							<!-- <button type="submit" class="baseButton skinYW" onClick="javascript:butt('regist');">글쓰기</button> -->
							<button type="submit" class="baseButton" onClick="location.href='/cmu/commentaire?mid=${brdVO.brdId}&post=${brdVO.postId}'">답글</button>		
						</div>
					</c:if>
					<div class="left2_area">
						<c:if test="${brdVO.memId eq pinfo.username or pinfo.role eq 'ROLE_ADMIN'}">					
							<button type="button" class="baseButton" onClick="location.href='/cmu/modify?mid=${brdVO.brdId}&post=${brdVO.postId}'">수정</button>
							<button type="button" id="remove" class="baseButton" style="color: #ef3e42; background: #ffe8e8;">삭제</button>
						</c:if>	
					</div>				
				</sec:authorize>
<!-- 				<div class="right_area">
					<button type="button" class="baseButton" onClick="location.href=''">▲TOP</button>
				</div> -->
			</div>		
		</div>	
	</c:if>	
		<div id="list-style">				
			<div class="sort_area">
				<div class="notice_check _check">
					<input type="checkbox" id="notice_hidden" class="noticeChk"> 
					<label for="notice_hidden">공지 숨기기</label>
				</div>
				<div id="listSizeSelectDiv" class="select_component2 " >
					<input type="hidden" value="${searchCriteria.pageLen}" id="pageLen">
					<a href="" onclick="toggleList(listSizeSelectDiv); return false;" class="selectBox">11</a>
					<ul class="select_list">
						<li onClick="location.href='${pageMk.makeQueryLen(5)}'">5개씩</li>
		 				<li onClick="location.href='${pageMk.makeQueryLen(10)}'">10개씩</li>
		 				<li onClick="location.href='${pageMk.makeQueryLen(20)}'">20개씩</li>
		 				<li onClick="location.href='${pageMk.makeQueryLen(30)}'">30개씩</li>
		 				<li onClick="location.href='${pageMk.makeQueryLen(40)}'">40개씩</li>
		 				<li onClick="location.href='${pageMk.makeQueryLen(50)}'">50개씩</li>
					</ul> 
				</div>				
			</div>
		</div>
		<div id="board_list">
			<table id="board_table">
				<tr class="tab_list">
					<th class="board_id"></th>
					<th class="board_brdname">게시판</th>
					<th class="board_title">제목</th>
					<th class="board_writer">작성자</th>
					<th class="board_date">작성일</th>
					<th class="board_hit">조회</th>
				</tr>
				<!-- items:배열,리스트  var:"변수명 " varState:"인덱스 변수명"-->
 				<c:forEach items="${noticeT}" var="boardVO">
				<c:if test="${boardVO.notice eq 'T'}">
					<tr id="notice1" class="tab_notice noticeChk" >
						<td class="board_id _notice"><span class="notice_id _bgcolorT">공지</span></td>
						<td class="board_name">전체공지</td>
						<td class="board_title_c _notice">
						<span class="_underline" onClick="location.href='/notice?mid=${boardVO.brdId}&post=${boardVO.postId}'">
								${boardVO.title}</span>

							<c:if test="${boardVO.replyCnt !=0}">
								<span style="font-size:14px; color:#23A41A;" >&#91;${boardVO.replyCnt}&#93;</span>
							</c:if>
						</td>
						<td class="board_writer">${boardVO.memNickName}</td>
						<td class="board_date td_font"><fmt:formatDate pattern="yy.MM.dd" value='${boardVO.regtime }' /></td>
						<td class="board_hit td_font">${boardVO.hit }</td>
					</tr>
				</c:if>
				</c:forEach>
				<c:set var="num" value="${pageMk.totalCount-((pageMk.cri.page-1)*pageMk.cri.pageLen) }" />	
				<c:forEach items="${list}" var="boardVO">
					<tr class="tab_line tr_hover">
					<!-- 현재 페이지 개수 -((현재페이지-1)*한  화면에 보여질 레코드의 개수) -->						
						<td class="board_id td_font">${num}</td>
						<td class="board_name">${boardVO.brdName}</td>
						<td class="board_title_c">
							<!-- 답글표시 -->
							<c:forEach begin="2" end="${boardVO.indent}">&nbsp;</c:forEach>
							<c:if test="${boardVO.indent != 0}">
								<span style="color:#14bf68;">&nbsp;└RE:</span>
							</c:if>
							<c:forEach items="${brdMenuName}" var="menu">
								<span class="_underline" onClick="location.href='?mid=${boardVO.brdId}&post=${boardVO.postId}'">
								${boardVO.title}</span>
							</c:forEach>
							<c:if test="${boardVO.replyCnt !=0}">
								<span style="font-size:14px; color:#23A41A;" >&#91;${boardVO.replyCnt}&#93;</span>
							</c:if>
						</td>
						<td class="board_writer">${boardVO.memNickName}</td>
						<td class="board_date td_font">${boardVO.regtimeB}
							<%-- <fmt:formatDate pattern="yy.MM.dd" value='${boardVO.regtime }' /> --%>
						</td>
						<td class="board_hit td_font">${boardVO.hit }</td>					
					</tr>
					<c:set var="num" value="${num-1}" />
				</c:forEach>
			</table>
		</div>
		<%-- <div id="register_btn">
			<sec:authorize access="isAuthenticated()">
				<a href="/board/register?brdKey=${brdKey}" class="baseButton skinYW">글쓰기</a>
			</sec:authorize>
		</div> --%>
		<div id="pageNav">		
			<c:if test="${pageMk.prev}">
				<span class="prev_btn">
					<a href="javascript:;" onClick="location.href='${pageMk.makeSearch(pageMk.startPage-1)}'">&#60; 이전</a>
					<%-- 	<a href="list${pageMk.makeQuery(pageMk.startPage-1,nav) }">&#60; 이전</a> --%>
				</span>
			</c:if>
			<c:forEach begin="${pageMk.startPage }" end="${pageMk.endPage }" var="pageNum">
				<c:if test="${pageNum!=0 }">
					<span <c:out value="${pageMk.cri.page==pageNum?'class=active':''}" />>
						<a href="javascript:;" class="_underline" onClick="location.href='${pageMk.makeSearch(pageNum)}'">${pageNum}</a>
					</span>
				</c:if>				
			</c:forEach>
			 	<c:if test="${pageMk.next && pageMk.endPage>0 }">
				 <span class="next_btn">
				 	<a href="javascript:;" onClick="location.href='${pageMk.makeSearch(pageMk.endPage+1)}'">다음	 &#62;</a>
				</span>
			</c:if> 
		</div>
		<div id="search_div">
			<div id="search_tit">
				<input type="hidden" name="searchType" id="searchType" value="tc"/>
				<span onclick="toggleList(search_tit); return false;" id="_target" class="_target">제목+내용</span>
				<ul class="_typeList">
					<li onClick="searchType('tc','제목+내용');">제목+내용</li>
					<li onClick="searchType('t','제목');">제목</li>
					<li onClick="searchType('w','글작성자');">글작성자</li>
<!-- 					<li onClick="searchType('rc','댓글내용');">댓글내용</li>
					<li onClick="searchType('rw','댓글작성자');">댓글작성자</li> -->
				</ul>
			</div>
			<div class="search_text">
				<input type="text" class="search_query" id="searchQuery" placeholder="검색어를 입력해주세요"/>
			</div>
			<div class="search_button">
				<button id="searchBtn" class="searchBtn" onClick="searchBtn('${pageMk.makeQuery(1)}')">검색</button>
			</div>
		</div>
	</div>
</body>
</html>
<%@ include file="../include/footer.jsp"%>
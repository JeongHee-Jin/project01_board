package org.project01.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

public class PageMaker {
	private int totalCount;			//총개수
	private int startPage;			//시작페이지
	private int endPage;			//끝페이지
	private boolean prev;			//이전페이지
	private boolean next;			//다음페이지
	private int displayPageNum=10;  //게시판 하단 페이지수(1~10,11~20)
	private SearchCriteria cri;		//검색기능
	
	public PageMaker(){}

	public PageMaker(int totalCount, int startPage, int endPage, boolean prev, boolean next, int displayPageNum,
			SearchCriteria cri) {
		super();
		this.totalCount = totalCount;
		this.startPage = startPage;
		this.endPage = endPage;
		this.prev = prev;
		this.next = next;
		this.displayPageNum = displayPageNum;
		this.cri = cri;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
	}
	private void calcData() {
//		Math.floor() : 소수점 이하를 버림한다.
//		Math.ceil() : 소수점 이하를 올림한다.
//		Math.round() : 소수점 이하를 반올림한다.
		
		//끝페이지(20) = 	(올림(현재 페이지(15)/페이지하단 수()10)*페이지하단 수(10))
		//1/10=>0.1 =>(올림)1*10=>10	//15/10=>1.5=>2*10=>20
		endPage=(int)(Math.ceil(cri.getPage()/(double)displayPageNum)*displayPageNum);
		//시작페이지(11)= 위에서 구한 끝페이지(20)-정해진 페이지 하단 수(10)+1;
		startPage=(endPage-displayPageNum)+1;
		//	(3)=(올림(현재페이지개수(25)/한페이지에 보여줄 게시물수(10))
		//25/10=>2.5=>3
		int tempEndPage =(int)(Math.ceil(totalCount/(double)cri.getPageLen()));
		//20>3
		if(endPage>tempEndPage) {
			endPage=tempEndPage;
		}		
		//이전페이지 = 시작페이지가 1이면 x 아니면 o //이전페이지가 1이면 이전 페이지 이동 불가능
		prev=startPage==1 ? false:true;
		//다음페이지 = 끝페이지(10)*게시물수(20)>=25	//30>=25 true //다음 페이지가 true이면 다음페이지 이동 가능
		next=endPage*cri.getPageLen()>=totalCount ? false : true;		
	}
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public SearchCriteria getCri() {
		return cri;
	}

	public void setCri(SearchCriteria cri) {
		this.cri = cri;
	}
	//MyBatis는 Mapper를 활용하여 DB에 특정 쿼리문을 날림.
	//내부적으로 몇개의 표현식을 가지고 있어서 상황에 따른 SQL 생성가능. 이것을 동적 SQL이라 함 

	//UriComponents :동적SQL, uri를 동적으로 생성해주는 클래스,UriComponentsBuilder와 같이 씀
	//파라미터가 조합된 URI를 손쉽게 만들어 주어서 코드상에서 직접 문자열을 조합할 때 생기는 실수를 방지
	//원하는 URI로 각각의 링크를 생성할 수 있어서 Rest 스타일로 개발하는데 편리
	
	//Comstruct URI 생성 query parameter
						//UriComponentsBuilder.fromPath("")
	
	//페이지수(게시판 기본형:검색X)
	public String makeQuery(int page) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("pageLen", cri.getPageLen()).build();
		return uriCom.toUriString();
	}
	public String makeQuery(int page,int mid) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()
				.queryParam("mid", mid)
				.queryParam("page", page)
				.queryParam("pageLen", cri.getPageLen()).build();
		return uriCom.toUriString();
	}
	public String makeQuery(int page,int mid,int sub) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()
				.queryParam("mid", mid)
				.queryParam("sub", sub)
				.queryParam("page", page)
				.queryParam("pageLen", cri.getPageLen()).build();
		return uriCom.toUriString();
	}
	//보여줄 게시물수
	public String makeQueryLen(int pageLen) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()
				.queryParam("page", 1)
				.queryParam("pageLen", pageLen).build();
		return uriCom.toUriString();
	}
	
	public String makeQueryLen(int mid,int pageLen) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()
				.queryParam("mid", mid)
				.queryParam("page", 1)
				.queryParam("pageLen", pageLen).build();
		return uriCom.toUriString();
	}
	
	public String makeQueryLen(int mid,int sub,int pageLen) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()
				.queryParam("mid", mid)
				.queryParam("sub", sub)
				.queryParam("page", 1)
				.queryParam("pageLen", pageLen).build();
		return uriCom.toUriString();
	}
	
	
	//기본/검색 페이지수
	public String makeSearch(int page) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()				
				.queryParam("page",page)
				.queryParam("pageLen",cri.getPageLen())
				.queryParam("searchType",cri.getSearchType())
				.queryParam("keyword",cri.getKeyword())
				.build();
		return uriCom.toUriString();
	}
	public String makeSearch(int page,int mid) {		
		UriComponents uriCom = UriComponentsBuilder.newInstance()	
				.queryParam("mid",mid)
				.queryParam("page",page)
				.queryParam("pageLen",cri.getPageLen())
				.queryParam("searchType",cri.getSearchType())
				.queryParam("keyword",cri.getKeyword())
				.build();
		return uriCom.toUriString();
	}
	public String makeSearch(int page,int mid,int sub) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()	
				.queryParam("mid",mid)
				.queryParam("sub", sub)
				.queryParam("page",page)
				.queryParam("pageLen",cri.getPageLen())
				.queryParam("searchType",cri.getSearchType())
				.queryParam("keyword",cri.getKeyword())
				.build();
		return uriCom.toUriString();
	}
	public String readPage(int page) {
		UriComponents uriCom = UriComponentsBuilder.newInstance()
				.queryParam("postId", page).build();
		return uriCom.toUriString(); 
	}

	@Override
	public String toString() {
		return "PageMaker [totalCount=" + totalCount + ", startPage=" + startPage + ", endPage=" + endPage + ", prev="
				+ prev + ", next=" + next + ", displayPageNum=" + displayPageNum + ", cri=" + cri + "]";
	}	
}

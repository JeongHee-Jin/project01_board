package org.project01.domain;

public class SearchCriteria{
	private String searchType;
	private String keyword;
	
	private int page;		//현재페이지
	private int pageLen;	//한페이지에 보여줄 게시물 수
	
	public SearchCriteria() {
		this.page=1;
		this.pageLen=10;
	}
	public SearchCriteria(int pageLen) {
		this.pageLen = pageLen;
	}
	public SearchCriteria(int page, int pageLen,String searchType, String keyword) {
		this.page = page;
		this.pageLen = pageLen;
		this.searchType = searchType;
		this.keyword = keyword;
	}	
	public int getPage() {
		return page;
	}	
	public void setPage(int page) {
		if(page<=0) {
			this.page=1;
			return;
		}
		this.page = page;
	}
	// method for MyBatis SQL Mapper
	public int getPageLen() {
		return pageLen;
	}

	public void setPageLen(int pageLen) {
		if(pageLen<=0 || pageLen>50) {
			this.pageLen=10;
		}
		this.pageLen = pageLen;
	}
	// method for MyBatis SQL Mapper
	public int getPageStart() {
		return (this.page-1)*pageLen;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + ", page=" + page + ", pageLen="
				+ pageLen + "]";
	}
	
	
	
}

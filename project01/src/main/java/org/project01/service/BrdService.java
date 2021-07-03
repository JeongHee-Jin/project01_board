package org.project01.service;

import java.util.List;
import java.util.Map;

import org.project01.domain.BoardAttachVO;
import org.project01.domain.BrdVO;

public interface BrdService {
	
	//선택한 메뉴,링크
	public String tabMenuName(String nav) throws Exception;

	//게시판1 게시물 리스트
	public List<BrdVO> listPageCri(Map<String, Object> map) throws Exception;
	//게시판2 게시물 리스트
	public List<BrdVO> listPageCri2(Map<String, Object> map) throws Exception;
	
	//게시물 총 수1
	public int listPageCnt(Map<String, Object> map) throws Exception;
	//게시물 총 수2
	public int listPageCnt2(Map<String, Object> map) throws Exception;
	//공지글 출력
	public List<BrdVO> noticeList(String nav) throws Exception;
	//게시판별 공지출력
	public List<BrdVO> noticeList2(String nav, String mid);
	
	//게시물 글쓰기
	public void regist(BrdVO vo) throws Exception;
	//게시판 메뉴 이름 출력
	public List<Map<String,Object>> brdMenuList(String nav) throws Exception;
	//게시판 이름(선택) 출력
	public List<Map<String,Object>> brdNameList(String nav) throws Exception;
	//게시판 카테고리(선택)출력
	public List<Map<String,Object>> cateNameList(String brdNameKey) throws Exception;
		
	//게시물 읽기
	public BrdVO read(Map<String, Object> map,int a) throws Exception;	
	//게시물 수정
	public void modify(BrdVO vo) throws Exception;
	//게시물 삭제
	public void remove(BrdVO vo) throws Exception;
	//답글달기
	public void commentaire(BrdVO vo) throws Exception;

	//글쓰기-게시판리스트(mid로 cla값찾기)
	public List<Map<String, Object>> brdNameListCla(String mid) throws Exception;
	//글쓰기-카테고리리스트
	public List<Map<String, Object>> cateNameListMid(String mid) throws Exception;
	
	//게시판메뉴이름출력
	public List<Map<String, Object>> brdMenuName(String cla) throws Exception;
	//게시판이름출력
	public List<Map<String,String>> brdName(String mid) throws Exception;
	//첨부파일 리스트
	public List<BoardAttachVO> getAttachList(Map<String,Object> map) throws Exception;

	public List<Map<String,String>> brdNameListNon() throws Exception;

}

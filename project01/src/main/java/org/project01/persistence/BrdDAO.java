package org.project01.persistence;

import java.util.List;
import java.util.Map;
import org.project01.domain.BrdVO;

public interface BrdDAO {
	
	//탭메뉴링크
	public String tabMenuName(String nav) throws Exception;
	//게시판1-게시물 리스트(검색포함)
	public List<BrdVO> listPageCri(Map<String, Object> map) throws Exception;
	//게시판2-게시물 리스트(검색포함)
	public List<BrdVO> listPageCri2(Map<String, Object> map) throws Exception;
	//게시물수1(검색포함)
	public int listPageCnt(Map<String, Object> map) throws Exception;
	//게시물수2(검색포함)
	public int listPageCnt2(Map<String, Object> map) throws Exception;

	//공지글
	public List<BrdVO> noticeList(String nav) throws Exception;
	//게시판별 공지글
	public List<BrdVO> noticeList2(Map<String, Object> map);
	
	//글쓰기
	public void create(BrdVO vo)throws Exception;

	//게시판분류list
	public List<Map<String, Object>> brdMenuList(String nav) throws Exception;
	//게시판이름 list
	public List<Map<String,Object>> brdNameList(String nav)throws Exception;
	
	//선택한 게시판의 카테고리(말머리)
	public List<Map<String,Object>> cateNameList(String nav)throws Exception;
	
	//게시물읽기
	public BrdVO readNotice(Map<String, Object> map) throws Exception;
	public BrdVO readCmu1(Map<String, Object> map) throws Exception;
	public BrdVO readCmu2(Map<String, Object> map) throws Exception;
	
	//게시물 조회 수정(+1)
	public void hitUpdate(Map<String, Object> map)throws Exception;
	//게시물 수정
	public void update(BrdVO vo)throws Exception;
	//게시물 삭제
	public void delete(BrdVO vo)throws Exception;
	
	//답글작성
	public void insertCommentaire(BrdVO vo)throws Exception;
	
	//댓글수 수정
	public void updateReplyCnt(int brdId,String brdKey,int i)throws Exception;
	
	//글쓰기-게시판리스트(mid로 cla값찾기)
	public List<Map<String, Object>> brdNameListCla(String mid) throws Exception;
	//글쓰기-카테고리리스트
	public List<Map<String, Object>> cateNameListMid(String mid) throws Exception;

	//게시판 메뉴 이름
	public List<Map<String, Object>> brdMenuName(String cla) throws Exception;
	//게시판 이름
	public List<Map<String,String>> brdName(String mid) throws Exception;
	//댓글수변경
	public void updateReplyCnt(Map<String, Object> map) throws Exception;
	
	//게시판 전체
	public List<Map<String, String>> brdNameListNon() throws Exception;
	//메인페이지 인기게시물 5개
	public List<Map<String, String>> mainHotList() throws Exception;

}

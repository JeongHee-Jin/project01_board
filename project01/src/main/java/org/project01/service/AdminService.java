package org.project01.service;

import java.util.List;
import java.util.Map;

public interface AdminService {

	//유저 리스트
	public List<Map<String, Object>> getUserList(Map<String, Object> map) throws Exception;
	//유저 리스트 수
	public int userTotal(Map<String, Object> map) throws Exception;
	//선택유저 상태변경
	public int chkUserRoleModify(Map<String,Object> map) throws Exception;
	//게시판메뉴 불러오기
	public List<Map<String, Object>> brdMenuList()  throws Exception;
	//게시판 리스트 불러오기
	public List<Object> boardList(int menuIdx) throws Exception;
	//카테고리 리스트 불러오기
	public List<Object> cateList(int brdIdx) throws Exception;
	
	//게시판 메뉴 추가
	public int addMenuName(Map<String, Object> map)  throws Exception;
	//게시판 추가
	public int addBoardName(Map<String, Object> map) throws Exception;
	//카테고리 추가
	public int addCateName(Map<String, Object> map) throws Exception;
	
	//게시판 메뉴 수정
	public int brdMenuModify(Map<String, Object> map) throws Exception;
	//게시판 메뉴 삭제
	public int brdMenuDelete(Map<String, Object> map) throws Exception;
	//게시판 수정
	public int brdModify(Map<String, Object> map) throws Exception;
	//게시판 삭제
	public int brdDelete(Map<String, Object> map) throws Exception;
	//카테고리 수정
	public int cateModify(Map<String, Object> map) throws Exception;
	//카테고리 삭제
	public int cateDelete(Map<String, Object> map) throws Exception;
	
}

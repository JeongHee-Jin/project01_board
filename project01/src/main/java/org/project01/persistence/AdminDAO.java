package org.project01.persistence;

import java.util.List;
import java.util.Map;

import org.project01.domain.MemberVO;


public interface AdminDAO {
	//유저 리스트
	public List<Map<String, Object>> getUserList(Map<String, Object> map) throws Exception;
	//유저 리스트 수
	public int userTotal(Map<String, Object> map) throws Exception;
	//선택유저 활동중지
	public int chkUserRoleModify(Map<String,Object> map) throws Exception;
	//게시판 메뉴 불러오기
	public List<Map<String, Object>> brdMenuList() throws Exception;
	//게시판 리스트
	public List<Object> boardList(int menuIdx) throws Exception;
	//카테고리 리스트
	public List<Object> cateList(int brdIdx) throws Exception;
	//게시판 메뉴 추가
	public int addMenuName(Map<String, Object> map) throws Exception;
	//게시판  추가
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
	
	//탈퇴한 유저 리스트
	public List<MemberVO> levMemList() throws Exception;
	public void deleteMem(String userId) throws Exception;
	

}

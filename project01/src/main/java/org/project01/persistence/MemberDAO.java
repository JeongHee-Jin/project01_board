package org.project01.persistence;

import java.util.List;
import java.util.Map;

import org.project01.domain.MemberVO;
import org.project01.domain.UserDetailsVO;

public interface MemberDAO {
	
	//id 중복체크
	public int idCheck(String userId) throws Exception;
	//닉네임 중복체크
	public int nickCheck(String nickName) throws Exception;
	//이메일 중복체크
	public int emailCheck(String email) throws Exception;
	//회원가입
	public int join(MemberVO vo) throws Exception;
	//로그인 아이디체크
	public int loginCheck(MemberVO vo) throws Exception;
	//로그인
	public MemberVO login(MemberVO vo)throws Exception;
	//ID찾기 (정보비교)
	public int findId(MemberVO vo) throws Exception;
	//PW찾기 (정보비교)
	public int findPw(MemberVO vo) throws Exception;
	//ID값 불러오기
	public String resultId(MemberVO vo) throws Exception;
	//id로 정보 가져오기
	public UserDetailsVO getUserInfo(String userId) throws Exception;
	//비밀번호->임시 비밀번호로 변경
	public void changePw(Map<String, String> map) throws Exception;
	//마이페이지
	public MemberVO mypageInfo(String userId)throws Exception;
	
	//회원정보수정
	public int infoModify(Map<String, String> map) throws Exception;
	//내가 쓴 게시물 가져오기
	public List<Map<String, Object>> myBoard(Map<String, Object> map) throws Exception;
	//내가 쓴 게시물 수
	public int myBoardCnt(String userId) throws Exception;
	//선택한 내 게시물 삭제
	public int myBoardDel(List<String> postIdArr) throws Exception;	
	//내가 쓴 댓글
	public List<Map<String, Object>> myReply(Map<String, Object> map) throws Exception;
	//내가 쓴 댓글 수
	public int myReplyCnt(String userId) throws Exception;
	//내가 쓴 댓글 삭제
	public int myReplyDel(List<String> replyIdArr) throws Exception;
	//탈퇴하기
	public int homeLeave(String userId) throws Exception;
	
	public int linkLogin(Map<String, String> map) throws Exception;
	
	public UserDetailsVO getUserInfoDetail(String userId) throws Exception;
		
}

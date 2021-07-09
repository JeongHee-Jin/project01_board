package org.project01.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.project01.domain.MemberVO;
import org.project01.domain.UserDetailsVO;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace = "org.project01.mappers.MemberMapper";

	//아이디 중복체크
	@Override
	public int idCheck(String userId) throws Exception{
		return session.selectOne(namespace+".idCheck",userId);
	}
	//닉네임 중복체크
	@Override
	public int nickCheck(String nickName) throws Exception {
		return session.selectOne(namespace+".nickCheck",nickName);
	}
	//이메일 중복체크
	@Override
	public int emailCheck(String email) throws Exception {
		return session.selectOne(namespace+".emailCheck",email);
	}
	//회원가입
	@Override
	public int join(MemberVO vo) throws Exception {
		return session.insert(namespace+".join",vo);
	}
	//로그인 아이디체크
	@Override
	public int loginCheck(MemberVO vo) throws Exception {
		return session.selectOne(namespace+".loginCheck",vo);
	}
	//로그인
	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		return session.selectOne(namespace+".loginBcrypt",vo);
	}
	//ID찾기 (정보비교)
	@Override
	public int findId(MemberVO vo) throws Exception {
		return session.selectOne(namespace+".findId",vo);
	}
	//PW찾기 (정보비교)
	@Override
	public int findPw(MemberVO vo) throws Exception {
		return session.selectOne(namespace+".findPw",vo);
	}
	//ID값 불러오기
	@Override
	public String resultId(MemberVO vo) throws Exception {
		return session.selectOne(namespace+".resultId",vo);
	}
	//
	@Override
	public UserDetailsVO getUserInfo(String userId) throws Exception {
		return session.selectOne(namespace + ".userInfo", userId);
	}
	//비밀번호 -> 임시 비밀번호로 변경
	@Override
	public void changePw(Map<String, String> map) throws Exception {
		session.update(namespace+".changePw",map);		
	}
	//마이페이지
	@Override
	public MemberVO mypageInfo(String userId) throws Exception{
		return session.selectOne(namespace+".mypageInfo",userId);
	}
	
	//회원정보수정
	@Override
	public int infoModify(Map<String, String> map) throws Exception {
		int cnt=0;
		if(map.containsKey("nick")) {
			cnt=session.update(namespace+".nickModify",map);
		}else if(map.containsKey("pw")) {
			cnt=session.update(namespace+".pwModify",map);
		}else if(map.containsKey("email")) {
			cnt=session.update(namespace+".emailModify",map);
		}else if(map.containsKey("phone")){
			cnt=session.update(namespace+".phoneModify",map);
		}else {
			cnt=session.update(namespace+".addrModify",map);
		}
		return cnt;
	}
	//내가 쓴 게시물 가져오기
	@Override
	public List<Map<String, Object>> myBoard(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return session.selectList("org.project01.mappers.BoardMapper"+".myBoard",map);
	}
	//내가 쓴 게시물 수
	@Override
	public int myBoardCnt(String userId) throws Exception {
		return session.selectOne("org.project01.mappers.BoardMapper"+".myBoardCnt",userId);
	}
	//선택한 글 삭제
	@Override
	public int myBoardDel(List<String> postIdArr) throws Exception {
		return session.delete("org.project01.mappers.BoardMapper"+".myBoardDel",postIdArr);
	}
	//내가 쓴 댓글
	@Override
	public List<Map<String, Object>> myReply(Map<String, Object> map) throws Exception {
		return session.selectList("org.project01.mappers.BoardMapper"+".myReply",map);
	}
	//내가 쓴 댓글 수
	@Override
	public int myReplyCnt(String userId) throws Exception {
		return session.selectOne("org.project01.mappers.BoardMapper"+".myReplyCnt",userId);
	}
	//
	@Override
	public int myReplyDel(List<String> replyIdArr) throws Exception {
		return session.delete("org.project01.mappers.BoardMapper"+".myReplyDel",replyIdArr);
	}
	@Override
	public int homeLeave(String userId) throws Exception {
		return session.update(namespace+".memLeave",userId);
	}
	@Override
	public int linkLogin(Map<String, String> map) {
		return session.insert(namespace+".linkLogin",map);
	}
	@Override
	public UserDetailsVO getUserInfoDetail(String userId) throws Exception {
		return session.selectOne(namespace + ".userInfoDetail", userId);
	}
}

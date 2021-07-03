package org.project01.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import org.project01.domain.MemberVO;
import org.project01.persistence.MemberDAO;
import org.project01.util.MailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sun.mail.smtp.*;
@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	private MemberDAO memberDAO;
	
	@Inject
	private JavaMailSender MailServiceImpl;	

	@Autowired 
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	//아이디 중복체크
	@Override
	public int idCheck(String userId) throws Exception{
		return memberDAO.idCheck(userId);
	}
	//닉네임 중복체크
	@Override
	public int nickCheck(String nickName) throws Exception {
		return memberDAO.nickCheck(nickName);
	}
	//이메일 중복체크
	@Override
	public int emailCheck(String email) throws Exception {
		return memberDAO.emailCheck(email);
	}
	
	//이메일코드생성
	@Override
	public String creatCode() {
		Random  ran = new Random();
		StringBuffer sb = new StringBuffer();
		do {
			int num = ran.nextInt(75)+48;
			if((num>=48 && num<=57) || (num>=65 && num<=90) || (num>=97 && num<=122)) {
				sb.append((char)num);
			}else if(num>=47){sb.append((int)num);
			}else {continue;}
		} while (sb.length() < 7);
		return sb.toString();
	}
	
	//회원가입 이메일전송
	@Transactional
	@Override
	public void sendEmailCode(String code,String userEmail) {
		try {
			MailHandler mailHandler = new MailHandler(MailServiceImpl);
			//받는 사람
			mailHandler.setTo(userEmail);
			//보내는 사람
			mailHandler.setFrom("BoardBell");
			//제목
			mailHandler.setSubject("[BoardBell] 회원가입 인증코드 입니다.");
			//내용
			mailHandler.setText(
				new StringBuffer().append("<p><span style=\"font-size: 12pt;\">안녕하세요 <b>"
						+ "<span style=\"font-size: 12pt;\">BoardBell</span></b> 입니다.</span>" + 
						"</p><p><span style=\"font-size: 12pt;\">고객님의 인증 번호를 알려드립니다.</span></p>"
						+ "<p><br></p><p><br></p><p><span style=\"font-size: 18pt;\"> 인증번호 :&nbsp;"
						+ "<b>"+code+"</b></span></p>").toString());
			mailHandler.send();
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	//임시비밀번호 전송
	@Transactional
	@Override
	public void sendEmailPw(String code,String userEmail) {
		try {
			String endcodedPassword = bcryptPasswordEncoder.encode(code);
			//비밀번호->임시 비밀번호로 변경
			Map<String,String> map=new HashMap<String,String>();
			map.put("pw",endcodedPassword);
			map.put("email",userEmail);
			memberDAO.changePw(map);
			
			MailHandler mailHandler = new MailHandler(MailServiceImpl);
			//받는 사람
			mailHandler.setTo(userEmail);
			//보내는 사람
			mailHandler.setFrom("BoardBell");
			//제목
			mailHandler.setSubject("[BoardBell] 임시비밀번호 입니다.");
			//내용
			mailHandler.setText(
				new StringBuffer().append("<p align=\"left\" style=\"text-align: left;\"></p>"
						+ "<div style=\"font-size: 12pt;\">안녕하세요 <b>BoardBell</b>입니다.</div>" + 
						"<div style=\"font-size: 12pt;\">고객님의 임시 비밀번호를 알려드립니다.</div>" + 
						"<div style=\"font-size: 12pt;\">임시 비밀번호로 로그인하신 후 원하시는 비밀번호로 수정해서 이용하시기 바랍니다.</div>" + 
						"<br><div style=\"font-size: 20px;\">임시 비밀번호:&nbsp;<b>"+code+"</b></div><br><br><br>" + 
						"<div style=\"font-size: 13px;\">※ 참고하세요!​</div>" + 
						"<div style=\"font-size: 13px;\">임시 비밀번호로 로그인 하신 후, 반드시 비밀번호를 수정해 주세요.</div>" + 
						"<div style=\"font-size: 13px;\">비밀번호는 쇼핑몰 로그인 &gt; 마이페이지 &gt;회원정보수정 에서 수정하실 수 있습니다.</div>" + 
						"<div style=\"font-size: 13px;\">안전한 서비스 이용을 위해서 비밀번호는 정기적으로 변경해주는 것이 좋습니다.</div>").toString());
			mailHandler.send();
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}	
	
//	@Autowired
//	private JavaMailSender mailSender;
//
//	@Bean
//	public JavaMailSenderImpl mailSender() {
//		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//		javaMailSender.setProtocol("smtp");
//		javaMailSender.setHost("127.0.0.1");
//		javaMailSender.setPort(25);
//		return javaMailSender;
//	} 
	//회원가입/비밀번호 암호화
	@Override
	public int join(MemberVO vo) throws Exception {
		String endcodedPassword = bcryptPasswordEncoder.encode(vo.getUserPw());
		vo.setUserPw(endcodedPassword);	
		return memberDAO.join(vo);
	}
	//로그인 아이디체크
	@Override
	public int loginCheck(MemberVO vo) throws Exception {
		return memberDAO.loginCheck(vo);
	}
	//ID-PW 찾기 (정보비교)
	@Override
	public int findIdPw(MemberVO vo) throws Exception {
		if(vo.getUserId()==null){
			return memberDAO.findId(vo);
		}else {
			return memberDAO.findPw(vo);
		}
	}
	//ID값 불러오기
	@Override
	public String resultId(MemberVO vo) throws Exception {
		return memberDAO.resultId(vo);
	}
	
	//로그인
	@Override
	public MemberVO login(MemberVO vo) throws Exception {
		String endcodedPassword = bcryptPasswordEncoder.encode(vo.getUserPw());
		vo.setUserPw(endcodedPassword);	
		return memberDAO.login(vo);
	}
	//마이페이지 정보
	@Override
	public MemberVO mypageInfo(String userId)throws Exception {
		return memberDAO.mypageInfo(userId);
	}
	//회원정보수정
	@Override
	public int infoModify(Map<String, String> map) throws Exception {
		if(map.containsKey("pw")) {
			String endcodedPassword = bcryptPasswordEncoder.encode(map.get("pw"));
			map.put("pw",endcodedPassword);
		}		
		return memberDAO.infoModify(map);
	}
	
	//내가 쓴 게시물 가져오기
	@Override
	public List<Map<String, Object>> myBoard(Map<String, Object> map) throws Exception {
		return memberDAO.myBoard(map);
	}
	//내가 쓴 게시물 수
	@Override
	public int myBoardCnt(String userId) throws Exception {
		return memberDAO.myBoardCnt(userId);
	}
	//선택한 내 게시물 삭제
	@Override
	public int myBoardDel(List<String> postIdArr) throws Exception {
		return memberDAO.myBoardDel(postIdArr);	
	}
	//내가 쓴 댓글 가져오기
	@Override
	public List<Map<String,Object>> myReply(Map<String, Object> map) throws Exception{
		return memberDAO.myReply(map);
	}
	//내가 쓴 댓글 수
	@Override
	public int myReplyCnt(String userId) throws Exception{
		return memberDAO.myReplyCnt(userId);
	}
	//내가 쓴 댓글 삭제
	@Override
	public int myReplyDel(List<String> replyIdArr) throws Exception {
		return memberDAO.myReplyDel(replyIdArr);
	}
	//탈퇴하기
	@Override
	public int homeLeave(String userId) throws Exception {
		return memberDAO.homeLeave(userId);	
	}
	//계정연동회원가입
	@Override
	public int linkLogin(Map<String, String> map) throws Exception {
		System.out.println(map.get("userPw"));
		String endcodedPassword = bcryptPasswordEncoder.encode(map.get("userPw"));
		System.out.println(endcodedPassword);
		map.put("userPw",endcodedPassword);
		System.out.println(map);
		return memberDAO.linkLogin(map);		
	}
}

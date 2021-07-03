package org.project01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.project01.domain.MemberVO;
import org.project01.domain.PageMaker;
import org.project01.domain.SearchCriteria;
import org.project01.domain.UserDetailsVO;
import org.project01.link.KakaoAuthBO;
import org.project01.link.NaverAuthBO;
import org.project01.persistence.MemberDAO;
import org.project01.service.MemberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.github.scribejava.core.model.OAuth2AccessToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
@RequestMapping("/member/*")
public class MemberController{
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
		
	@Inject
	private MemberService service;
	@Inject
	private MemberDAO memberDAO;
	@Inject
	private NaverAuthBO naverAuthBO;
	@Inject
	private KakaoAuthBO kakaoAuthBO;
	@Autowired
	private BCryptPasswordEncoder pwEncoding;
		
	@PostMapping("/ll")
	public @ResponseBody String getget() throws Exception{
		System.out.println("끝끝");
		return "sdf";
	}
	//회원가입 페이지
	@GetMapping("/join")
	public void getJoin() throws Exception{
		logger.info("가입 페이지");
	}
	//아이디 중복체크
	@PostMapping("/id_check")
	public @ResponseBody int idCheck(String userId) throws Exception{
		logger.info("아이디 중복 체크");
		int idCheck=0;
		try {
			idCheck=service.idCheck(userId);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return idCheck;
	}
	//닉네임 중복체크
	@PostMapping("/nick_check")
	public @ResponseBody int nickCheck(String nickName) throws Exception{
		logger.info("닉네임 중복 체크");
		int nickCheck=0;
		try {
			nickCheck=service.nickCheck(nickName);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return nickCheck;
	}
	//이메일 중복체크
	@PostMapping("/email_check")
	public @ResponseBody int emailCheck(String email) throws Exception{
		logger.info("이메일 중복 체크");
		int emailCheck=0;
		try {
			emailCheck= service.emailCheck(email);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return emailCheck;
	}
	
	String code=null;	
	//회원가입 이메일 코드 보내기
	@PostMapping("/email_code")
	public @ResponseBody String emailcode(String userEmail) throws Exception{
		logger.info("회원가입 이메일 코드 보내기");		
		try {
			code=service.creatCode();
			service.sendEmailCode(code,userEmail);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return code;
	}
	
	//회원가입POST
	@PostMapping(value="/join", produces = "text/html; charset=utf8")
	public String postJoin(MemberVO vo,RedirectAttributes rttr) throws Exception{
		logger.info("회원가입 처리");
		int result=0;
		try {
			result=service.join(vo);
			if(result==1) {
				rttr.addFlashAttribute("joinMsg","success");
			}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return "redirect:/";
	}
	//로그인 페이지 GET
	//실제 로그인은 "/login"을 통해 Security에서 실행됨 :post방식으로 처리
	//Referer: 이전접속경로  /user-agent: 유저의 시스템,브라우저 정보 /host 접속IP
	//WL-Proxy-Client-IP, Proxy-Client-IP, X-Forwarded-For: 클라이언트 ip 주소
	
	@GetMapping(value="/login")
	public void getLogin(HttpServletRequest request) throws Exception{
		logger.info("로그인 페이지");
		try {
			String referrer = request.getHeader("Referer");
			if(!referrer.contains("/member/")){ //이전 경로에 /member/가 없으면 저장
				request.getSession().setAttribute("prevPage", referrer); 
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
	}
	@PostMapping(value="/login")
	public void postLogin() throws Exception{
		logger.info("로그인처리");
	}
	@PostMapping(value="/linklogin")	
	public @ResponseBody String getLinkLogin(HttpServletRequest request, HttpSession session,String link) throws Exception{
		String serverUrl = request.getScheme()+"://"+request.getServerName();
		if(request.getServerPort() != 80) {
			serverUrl = serverUrl + ":" + request.getServerPort();
		}
		System.out.println(serverUrl);
		String authUrl="";
		try {
			if(link.equals("naver")) {
				/* 네이버아이디로 인증 URL을 생성하기 위하여 naverAuthBO클래스의 getAuthorizationUrl메소드 호출 */
		        authUrl = naverAuthBO.getAuthorizationUrl(session,serverUrl);
			}else {
				authUrl = kakaoAuthBO.getAuthorizationUrl(session,serverUrl); //카카오
			}
			System.out.println(authUrl);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		return authUrl;
	}

	@GetMapping(value="/callback_login")
	public void callback2() throws Exception{
		logger.info("링크로그인처리");
	}
	// 네이버 연동정보 조회
	//네이버 로그인 성공시 callback호출 메소드
	@RequestMapping(value = "/naver_callback", method = { RequestMethod.GET, RequestMethod.POST })	
	public String callback(HttpServletRequest request,Model model, @RequestParam String code, 
			@RequestParam String state, HttpSession session,RedirectAttributes rttr) throws Exception {
		logger.info("네이버로그인"+code+" : "+state);
		//현재페이지주소값
		String serverUrl = request.getScheme()+"://"+request.getServerName();
		if(request.getServerPort() != 80) {
			serverUrl = serverUrl + ":" + request.getServerPort();
		}
		OAuth2AccessToken oauthToken=naverAuthBO.getAccessToken(session, code, state,serverUrl); //인증토큰값 가져오기
		if(oauthToken == null) {
			model.addAttribute("msg", "네이버 로그인 access 토큰 발급 오류 입니다.");
			model.addAttribute("url", "/");
			return "/";
		}
		
		String apiResult = naverAuthBO.getUserProfile(oauthToken,serverUrl); //사용자 정보 가져오기
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;		
		JSONObject response_obj = (JSONObject) jsonObj.get("response");

		// 프로필 조회
		String id = (String) response_obj.get("id");
		String email = (String)response_obj.get("email");
		String pw=id.substring(2,10);	
		int join=service.idCheck(id.substring(0,5)); //회원가입여부
		if(join==0) {//회원가입		
			Map<String,String> map=new HashMap<String,String>();
			map.put("userId",id.substring(0,5));
			map.put("userPw",pw);
			map.put("email",email);
			map.put("nick","nv_"+id.substring(0,5));
			map.put("link","naver");
			service.linkLogin(map);
		}
		session.setAttribute("link", "link");
		//로그인
		rttr.addFlashAttribute("id", id.substring(0,5));
		rttr.addFlashAttribute("pw", pw);
		return "redirect:/member/callback_login";
	}	

	@RequestMapping(value="/kakao_callback",method= {RequestMethod.GET,RequestMethod.POST})
	public String kakaoCallBack(HttpServletRequest request,RedirectAttributes rttr,Model model,
			@RequestParam String state,@RequestParam String code,HttpSession session)throws Exception{
		//@RequestParam String state,
		logger.info("카카오로그인");
		String serverUrl = request.getScheme()+"://"+request.getServerName();
		if(request.getServerPort() != 80) {
			serverUrl = serverUrl + ":" + request.getServerPort();
		}
		
		OAuth2AccessToken oauthToken;
		oauthToken = kakaoAuthBO.getAccessToken(session, code, state,serverUrl);
		if(oauthToken == null) {
			model.addAttribute("msg", "카카오 로그인 access 토큰 발급 오류 입니다.");
			model.addAttribute("url", "/");
			return "/";
		}
		System.out.println(oauthToken);
		// 로그인 사용자 정보를 읽어온다
		String apiResult = kakaoAuthBO.getUserProfile(oauthToken,serverUrl);
		JSONParser jsonParser = new JSONParser();
		Object obj = jsonParser.parse(apiResult);	
		JSONObject jsonObj = (JSONObject) obj;
		String id = String.valueOf(jsonObj.get("id"));
		int join=service.idCheck(id.substring(0,8)); //가입된아이디인가?
		if(join==0) {//회원가입
			// 사용자 정보 등록		
			Map<String,String> map=new HashMap<String,String>();
			map.put("userId",id.substring(0,8));
			map.put("userPw",id);
			map.put("email",id+"@kakao.com");
			map.put("nick","ka_"+id.substring(0,5));
			map.put("link","kakao");
			service.linkLogin(map);
		}
		session.setAttribute("link", "link");
		rttr.addFlashAttribute("id", id.substring(0,8));
		rttr.addFlashAttribute("pw", id);
		return "redirect:/member/callback_login";
	}
//
//	    if (naverUniqueNo != null && !naverUniqueNo.equals("")) {
//
//	        /** 
//	        
//	            TO DO : 리턴받은 naverUniqueNo 해당하는 회원정보 조회 후 로그인 처리 후 메인으로 이동
//	        
//	        */
//
//	    // 네이버 정보조회 실패
//	    } else {
//	        throw new ErrorMessage("네이버 정보조회에 실패했습니다.");
//	    }
//
//	}

	//로그아웃POST
	@PostMapping("/logout")
	public void postLogout(HttpServletRequest request ) throws Exception{
		logger.info("로그아웃");
		String referrer=request.getHeader("Referer");
		try {
			request.getSession().setAttribute("prevPage", referrer);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}
	//ID 찾기 페이지
	@GetMapping("/find_id")
	public void getFindIdPage() throws Exception{
		logger.info("아이디 찾기 페이지");
	}
	//PW 찾기 페이지
	@GetMapping("/find_pw")
	public void getFindPwPage() throws Exception{
		logger.info("비밀번호 찾기 페이지");
	}
	
	//id-pw 찾기 (정보비교)
	@PostMapping("/find_id_pw")
	public @ResponseBody int findId(MemberVO vo) throws Exception{
		logger.info("아이디 / 비밀번호 찾기를 위한 정보 비교 처리");
		int findId=0;
		try {
			findId=service.findIdPw(vo);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		return findId;
	}
	//id-pw찾기 결과페이지
	@PostMapping("/resultIdPw")
	public void resultIdPage(MemberVO vo,Model model) throws Exception{
		logger.info("id값 출력 및 임시비밀번호 발급");
		try {
			if(vo.getUserId()==null) {
				model.addAttribute("idText",service.resultId(vo));
				model.addAttribute("result","id");
			}else {
				//임시비밀번호 생성
				String code=service.creatCode();
				//코드전송
				service.sendEmailPw(code,vo.getEmail());
				model.addAttribute("result","pw");	
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}			
	}
	//권한없는 사용자 접속시 처리
	@RequestMapping("/denied")
	public String denied(Model model, Authentication auth, HttpServletRequest req){
		AccessDeniedException ade = (AccessDeniedException) req.getAttribute(WebAttributes.ACCESS_DENIED_403);
		logger.info("ex : {}",ade);
		try {
			 model.addAttribute("auth", auth);
			 model.addAttribute("errMsg", ade);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}			
		 return "redirect:/";
	}
		
	int auth=0;
	String url=null;	
	//회원정보수정페이지
	@GetMapping("/mypage")
	public ModelAndView mypageInfo(@ModelAttribute("_method")String method,Model model,
			SearchCriteria cri) throws Exception{
		logger.info("회원정보수정");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ModelAndView mv = new ModelAndView();
		UserDetailsVO userDetails = (UserDetailsVO)principal;	
		System.out.println("userDetails"+userDetails);		
		try {
			if(auth==0&&method.equals("")){ //마이페이지접속
				auth=0;
				mv.setViewName("/member/mypage");
			}else if(auth==0&&!method.equals("")) {	//패스워드 다시 한번 입력
				auth=0;
				url=method;			
				if(userDetails.getLink()!=null) {
					mv.setViewName("/member/"+url); //소셜로그인
				}else {
					mv.setViewName("/member/password"); //일반로그인
				}				
			}else if(auth==1 && url.equals("myInfoModify")){	//회원정보수정
				auth=0;
				model.addAttribute("myInfo",service.mypageInfo(userDetails.getUserId()));
				mv.setViewName("/member/mypageinfo");
			}else if(auth==1 && url.equals("myBoardMng")) {	//게시물관리
				auth=0;
				mv.setViewName("/member/mypageboard");
			}else if(auth==1 &&url.equals("myReplyMng")) {	//댓글관리
				auth=0;
				mv.setViewName("/member/mypagereply");
			}else if(auth==1 &&url.equals("memberLeave")) {	//탈퇴
				auth=0;
				mv.setViewName("/member/mypageleave");
			}else {
				auth=0;	
			}		
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
	    return mv;
	}
	
	//내가 쓴 게시물 리스트
	@PostMapping("/myBoard")
	public @ResponseBody ModelAndView myBoardList(Integer page,Model model,
			SearchCriteria cri) throws Exception {
		logger.info("내 게시물 보기");
		ModelAndView mv = new ModelAndView();
		try {
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();		
			UserDetailsVO userDetails = (UserDetailsVO)principal;	
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", userDetails.getUserId());	
			map.put("cri", cri);	
			model.addAttribute("board",service.myBoard(map));	
			
			PageMaker pageMk=new PageMaker();
			pageMk.setCri(cri);
			pageMk.setTotalCount(service.myBoardCnt(userDetails.getUserId()));
			cri.setPage(page);
			model.addAttribute("pageMk", pageMk);
			
			mv.setViewName("/member/mypageboard");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return mv;
	}
	
	
	//내가 쓴 댓글 리스트
	@PostMapping("/myReply")
	public @ResponseBody ModelAndView myReplyList(Integer page,Model model,
			SearchCriteria cri) throws Exception {
		logger.info("내 댓글 보기");
		ModelAndView mv = new ModelAndView();
		try {
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();		
			UserDetailsVO userDetails = (UserDetailsVO)principal;
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userId", userDetails.getUserId());	
			map.put("cri", cri);	
			model.addAttribute("reply",service.myReply(map));
	    
			PageMaker pageMk=new PageMaker();
			pageMk.setCri(cri);
			pageMk.setTotalCount(service.myReplyCnt(userDetails.getUserId()));
			cri.setPage(page);
			model.addAttribute("pageMk", pageMk);

			mv.setViewName("/member/mypagereply");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return mv;
	}
	//비밀번호 확인
	@PostMapping("/pwCheck")
	public @ResponseBody int pwCheck(String userId,String userPw)throws Exception{
		UserDetailsVO userVO=new UserDetailsVO();
		int result=0;
		try {
			userVO=memberDAO.getUserInfo(userId);
			if(pwEncoding.matches(userPw, userVO.getPassword())) {
				result=1;
				auth=1;
			}
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
		return result;
	}
	//insert() 성공 null/실패 e 에러
	//update() 성공 1/실패 0
	//delete() 성공 삭제된row수 / 실패 0	
	//회원정보 수정처리
	@PostMapping("/info_modify")
	public @ResponseBody int infoModify(MemberVO vo) throws Exception{		
		logger.info("회원정보수정");
		Map<String,String> map=new HashMap<String,String>();
		int modify=0;
		try {			
			map.put("id",vo.getUserId());
			if(vo.getUserNickName()!=null) {	//닉네임
				map.put("nick",vo.getUserNickName());
			}else if(vo.getUserPw()!=null) {	//비밀번호
				System.out.println(vo.getUserPw());
				map.put("pw",vo.getUserPw());
			}else if(vo.getEmail()!=null) {		//이메일
				map.put("email",vo.getEmail());
			}else if(vo.getPhoneNum()!=null) {	//폰번호
				map.put("phone",vo.getPhoneNum());
			}else if(vo.getPostCode()!=null) {	//주소
				map.put("postCode",vo.getPostCode());
				map.put("roadAddr",vo.getRoadAddr());
				if(vo.getDetailAddr()!=null) {
					map.put("detailAddr",vo.getDetailAddr());
				}
			}
			modify=service.infoModify(map);	
			System.out.println("modify : "+modify);
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
		return modify;		
	}

	//게시물삭제
	@PostMapping("/myBoardDelete")
	public @ResponseBody int myBoardDelete(@RequestParam(value="postIdArr[]") 
					List<String> postIdArr )throws Exception{
		logger.info("내 게시물 삭제");
		int result=0;
		try {
			result=service.myBoardDel(postIdArr);    	
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
		return result;
	}
	
	//댓글삭제
	@PostMapping("/myReplyDelete")
	public @ResponseBody int myReplyDelete(@RequestParam(value="replyIdArr[]") 
	List<String> replyIdArr )throws Exception{
		logger.info("내 게시물 삭제");
		int result=0;
		try {
			result=service.myReplyDel(replyIdArr);
	    }catch(Exception e) {
	    	System.out.println(e.getMessage());
	    }
		return result;
	}
	//회원탈퇴페이지
	@GetMapping(value="/mypageleave")
	public void mypageLeave() throws Exception{
		logger.info("탈퇴페이지");
	}
	//회원탈퇴처리
	@PostMapping(value="/memLeave")
	public @ResponseBody int memLeave() throws Exception{
		logger.info("탈퇴처리");
		int result=0;
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		    UserDetailsVO userDetails = (UserDetailsVO)principal;			    
		    String userId=userDetails.getUserId();
		    result=service.homeLeave(userId);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result; 
	}

}

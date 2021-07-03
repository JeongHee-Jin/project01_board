package org.project01.security;

import org.project01.domain.UserDetailsVO;
import org.project01.persistence.MemberDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//로그인시 처리
public class UserLoginAuthProvider implements AuthenticationProvider{

	//@Autowired
	// DB의 값을 가져다주는 커스터마이징 클래스
	//UserDetailsService userDetailsServcie;
	
	@Autowired
	// DB의 값을 가져다주는 커스터마이징 클래스
	private MemberDAO memberDao;
	
	// 패스워드 암호화 객체
	@Autowired 
	private BCryptPasswordEncoder pwEncoding;
	
//	DB작업 및 로그인과 관련된 처리를 한다.
//	만약 <form-login>의 default=target-url 속성에 보내고싶은 값이 있을 때는
//  CustomUserDetail에 세팅을하고 아래에서 CustomUserDetail의 객체를 set해준다.	
	// DB에 정보가 없는 경우 예외 발생 (아이디/패스워드 잘못됐을 때와 동일한 것이 좋음)
	// ID 및 PW 체크해서 안맞을 경우 (matches를 이용한 암호화 체크를 해야함)
	// 인증 로직
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		/* 사용자가 입력한 정보 */
		String userId = authentication.getName();
		String userPw = (String) authentication.getCredentials();

		/* DB에서 가져온 정보 */
		UserDetailsVO userDetails=new UserDetailsVO();
		try {
			userDetails = (UserDetailsVO)memberDao.getUserInfo(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 인증 진행 */	
		if(userDetails==null ) {
			throw new UsernameNotFoundException(userId);
		}else if (!userId.equals(userDetails.getUsername()) 
				|| !pwEncoding.matches(userPw, userDetails.getPassword()) ) {
			throw new BadCredentialsException(userId);
		// 계정 정보 맞으면 나머지 부가 메소드 체크 (이부분도 필요한 부분만 커스터마이징 하면 됨)
		// 비활성화된 계정일 경우
		} else if (!userDetails.isEnabled()|| userDetails.getRole().equals("ROLE_STOP")) {
			throw new DisabledException(userId);

		// 만료된 계정일 경우
		} else if (!userDetails.isAccountNonExpired()) {
			throw new AccountExpiredException(userId);

		// 비밀번호가 만료된 경우
		} else if (!userDetails.isCredentialsNonExpired()) {
			throw new CredentialsExpiredException(userId);
			
		// 잠긴 계정일 경우
		}else if (!userDetails.isAccountNonLocked()) {
			throw new LockedException(userId);
		}
		// 다 썼으면 패스워드 정보는 지워줌 (객체를 계속 사용해야 하므로)
		userDetails.setUserPw(null);			
		/* 최종 리턴 시킬 새로만든 Authentication 객체 */
		Authentication newAuth = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		return newAuth;
	}
	
	// 위의 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사
	// null 값이거나 잘못된 타입을 반환했을 경우 인증 실패로 간주
	@Override
	public boolean supports(Class<?> authentication) {
		// 스프링 Security가 요구하는 UsernamePasswordAuthenticationToken 타입이 맞는지 확인
	return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

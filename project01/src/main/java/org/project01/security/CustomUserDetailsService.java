package org.project01.security;

import javax.inject.Inject;

import org.project01.domain.MemberVO;
import org.project01.domain.UserDetailsVO;
import org.project01.persistence.MemberDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//로그인 사용자 정보 인증 서비스
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Inject
	private MemberDAO memberDAO;
		
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
		UserDetailsVO userVO=new UserDetailsVO();		
		try {
			userVO=memberDAO.getUserInfoDetail(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//시큐리티 사용자 인증
		if(userVO==null) {
			System.out.println("계정정보가 존재하지 않습니다.");
			throw new UsernameNotFoundException(userVO.getUserId());
		}
		return userVO;
	}
}
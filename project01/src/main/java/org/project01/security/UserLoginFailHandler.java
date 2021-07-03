package org.project01.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;


/* 로그인 실패 대응 로직 */
@Service
public class UserLoginFailHandler implements AuthenticationFailureHandler{

    private static final Logger logger = LoggerFactory.getLogger(UserLoginFailHandler.class);

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		System.out.println("로그인실패 : "+exception);
	    logger.info(" :::::::::::::::::::::::::::: 로그인실패 :::::::::::::::::::::::: ");
	    
		if (exception instanceof AuthenticationServiceException) {
			request.setAttribute("loginFailMsg", "존재하지 않는 사용자입니다.");
		
		} else if(exception instanceof BadCredentialsException) {
			request.setAttribute("loginFailMsg", "아이디 또는 비밀번호가 틀립니다.");
			
		} else if(exception instanceof LockedException) {
			request.setAttribute("loginFailMsg", "잠긴 계정입니다.");
			
		} else if(exception instanceof DisabledException) {
			request.setAttribute("loginFailMsg", "비활성화된 계정입니다.");
			
		} else if(exception instanceof AccountExpiredException) {
			request.setAttribute("loginFailMsg", "만료된 계정입니다.");
			
		} else if(exception instanceof CredentialsExpiredException) {
			request.setAttribute("loginFailMsg", "비밀번호가 만료되었습니다.");
		} 

		// 로그인 페이지로 다시 포워딩
		request.getRequestDispatcher("/member/login").forward(request, response);
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/member/login");
//		dispatcher.forward(request, response);
		
	}
//	@Configuration
//	public class WebConfig  extends WebMvcConfigurerAdapter {
//	  @Override
//	    public void addViewControllers(ViewControllerRegistry registry) {
//	        registry.addViewController("/member/login").setViewName("front/login");  // 로그인 
//	    }
//	}
}

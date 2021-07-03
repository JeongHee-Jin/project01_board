package org.project01.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class UserDeniedHandler implements AccessDeniedHandler{
	//private static final Logger logger = LoggerFactory.getLogger(UserDeniedHandler.class);
	//로그인 후 뒤로가기시 로그인 페이지가 아닌 메인페이지로 이동
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException ade) throws IOException, ServletException {
		request.setAttribute("errMsg",ade.getMessage());
		request.getRequestDispatcher("/").forward(request,response);				
	}
}

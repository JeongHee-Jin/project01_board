package org.project01.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

//로그인 성공시 이전 페이지로 이동 처리
public class UserLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) {
			String redirectUrl = (String) session.getAttribute("prevPage");				
			session.removeAttribute("prevPage");	//session에 있는 이전경로 삭제=>메모리 누수 방지
			String link = (String) session.getAttribute("link");			
			if(link!=null) {
				session.removeAttribute("link");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out=response.getWriter();
				out.println("<script>opener.location.reload();" + 
							"    opener.location.href=\""+redirectUrl+"\"; self.close();</script>");
				out.flush();
			}else {
				getRedirectStrategy().sendRedirect(request, response, redirectUrl);
			}
			
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}

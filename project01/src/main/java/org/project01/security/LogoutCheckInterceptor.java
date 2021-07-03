package org.project01.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//로그아웃시 뒤로가기 방지
public class LogoutCheckInterceptor extends HandlerInterceptorAdapter {
	// 클라이언트의 요청이 해당 URL 과 매핑되는 컨트롤러에 전달되기전에 실행 되어지는 메소드
	// 리턴타입이 false 가 되면 해당 요청이 실행 되지않고 종료됨
	//getSession == true: 무조건 세션 생성, 없으면 새로만들기
	//				false: 세션 없으면 없는대로 있으면 있는대로 실행
	//요청가기전 처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getSession().getAttribute("id") == null) {
			response.sendRedirect("/member/login");
			return false;
		}
		return super.preHandle(request, response, handler);
	}
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }


}
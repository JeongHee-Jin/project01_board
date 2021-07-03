package org.project01.link;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.apache.commons.codec.binary.StringUtils;


public class NaverAuthBO{
	/* 인증 요청문 파라미터 */
	//client_id : 어플리케이션 등록 후 발급 받은 클라이언트 아이디
	//esponse_type: 인증 과정에 대한 구분값, code로 값 고정
	//redirect_uri: 네이버 로그인 인증의 결과를 전달받을 콜백 URL(URL 인코딩). 애플리케이션을 등록할 때 Callback URL에 설정한 정보
	//state: 애플리케이션이 생성한 상태 토큰
	private final static String CLIENT_ID = "";  
	private final static String CLIENT_SECRET = ""; 	//클라이언트시크릿
	private final static String REDIRECT_URI = "/member/naver_callback"; 	//주소
	private final static String SESSION_STATE = "oauth_state"; 
	/* 프로필 조회 API URL */
	private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";
	
	//네아로 인증 URL 생성
	public String getAuthorizationUrl(HttpSession session,String serverUrl) { 
		String state = generateRandomString(); //세션 유효성 검증을 위하여 난수를 생성
		setSession(session, state); //생성한 난수 값을 session에 저장
		//Scribe에서 제공하는 인증 URL 생성 기능을 이용하여 네아로 인증 URL 생성 
		OAuth20Service oauthService = new ServiceBuilder()
				.apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET) .callback(serverUrl+REDIRECT_URI)
				.state(state).build(NaverLoginApi.instance());  // 앞서 생성한 난수값을 인증 URL생성시 사용함 				
		return oauthService.getAuthorizationUrl(); 
	} 
	
	//네아로 Callback 처리 및 AccessToken 획득
	public OAuth2AccessToken getAccessToken(HttpSession session, String code, String state,String serverUrl) throws IOException { 
		//Callback으로 전달받은 세선검증용 난수값과 세션에 저장되어있는 값이 일치하는지 확인
		String sessionState = getSession(session); 
		if (StringUtils.equals(sessionState, state)) { 
			OAuth20Service oauthService = new ServiceBuilder()
					.apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET) 
					.callback(serverUrl+REDIRECT_URI).state(state).build(NaverLoginApi.instance()); 
			//Scribe에서 제공하는 AccessToken 획득 기능으로 네아로 Access Token을 획득
			OAuth2AccessToken accessToken = oauthService.getAccessToken(code); 
			return accessToken; 			
		}
		return null;
	} 
	/* 세션 유효성 검증을 위한 난수 생성기 */
	private String generateRandomString() { 
		return UUID.randomUUID().toString(); 
	} 
	
	/* http session에 데이터 저장 */ 
	private void setSession(HttpSession session, String state) {
		session.setAttribute(SESSION_STATE, state);
		
	} /* http session에서 데이터 가져오기 */ 
	private String getSession(HttpSession session) { 
		return (String) session.getAttribute(SESSION_STATE);
	} 
		
	//Access Token을 이용하여 네이버 사용자 프로필 API를 호출
	public String getUserProfile(OAuth2AccessToken oauthToken, String serverUrl) throws IOException { 
		OAuth20Service oauthService = new ServiceBuilder()
				.apiKey(CLIENT_ID).apiSecret(CLIENT_SECRET) 
				.callback(serverUrl+REDIRECT_URI).build(NaverLoginApi.instance()); 
		OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL, oauthService);;
		oauthService.signRequest(oauthToken, request); 
		Response response = request.send();
		return response.getBody(); 
	} 
}

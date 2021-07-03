package org.project01.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ServerEndpoint(value="/chat")
public class WebSocketChat{
	//1:1  채팅 사용시  map
	//Map <String,WebSocketSession> session = new HashMap<String, WebSocketSession>();
	// 접속 된 클라이언트 WebSocket session 관리 리스트
	 private static final List<Session> sessionList=new ArrayList<Session>();
	 private static final Logger logger = LoggerFactory.getLogger(WebSocketChat.class);
	
	//채팅페이지
	@GetMapping("/chatting")
	public String getChat(Model model) throws Exception {
		logger.info("채팅페이지");
		return "/chat/chatting";
	}
	
	public WebSocketChat() {
		logger.info("웹소켓 객체 생성");
	}
	//웹소켓생성
	@OnOpen
	public void onOpen(Session session) throws IOException {
		logger.info("연결된 ID : "+session.getId());
		System.out.println("연결된 ID : "+session.getId());
		try {
			sessionList.add(session);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}	
	//소켓닫기
	@OnClose
	public void onClose(Session session)  throws Exception{	
		try {
			logger.info("퇴장 : "+session.getId());
			sessionList.remove(session);			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	//채팅방인원수
	@PostMapping("/size")
	@ResponseBody
	public int listSize()  throws Exception{
		System.out.println("인원수 : "+sessionList.size());
		return sessionList.size();		
	}
	//final Basic basic=session.getBasicRemote();
	//map사용시
	//session.put(session.getId(),session);
	//웹소켓 세션을 리스트에 저장
	//메세지 입력
    @OnMessage
    public void onMessage(String message,Session session) throws Exception{	    	
        logger.info("Message From ");
		try {
	    	String sender = message.split(",")[0];
	    	String text = message.split(",")[1];
	    	String type = message.split(",")[2];  
			sendAllSessionToMessage(session,sender,text,type);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}         
	}
	//모든 사용자에게 메세지 전송
	private void sendAllSessionToMessage(Session self, String sender, 
			String text,String type) throws Exception{
		System.out.println(sender+" , "+text+" , "+type);
		try {
			for(Session session:WebSocketChat.sessionList ) {					
			    if(type.equals("ENTER")){	 
			    	session.getBasicRemote().sendText("<div class='enter-alarm'>"
			    			+sender + "님이 입장하셨습니다.</div>");
			    }else if(type.equals("LEAVE")){
			    	session.getBasicRemote().sendText("<div class='enter-alarm'>"
			    			+sender + "님이 퇴장하셨습니다.</div>");
			    }else if(type.equals("CHAT")){			    	
			    	if(self.getId().equals(session.getId())) {
			    		session.getBasicRemote().sendText("<div class='_right box'>"
			    				+ "<div class='msgbox'>"
			    				+text+"</div><div class='triangle trg_r'></div>"
			    				+ "<div class='usernick'>"
			    				+sender+"</div></div>");	
			    	}else {
			    		session.getBasicRemote().sendText("<div class='_left box'>"
			    				+sender+"<div class='triangle trg_l'></div>"
			    				+ "<div class='msgbox'>"
			    				+text+"</div></div>");
			    	}	
			    }
			}	
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	//에러출력
	@OnError
	public void onError(Throwable e,Session session)  throws Exception{
		  e.printStackTrace();
	}	
	
	//e.getMessage() : 에러의 원인을 간단하게 출력합니다.
	//e.toString() : 에러의 Exception 내용과 원인을 출력합니다.
	//e.printStackTrace() : 에러의 발생근원지를 찾아서 단계별로 에러를 출력합니다.
}

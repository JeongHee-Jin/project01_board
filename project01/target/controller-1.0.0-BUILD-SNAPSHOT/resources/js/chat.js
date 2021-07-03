//페이지 이탈시 소켓종료
$(window).on('beforeunload', function () {
	closeSocket();
});
$(document).ready(function() {
	//메세지입력시
	$("#my_message").keyup(function(e){
		//보내기 활성화
		if($("#my_message").val()==""){
			$("#sand_btn").attr("disabled",true);
		}else{
			$("#sand_btn").attr("disabled",false);
		}
		//textarea 키 값 이벤트
		if((e.ctrlKey || e.metaKey) && (e.keyCode == 13 || e.keyCode == 10)) {
			//ctrl+enter
			$("#my_message").val(function(i,val){
	            return val + "\n";
	        });
		}else if(e.keyCode==13){	//엔터시 전송
			e.preventDefault(); //엔터시 줄바꿈 방지(동작중단)
			sendMsg();	
		}
	});
//	window.onbeforeunload = function(e) {
//		return "채팅방을 나가시겠습니까?"
//		closeSocket();
//	    //var dialogText = "/member/mypage?_method=memberFront";
//	  
//	};
});

	var webSocket;
	var userNick;
	var type;
	//입장 버튼 클릭시 
	function openSocket(){
		if(document.getElementById("chat_nick").value==""){
			alert("닉네임을 입력하세요.");
			return;
		}
        if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED ){
            return;
        }
        //닉네임 저장
		userNick=document.getElementById("chat_nick").value;
        //닉네임 입력창 숨기고 채팅창 보여줌
		$("#nick_box").addClass("blind");
		$("#chat_box").removeClass("blind");
		//웹소켓 생성
		webSocket = new WebSocket("ws://localhost:8012/chat");
		
		//.onopen,.onmessage,.onclose 함수 정의
		webSocket.onopen = function(event){					
			if(event.data === undefined){
				chatSum();
				webSocket.send(userNick+", 님이 입장하셨습니다. ,ENTER");
				return;
			}
			closeSocket();
		}		
		webSocket.onmessage=function(event){
			chat_messages.innerHTML+=event.data;
			scroll();
			chatSum();
		}		
		webSocket.onclose = function(event){
			chatSum();
		}
	}
	//메세지전송
	function sendMsg(){
		var msg= document.getElementById("my_message").value;
		msg = msg.replace(/(?:\r\n|\r|\n)/g,"<br>");	//줄바꿈적용
		var text = userNick+","+msg+",CHAT";
		webSocket.send(text);
		document.getElementById("my_message").value='';	//입력값삭제
		$("#sand_btn").attr("disabled",true);			//버튼비활성
	}
	
	//퇴장
	function closeSocket(){
		webSocket.send(userNick+", 님이 퇴장하셨습니다. ,LEAVE");
		webSocket.close();
		chat_messages.innerHTML="";
 		$("#nick_box").removeClass("blind");
		$("#chat_box").addClass("blind");
	}
	//채팅방인원
	function chatSum(){
		$.ajax({
			url:"/size",
			type:"POST",
			success:function(data){
				document.getElementById("chat_title").innerHTML="채팅방 인원("+data+")";
			}
		});
	}
	
	
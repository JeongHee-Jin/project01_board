function linkLogin(link){	
	$.ajax({
		url:"/member/linklogin",
		type:"POST",
		data: {"link":link},
		asyne:false,
		dataType:"text",
		success:function(url){
			console.log(url);
			window.open(url,'_blank','width=500,height=700');
		}
	})
}
/* - 이벤트의 종류
	onclick              : 엘리먼트를 클릭했을 때 발생하는 이벤트
	onchange          : 엘리먼트의 상태가 변경되었을 때 발생하는 이벤트
	onmouseenter    : 엘리먼트안으로 마우스 포인트가 옮겨왔을 때 발생하는 이벤트 
	onmouseleave    : 엘리먼트밖으로 아무스 포인트가 옮겨갔을 때 발생하는 이벤트
	onkeydown        : 엘리먼트에서 키보드의 키가 눌렀을 때 발생하는 이벤트
	onkeyup            : 엘리먼트에서 키보드의 키가 올라올때 발생하는 이벤트
	onkeypress        : 엘리먼트에서 키보드의 키가 눌렀다가 올라온 후 발생하는 이벤트
	onsubmit           : 폼의 입력값이 서버로 제출될 때 발생하는 이벤트
	onscroll             : 스크롤바가 스크롤될 때 발생하는 이벤트
	onload              : 웹페이지가 전부 로딩된 후에 발생하는 이벤트
	onfocus            : 엘리먼트가 포커스를 획득했을 때 발생하는 이벤트
	onblur              : 엘리먼트가 포커스를 잃었을 때 발생하는 이벤트*/
//회원가입 page
function joinFunction(){
	//아이디
	var join_id = document.getElementById("join_id");
	var id_regul2 = /^[-_0-9a-zA-Z]([-_]?[0-9a-zA-Z-_]){4,10}$/;
	join_id.onkeyup= function(){
		if(!id_regul2.test(join_id.value)){
			 if((join_id.value)==''){
				 id_regul.innerHTML="필수 입력입니다.";
			 }else{
				 id_regul.innerHTML="5~10자의 영문 대 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다."; 
			 }
			 id_regul.classList.remove('green');
		}else{
			$.ajax({
				url: "/member/id_check",
				type: "POST",
				data: {"userId":(join_id.value)},
				datatype:"json",
				success:function(data){
					if(data==0){
						id_regul.classList.add('green');
						id_regul.innerHTML="사용 가능한 ID입니다.";
					}else{
						id_regul.innerHTML="이미 사용중이거나 탈퇴한 아이디입니다.";
						id_regul.classList.remove('green');
					}
				},error : function(errThrown){
					console.log("err",errThrown);
				}
			})
		}
	}
	//비밀번호
	var join_pw = document.getElementById("join_pw");
	var join_rpw = document.getElementById("join_rpw");
	var pw_regul2 = /^[!@#$%^&*()-_+=\.0-9a-zA-Z]([!@#$%^&*()-_+=\.]?[0-9a-zA-Z!@#$%^&*()-_+=\.]){7,16}$/;
	join_pw.onkeyup = function(){
		//비밀번호 유효값 비교
		if(!pw_regul2.test(join_pw.value)){
			if((join_pw.value)==''){
				pw_regul.innerHTML="필수 입력입니다.";
			}else{
				pw_regul.innerHTML="8~16자 영문, 숫자, 특수문자(!@#$%^&*()-_+=\.)만 사용 가능합니다.";
			}
			pw_regul.classList.remove('green');
		}else{
			pw_regul.innerHTML="사용 가능한 비밀번호 입니다.";
			pw_regul.classList.add('green');
			document.querySelector('.pw1_label').classList.remove('warning');
			//$(join_pw).next('label').removeClass('warning');
		}
		//비밀번호 변경시 비밀번호 확인과 일치하지 않을때
		if((join_pw.value)==(join_rpw.value) && join_rpw.value!=""){
			rpw_regul.innerHTML="비밀번호가 일치합니다.";
			rpw_regul.classList.add('green');
			document.querySelector('.pw2_label').classList.remove('warning');
		}else if(join_rpw.value!="" && (join_pw.value)!=(join_rpw.value)){
			rpw_regul.innerHTML="비밀번호가 일치하지 않습니다.";
			rpw_regul.classList.remove('green');
		}
	}
	//비밀번호-비밀번호 확인 비교 
	join_rpw.onkeyup = function(){
		if((join_rpw.value)==''){
			rpw_regul.innerHTML='';
		}else if((join_pw.value)==(join_rpw.value)){
			rpw_regul.innerHTML="비밀번호가 일치합니다.";
			rpw_regul.classList.add('green');
			document.querySelector('.pw2_label').classList.remove('warning');
		}else{
			rpw_regul.innerHTML="비밀번호가 일치하지 않습니다.";
			rpw_regul.classList.remove('green');
		}
	}

	//닉네임
	var nick=document.getElementById("nick");
	var nick_regul2 = /^[-_0-9a-zA-Zㄱ-ㅎ|ㅏ-ㅣ|가-힣]([-_]?[0-9a-zA-Zㄱ-ㅎ|ㅏ-ㅣ|가-힣-_]){1,10}$/;
	nick.onkeyup = function(){		
		if(!nick_regul2.test(nick.value)){		
			if(nick.value==''){
				nick_regul.innerHTML="필수 입력입니다.";			
			}else{
				nick_regul.innerHTML="2~10자의 한글,영문 대 소문자,숫자,특수기호(_),(-)만 사용 가능합니다.";
			}
			nick_regul.classList.remove('green');
		}else{
			$.ajax({
				url: "/member/nick_check",
				type: "POST",
				data: {"nickName":(nick.value)},
				datatype:"json",
				success:function(data){
					if(data==0){
						 nick_regul.classList.add('green');
						 nick_regul.innerHTML="사용 가능한 닉네임 입니다.";
					}else{
						 nick_regul.innerHTML="이미 사용중인 닉네임 입니다.";
						 nick_regul.classList.remove('green');
					}
				},error : function(errThrown){
					console.log("err",errThrown);
				}
			});
		}
	}
	
	// 휴대번호 하이픈 넣기
	var phoneNum = document.getElementById('phoneNum');
	var phon_regul2=/^[0-9\-]{10,13}$/;
	phoneNum.onkeyup = function(){
		phoneNum.value=(phoneNum.value.replace(/[^0-9]/g, "").replace(/^([0-9]{3})([0-9]{4})?([0-9]{4})$/,"$1-$2-$3"));
		if(!phon_regul2.test(phoneNum.value)){			
			if(phoneNum.value==''){
				phon_regul.innerHTML="";
			}else{
				phon_regul.innerHTML="입력한 전화번호를 다시 한번 확인해주세요";
			}	
		}else{
			phon_regul.innerHTML="";
		}
	}
	
	// 이메일 유효성 체크
						// 이메일ID //@주소 //.주소
	var em_regul2 = /^[-_0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]+\.[a-zA-Z]{2,3}$/;
	var email = document.getElementById("email");
	var code=null;
	email.onkeyup=function(){
		code=null;
	/*	if(code_regul_div.innerHTML!=""){
			code_regul_div.innerHTML="인증코드가 다릅니다.";
			code_regul_div.classList.remove('green');
		}*/
		if(!em_regul2.test(email.value)){
			if((email.value)==''){
				email_regul.innerHTML="필수 입력입니다.";
			}else{
				email_regul.innerHTML="이메일 주소를 다시 확인해주세요.";
			}
			email_regul.classList.remove('green');
		}else{			
			$.ajax({
				url: "/member/email_check",
				type: "post",
				data: {"email":(email.value)},
				datatype:"json",
				success:function(data){
					if(data==0){
						email_regul.innerHTML="사용 가능한 E-mail 입니다.";
						email_regul.classList.add('green');
					}else{
						email_regul.innerHTML="이미 사용중인 이메일입니다.";
						email_regul.classList.remove('green');
					}
				},error : function(errThrown){
					console.log("err",errThrown);
				}
			})
			
		}
	}
	
	var sendCode=document.getElementById("sendCode");
	//이메일 코드 전송
	sendCode.addEventListener('click',function(){
		var email = document.getElementById("email");
		var email_regul_div = document.getElementById("email_regul").getAttribute("class");
		//클래스명이 현재 있는지 검사하는 정규표현식(true,false)
		var hasClass = new RegExp("(^|\\s)" + "green" + "(\\s|$)").test(email_regul_div);
		if(!hasClass){
			email.focus();
		}else{
			alert("이메일을 발송하였습니다.");
			$.ajax({
				url: "/member/email_code",
				type: "post",
				data: {"userEmail":(email.value),"joinEmail":"email"},
				datatype:"json",
				success:function(data){					
					emailCode.value=data;
					code=data;
				},error : function(errThrown){
					console.log("err",errThrown);
				}
			})
		}
	})
	//@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
	//이메일 전송 코드 체크
	//var hiddenCode =document.getElementById("hiddenCode");
	var emailCode=document.getElementById("emailCode");
	var code_regul_div = document.getElementById("code_regul"); 
	emailCode.onkeyup=function(){
		if(emailCode.value==""){	
			code_regul_div.innerHTML="";
		}else{
			if(emailCode.value==code){
				code_regul_div.innerHTML="인증코드가 맞습니다.";
				code_regul_div.classList.add('green');
			}else{
				code_regul_div.innerHTML="인증코드가 다릅니다.";
				code_regul_div.classList.remove('green');
			}
		}
	}
}

//주소입력창
function postCodeSearch(){
    new daum.Postcode({
        oncomplete: function(data) {
        	// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
               extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById("roadAddress").value = roadAddr;
            /*document.getElementById("jibunAddress").value = data.jibunAddress;*/          
        }
    }).open();
}
//회원가입  form
function join(form){
	var id = document.getElementById("join_id");
	var pw = document.getElementById("join_pw");
	var rpw = document.getElementById("join_rpw");
	var nick = document.getElementById("nick");
	var email = document.getElementById("email");
	var code = document.getElementById("emailCode");
	var phoneNum = document.getElementById('phoneNum');
	
	var phon_regul2=/^[0-9\-]{10,13}$/;
	//유효값체크
	if(id.value==""){
		id_regul.innerHTML="필수 입력입니다.";
	}
	if(pw.value==""){
		pw_regul.innerHTML="필수 입력입니다.";
	}
	if(rpw.value==""){
		rpw_regul.innerHTML="필수 입력입니다.";
	}
	if(nick.value==""){
		nick_regul.innerHTML="필수 입력입니다.";
	}
	if(email.value==""){
		email_regul.innerHTML="필수 입력입니다.";
	}
	if(code.value==""){
		code_regul.innerHTML="인증이 필요합니다.";
	}
	//라벨애니
	if(!(id_regul.classList.contains('green'))){	//아이디
		idError();
	} 
	if(!(pw_regul.classList.contains('green'))){	//비밀번호
		document.querySelector('.pw1_label').classList.add('warning');
		setTimeout(function(){
			document.querySelector('.pw1_label').classList.remove('warning')},1500)
	}
	if(!(rpw_regul.classList.contains('green'))){	//비밀번호확인
		document.querySelector('.pw2_label').classList.add('warning');
		setTimeout(function(){
			document.querySelector('.pw2_label').classList.remove('warning')},1500)
	}
	if(!(nick_regul.classList.contains('green'))){	//닉네임
		document.querySelector('.nick_label').classList.add('warning');
		setTimeout(function(){
			document.querySelector('.nick_label').classList.remove('warning')},1500)
	}
	if(!(email_regul.classList.contains('green'))){	//이메일
		emailError();
	}
	if(!(code_regul.classList.contains('green'))){	//이메일코드
		document.querySelector('.emailCode_label').classList.add('warning');
		setTimeout(function(){
			document.querySelector('.emailCode_label').classList.remove('warning')},1500)
	}
	if(phoneNum.value!="" && !phon_regul2.test(phoneNum.value)){
		document.querySelector('.phon_label').classList.add('warning');
		setTimeout(function(){
			document.querySelector('.phon_label').classList.remove('warning')},1500)
		phon_regul.innerHTML="입력한 전화번호를 다시 한번 확인해주세요";
		return false;
	}
	if(id_regul.classList.contains('green') && pw_regul.classList.contains('green') && 
		rpw_regul.classList.contains('green') && nick_regul.classList.contains('green')&&
		email_regul.classList.contains('green') && code_regul.classList.contains('green')){
			form.submit();
			return true;
			location.href=location.origin+"/cmu/total";	
	}
}

//id찾기
function findId(form){
	var email=document.getElementById("email");
	var email_regul2=/^[-_0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+@[0-9a-zA-Z]+\.[a-zA-Z]{2,3}$/;
	//빈칸일때 경고
	if(email.value==""){
		document.querySelector('.email_label').classList.add('warning');
		//$(email).next('label').addClass('warning');
		setTimeout(function(){$(email).next('label').removeClass('warning')},1500)
		email_regul.innerHTML="이메일을 입력하세요.";
	}else if(!email_regul2.test(email.value)){
		document.querySelector('.email_label').classList.add('warning');
		email_regul.innerHTML="이메일 형식이 맞지 않습니다.";
		setTimeout(function(){
			document.querySelector('.email_label').classList.remove('warning');
			email_regul.innerHTML="";
		},1500)		
	}else{
		//이메일 정보 비교
		$.ajax({
			url: "/member/find_id_pw",
			type:"POST",
			data:{"email":email.value},
			success:function(data){
				if(data==0){
					alert("입력하신 정보로 가입 된 회원 아이디가 존재하지 않습니다.");					
				}else{
					form.action="/member/resultIdPw";
					form.submit();
					return true;
				}
			},error : function(errThrown){
				console.log("err",errThrown);
			}
		})
	}
}

//pw찾기
function findPw(form){
	var userId=document.getElementById("userId");
	var id_regul2 = /^[-_0-9a-zA-Z]([-_]?[0-9a-zA-Z-_]){4,10}$/;
	var email=document.getElementById("email");
	var email_regul2=/^[-_0-9a-zA-Z]([-_.]?[0-9a-zA-Z])+@[0-9a-zA-Z]+\.[a-zA-Z]{2,3}$/;
	//id,email 빈칸 경고
	if(userId.value==""){
		idError();
		id_regul.classList.remove('ok');
		id_regul.innerHTML="아이디를 입력하세요.";
	}else if(!id_regul2.test(userId.value)){
		idError();
		id_regul.classList.remove('ok');
		id_regul.innerHTML="5~10자의 영문 대 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다."; 
	}else{
		id_regul.classList.add('ok');
		id_regul.innerHTML="";
	}
	if(email.value==""){
		emailError();
		email_regul.classList.remove('ok');
		email_regul.innerHTML="이메일을 입력하세요.";
	}else if(!email_regul2.test(email.value)){
		emailError();
		email_regul.classList.remove('ok');
		email_regul.innerHTML="이메일 형식이 맞지 않습니다.";
	}else{
		email_regul.classList.add('ok');
		email_regul.innerHTM="";
	}
	if(id_regul.classList.contains('ok') && email_regul.classList.contains('ok')){
		//이메일 정보 비교
		$.ajax({
			url:"/member/find_id_pw",
			type:"post",
			data:{"userId":userId.value,"email":email.value},
			success:function(data){
				if(data==0){
					alert("입력하신 정보로 가입 된 회원 정보가 존재하지 않습니다.");
				}else{
					form.submit();
					return true;
				}
			},error : function(errThrown){
				console.log("err",errThrown);
			}
		})
	}
}

//회원정보수정 : 수정-취소 버튼
function jsHideArea(target,modify){
	target.classList.toggle('red');
	if(target.classList.contains('red')){
		target.innerHTML="취소";
		target.style.color="#ef3e42";
		target.style.background="#ffe8e8";
		modify.style.display="block";
		modify.style.height="100%";	
		modify.style.animation="open 0.3s ease-in-out";
		//modify.style.animation="open 0.3s cubic-bezier(0.14, 0.37, 0.42, 0.87)";
		//setTimeout(function() { modify.style.display="block"; },200);
		if(modify.id=="pw_modify"){
			pwInput.value="";
			repwInput.value="";
			pw_label.innerHTML="";
			repw_label.innerHTML="";
		}else if(modify.id=="nick_modify"){
			nickInput.value="";	
			nick_label.innerHTML="";
		}else if(modify.id=="email_modify"){
			emailInput.value="";
			codeInput.value="";			
			codeInput.style.display="none";
			email_btn.innerHTML="인증코드 전송";
			email_label.innerHTML="";
		}else if(modify.id=="phone_modify"){
			phoneInput.value="";
			phone_label.innerHTML="";
		}else if(modify.id=="addr_modify"){
			addr_label.innerHTML="";
		}
	}else{
		modify.style.animation="close 0.3s ease-in-out";
		modify.style.height="0";
		setTimeout(function() { modify.style.display="none"; },200);
		target.style.color="#009f47";
		target.style.background="#e0f8eb";
		target.innerHTML="수정";
	}
};

//회원정보수정
function infoFunction(){
	//아이디값
	var id=document.getElementById("id_view").innerHTML;	
	//비밀번호 변경
	var input_pw = document.getElementById("pwInput");
	var input_repw = document.getElementById("repwInput");
	var pw_regul = /^[!@#$%^&*()-_+=\.0-9a-zA-Z]([!@#$%^&*()-_+=\.]?[0-9a-zA-Z!@#$%^&*()-_+=\.]){7,16}$/;
	input_pw.onkeyup= function(){	
		if(!pw_regul.test(input_pw.value) || (input_pw.value)==''){
			pw_label.innerHTML="8~16자 영문, 숫자, 특수문자(!@#$%^&*()-_+=\.)만 사용 가능합니다.";
		}else{
			pw_label.innerHTML="";
		}
		if(input_pw.value==input_repw.value){
			pw_label.innerHTML="";
		}else{
			repw_label.innerHTML="비밀번호가 일치하지 않습니다.";
		}
		
	}
	input_repw.onkeyup= function(){	
		if(input_pw.value==input_repw.value){
			repw_label.innerHTML="";
		}else{
			repw_label.innerHTML="비밀번호가 일치하지 않습니다.";
		}
	}
	pw_btn.addEventListener('click',function(){		
		if(pw_regul.test(input_pw.value)&&input_pw.value==input_repw.value){
			$.ajax({
				url: "/member/info_modify",
				type:"post",
				data:{"userId":id,"userPw":input_pw.value},
				datatype:"json",
				success:function(data){
					if(data==0){
						alert("수정 실패 하였습니다. 다시 시도해주세요.");
					}else{
						alert("비밀번호가 변경되었습니다.");
					}
					jsHideArea(pwOpen,pw_modify);
				},error:function(errThrown){
					consol.log("err",errThrown);
				}
			})
		}else{
			alert("빈칸을 입력하세요");
		}	
	})
	
	//닉네임수정
	var nick_btn =document.getElementById("nick_btn");
	nick_btn.addEventListener('click',function(){
		var id=document.getElementsByClassName("dd")[0].innerHTML;
		var nick=document.getElementById("nickInput");
		var nick_regul = /^[-_0-9a-zA-Zㄱ-ㅎ|ㅏ-ㅣ|가-힣]([-_]?[0-9a-zA-Zㄱ-ㅎ|ㅏ-ㅣ|가-힣-_]){1,10}$/;
		if(nick.value==""){
			nick_label.innerHTML="닉네임을 입력해주세요.";
		}else if(!nick_regul.test(nick.value)){
			nick_label.innerHTML="2~10자의 한글,영문 대 소문자,숫자,특수기호(_),(-)만 사용 가능합니다.";
		}else{
			$.ajax({
				url: "/member/nick_check",
				type:"post",
				data:{"nickName":nick.value},
				datatype:"json",
				success:function(data){
					if(data==0){
						if(confirm("사용 가능한 닉네임 입니다. 사용 하시겠습니까?")==true){						
							$.ajax({
								url:"/member/info_modify",
								type:"post",
								data:{"userId":id,"userNickName":nick.value},
								datatype:"json",
								success:function(data){
									if(data==0){
										alert("수정 실패 하였습니다. 다시 시도해주세요.");
									}else{
										alert("수정되었습니다.");
										document.getElementById("nick_view").innerHTML=nick.value;
										nick.value="";
									}
								}
							});
						}
						jsHideArea(nickOpen,nick_modify);
					}else{
						nick_label.innerHTML="이미 사용중인 닉네임 입니다.";
					}
				},error : function(errThrown){
					console.log("err",errThrown);
				}
			})
		}
	})
	var code;
	var orgEmail;
	//이메일수정 : 이메일 코드전송
	var email=document.getElementById("emailInput");
	var email_btn =document.getElementById("email_btn");
	email_btn.addEventListener('click',function(){					
		var em_regul = /^[-_0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]+\.[a-zA-Z]{2,3}$/;
		if(email_btn.innerHTML=="인증코드 전송"){	
			if(!em_regul.test(email.value)){
				if(email.value==""){
					email_label.innerHTML="이메일를 입력해주세요.";
				}else{
					email_label.innerHTML="이메일 주소를 다시 확인해주세요.";
				}
			}else{
				email_label.innerHTML="";
				alert("이메일을 전송하였습니다.");
				document.getElementById("codeInput").style.display="inline-block";
				$.ajax({
					url: "/member/email_code",
					type:"post",
					data:{"userEmail":email.value},
					datatype:"json",
					success:function(data){
						code=data;
						orgEmail=email.value;
						email_btn.innerHTML="이메일 수정";
					},error:function(errThrown){
						console.log("err",errthrown);
					}
				})
			}
		}
	})
	//이메일수정: 수정
	email_btn.addEventListener('click',function(){
		if(email_btn.innerHTML=="이메일 수정"){
			var emailCode = document.getElementById("codeInput");
			var id=document.getElementsByClassName("dd")[0].innerHTML;
			if(emailCode.value==code && emailInput.value==orgEmail){				
				$.ajax({
					url:"/member/info_modify",
					type:"post",
					data:{"userId":id,"email":email.value},
					datatype:"json",
					success:function(data){
						if(data==0){
							alert("수정 실패 하였습니다. 다시 시도해주세요.");
						}else{
							alert("이메일 주소가 수정되었습니다.");
							document.getElementById("email_view").innerHTML=email.value;
						}
					},error:function(errThrown){
							console.log("err",errthrown);
					}
				});							
				jsHideArea(emailOpen,email_modify);
			}else{
				email_label.innerHTML="입력 칸을 다시 확인해주세요.";
			}
		}
	});
	// 휴대번호 하이픈 넣기
	var phoneInput = document.getElementById('phoneInput');
	phoneInput.onkeyup = function(){
		phoneInput.value=(phoneInput.value.replace(/[^0-9]/g, "").replace(/^([0-9]{2,3})([0-9]{3,4})?([0-9]{4})$/,"$1-$2-$3"));
	}
	//전화번호수정
	var phone_btn =document.getElementById("phone_btn");
	var phon_regul2=/^[0-9\-]{10,13}$/;
	var phoneInput=document.getElementById("phoneInput");
	phone_btn.addEventListener('click',function(){		
		if(!phon_regul2.test(phoneInput.value)){
			if(phoneNum.value==''){
				phone_label.innerHTML="전화번호를 입력하세요.";
			}else{
				phone_label.innerHTML="입력한 전화번호를 다시 한번 확인해주세요";
			}	
		}else{
			$.ajax({
				url:"/member/info_modify",
				type:"post",
				data:{"userId":id,"phoneNum":phoneInput.value},
				datatype:"json",
				success:function(data){
					if(data==0){
						alert("수정 실패 하였습니다. 다시 시도해주세요.");
					}else{
						alert("전화번호가 수정되었습니다.");
						document.getElementById("phone_view").innerHTML=phoneInput.value;
						document.getElementById("phone_view").classList.remove("gray");
					}
					jsHideArea(phoneOpen,phone_modify);
				},error : function(errThrown){
					console.log("err",errThrown);
				}
			});							
		}
	});
	//주소변경
	var addr_btn =document.getElementById("addr_btn"); 
	addr_btn.addEventListener('click',function(){
		if(postcode.value=="" ||roadAddress.value==""){
			addr_label.innerHTML="변경할 주소를 입력하세요."
		}else if(postcode.value!="" && roadAddress.value!=""){
			$.ajax({
				url:"/member/info_modify",
				type:"post",
				data:{"userId":id,"postCode":postcode.value,
					"roadAddr":roadAddress.value,"detailAddr":detailInput.value},
				datatype:"json",
				success:function(data){
					if(data==0){
						alert("수정 실패 하였습니다. 다시 시도해주세요.");						
					}else{
						alert("주소가 수정되었습니다.");
						document.getElementById("post_view").innerHTML=postcode.value;
						document.getElementById("post_view").classList.remove("gray");
						document.getElementById("road_view").innerHTML=roadAddress.value;
						document.getElementById("detail_view").innerHTML=detailInput.value;
					}
					jsHideArea(addrOpen,addr_modify);
				},error : function(errThrown){
					console.log("err",errThrown);
				}					
			});
		}
	})
}

//전체선택
function allChk(check){
	const checkboxes=document.querySelectorAll("input[type='checkbox']");
	checkboxes.forEach((checkbox)=>{
		checkbox.checked=check.checked;
	})
}
//선택 삭제
function chkBoardDel(){
	/*  엘리먼트의 속성을 추가/변경/삭제/조회하기
    - 속성의 추가 : 엘리먼트에 새로운 속성과 속성값을 추가한다.
        el.setAttribute(속성명, 속성값);
    - 속성의 변경 : 엘리먼트에 지정된 속성명을 새로운 속성값으로 바꾼다.
        el.setAttribute(속성명, 속성값);
    - 속성의 삭제 : 엘리먼트에서 지정된 송성명과 일치하는 속성을 삭제한다.
        el.removeAttribute(속성명);
    - 속성값 조회 : 엘리먼트에서 지정된 속성명의 속성값을 가져간다.
        el.getAttribute(속성명);*/
	var cnt = document.querySelectorAll("input[type='checkbox']:checked");

	if(cnt.length==0){
		alert("선택한 게시물이 없습니다.");
	}else{
		if(confirm("선택한 게시물을 삭제 하시겠습니까?")==true){
			var cnt = document.querySelectorAll("input[type='checkbox']:checked");
			var arr = new Array();
			cnt.forEach((checkbox)=>{
				arr.push(checkbox.getAttribute("value"));
			})
			$.ajax({
				url:"/member/myBoardDelete",
				type:"POST",
				dataType:'json',
				data:{"postIdArr":arr},
				success:function(data){
					if(data==0){
						alert("게시물 삭제에 실패하였습니다.");
					}else{
						alert("게시물이 삭제 되었습니다.");
						reloadBoard(1);
					}
				},error:function(errThrown){
					console.log("err",errThrown);
					alert("서버 오류로 삭제에 실패하였습니다.");
				}
					
			})
		}
	}

}
//선택댓글삭제
function chkReplyDel(){
	var cnt = document.querySelectorAll("input[type='checkbox']:checked");
	if(cnt.length==0){
		alert("선택한 댓글이 없습니다.");
	}else{
		if(confirm("선택한 댓글을 삭제 하시겠습니까?")==true){		
			var arr = new Array();
			cnt.forEach((checkbox)=>{
				arr.push(checkbox.getAttribute("value"));
			})
			$.ajax({
				url:"/member/myReplyDelete",
				type:"POST",
				dataType:'json',
				data:{"replyIdArr":arr},
				success:function(data){
					if(data==0){
						alert("댓글 삭제에 실패하였습니다.");
					}else{
						alert("댓글이 삭제 되었습니다.");
						reloadReply(1);
					}
				},error:function(errThrown){
					console.log("err",errThrown);
					alert("서버 오류로 삭제에 실패하였습니다.");
				}
					
			})
		}
	}	
}
//내가 쓴 게시글 불러오기
function reloadBoard(page){
	$.ajax({
		type:"POST",
		url:"/member/myBoard",
		data:{"page":page},
		success:function(result){
			$("#myboard_table").empty();
			var list= $(result).find("#mypageBoard_content").html();
			mypageBoard_content.innerHTML=list;
		},error:function(error){console.log("err",error)}
	})
}
//내가 쓴 댓글 불러오기
function reloadReply(page){
	$.ajax({
		type:"POST",
		url:"/member/myReply",
		data:{"page":page},
		success:function(result){
			$("#myreply_table").empty();
			var list= $(result).find("#mypageReply_content").html();
			mypageReply_content.innerHTML=list;
		},error:function(error){console.log("err",error)}
	})
}
//페이지이동
function send(url){
	location.href='/member/mypage?_method='+url;
}
//뒤로가기
function goBack(){
	window.history.back();
}

function passwordCheck(){
	var id=document.getElementById('userId').innerHTML;
	var password=document.getElementById('userPw').value;
	if(password==""){
		document.getElementById('passLabel').innerHTML="비밀번호를 입력해 주세요."
	}else{
		$.ajax({
			url:"/member/pwCheck",
			type:"POST",
			dataType:"json",
			data:{"userId":id,"userPw":password},
			success:function(data){
				if(data==0){
					document.getElementById('passLabel').innerHTML="비밀번호를 다시 확인해 주세요."
				}else{
					location.href="/member/mypage";
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 실패하였습니다.");
			}
		})
		
	}
}
//탈퇴
function leave(){
	var a=document.getElementById("agree");
	if(a.checked==true){
//		Swal.fire({
//			  title: '정말로 탈퇴하시겠습니까?',
//			  icon: 'warning',
//			  showCancelButton: true,
//			  confirmButtonColor: '#FF9021',
//			  cancelButtonColor: '#009f47',
//			  confirmButtonText: '네',
//			  cancelButtonText:'아니요'
//			}).then((result) => {
//			  if (result.isConfirmed) {
//				  $.ajax({
//					  url:"/member/memLeave",
//					  type:"POST",
//					  dataType:"json",
//					  success:function(data){
//						  if(data==0){
//							  alert('서버 오류로 실패하였습니다.');
//						  }else{
//							location.href="/member/logout";
//						  }
//					  },error:function(errThrown){
//						  console.log("err",errThrown);
//						  alert('서버 오류로 실패하였습니다.');
//					  }
//					})
//			    Swal.fire(
//			      '회원탈퇴가 완료되었습니다.','보다 나은 서비스로 다시 만나뵐 수 있기를 바랍니다.')
//			  }
//			})
		if(confirm("정말로 탈퇴하시겠습니까?")==true){		
			$.ajax({
				url:"/member/memLeave",
				type:"POST",
				dataType:"json",
				success:function(data){
					if(data==0){
						alert('서버 오류로 실패하였습니다.');
					}else{
						alert('회원탈퇴가 완료되었습니다.보다 나은 서비스로 다시 만나뵐 수 있기를 바랍니다.');
						location.href="/member/logout";
					}
				},error:function(errThrown){
					console.log("err",errThrown);
					alert('서버 오류로 실패하였습니다.');
				}
			})
		}
	}else{
		alert("동의에 체크해주세요.");
	}
}
//아이디입력 경고
function idError(){
	document.querySelector('.id_label').classList.add('warning');
	setTimeout(function(){
		document.querySelector('.id_label').classList.remove('warning')},1500)
}
//이메일 입력 경고
function emailError(){
	document.querySelector('.email_label').classList.add('warning');
	setTimeout(function(){
		document.querySelector('.email_label').classList.remove('warning')},1500)
}

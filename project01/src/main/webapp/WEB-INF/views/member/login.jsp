<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<link href="/resources/css/member.css?ver=1" rel="stylesheet" type="text/css"/>
<script src="/resources/js/member.js"></script>
<script type="text/javascript" src="https://static.nid.naver.com/js/naveridlogin_js_sdk_2.0.0.js" charset="utf-8"></script>
<script type="text/javascript" src="https://developers.kakao.com/sdk/js/kakao.js" charset="utf-8"></script>
<script>
history.replaceState({}, null, "/member/login");
</script>
<body>
	<div id="loginPage_container">
		<div id="login-form">
			<h1>로그인</h1>
			<form name="loginForm" method="post" action="/member/login-processing?${_csrf.parameterName}=${_csrf.token}" 
			onsubmit="login(document.loginForm); return false;" ><!-- enctype="multipart/form-data" -->
				<div class="input_area">
					<div class="int-area login_In">
					<!-- value="admin000" -->
						<input type="text" name="userId" id="login-id" autocomplete="off">
						<label for="userId">USER ID</label>
					</div>
					<div class="int-area login_In">
						<input type="password" name="userPw" id="login-pw"autocomplete="off">
						<label for="userPw">PASSWORD</label>
					</div>
					<div id="login_regul" class="regul_div">${requestScope.loginFailMsg}</div>
				</div>
 				 <div class="_check" style="margin-top: 20px;">
					<input type="checkbox" name="remember-me" id="keep_signed" value="true">
					<label for="keep_signed">자동 로그인</label>
				</div>			
<!-- 				<div class="_memo">
					<p>*회원 계정: cocoa12/ cocoa12</p>
					<p>*관리자 계정: admin000/ admin000</p>
				</div> -->
				<div id="link_id_login"> 
					<a href="javascript:;" class="naver_icon link_i" onclick="linkLogin('naver');" >N</a>
					<a href="javascript:;" class="kakao_icon link_i" onclick="linkLogin('kakao');" ><img src="/resources/img/icon/kakao.png"></a>
				</div>
				<div id="btn-area">
					<button type="submit" id="btnL" class="btn_login">로그인</button>
					<button type="button" id="btnJ" value="JOIN" onclick="location.href='join'" class="btn_join">회원가입</button>
				</div>				
			</form>
			<div id="btn-area"></div>
			<div class="caption">
				<a href="/member/find_id">아이디 찾기</a> <em></em> 
				<a href="/member/find_pw">비밀번호 찾기</a>
			</div>
		</div>
	</div>
<!-- <script>
Kakao.init('c2dbd6c0b5c00df629f26d19c5981c33'); //발급받은 키 중 javascript키를 사용해준다.
Kakao.isInitialized(); 
console.log(Kakao.isInitialized()); // sdk초기화여부판단
//카카오로그인
function kakaoLogin() {
    Kakao.Auth.login({
      success: function (response) {
        Kakao.API.request({
          url: '/v2/user/me',
          success: function (response) {
        	  console.log(response)
          },
          fail: function (error) {
            console.log(error)
          },
        })
      },
      fail: function (error) {
        console.log(error)
      },
    })
  }
//카카오로그아웃  
function kakaoLogout() {
    if (Kakao.Auth.getAccessToken()) {
      Kakao.API.request({
        url: '/v1/user/unlink',
        success: function (response) {
        	console.log(response)
        },
        fail: function (error) {
          console.log(error)
        },
      })
      Kakao.Auth.setAccessToken(undefined)
    }
  }  
</script> -->
</body>
</html>
<%@ include file="../include/footer.jsp"%>
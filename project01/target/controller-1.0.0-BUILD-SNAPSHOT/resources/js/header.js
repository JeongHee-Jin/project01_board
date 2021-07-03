function logout(){
	if(confirm("로그아웃 하시겠습니까?")==true){
		location.href="/member/logout";
	}
}
function sideNavOpen(sideNav){
/* 	if(sideNav.classList.contains('blind')){
		sideNav.style.animation="sideNavAniOn 0.3s ease-in-out";
		sideNav.classList.toggle('blind')
	}else{
		sideNav.style.animation="sideNavAniOff 0.3s ease-in-out";
		setTimeout(function(){ sideNav.classList.toggle('blind')},280);
	} */
	sideNav.classList.toggle('blind');
	if(sideNav.classList!='blind'){
		var str1="";
		var str2="";
		$.ajax({
			url:"/sideNav",
			type:"POST",
			dataType:"json",
			success:function(data){
				for(var i=0;i<data.length/2;i++){
					str1+="<div class=brd_name><a href='/cmu/brd/"+data[i].BRD_MENU_LINK+"?mid="+data[i].BRD_IDX+"'>"+data[i].BRD_NAME+"</a></div>"
				}
				for(var i=data.length/2;i<data.length;i++){
					str2+="<div class=brd_name><a href='/cmu/brd/"+data[i].BRD_MENU_LINK+"?mid="+data[i].BRD_IDX+"'>"+data[i].BRD_NAME+"</a></div>"
				}
				$("#cmu1").empty();
				$("#cmu1").append(str1);
				$("#cmu2").empty();
				$("#cmu2").append(str2);
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 불러오기 실패하였습니다.");
			}
		})
	}
}

//window.onpageshow=function(event) {
//   //뒤로가기시 새로고침
//	if(event.persisted || (window.performance && window.performance.navigation.type==2)){
//		window.location.reload(true);
//	}
//};
document.addEventListener("DOMContentLoaded", function(){	
	//보여줄 게시물수 : 선택 후 보여질때
	var pageLen=document.getElementById('pageLen').value;
	var selectBox=document.getElementsByClassName('selectBox')[0];
	
	if(pageLen==5){
		selectBox.innerHTML="5개씩";
	}else if(pageLen==10){
		selectBox.innerHTML="10개씩";
	}else if(pageLen==15){
		selectBox.innerHTML="15개씩"; 
	}else if(pageLen==20){
		selectBox.innerHTML="20개씩";
	}else if(pageLen==30){
		selectBox.innerHTML="30개씩";
	}else if(pageLen==40){
		selectBox.innerHTML="40개씩";
	}else if(pageLen==50){
		selectBox.innerHTML="50개씩";
	}
	cateNameColor();
})
function toggleList(listSelect){
	listSelect.classList.toggle('is_selected');
}
function regist(){
	var brdKey=document.getElementById("boardKey").value;
	location.href="/board/register?brdKey="+brdKey;
}
function searchType(key,type){
	//input value에 searchType key 넣기
	document.getElementById("searchType").value=key;
	//span에 type명 넣게
	document.getElementById("_target").innerHTML=type;
	var searchTit=document.getElementById("search_tit");
	searchTit.classList.toggle('is_selected');
	
}
function searchBtn(a){
	var searchType=document.getElementById("searchType");
	var searchQuery=document.getElementById("searchQuery");
	var org=location.origin;
	var path=location.pathname;
	self.location= org+path+a
		+ "&searchType=" + searchType.value
		+ "&keyword="+searchQuery.value;
}
function searchBtn(a,mid){
	var searchType=document.getElementById("searchType");
	var searchQuery=document.getElementById("searchQuery");
	var org=location.origin;
	var path=location.pathname;
	self.location= org+path+a+mid
		+ "&searchType=" + searchType.value
		+ "&keyword="+searchQuery.value;
}
function searchBtn(a,mid,sub){
	var searchType=document.getElementById("searchType");
	var searchQuery=document.getElementById("searchQuery");
	var org=location.origin;
	var path=location.pathname;
	self.location= org+path+a
		+ "&searchType=" + searchType.value
		+ "&keyword="+searchQuery.value;
}
//카테고리 색
function cateNameColor(){
	var color= ["#44bbf5","#2a58ff","#9b5cff","#f174ca","#f77272","#ff9e6f","#f9c818","#93de2f","#18b139"];
	var cateAll=$('.colorize');
	var cateName=$('._cate');
	if(document.getElementById("brdId")){
		var mid=document.getElementById("brdId").value;		
		var num ;
		if(mid%2==0){
			num=4;
		}else{
			num=0;			
		}
		for (var i = 0; i < cateAll.length; i++) {
			num--;
			if(num==-1){
				num=8;
			}
			cateAll[i].style.color = color[num];
			for(var j = 0; j < cateName.length; j++){
				if(cateAll[i].innerText==cateName[j].innerText){
					cateName[j].style.color= color[num];
				}
			}
		}	
	}	
}
$(document).ready(function() {
	replyList(0);
	uploadFileList();
	//첨부파일 다운로드
	$(".uploadResult").on("click","span",function(e){
		var liobj=$(this).parents("li");
		//var path = encodeURIComponent(liobj.data("path")+"/"+liobj.data("uuid")+"_"+liobj.data("filename"));
		var path = encodeURIComponent(liobj.data("path"));
		var file = encodeURIComponent(liobj.data("uuid")+"_"+liobj.data("filename"));
		self.location = "/download?path=" + path+"&fileName="+file;
//		if (liobj.data("type")) {
//			showImage(path.replace(new RegExp(/\\/g), "/"));
//		} else {
//			self.location = "/download?path=" + path+"&file="+file;
//		}
	});
	
	//글삭제
	$("#remove").on("click", function() {
		if(confirm("게시글을 삭제하시겠습니까?")==true){
			var org=location.origin;
			var path=location.pathname;
			var nav=document.getElementById("nav").value;
			var postId=document.getElementById("postId").value;
			var mid=document.getElementById("mid").value;
			$.ajax({
				url:'/board/remove',
				dataType : "text",
				type : "POST",
				data : {"nav":nav,"postId":postId},
				success : function(data, textStatus, jqXHR) // 3개중에 하나만 써도 됨
				{
					if(path.includes("brd")){
						self.location=org+path+"?mid="+mid;
					}else{
						location.replace(org+path);
					}					
				},
				error : function(jqXHR, textStatus, errThrown) {
					console.log("err",errThrown);
				}
			})
		}		
	})
	$("input:checkbox[id='notice_hidden']").click(function(){
		var noticeChk = document.getElementsByClassName("noticeChk");
		for(var i=0;i<noticeChk.length;i++){
			noticeChk[i].classList.toggle('blind');
		}
	})

});
  
function copyURL(){
	//더미텍스트상자만들기
	var dummy = document.createElement("textarea");
	document.body.appendChild(dummy);
	//현재 url 가져오기
	var url =document.location.href;
	//더미에 value 값으로 url 넣기
	dummy.value=url;
	//더미 value선택
	dummy.select();
	//클립보드에 복사합니다.
	document.execCommand("copy");
	//더미삭제
	document.body.removeChild(dummy);
	//복사 후 출력
	alert("URL이 클립보드에 복사되었습니다"); 	
	//파라미터값만 가져오기
	//var para = document.location.href.split("?");
}

//function showImage(fileCallPath){ 
//    alert(fileCallPath);   
//    $(".bigPictureWrapper").css("display","flex").show();    
//    $(".bigPicture")
//    .html("<img src='/display?fileName="+fileCallPath+"' >")
//    .animate({width:'100%', height: '100%'}, 1000);    
//  }
//  $(".bigPictureWrapper").on("click", function(e){
//    $(".bigPicture").animate({width:'0%', height: '0%'}, 1000);
//    setTimeout(function(){
//      $('.bigPictureWrapper').hide();
//    }, 1000);
//  });

//댓글리스트
function replyList(pageNum){
	if(document.getElementById("nav")){
		if((document.getElementById("nav").value)!=10000){
			var nav=document.getElementById("nav").value;
			var postId=document.getElementById("postId").value;
			var ReplyBox=document.getElementById("ReplyBox");
			var replyCnt=document.getElementById("replyCnt");
			$.ajax({
				type:"POST",
				url: "/reply/list/"+nav+"/"+postId+"/"+pageNum,
				success: function(result){
					$("#reply_list").empty();
					var list= $(result).find("#ReplyBox").html();
					ReplyBox.innerHTML=list;
					$("#replyContent").val('');
					//var offset=$('#ReplyBox').offset(); 
					//$('html,body').animate({scrollTop:offset.top});
				},
				error : function(error) {console.log("err",error)}
			})
		}
	}
}
//headers: { 
//"Content-Type": "application/json",
//"X-HTTP-Method-Override": "POST" },
// 예를 들면 WAS가 4개의 메소드(CURD)를 모두 지원해도 
// 방화벽에서 GET과 POST만 받아들이면 PUT과 DELETE는 이용할 수 없죠.
// 이럴때 저 헤더키를 통해 사용하고자 하는 메소드를 별도로 설정해주면 저걸 key 값으로 해서 작업을 하게 되는거죠.
// post로 했는데도 다시 post로 지정한건 단지 일관성을 맞추기 위한거라 보입니다.
//JSON.stringify()
//value의 데이터 타입이 number 또는 boolean일 경우, 그 값 자체를 그대로 가져오고, 
//데이터타입은 string(문자열)이 된다.

//댓글입력
function replyInsert(){
	var nav=document.getElementById("nav").value;
	var brdId=document.getElementById("mid").value;
	var postId = document.getElementById("postId").value;
	var rpContent = document.getElementById("replyContent").value;
	if(rpContent==""){
		alert("댓글 내용을 입력하세요.");
	}else{
		$.ajax({
			type:"POST",
			url: "/reply/insert",
			headers: { 
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "POST" },
			dataType:"text",
			data: JSON.stringify({nav,brdId,postId,rpContent}) ,
			success:function(result){
				replyList(0);
			},error:function(err){
				console.log("err",err)
			}
		})
	}
}
//댓글수정
function replyUpdate(replyId){
	var nav=document.getElementById("nav").value;
	var postId = document.getElementById("postId").value;	
	var rpContent =document.getElementById("replyContent_"+replyId).value;
	//현재페이지
	var rpPageAct = document.querySelector("#rpPageAct ._underline").innerHTML;
	$.ajax({
		type:"put",
		url: "/reply/reply_update",
		headers: { 
		      "Content-Type": "application/json",
		      "X-HTTP-Method-Override": "PUT" },
		dataType:'text',
		data: JSON.stringify
			({nav,postId,replyId,rpContent}),
		success: function(result){
			replyList(rpPageAct);
		},error:function(err){console.log("err",err)}
	})
}
//댓글삭제
function replyDelete(replyId){
	//현재페이지
	var rpPageAct = document.querySelector("#rpPageAct ._underline").innerHTML;
	if(confirm("댓글을 삭제하시겠습니까?")==true){
		var nav=document.getElementById("nav").value;
		var postId = document.getElementById("postId").value;
		$.ajax({
			type:"delete",
			url: "/reply/reply_delete",
			headers: { 
			      "Content-Type": "application/json",
			      "X-HTTP-Method-Override": "DELETE" },
			dataType:'text',
			data: JSON.stringify
				({nav,postId,replyId }),
			success: function(result){
				replyList(rpPageAct);
			},error:function(err){console.log("err",err)}
		})
	}	
}
//게시물 페이지 첨부파일 리스트
function uploadFileList(){
	if($("input[name='nav']").val()){
		var nav = $("input[name='nav']").val();
		var postId = $("input[name='postId']").val();
		var uploadUL = $(".uploadResult");	
		var str = "";
		$.getJSON("/getAttachList",{nav,postId},function(arr){
			if(arr!=false){
				str+="<h4>첨부파일</h4><ul>"
				$(arr).each(function(i,obj) {
				      var fileCallPath =  encodeURIComponent(obj.fileId+"_"+obj.fileName);            
				      var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");       
			          str += "<li data-path = '"+obj.uploadPath+"' data-uuid='"+obj.fileId+"' data-filename='"+obj.fileName+"'" +
			        		" data-type='"+obj.fileType+"' class='fileLi'>";
			          str += "<span class='_underline'>"+ obj.fileName+"</span>"
				      str += "<input type = 'hidden' name = 'attachList["+i+"].uploadPath' value = '" + obj.uploadPath + "'>";
				      str += "<input type = 'hidden' name = 'attachList["+i+"].fileId' value = '" + obj.fileId + "'>";
				      str += "<input type = 'hidden' name = 'attachList["+i+"].fileName' value = '" + obj.fileName+"'>";
				      str +="</li>";
				})
				str+="</ul>"
				$(".uploadResult").html(str);
			}
		})
	}

}

function replyUpDiv(reply_id){
	//수정창 보이기
	var replyUpdate_view = document.getElementById("replyUpdate_"+reply_id);
	replyUpdate_view.classList.toggle('blind');
	//댓글 내용 가리기
	var reply_bline=document.getElementById("reply_area_"+reply_id);
	reply_bline.classList.toggle('blind');
}

function replyCancel(reply_id){
	var replyUpdate_view = document.getElementById("replyUpdate_"+reply_id);
	replyUpdate_view.classList.toggle('blind');
	var replyUpdate_view = document.getElementById("replyUpdate_"+reply_id);
	replyUpdate_view.classList.toggle('blind');
}

//function remove(){
//	var a=location.origin;
//	var b=location.param;
//	var $frm = $j('.read :input');
//	var param = $frm.serialize();
//	alert($frm);
//	$.ajax({
//		url:'/cmu/remove',
//		dataType : "json",
//		type : "POST",
//		data : param,
//		success : function(data, textStatus, jqXHR) // 3개중에 하나만 써도 됨
//		{
//			alert("작성완료");
//
//			alert("메세지:" + data.success);
//
//			location.href = a+b;
//		},
//		error : function(err) {
//			console.log("err",err);
//		}
//	})
//}
//function butt(mode){
////	var brdId=document.getElementById("brdId").value;
////	var brdKey=document.getElementById("brdKey").value;
//	var formObj = document.readForm;
//	if(mode=="regist"){
//		//글쓰기
//		formObj.action="/cmu/register";
//		/* formObj.action="/board/register?brdKey="+brdKey; */
//	}else if(mode=="commentaire"){
//		//답글
//		formObj.action="/cmu/commentaire";
//	}else if(mode=="modify"){
//		//수정
//		/* formObj.action="/board/modify?brdKey="+brdKey+"&brdId="+brdId; */
//		formObj.action="/cmu/modify";					
//	}else if(mode=="remove"){
//		//삭제
//		var rm=confirm("게시물을 삭제 하시겠습니까?");
//		if(rm==true){
//			formObj.action="/cmu/remove";
//			formObj.method= "post";
//		}else{
//			return;
//		}
//	}else if(mode=="list"){
//		//목록
//		formObj.action="/board/list";
//	}
//	formObj.submit();
//}
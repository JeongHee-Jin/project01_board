
//글쓰기 페이지
function registerView(){
	//들어온 게시판에서 글쓰기 클릭시 게시판 선택 selected
	var option_id=document.getElementById("brdId").value;
	var option_len=document.getElementById("selBoard").options.length;
	var option_val=document.getElementById("selBoard");
	if(option_len !=null){
		for (i = 0; i < option_len; i++) {
		    if (option_val.options[i].value==option_id) {
		    	option_val.options[i].selected = "selected";
		    }
		}
	}
	//첫카테고리 선택
	$("#selBCate option:eq(1)").prop("selected", true);

};
//수정페이지
function modifyView(){
	//전체공지/공지 체크
	var notice=document.getElementById("notice").value;
	if(notice=='T'){
		document.getElementById("chkNoticeTotal").setAttribute('checked',true);
	}else if(notice=='Y'){
		document.getElementById("chkNotice").setAttribute('checked',true);
	}
		
	//파일불러오기
	var nav = $("input[name='nav']").val();
	var postId = $("input[name='postId']").val();	
	if(postId!=null){
		$.getJSON("/getAttachList",{nav,postId},function(arr){
			var str = "";
			$(arr).each(function(i,obj) {
				//var fileCallPath =  encodeURIComponent(obj.uploadPath+"\\"+obj.fileId+"_"+obj.fileName);
				var fileCode =  encodeURIComponent(obj.fileId+"_"+obj.fileName);            
			    str += "<li data-path = '"+obj.uploadPath+"' data-uuid='"+obj.fileId+"' data-filename='"+obj.fileName+"'" +
				      	" data-type='"+obj.fileType+"' class='fileLi'>";
				str += "<span>"+ obj.fileName+"</span>"
				str += "<button type='button' data-file=\'"+fileCode+"\' data-path='"+obj.uploadPath+"' class='btn_file'>" +
			          	"<i class='fa fa-times'>x</i></button>";
//				str += "<button type='button' data-file=\'"+fileCode+"\' data-path='"+obj.uploadPath+"' class='btn_file'>" +
//	          	"<i class='fa fa-times'>x</i></button>";
			    str +="</li>";
			})			
			$(".uploadResult ul").html(str);
		})
	}

	//iframe에 값넣기
	var iframe= document.getElementById("content_iframe").contentWindow;
	var lodeContent=document.getElementById("content_iframe").innerHTML;
	var iframeD = iframe.document;

	iframeD.write("<!DOCTYPE html><html><body><font face='NanumSquareRound'>"+lodeContent+"</font></body></html>");
	
	iframeD.designMode = 'on';
	var iframeE = document.getElementById("content_iframe");
	iframe.focus();
};

$(document).ready(function() {	
	//textarea 숨기기
	var text= document.getElementById("content_text");
	text.style.visibility = "hidden"; 
	text.style.display="none";
	
	//iframe 디자인모드
	var iframe= document.getElementById("content_iframe").contentWindow;
	var iframeD = iframe.document;	
	iframeD.designMode = 'on';
	var iframeE = document.getElementById("content_iframe");
	iframe.focus();	
	document.getElementById("content_iframe").contentDocument
				.body.style.fontFamily  = "NanumSquareRound";
	
	//파일등록
	$("#uploadFile").change(function(e){
		var formData = new FormData();
		var inputFile = $("input[name='uploadFile']");
		var files = inputFile[0].files;
		
		for(var i=0; i<files.length; i++){
			if(!checkExtension(files[i].name, files[i].size)){
				return false;
			}
			formData.append("uploadFile", files[i]);
		}
		$.ajax({
			url : '/uploadAws',
			processData : false,
			contentType : false, 
			data : formData,
			type : 'POST',
			dataType : 'json',
				success:function(result){				
					showUploadResult(result);
				},error : function(error) {
					console.log("err",error);
				}
		});
	});
	//파일업로드 상세처리
	var regex = new RegExp("(.*?)\.(exe|sh|alz)$");
	var maxSize = 2097152;
	//var maxSize = 5242880; // 5MB		
	function checkExtension(fileName, fileSize){
		if(fileSize >= maxSize){
			alert("파일 사이즈 초과");
			return false;
		}
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		return true;
	}
	
	//첨부파일 삭제(글쓰기 삭제)
	 $(".uploadResult").on("click", "button", function(e){
		 var targetLi = $(this).closest("li");	  
		 var targetFile = $(this).data("file");
		 var path = $(this).data("path"); 
		 $.post("/deleteFile",{path,targetFile},function(arr){
			 
		 },'json');
		 targetLi.remove();
	 });
	 
	//게시물 글자효과
	$("select").change(function(){				//command,showUI,value
		iframeE.contentDocument.execCommand($(this).attr('id'),false,$(this).val());
	})	
	$(".editor_btn").click(function(){
		iframeE.contentDocument.execCommand($(this).attr('id'),false,null);
	})
	//공지글,비밀글 설정
	$(function(){
		//전체공지
		$("input:checkbox[id='chkNoticeTotal']").click(function(){
			const checkboxs = document.getElementsByName("notice");			
			if($(this).is(":checked")){
				$(this).val('T');
				if($("input:checkbox[id='chkNotice']").is(":checked")==true){
					$("input:checkbox[id='chkNotice']").prop("checked", false);
				}
			}else{
				$(this).val('N');
			}
		})
		//공지
		$("input:checkbox[id='chkNotice']").click(function(){
			const checkboxs = document.getElementsByName("notice");			
			if($(this).is(":checked")){
				$(this).val('Y');
				if($("input:checkbox[id='chkNoticeTotal']").is(":checked")==true){
					$("input:checkbox[id='chkNoticeTotal']").prop("checked", false);
				}
			}else{
				$(this).val('N');
			}
		})
		//비밀글
		$("input:checkbox[id='chkSecret']").click(function(){
			if($(this).is(":checked")){
				$(this).val('Y');
			}else{
				$(this).val('N');
			}
		})
	})	
});
//첨부파일 파일명 보여주기
function showUploadResult(uploadResultArr){
	  if(!uploadResultArr || uploadResultArr.length == 0){return ;}
	  var uploadUL = $(".uploadResult ul");
	  var str = "";
	  //.each 반복
	  $(uploadResultArr).each(function(i, obj){
		  //첨부파일 표시
		  var fileCode =  encodeURIComponent(obj.fileId+"_"+obj.fileName);            
		  //var fileLink = fileCallPath.replace(new RegExp(/\\/g),"/");  
		  str += "<li data-path = '"+obj.uploadPath+"' data-uuid='"+obj.fileId+"' " +
		  		"data-filename='"+obj.fileName+"' class='fileLi'>";
		  str += "<span> "+ obj.fileName+"</span>";
		  str += "<button type='button' data-file=\'"+fileCode+"\' data-path='"+obj.uploadPath+"' class='btn_file'>" +
	          		"<i class='fa fa-times'>x</i></button>";
		  str +"</li>";
		});
	  
		uploadUL.append(str);
}
//게시판 선택값 바뀔시
function chageMenu(){
	var menuBoard=document.getElementById("menuBoard");
	var menuBoardVal=selBoard.options[menuBoard.selectedIndex].value;
	var selBoard=document.getElementById("selBoard");
	$.ajax({
		url:"/board/catelist/"+menuBoardVal,
		type:"POST",
		success:function(data){
			str+="<option value='' id='optBCate' >말머리선택</option>"
			data.forEach(brd=>{
				str+="<option value="+brd.CATE_IDX+">"+brd.CATE_NAME+"</option>";
			})
			selBoard.innerHTML=str;
		},error : function(error) {
			console.log("err",error);
		}		
	})
}
function chageBoard(){
	var selBoard=document.getElementById("selBoard");
	var selBoardVal=selBoard.options[selBoard.selectedIndex].value;
	var selBCate=document.getElementById("selBCate");
	var str="";
	$.ajax({
		url:"/board/catelist",
		type:"POST",
		data:{"mid":selBoardVal},
		dataType:"json",
		success:function(data){
			str+="<option value='' id='optBCate' >말머리선택</option>"
			data.forEach(cate=>{
				str+="<option value="+cate.CATE_IDX+">"+cate.CATE_NAME+"</option>";
			})
			selBCate.innerHTML=str;
		},error : function(error) {
			console.log("err",error);
		}		
	})
}


//	url : 요청 Url
//	data : 요청과 함께 서버로 데이터를 전송 할 string 또는 map
//	dataType : 서버측에서 전송받은 데이터의 형식 (default : xml, json, script, text, html)
//"xml": xml문서로 파싱, 콜백에 xml Dom전달
//"html": HTML을 텍스트 데이터로. 여기에 script 태그가 포함된 경우 처리가 실행됩니다.응답텍스트 처리과정없이, 콜백 함수로 전달
//"script": JavaScript 코드를 텍스트 데이터로. cache 옵션 특히 지정이 없으면 캐시가 자동으로 비활성화됩니다. 원격 도메인에 대한 요청의 경우 POST는 GET으로 변환됩니다.
//			응답텍스트 콜백에 전달, 응답은 모든 콜백의 호출보다 먼저 자바스크립트 구문으로 처리
//"json": JSON 형식 데이터로 평가하고 JavaScript의 개체로 변환합니다.
//"jsonp": JSONP로 요청을 부르고 callback 매개 변수에 지정된 함수 회수 값을 JSON 데이터로 처리합니다. 생성된 객체 콜백에 전달 (jQuery 1.2 추가)
//"text": 일반 텍스트.
//	seccess (PlainObject data, String textStatus, jqXHR jqXHR) : 요청이 성공 했을 때 호출할 콜백 함수
//	type : 데이터를 전송하는 방법 (get, post)
//	var xhr=new XMLHttpRequest(); //객체인스턴스는 객체의 생성자 표현식으로 생성	
//	xhr.onload=function(){
//		if(xhr.status===200){//서버의 응답이 정상이면,
//			//서버로부터 전달 된 데이터를 responseObject 라는 변수에 저장
//			//이 데이터는 XMLHttpRequest객처의 responseText속성을 통해 가져올 수 있다.
//			//일단 서버로부터 데이터가 전송되면, json데이터는 문자열이므로 
//			//json객체의 parse()메서드를 호출하여 자바스크립트 객체로 변환
//			alert(httpRequest.responseText);
//			responseObject = JSON.parse(xhr.responseText);
//			var newContent='';
//		}	
//	}
//	xhr.open("POST",'data/data.json',true);//요청준비(세 개의 매개변수를 정의)
//	// 준비된 요청을 전달하는 메서드/괄호 내 서버에 전달될 추가 정보를 지정 가능 / 추가 정보전달되지 않으면 null사용
//	xhr.send('search=arduino'); 	
//}
//소스보여주기
function showSource() {
	var text = document.getElementById("content_text");
	var ifr = document.getElementById('content_iframe').contentWindow.document.body.innerHTML;
	var ifrv = document.getElementById('content_iframe');
	if(text.style.visibility=="hidden"){
		document.getElementById("content_text").value =ifr;
		text.style.visibility="visible";
		text.style.display="block";
		text.style.width="100%";
		text.style.height="400px";
		text.style.background="black";
		text.style.color="white";
		ifrv.style.visibility="hidden";
		ifrv.style.display="none";
	}else if(text.style.visibility=="visible"){
		ifrv.style.visibility="visible";
		ifrv.style.display="block";
		text.style.visibility="hidden";
		text.style.display="none";
	}
}
function showText() {
	document.getElementById("li_txt").innerHTML	
	
}
//선택영역에 링크 첨부
function createLink() {
	var iframe= document.getElementById("content_iframe");
    var url = prompt("Enter URL:", "http://");
    if (url)
        iframe.contentDocument.execCommand("createlink", false, url);
}
//form 전송
function insert(a){	
	//var path=location.pathname;
	var selB=document.getElementById("selBoard");
	var tit= document.getElementById("title");
	var ifr= document.getElementById('content_iframe');
	//태그 글자 포함
	var ifrHTML= document.getElementById('content_iframe').contentWindow.document.body.innerHTML;
	//글자만
	if(selB.value==""){
		alert("게시판을 선택하세요.");
		return false;
	}
	if(tit.value==""){		
		alert("제목을 입력하세요.");
		tit.focus();
		return false;
	}else if(ifrHTML.length==0){
		alert("내용을 입력하세요.");
		ifr.focus();
		return false;
	}else if(ifrHTML.length>3000){
		alert("글자수 및 태그 수를 초과하였습니다.");
		ifr.focus();
		return false;
	}else{
		var text = document.getElementById("content_text");
		text.value=ifrHTML;
		
		//파일정보
		str="";
		$(".uploadResult ul li").each(function(i,obj){
			var job=$(obj);
			console.log(job.data);
	        str += "<input type = 'hidden' name = 'attachList["+i+"].uploadPath' value = '" + job.data("path") + "'>";
	        str += "<input type = 'hidden' name = 'attachList["+i+"].fileId' value = '" + job.data("uuid") + "'>";
	        str += "<input type = 'hidden' name = 'attachList["+i+"].fileName' value = '" + job.data("filename")+"'>";
		});
		$(".uploadResult .attach-input").html(str);
		a.encoding="application/x-www-form-urlencoded";
		a.submit();
	}
}

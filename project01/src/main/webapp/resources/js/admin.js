//var getUrl=window.location;
//var baseUrl="/"+getUrl.pathname.split('/')[1];
//if(baseUrl!="/project01"){
//	baseUrl="";
//}
function searchUser(a){
	var searchType=$("#searchType option:selected").val();
	var searchWord=document.getElementById("mem_srch").value;
	var org=location.origin;
	var path=location.pathname;
	self.location= org+path+"?_method=user"
	+"&searchType=" + searchType+ "&keyword="+searchWord;
}
//전체선택
function allChk(check){
	const checkboxes=document.querySelectorAll("input[type='checkbox']");
	checkboxes.forEach((checkbox)=>{
		checkbox.checked=check.checked;
	})
}
function chkUser(role){
	var cnt = document.querySelectorAll("input[type='checkbox']:checked");
	if(cnt.length==0){
		alert("선택한 유저가 없습니다.");
	}else{
		if(confirm("선택한 유저의 상태를 변경 하시겠습니까?")==true){		
			var arr = new Array();
			cnt.forEach((checkbox)=>{
				arr.push(checkbox.getAttribute("value"));
			})
			$.ajax({
				url:"/manage/userRoleModify",
				type:"POST",
				dataType:'json',
				data:{"userIdArr":arr,"role":role},
				success:function(data){
					if(data==0){
						alert("변경에 실패하였습니다.");
					}else{
						alert("변경 되었습니다.");
						location.reload();
					}
				},error:function(errThrown){
					console.log("err",errThrown);
					alert("서버 오류로 변경에 실패하였습니다.");
				}
					
			})
		}
	}	
}


//숨긴 div 열기/닫기
function divOpen(box){
	box.classList.toggle('hidden');
	var input = box.querySelectorAll("input[type='text']");
	for(var i=0;input.length>i;i++){
		input[i].value="";
	}
}

//게시판 메뉴 추가
function addBoardMenu_btn(){
	var tabId=document.querySelectorAll("input[name='boardMenuVal']:checked")[0].value;
	var addMenuName=document.getElementById("addBrdMenuTxt").value;
	var addMenuLink=document.getElementById("addBrdMenulink").value;
	if(tabId==""||addMenuName==""||addMenuLink==""){
		alert("입력값을 확인하세요.");
	}else{
		$.ajax({
			url:"/manage/addMenuName",		
			type:"POST",
			data:{tabId,addMenuName,addMenuLink},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("게시판 메뉴 추가에 실패하였습니다.");
				}else{
					alert("게시판 메뉴가 추가 되었습니다.");
					var str="";
					location.reload();
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 등록에 실패하였습니다.");
			}
		})
	}
	
}
//게시판 메뉴 이름 수정
function brdMenuMod(brdMenuId){
	var brdMenuValue=document.getElementById("brdmenuTxt").value;
	if(brdMenuValue==""){
		alert("입력값을 확인해 주세요");
	}else{
		$.ajax({
			url:"/manage/brdMenuModify",
			type:"POST",
			data:{brdMenuId,brdMenuValue},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("수정에 실패하였습니다");
				}else{
					alert("수정되었습니다.");
					location.reload();
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 불러오기 실패하였습니다.");
			}
		})
	}	
}
//게시판 메뉴 삭제
function brdMenuDel(brdMenuId){
	if(confirm("선택한 게시판 메뉴를 삭제 하시면 하위 게시판도 같이 삭제됩니다.\n 삭제 하시겠습니까?")==true){
		$.ajax({
			url:"/manage/brdMenuDelete",
			type:"POST",
			data:{brdMenuId},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("삭제에 실패하였습니다");
				}else{
					alert("삭제되었습니다.");
					location.reload();
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 불러오기 실패하였습니다.");
			}
		})
	}
}
//게시판 이름 나열+게시판 메뉴 수정/삭제창
var brdMenuIdx="";
var menuIdx="";
function boardListOpen(menuId,brdMenuId,text){
	var brdMenuSet=document.getElementById("brdMenuSet");
	var board_box=document.getElementById("board_ListBox");
	menuIdx=menuId;
	//게시판 
	if(brdMenuIdx==""||brdMenuIdx==brdMenuId){	
		brdMenuIdx=brdMenuId;	
		brdMenuSet.classList.toggle('hidden');
		board_box.classList.toggle('hidden');
	}else if(brdMenuIdx!=brdMenuId && brdMenuSet.classList=="hidden"){
		brdMenuIdx="";
		brdMenuSet.classList.toggle('hidden');
		board_box.classList.toggle('hidden');
	}else{
		brdMenuIdx=brdMenuId;
	}
	
	document.getElementById("brdmenuTxt").value=text;
	document.getElementById("brdMenuMod_btn").setAttribute("onclick","brdMenuMod("+brdMenuId+")");
	document.getElementById("brdMenuDel_btn").setAttribute("onclick","brdMenuDel("+brdMenuId+")");
	if(brdMenuSet.classList!="hidden"){
		loadBrd();
	}
}
//게시판 추가
function addBoard_btn(){
	if(brdMenuIdx!=""){
		var brdName_input=document.getElementById("addBrdTxt");
		brdName=brdName_input.value;
		$.ajax({
			url:"/manage/addBoardName",		
			type:"POST",
			data:{brdMenuIdx,brdName,menuIdx},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("게시판 증설에 실패하였습니다.");
					brdName_input.value="";
				}else{
					alert("게시판이 증설되었습니다.");
					brdName_input.value="";
					loadBrd();
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 등록에 실패하였습니다.");
			}
		})
	}else{
		alert("게시판 메뉴를 선택해주세요.");
	}
}
function loadBrd(){
	var str="";
	$.ajax({
		url:"/manage/boardList",
		type:"POST",
		data:{"brdMenuIdx":brdMenuIdx},
		dataType:"json",
		success:function(data){
			$.each(data, function(key, value){
				str+="<li onclick=cateListOpen("+value.BRD_IDX+",'"+value.BRD_NAME+"');>"+value.BRD_NAME +"</li> ";
			});
			$("#board_nav").empty();
			$("#board_nav").append(str);
		},error:function(errThrown){
			console.log("err",errThrown);
			alert("서버 오류로 불러오기 실패하였습니다.");
		}
	})
}
//게시판이름 수정
function brdMod(brdId){
	var brdValue=document.getElementById("boardTxt").value;
	if(brdValue==""){
		alert("입력값을 확인해 주세요");
	}else{
		$.ajax({
			url:"/manage/brdModify",
			type:"POST",
			data:{brdId,brdValue},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("수정에 실패하였습니다");
				}else{
					alert("수정되었습니다.");
					loadBrd();
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 불러오기 실패하였습니다.");
			}
		})
	}	
}
//게시판 삭제
function brdDel(brdId){
	if(confirm("선택한 게시판을 삭제 하시면 하위 카테고리도 같이 삭제됩니다.\n 삭제 하시겠습니까?")==true){
		$.ajax({
			url:"/manage/brdDelete",
			type:"POST",
			data:{brdId},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("삭제에 실패하였습니다");
				}else{
					alert("삭제되었습니다.");
					loadBrd();
					cateListOpen(brdId,'');
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 불러오기 실패하였습니다.");
			}
		})
	}
}

//카테고리 이름 나열
var brdIdx="";
function cateListOpen(brdId,text){
	var brdSet=document.getElementById("brdSet");
	var cate_box=document.getElementById("cate_ListBox");
	if(brdIdx==""||brdIdx==brdId){	
		brdIdx=brdId;		
		brdSet.classList.toggle('hidden');
		cate_box.classList.toggle('hidden');
	}else if(brdIdx!=brdId && brdMenuSet.classList=="hidden"){
		brdIdx="";
		brdSet.classList.toggle('hidden');
		cate_box.classList.toggle('hidden');
	}else{
		brdIdx=brdId;
	}
	document.getElementById("boardTxt").value=text;
	document.getElementById("brdMod_btn").setAttribute("onclick","brdMod("+brdId+")");
	document.getElementById("brdDel_btn").setAttribute("onclick","brdDel("+brdId+")");
	
	if(brdMenuSet.classList!="hidden"){
		loadCate();
	}
}

//카테고리 이름 수정창 열기
var cateIdx="";
function cateNameOpen(cateId,text){
	var cateSet=document.getElementById("cateSet");
	if(cateIdx==""||cateIdx==cateId){	
		cateIdx=cateId;		
		cateSet.classList.toggle('hidden');
	}else if(cateIdx!=cateId && cateSet.classList=="hidden"){
		cateIdx="";
		cateSet.classList.toggle('hidden');
	}else{
		cateIdx=cateId;
	}
	document.getElementById("cateTxt").value=text;
	document.getElementById("cateMod_btn").setAttribute("onclick","cateMod("+cateId+")");
	document.getElementById("cateDel_btn").setAttribute("onclick","cateDel("+cateId+")");
}

//카테고리 추가
function addCate_btn(){	
	if(brdIdx!=""){
		var cateName_input=document.getElementById("addCateTxt");
		cateName=cateName_input.value;
		$.ajax({
			url:"/manage/addCateName",		
			type:"POST",
			data:{cateName,brdIdx},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("카테고리 등록에 실패하였습니다.");
					cateName_input.value="";
				}else{
					alert("카테고리가 등록되었습니다.");
					cateName_input.value="";
					loadCate();
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 등록에 실패하였습니다.");
			}
		})
	}else{
		alert("게시판을 선택해주세요.");
	}
}
function loadCate(){
	var str="";
	$.ajax({
		url:"/manage/cateList",
		type:"POST",
		data:{"brdIdx":brdIdx},
		dataType:"json",
		success:function(data){
			$.each(data, function(key, value){
				str+="<li onclick=cateNameOpen("+value.CATE_IDX+",'"+value.CATE_NAME+"');>"+value.CATE_NAME +"</li> ";
			});
			$("#cate_nav").empty();
			$("#cate_nav").append(str);
		},error:function(errThrown){
			console.log("err",errThrown);
			alert("서버 오류로 등록에 실패하였습니다.");
		}
	})
}
//카테고리이름 수정
function cateMod(cateId){
	var cateValue=document.getElementById("cateTxt").value;
	if(cateValue==""){
		alert("입력값을 확인해 주세요");
	}else{
		$.ajax({
			url:"/manage/cateModify",
			type:"POST",
			data:{cateId,cateValue},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("수정에 실패하였습니다");
				}else{
					alert("수정되었습니다.");
					loadCate();
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 불러오기 실패하였습니다.");
			}
		})
	}	
}
//카테고리 삭제
function cateDel(cateId){
	if(confirm("선택한 카테고리를 삭제 하시겠습니까?")==true){
		$.ajax({
			url:"/manage/cateDelete",
			type:"POST",
			data:{cateId},
			dataType:"json",
			success:function(data){
				if(data==0){
					alert("삭제에 실패하였습니다");
				}else{
					alert("삭제되었습니다.");					
					loadCate();
					cateNameOpen(cateId,'');
				}
			},error:function(errThrown){
				console.log("err",errThrown);
				alert("서버 오류로 불러오기 실패하였습니다.");
			}
		})
	}
}
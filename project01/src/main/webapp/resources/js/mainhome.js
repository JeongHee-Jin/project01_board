getGeolocation();
function getGeolocation(){ 
	if(navigator.geolocation) { //사용 가능 여부
	      navigator.geolocation.getCurrentPosition(position => {//위치 허용 여부
	        const lat = position.coords.latitude;	//위도
	        const lon = position.coords.longitude;	//경도
	        getWeather(lat, lon);
	      }, error => { //위치 정보 허용 X= 기본값: 서울
	    	  getWeather(37.56826,126.977829);
	      })
	} else { //사용 불가 브라우저= 기본값: 서울
		getWeather(37.56826,126.977829);
	}
}
function getWeather(lat, lon) {
	var svg="";
	var str="";
    var chartData=[];
    var chartDataTemp=[];
	fetch(`https://api.openweathermap.org/data/2.5/forecast?lat=`+lat+`&lon=`+lon
		    +`&appid=bafd431d2ed57c71b8ac678da1847fa3`)
    .then(res => res.json())
    .then(data => {
    	for(var a=0;a<5;a++){			
    		var temp=Math.ceil(data.list[a].main.temp-273.15); //기온
    		var w_Icon=`https://openweathermap.org/img/wn/`+data.list[a].weather[0].icon+`@2x.png`;  //아이콘
    		var w_time=data.list[a].dt_txt;	//시간대
    		var city=data.city.name;
    		document.getElementById("city").innerText='■'+city;
    		if(w_time.indexOf("00:00:00")!=-1){
    			w_time="00시";
    		}else if(w_time.indexOf("03:00:00")!=-1){w_time="03시";
    		}else if(w_time.indexOf("06:00:00")!=-1){w_time="06시";
    		}else if(w_time.indexOf("09:00:00")!=-1){w_time="09시";
    		}else if(w_time.indexOf("12:00:00")!=-1){w_time="12시";
    		}else if(w_time.indexOf("15:00:00")!=-1){w_time="15시";
    		}else if(w_time.indexOf("18:00:00")!=-1){w_time="18시";
    		}else if(w_time.indexOf("21:00:00")!=-1){w_time="21시";
    		}   	
    		chartData.push((data.list[a].main.temp)-350); 
    		
    		//마지막 날씨 이미지 추가 안함
    		if(a!=4){
    			str+="<li class='time_li'><dl><dd class='weather_item'><span class='weatherTxt'>"+
    				temp+`&#176;C`+"</span><span class='dot_point'></span></dd>";
    			str+="<dd class='_icon'><img class='weatherIcon' src='"+w_Icon+"' alt='Weather icon'></dd>";
    			str+="<dd class='_time'>"+w_time+"</dd></dl></li>"
    		}else{
    			str+="<li class='time_li _width'><dl><dd class='weather_item'><span class='weatherTxt'>"+
					temp+`&#176;C`+"</span><span class='dot_point'></span></dd>";
    			str+="<dd class='_icon'><div class='weatherIcon _box'></div></dd>";
    			str+="<dd class='_time'>"+w_time+"</dd></dl></li>"
    		}   
    	} 
    	//그래프 위치 계산
    	for(var i=0;i<5;i++){
    		chartData[i]=Math.ceil(Math.abs(chartData[i])*100)/100;
    		if(chartData[i]<100){
    			chartData[i]=Math.ceil((chartData[i]-30)*100)/100;
    		}   		
    	}
    	//그래프선
    	var dUrl="M17,"+chartData[0]+" L102.5,"+chartData[1]+" L189,"+chartData[2]
			+" L275.5,"+chartData[3]+" L360.5,"+chartData[4];
    	document.getElementById("weather_graph").setAttribute("d",dUrl);
    	document.getElementById("weather_ul").innerHTML=str;
    	//$("#weather_ul").append(str);
    
    	//그래프 점 위치
    	var dotBottom=document.getElementsByClassName("weather_item");
    	for(var i=0;i<dotBottom.length;i++){  
        	dotBottom[i].style.top = (chartData[i]-15)+"px";    		
    	}
    })
}

//슬라이드 광고 이미지
var pos = 0;
var totalSlides =0;
var slideIndex = 0;
window.onload=function(){
	totalSlides =slider.getElementsByTagName("li").length;
	for(var i=0;i<totalSlides;i++){
		$('#img_dots').append('<span class="dot" onclick="currentSlide('+i+')"></span>');
	}
	countSlides(0);
	showSlides(slideIndex);
	//이미지 자동 변경
	var sec = 3000;
	setInterval(function(){
		slideIndex++;
		showSlides(slideIndex);
		countSlides(slideIndex);
	}, sec);
}
//prev/next 버튼 작동
function moveSlides(n) {
  slideIndex = slideIndex + n;
  showSlides(slideIndex);
  countSlides(slideIndex);
}
//이미지번호
function countSlides(n){
  $('#counter').html(n+ 1+' / ' + totalSlides);
}
//원형 버튼 작동
function currentSlide(n) {
  slideIndex = n;
  showSlides(slideIndex);
  countSlides(n);
}
//
function showSlides(n) {
	var slides = document.getElementsByClassName("mySlides");
	var dots = document.getElementsByClassName("dot");
	var size = slides.length;
	if ((n+1) > size) {
	  slideIndex = 0; n = 0;
	}else if (n < 0) {
	  slideIndex = (size-1);
	  n = (size-1);
	}
	for (i = 0; i < slides.length; i++) {
		slides[i].style.display = "none";
	}
	for (i = 0; i < dots.length; i++) {
		dots[i].className = dots[i].className.replace(" active", "");
	}
	slides[n].style.display = "block";
	dots[n].className += " active";
}

/*function pagination(){
	  $('#pagination-wrap ul li').removeClass('active');
	  $('#pagination-wrap ul li:eq('+pos+')').addClass('active');
	}*/	

package org.project01.controller;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.project01.domain.PageMaker;
import org.project01.domain.ReplyVO;
import org.project01.domain.SearchCriteria;
import org.project01.domain.UserDetailsVO;
import org.project01.service.ReplyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

//@Controller와 @RestController의 차이
//HTTP Response Body가 생성되는 방식의 차이라고 한다.
//기존의 MVC @Controller는 view를 기본적으로 리턴하는데, @RestController는  객체 반환시 데이터를 바로 
//JSON/XML 타입의 HTTP 응답을 직접 리턴하게 된다.
//Controller에 @ResponseBody가 붙은 셈.

//흐름순서
//﻿@Controller
//Client(Browser) -> Request -> Dispatcher Servlet -> Handler Mapping -> Controller -> 
//View -> Dispatcher Servlet -> Response -> Client(Browser)
//
//@RestController 
//Client(browser) -> HTTP Request -> Dispatcher Servlet -> Handler Mapping -> 
//RestController(자동 ResponseBody 추가) -> Http Response -> Client(browser)

//ResponseEntity
//별도의 View를 제공하지 않는 형태로 서비스를 실행한다. 예외의 상황에서 문제가 발생 할 수 있다.
//개발자가 직접 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스로 
//404, 500같은 HTTP 상태 코드를 전송하려는 데이터와 함께 전송 가능 하므로 세밀한 제어를 원하는 경우 사용 가능하다.

//@ResponseBody를 붙이지 않아도 뷰가 아닌 데이터를 리턴할 수 있다
//하지만 @RestController을 사용할때 리턴값으로 바로 주소를 입력해버리면 그 주소 자체가 화면에 떠버리니 주의!

@Controller
@RequestMapping(value="/reply/*")
public class ReplyController {
	
	private static final Logger logger = LoggerFactory.getLogger(BrdController.class);
	
	@Inject
	private ReplyService service;
	  
	//댓글 리스트
	@RequestMapping(value="/list/{nav}/{postId}/{pageNum}" , method = RequestMethod.POST)
	public ModelAndView replyList(@PathVariable("nav") int nav,@PathVariable("postId") int postId, 
			@PathVariable("pageNum") int pageNum, ModelAndView mav) {
		logger.info("댓글리스트");	
		ResponseEntity<Map<String, Object>> entity = null;
		try {
			//한 페이지에 댓글 출력수 vo에 넣기
			SearchCriteria cri = new SearchCriteria();
			cri.setPage(pageNum);
			
			Map<String,Object> map = new HashMap<>(); 
			map.put("nav",nav);
			map.put("postId",postId);
			map.put("cri",cri);			
			
			//페이지번호
			PageMaker pageMk = new PageMaker();			
			int count=service.count(map);
			//게시물 클릭시 맨 마지막 페이지번호로 이동 
			if(pageNum==0) {
				int num=(int)(Math.ceil(count/(double)cri.getPageLen()));
				cri.setPage(num);
			}
			pageMk.setCri(cri);
			pageMk.setTotalCount(count); 	//총개수
			
			mav.addObject("replyList", service.listReplyPage(map));			
			mav.addObject("pageMkRp", pageMk);
			mav.addObject("replyCnt",pageMk.getTotalCount());
			mav.addObject("replyPrint",1);		//view구분
			mav.setViewName("/board/brdnormal");//뷰의 이름
						
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return mav;
	}
	//@ResponseEntity : 데이터 + http status code
	//@ResponseBody : 객체를 json으로 (json-String)
	//@RequestBody : json을 객체로
	
	//댓글 등록
	@RequestMapping(value="/insert", method = RequestMethod.POST)
	public ResponseEntity<String> replyInsert(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		logger.info("댓글쓰기");		
		try {
			UserDetailsVO user=(UserDetailsVO)SecurityContextHolder.getContext()
								.getAuthentication().getPrincipal();
			vo.setMemId(user.getUserId());
			vo.setMemNickName(user.getUserNickName());
			service.replyInsert(vo);
			entity = new ResponseEntity<String>("success",HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//댓글수정
	@RequestMapping(value="/reply_update",method = { RequestMethod.PUT, RequestMethod.PATCH })
	public ResponseEntity<String> replyUpdate(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		logger.info("댓글수정"+vo);	
		//String userId = (String) session.getAttribute("userId");
		try {
			service.replyUpdate(vo);
			entity = new ResponseEntity<String>("success",HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;

	}
	//댓글삭제
	@RequestMapping(value="/reply_delete",method = RequestMethod.DELETE)
	public ResponseEntity<String> replyDelete(@RequestBody ReplyVO vo){
		ResponseEntity<String> entity = null;
		logger.info("삭제");
		try {
			service.replyDelete(vo);
			entity = new ResponseEntity<String>("success",HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
			entity=new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;

	}
}

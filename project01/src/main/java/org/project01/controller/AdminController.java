package org.project01.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.project01.domain.PageMaker;
import org.project01.domain.SearchCriteria;
import org.project01.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/manage/*")
public class AdminController {
	private static final Logger logger=LoggerFactory.getLogger(AdminController.class);
	
	@Inject
	private AdminService service;
	
	
	//유저관리
	@GetMapping("/admin")
	public ModelAndView adminPage(@ModelAttribute("_method")String method,Model model,SearchCriteria cri) 
			throws Exception{
		logger.info("관리자페이지");
		ModelAndView mv = new ModelAndView();		
		try {
		    if(method.equals("user")) {
		    	logger.info("유저관리 페이지");
		    	Map<String, Object> map = new HashMap<String, Object>();
				map.put("cri", cri);
		    	model.addAttribute("userList",service.getUserList(map));	
			   	
			   	PageMaker pageMk=new PageMaker();
			  	pageMk.setCri(cri);
		    	pageMk.setTotalCount(service.userTotal(map));
		    	model.addAttribute("pageMk", pageMk);
		    	
		    	mv.setViewName("/manage/admin");
	    	}else if(method.equals("menu")){
		    	logger.info("메뉴관리 페이지");
		   		model.addAttribute("brdMenuList",service.brdMenuList());
		   		mv.setViewName("/manage/adminMenu");		    		
		   	}else {
		   		mv.setViewName("/");
		   	}		    	
		}catch(Exception e) {
			logger.info(e.getMessage());
		}
		return mv;
	}
	//유저상태변경
	@PostMapping("/userRoleModify")
	public @ResponseBody int chkUserRoleModify(@RequestParam(value="userIdArr[]") 
	List<String> userIdArr,String role) throws Exception{
		logger.info("선택 유저 상태 변경");
		int result=0;
		try {
			if(role.equals("stop")) {
				role="ROLE_STOP";
			}else {
				role="ROLE_MEMBER";
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("userIdArr", userIdArr);
			map.put("role", role);
			result=service.chkUserRoleModify(map);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	//게시판메뉴관리
	@PostMapping("/addMenuName")	
	public @ResponseBody int addMenuName(int tabId,String addMenuName,
			String addMenuLink) throws Exception {
		logger.info("게시판메뉴 추가하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("tabId",tabId);
			map.put("addMenuName",addMenuName);
			map.put("addMenuLink",addMenuLink);
			result=service.addMenuName(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	@PostMapping("/brdMenuModify")	
	public @ResponseBody int brdMenuModify(int brdMenuId,
			String brdMenuValue) throws Exception {
		logger.info("게시판메뉴 수정하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("brdMenuId",brdMenuId);
			map.put("brdMenuValue",brdMenuValue);
			result=service.brdMenuModify(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	@PostMapping("/brdMenuDelete")	
	public @ResponseBody int brdMenuDelete(int brdMenuId) 
			throws Exception {
		logger.info("게시판메뉴 삭제하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("brdMenuId",brdMenuId);
			result=service.brdMenuDelete(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	
	//게시판 관리
	@PostMapping("/boardList")	
	public @ResponseBody List<Object> boardList(int brdMenuIdx) throws Exception {
		logger.info("게시판 리스트 불러오기"+brdMenuIdx);
		List<Object> map=new ArrayList<Object> ();
		try {
			map=service.boardList(brdMenuIdx);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return map;
	}
	
	@PostMapping("/addBoardName")	
	public @ResponseBody int addBoardName(int brdMenuIdx,String brdName,int menuIdx) 
			throws Exception {
		logger.info("게시판 추가하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("brdMenuIdx",brdMenuIdx);
			map.put("brdName",brdName);
			map.put("menuIdx",menuIdx);
			result=service.addBoardName(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}	
	
	@PostMapping("/brdModify")	
	public @ResponseBody int brdModify(int brdId,String brdValue) throws Exception {
		logger.info("게시판 수정하기 ");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("brdId",brdId);
			map.put("brdValue",brdValue);
			result=service.brdModify(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	@PostMapping("/brdDelete")	
	public @ResponseBody int brdDelete(int brdId) throws Exception {
		logger.info("게시판 삭제하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("brdId",brdId);
			result=service.brdDelete(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	
	//카테고리  관리
	@PostMapping("/cateList")	
	public @ResponseBody List<Object> cateList(int brdIdx) throws Exception {
		logger.info("카테고리 리스트 불러오기");
		List<Object> map=new ArrayList<Object> ();
		try {
			map=service.cateList(brdIdx);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return map;
	}
	@PostMapping("/addCateName")	
	public @ResponseBody int addCateName(int brdIdx,String cateName) throws Exception {
		logger.info("카테고리 추가하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("brdIdx",brdIdx);
			map.put("cateName",cateName);
			result=service.addCateName(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	@PostMapping("/cateModify")	
	public @ResponseBody int cateModify(int cateId,String cateValue) throws Exception {
		logger.info("카테고리 수정하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("cateId",cateId);
			map.put("cateValue",cateValue);
			result=service.cateModify(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	@PostMapping("/cateDelete")	
	public @ResponseBody int cateDelete(int cateId) throws Exception {
		logger.info("카테고리 삭제하기");
		int result=0;
		Map<String,Object> map =new HashMap<String,Object>();
		try {
			map.put("cateId",cateId);
			result=service.cateDelete(map);
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
}

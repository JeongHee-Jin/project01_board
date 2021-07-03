package org.project01.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.project01.domain.SearchCriteria;
import org.project01.persistence.BrdDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Inject
	private BrdDAO brdDAO;
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(SearchCriteria cri,Model model,Principal principal){
		logger.info("메인 페이지");	
		try {
			//커뮤니티 메뉴(일상,뷰티 등)
			model.addAttribute("brdNameList",brdDAO.brdNameList("20000"));							
			//인기 게시물 출력
			model.addAttribute("list",brdDAO.mainHotList());
			System.out.println(model);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return "home";
	}
	@PostMapping("/sideNav")
	public @ResponseBody List<Map<String, Object>> sideNavPage() throws Exception{
		logger.info("사이드 메뉴");	
		List<Map<String, Object>> map= new ArrayList<Map<String, Object>>();
		map=brdDAO.brdNameList("20000");
		return map;
	}
	@RequestMapping(value = "/favicon.ico", method = RequestMethod.GET)
	public void favicon( HttpServletRequest request, HttpServletResponse reponse ) {
		try {
		  reponse.sendRedirect("/resources/favicon.ico");
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
}

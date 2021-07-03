package org.project01.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.project01.domain.BoardAttachVO;
import org.project01.domain.BrdVO;
import org.project01.domain.PageMaker;
import org.project01.domain.SearchCriteria;
import org.project01.domain.UserDetailsVO;
import org.project01.persistence.MemberDAO;
import org.project01.service.BrdService;
import org.project01.util.AwsS3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/board/*")
public class BrdController {

	private static final Logger logger = LoggerFactory.getLogger(BrdController.class);
	
	@Inject
	private BrdService service;
	@Inject
	private MemberDAO memberDAO;
	
	private AwsS3 awsS3;
	
	
	//공지페이지
	@RequestMapping(value="/notice", method=RequestMethod.GET)
	public void noticeGET(@ModelAttribute("nav")String nav,@ModelAttribute("cla")String cla,
			@ModelAttribute("mid")String mid,@ModelAttribute("sub")String sub,String post,
			SearchCriteria cri, Model model)throws Exception{
		logger.info("공지 페이지");
		try {		
			model.addAttribute("tabMenu",service.tabMenuName(nav));//선택한 홈피 메뉴 이름 ,링크(커뮤니티 등)
			model.addAttribute("brdMenuName",service.brdMenuName(cla));//게시판 이름
			model.addAttribute("cateNameList",service.cateNameListMid(mid));		//카데고리 출력			
			model.addAttribute("noticeT",service.noticeList(nav));	//전체 공지사항  리스트 출력			
			model.addAttribute("notice",service.noticeList2(nav,mid));//게시판 공지사항 출력
				
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nav", nav);
			map.put("searchType", cri.getSearchType());
			map.put("cri", cri);
			if(sub.equals("")) {map.put("mid", mid);
			}else {map.put("sub", sub);}			
			//페이지데이터
			PageMaker pageMk=new PageMaker();
			pageMk.setCri(cri);
			pageMk.setTotalCount(service.listPageCnt2(map));
			model.addAttribute("pageMk", pageMk);
			
			//게시판 리스트 출력
			model.addAttribute("list",service.listPageCri2(map));		
			//게시물 내용
			if(mid!=null && post!=null) {
				logger.info("공지 보기 페이지");
				Map<String,Object> PageMap=new HashMap<String,Object>();
				PageMap.put("nav",nav);
				PageMap.put("mid",mid);
				PageMap.put("sub",sub);
				PageMap.put("postId",post);
				model.addAttribute(service.read(PageMap,1));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/brdnormal", method=RequestMethod.GET)
	public void tabCla(@ModelAttribute("nav")String nav,@ModelAttribute("cla")String cla,
			String mid,String post,SearchCriteria cri, Model model) throws Exception {
		logger.info("커뮤니티 게시판 리스트");
		try {
			//선택한 홈피 메뉴 이름 ,링크(커뮤니티 등)
			model.addAttribute("tabMenu",service.tabMenuName(nav));
			//선택한 홈피 메뉴에 게시판 메뉴(전체,일상,뷰티 등)
			model.addAttribute("brdMenuList",service.brdMenuList(nav));
			model.addAttribute("brdNameList",service.brdNameList(nav));	
			//게시판 이름
			model.addAttribute("brdMenuName",service.brdMenuName(cla));
			//전체 공지사항  리스트 출력
			model.addAttribute("noticeT",service.noticeList(nav));
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nav", nav);	
			map.put("cla", cla);	
			map.put("searchType", cri.getSearchType());
			map.put("cri", cri);
			
			//페이지데이터
			PageMaker pageMk=new PageMaker();
			pageMk.setCri(cri);			
			pageMk.setTotalCount(service.listPageCnt(map));
			model.addAttribute("pageMk", pageMk);
			
			//게시판 리스트 출력
			model.addAttribute("list",service.listPageCri(map));
			model.addAttribute("cri",cri);
			model.addAttribute("searchType",cri.getSearchType());
			//게시물 내용
			if(mid!=null && post!=null) {
				logger.info("게시물 보기 페이지");
				Map<String,Object> PageMap =new HashMap<String,Object>();
				PageMap.put("nav",nav);
				PageMap.put("cla",cla);
				PageMap.put("mid",mid);
				PageMap.put("postId",post);
				model.addAttribute(service.read(PageMap,1));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@RequestMapping(value = "/brdcate", method=RequestMethod.GET)
	public void tabMid(@ModelAttribute("nav")String nav,@ModelAttribute("mid")String mid,
			@ModelAttribute("sub")String sub,String post,SearchCriteria cri, Model model) throws Exception {
		logger.info("카테고리 리스트 ");	
		try {
			//선택한 홈피 메뉴 이름 (커뮤니티 등)
			model.addAttribute("tabMenu",service.tabMenuName(nav));
			//선택한 홈피 메뉴에 게시판 메뉴(전체,일상,뷰티 등)
			model.addAttribute("brdMenuList",service.brdMenuList(nav));
			model.addAttribute("brdNameList",service.brdNameList(nav));	
			model.addAttribute("cateNameList",service.cateNameListMid(mid));
			//게시판 메뉴 이름-게시판이름-링크
			model.addAttribute("brdName",service.brdName(mid));		
			//전체 공지사항  리스트 출력
			model.addAttribute("noticeT",service.noticeList(nav));
			//게시판 공지사항 출력
			model.addAttribute("notice",service.noticeList2(nav,mid));			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nav", nav);
			map.put("searchType", cri.getSearchType());
			map.put("cri", cri);
			if(sub.equals("")) {
				map.put("mid", mid);
			}else {
				map.put("sub", sub);
			}			
			//페이지데이터
			PageMaker pageMk=new PageMaker();
			pageMk.setCri(cri);
			pageMk.setTotalCount(service.listPageCnt2(map));
			model.addAttribute("pageMk", pageMk);
			
			//게시판 리스트 출력
			model.addAttribute("list",service.listPageCri2(map));
			//게시물 내용				
			if(mid!=null && post!=null) {
				logger.info("게시물 보기 페이지");
				Map<String,Object> PageMap =new HashMap<String,Object>();
				PageMap.put("nav",nav);
				PageMap.put("mid",mid);
				PageMap.put("postId",post);
				PageMap.put("sub",sub);
				model.addAttribute(service.read(PageMap,1));
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
//	//게시판 출력
//	@RequestMapping(value="/board/catalist/{menuBoardVal}", method=RequestMethod.POST)
//	public List<Map<String,Object>> menuNav(@PathVariable("menuBoardVal")int menuBoardVal, Model model) throws Exception{
//		logger.info("게시판  출력");
//		List<Map<String,Object>> brdN= new ArrayList<Map<String, Object>>();
//		try {
//			brdN=service.brdList(menuBoardVal); //카테고리리스트
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}	
//		return brdN;
//	}
	//게시판 말머리출력
	@RequestMapping(value="/catelist",method=RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> brdNav(
			@ModelAttribute("mid")String mid, Model model) throws Exception{
		logger.info("게시판 말머리 출력");
		List<Map<String,Object>> cateN=  new ArrayList<Map<String, Object>>();
		try {
			cateN=service.cateNameListMid(mid); //카테고리리스트
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}	
		return cateN;
	}
	
	
	
	//글쓰기페이지출력(게시판메뉴, 카테고리메뉴 출력)
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void registerGET(@ModelAttribute("nav")String nav, @ModelAttribute("mid")String mid,
			Model model,SearchCriteria cri) throws Exception{
		logger.info("글쓰기페이지"+mid);
		try {
			if(mid.equals("")) {
				model.addAttribute("brd",service.brdNameListNon());    //게시판메뉴
			}else {
				model.addAttribute("brd",service.brdNameListCla(mid));  //게시판리스트
				model.addAttribute("cate",service.cateNameListMid(mid));//카테고리리스트
			}		
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	//글쓰기 등록
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String insertPOST(@ModelAttribute("vo")BrdVO vo, Model model) throws Exception{
		logger.info("글쓰기 등록"+vo);
		String str="redirect:/cmu/brd";
		try {
			//유저 ID,닉네임
			UserDetailsVO principal = (UserDetailsVO) SecurityContextHolder
					.getContext()
					.getAuthentication().getPrincipal();
			vo.setMemId(principal.getUserId());
			vo.setMemNickName(principal.getUserNickName());
			//공지글 체크 없을시
			if(vo.getNotice()==null) {vo.setNotice("N");}			
			service.regist(vo);	//등록
			model.addAttribute("mid",vo.getBrdId());
			if(vo.getNav().equals(10000)) {
				str= "redirect:/notice";
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return str;
	}

	
	
	//수정페이지 들어가기
	@RequestMapping(value="/modify",method = RequestMethod.GET)
	public String modifyGET(@ModelAttribute("nav")String nav,String mid,String post,
			Model model,SearchCriteria cri,HttpServletRequest request) throws Exception{
		logger.info("게시물 수정페이지");
		Map<String,Object>map=new HashMap<String,Object>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		UserDetailsVO userDetails = (UserDetailsVO)principal;	
		map.put("nav",nav);
		map.put("mid",mid);
		map.put("postId",post);
		System.out.println(map);
		BrdVO vo=service.read(map,2);
		try {
			if(userDetails.getUserId().equals(vo.getMemId()) || userDetails.getRole().equals("ROLE_ADMIN")) {	
				model.addAttribute(vo);					
				model.addAttribute("brd",service.brdNameListCla(mid));
				model.addAttribute("cate",service.cateNameListMid(mid));	
			}else { 
				String referer = (String)request.getHeader("REFERER");
				if(referer==null) {
					referer="redirect:/";
				}
				return referer;
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "board/modify";
	}
	//수정완료처리
	@RequestMapping(value="/modify",method = RequestMethod.POST)
	public String modifyPOST(@ModelAttribute("vo")BrdVO vo,Model model) throws Exception{
		logger.info("게시물 수정 처리");
		String str="redirect:/cmu/brd";
		try {
			model.addAttribute("nav",vo.getNav());
			model.addAttribute("mid",vo.getBrdId());
			if(vo.getNotice()==null) {vo.setNotice("N");}
			service.modify(vo);			
			if(vo.getNav().equals(10000)) {
				str= "redirect:/notice";
			}else {
				str= "redirect:/cmu/brd";
			}			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return str;		
	}
	
	//RedirectAttributes의 addFlashAttribute메서드에 key/value로 웹페이지에 값을 넘겨줄수 있지만 
	//하나의 key값으로 넘겨주어야 하므로 여러개의 값을 페이지로 넘겨 줄 경우 list나 map 형태의 객체를 value로 담아서 넘겨주면 되겠습니다.
	
	
	//게시물삭제	
	@RequestMapping(value="/remove", method=RequestMethod.POST)
	@ResponseBody
	public void remove(BrdVO vo) throws Exception{
		logger.info("게시물 삭제 처리 ");
		Map<String,Object> map=new HashMap<String,Object>();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		UserDetailsVO userDetails = (UserDetailsVO)principal;
		awsS3=AwsS3.getInstance();
		try {
			memberDAO.mypageInfo(userDetails.getUserId());
			map.put("nav",vo.getNav());
			map.put("postId",vo.getPostId());
			List<BoardAttachVO> attachList = service.getAttachList(map);
			if(attachList!=null && attachList.size()>0) {
				attachList.forEach(attach->{
					String fileName=attach.getFileId()+"_"+attach.getFileName();
					awsS3.delete(attach.getUploadPath(),fileName);
				});
			}		
			service.remove(vo);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}	
	//게시물삭제	
//	@RequestMapping(value="/remove", method=RequestMethod.POST)
//	@ResponseBody
//	public void remove(BrdVO vo) throws Exception{
//		logger.info("게시물 삭제 처리 ");
//		Map<String,Object> map=new HashMap<String,Object>();
//		try {
//			map.put("nav",vo.getNav());
//			map.put("postId",vo.getPostId());
//			List<BoardAttachVO> attachList = service.getAttachList(map);
//			if(attachList!=null && attachList.size()>0) {
//				attachList.forEach(attach->{
//					String targetFile=attach.getUploadPath()+"\\"+attach.getFileId()
//						+"_"+attach.getFileName();
//					File file;
//					try {
//						file = new File("C:\\Users\\jh\\Desktop\\project01\\uplode\\" 
//								+ URLDecoder.decode(targetFile, "UTF-8"));
//						file.delete();
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}	
//				});
//			};			
//			service.remove(vo);
//		}catch(Exception e) {
//			System.out.println(e.getMessage());
//		}		
//	}		
	
	//답글달기페이지
	@RequestMapping (value="/commentaire", method=RequestMethod.GET)
	public void commentaireGET(@ModelAttribute("nav")String nav,@ModelAttribute("mid")String mid,
			String post, Model model,SearchCriteria cri) throws Exception{
		logger.info("답글 달기 페이지");
		try {
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("nav",nav);
			map.put("mid",mid);
			map.put("postId",post);		
			model.addAttribute(service.read(map,2));	
			
			List<Map<String,Object>> brdN=service.brdNameListCla(mid);	//게시판리스트
			List<Map<String,Object>> cateN=service.cateNameListMid(mid); //카테고리리스트
			model.addAttribute("brd",brdN);
			model.addAttribute("cate",cateN);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
	}

	//답글달기
	@RequestMapping (value="/commentaire", method=RequestMethod.POST)
	public String commentairePOST(@ModelAttribute("vo")BrdVO vo,Model model)throws Exception{
		logger.info("답글 달기 처리"+vo);
		String str="redirect:/cmu/brd";
		try {
			//유저 ID,닉네임
			UserDetailsVO principal = (UserDetailsVO) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			vo.setMemId(principal.getUserId());
			vo.setMemNickName(principal.getUserNickName());

			model.addAttribute("nav",vo.getNav());
			model.addAttribute("mid",vo.getBrdId());			
			service.commentaire(vo);			
			if(vo.getNav().equals(10000)) {
				str= "redirect:/notice";
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return str;
	}
}

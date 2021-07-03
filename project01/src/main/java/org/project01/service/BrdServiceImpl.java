package org.project01.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.project01.domain.BoardAttachVO;
import org.project01.domain.BrdVO;
import org.project01.persistence.BrdDAO;
import org.project01.persistence.UploadFileDAO;
import org.springframework.stereotype.Service;

@Service
public class BrdServiceImpl implements BrdService {
	
	@Inject
	private BrdDAO brdDAO;
	
	@Inject
	private UploadFileDAO FileDAO;
	
	@Override
	public String tabMenuName(String nav) throws Exception {
		return brdDAO.tabMenuName(nav);
	}
	
	@Override
	public List<BrdVO> listPageCri(Map<String, Object> map) throws Exception {		
		return brdDAO.listPageCri(map);
	}
	@Override
	public List<BrdVO> listPageCri2(Map<String, Object> map) throws Exception {
		return brdDAO.listPageCri2(map);
	}
	
	@Override
	public int listPageCnt(Map<String, Object> map) throws Exception {

		return brdDAO.listPageCnt(map);
	}
	@Override
	public int listPageCnt2(Map<String, Object> map) throws Exception {
		return brdDAO.listPageCnt2(map);
	}
	@Override
	public List<BrdVO> noticeList(String nav) throws Exception {
		return brdDAO.noticeList(nav);
	}
	@Override
	public List<BrdVO> noticeList2(String nav, String mid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nav", nav);
		map.put("mid", mid);
		return brdDAO.noticeList2(map);
	}

	@Override
	public void regist(BrdVO vo) throws Exception {
		brdDAO.create(vo);
	}
	@Override
	public List<Map<String, Object>> brdMenuList(String nav) throws Exception {
		return brdDAO.brdMenuList(nav);
	}
	@Override
	public List<Map<String, Object>> brdNameList(String nav) throws Exception {
		return brdDAO.brdNameList(nav);
	}
	@Override
	public List<Map<String, Object>> cateNameList(String brdNameKey) throws Exception {
		return brdDAO.cateNameList(brdNameKey);
	}
	@Override
	public BrdVO read(Map<String, Object> map, int a) throws Exception {
		if(a==1) {
			brdDAO.hitUpdate(map);
		}
		if(map.get("nav").equals("10000")) {
			return brdDAO.readNotice(map);
		}else {
			if(map.containsKey("cla")) {
				return brdDAO.readCmu1(map);
			}else {
				return brdDAO.readCmu2(map);
			}			
		}		
	}
	@Override
	public void modify(BrdVO vo) throws Exception {
		brdDAO.update(vo);
	}
	@Override
	public void remove(BrdVO vo) throws Exception {
		brdDAO.delete(vo);
	}
	@Override
	public void commentaire(BrdVO vo) throws Exception {
		brdDAO.insertCommentaire(vo);
		
	}

	@Override
	public List<Map<String, Object>> brdNameListCla(String mid) throws Exception{
		return brdDAO.brdNameListCla(mid);
	}

	@Override
	public List<Map<String, Object>> cateNameListMid(String mid) throws Exception{
		return brdDAO.cateNameListMid(mid);
	}
	
	@Override
	public List<Map<String, Object>> brdMenuName(String cla) throws Exception {
		return brdDAO.brdMenuName(cla);
	}
	@Override
	public List<Map<String,String>> brdName(String mid) throws Exception {
		return brdDAO.brdName(mid);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Map<String, Object> map) throws Exception {
		return FileDAO.getAttachList(map);
	}

	@Override
	public List<Map<String, String>> brdNameListNon() throws Exception {
		return brdDAO.brdNameListNon();
	}
}

package org.project01.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.project01.persistence.AdminDAO;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Inject
	private AdminDAO adminDAO;

	@Override
	public List<Map<String, Object>> getUserList(Map<String, Object> map) throws Exception {
		return adminDAO.getUserList(map);
	}

	@Override
	public int userTotal(Map<String, Object> map) throws Exception {
		return adminDAO.userTotal(map);
	}

	@Override
	public int chkUserRoleModify(Map<String,Object> map) throws Exception {
		return adminDAO.chkUserRoleModify(map);
	}

	@Override
	public List<Map<String, Object>> brdMenuList() throws Exception {
		return adminDAO.brdMenuList();
	}

	@Override
	public List<Object> boardList(int menuIdx) throws Exception {
		return adminDAO.boardList(menuIdx);
		
	}
	@Override
	public List<Object> cateList(int brdIdx) throws Exception {
		return adminDAO.cateList(brdIdx);
	}
	@Override
	public int addMenuName(Map<String, Object> map) throws Exception {
		return adminDAO.addMenuName(map);
	}
	@Override
	public int addBoardName(Map<String, Object> map) throws Exception {
		return adminDAO.addBoardName(map);
	}

	@Override
	public int addCateName(Map<String, Object> map) throws Exception {
		return adminDAO.addCateName(map);
	}

	@Override
	public int brdMenuModify(Map<String, Object> map) throws Exception {
		return adminDAO.brdMenuModify(map);
	}

	@Override
	public int brdMenuDelete(Map<String, Object> map) throws Exception {
		return adminDAO.brdMenuDelete(map);
	}

	@Override
	public int brdModify(Map<String, Object> map) throws Exception {
		return adminDAO.brdModify(map);
	}

	@Override
	public int brdDelete(Map<String, Object> map) throws Exception {
		return adminDAO.brdDelete(map);
	}

	@Override
	public int cateModify(Map<String, Object> map) throws Exception {
		return adminDAO.cateModify(map);
	}

	@Override
	public int cateDelete(Map<String, Object> map) throws Exception {
		return adminDAO.cateDelete(map);
	}

}

package org.project01.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.project01.domain.MemberVO;
import org.springframework.stereotype.Repository;

@Repository
public class AdminDAOImpl implements AdminDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace="org.project01.mappers.AdminMapper";

	@Override
	public List<Map<String, Object>> getUserList(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return sqlSession.selectList(namespace+".userList",map);
	}

	@Override
	public int userTotal(Map<String, Object> map) throws Exception {
		return sqlSession.selectOne(namespace+".userTotal",map);
	}

	@Override
	public int chkUserRoleModify(Map<String,Object> map) throws Exception {	
		return sqlSession.update(namespace+".chkUserRoleModify",map);
	}

	@Override
	public List<Map<String, Object>> brdMenuList() throws Exception {
		return sqlSession.selectList(namespace+".brdMenuList");
	}

	@Override
	public List<Object> boardList(int menuIdx) throws Exception {
		return sqlSession.selectList(namespace+".brdList",menuIdx);
	}

	@Override
	public List<Object> cateList(int brdIdx) throws Exception {
		return sqlSession.selectList(namespace+".cateList",brdIdx);
	}
	
	@Override
	public int addMenuName(Map<String, Object> map) throws Exception {
		return sqlSession.insert(namespace+".addMenuName",map);
	}
	@Override
	public int addBoardName(Map<String, Object> map) throws Exception {
		int num=sqlSession.selectOne(namespace+".boardNum",map);
		if(num==0) {
			return sqlSession.insert(namespace+".firBoardName",map);
		}else {
			return sqlSession.insert(namespace+".addBoardName",map);
		}		
	}

	@Override
	public int addCateName(Map<String, Object> map) throws Exception {
		int num=sqlSession.selectOne(namespace+".cateNum",map);
		if(num==0) {
			return sqlSession.insert(namespace+".firCateName",map);
		}else {
			return sqlSession.insert(namespace+".addCateName",map);
		}
	}

	@Override
	public int brdMenuModify(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return sqlSession.update(namespace+".brdMenuModify",map);
	}

	@Override
	public int brdMenuDelete(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return sqlSession.delete(namespace+".brdMenuDelete",map);
	}

	@Override
	public int brdModify(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return sqlSession.update(namespace+".brdModify",map);
	}

	@Override
	public int brdDelete(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return sqlSession.delete(namespace+".brdDelete",map);
	}

	@Override
	public int cateModify(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return sqlSession.update(namespace+".cateModify",map);
	}

	@Override
	public int cateDelete(Map<String, Object> map) throws Exception {
		System.out.println(map);
		return sqlSession.delete(namespace+".cateDelete",map);
	}

	@Override
	public List<MemberVO> levMemList() throws Exception {
		return sqlSession.selectList("org.project01.mappers.MemberMapper"+".levMem");
	}

	@Override
	public void deleteMem(String userId) throws Exception {
		sqlSession.delete(namespace+".deleteMem",userId);
	}


}

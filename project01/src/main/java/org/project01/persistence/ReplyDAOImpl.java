package org.project01.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.project01.domain.BrdVO;
import org.project01.domain.ReplyVO;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyDAOImpl implements ReplyDAO {
	
	@Inject
	private SqlSession sqlSession;
	
	private static String namespace = "org.project01.mappers.ReplyMapper";
	
	@Override
	public List<ReplyVO> listReply(Map<String, Object> map) throws Exception {
		return sqlSession.selectList(namespace+".listReply",map);
	}
	
	@Override
	public int replyCnt(Map<String, Object> map) throws Exception {
		return sqlSession.selectOne(namespace+".replyCnt",map);
	}
	
	@Override
	public void create(ReplyVO vo) throws Exception {
		sqlSession.insert(namespace+".createReply",vo);	
	}

	@Override
	public void update(ReplyVO vo) throws Exception {
		sqlSession.update(namespace+".updateReply",vo);
	}

	@Override
	public void delete(ReplyVO vo) throws Exception {
		sqlSession.delete(namespace+".deleteReply",vo);

	}

	@Override
	public void allDelete(BrdVO vo) throws Exception {
		sqlSession.delete(namespace+".allDelete",vo);
		
	}

}

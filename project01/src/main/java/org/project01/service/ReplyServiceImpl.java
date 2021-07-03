package org.project01.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.project01.domain.ReplyVO;
import org.project01.persistence.ReplyDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO replyDAO;

	
	@Transactional
	@Override
	public List<ReplyVO> listReplyPage(Map<String, Object> map) throws Exception{
		 
		return replyDAO.listReply(map);
	}

	@Override
	public int count(Map<String, Object> map) throws Exception{
		return replyDAO.replyCnt(map);
	}
	@Transactional
	@Override
	public void replyInsert(ReplyVO vo) throws Exception{
		replyDAO.create(vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nav", vo.getNav());
		map.put("postId", vo.getPostId());
		map.put("num",1);	
		//brdDAO.updateReplyCnt(map);
		
	}

	@Override
	public void replyUpdate(ReplyVO vo) throws Exception{
		replyDAO.update(vo);
		
	}
	@Transactional
	@Override
	public void replyDelete(ReplyVO vo)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nav", vo.getNav());
		map.put("postId", vo.getPostId());
		map.put("num",-1);	
		replyDAO.delete(vo);
		//brdDAO.updateReplyCnt(map);
		
	}
}

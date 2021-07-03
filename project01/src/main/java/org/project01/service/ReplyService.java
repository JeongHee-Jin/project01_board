package org.project01.service;

import java.util.List;
import java.util.Map;

import org.project01.domain.ReplyVO;

public interface ReplyService {
	
	//댓글list
	public List<ReplyVO> listReplyPage(Map<String, Object> map) throws Exception;
	//댓글수
	public int count(Map<String, Object> map) throws Exception;
	//댓글등록
	public void replyInsert(ReplyVO vo) throws Exception;
	//댓글수정
	public void replyUpdate(ReplyVO vo) throws Exception;
	//댓글삭제
	public void replyDelete(ReplyVO vo) throws Exception;

}

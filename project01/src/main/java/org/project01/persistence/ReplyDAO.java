package org.project01.persistence;

import java.util.List;
import java.util.Map;
import org.project01.domain.BrdVO;
import org.project01.domain.ReplyVO;

public interface ReplyDAO {

	public List<ReplyVO> listReply(Map<String, Object> map) throws Exception;

	public void create(ReplyVO vo) throws Exception;

	public void update(ReplyVO vo) throws Exception;

	public void delete(ReplyVO vo) throws Exception;

	public int replyCnt(Map<String, Object> map) throws Exception;

	public void allDelete(BrdVO vo) throws Exception;

}

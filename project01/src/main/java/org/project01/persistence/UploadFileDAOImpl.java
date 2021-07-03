package org.project01.persistence;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.project01.domain.BoardAttachVO;
import org.springframework.stereotype.Repository;

@Repository
public class UploadFileDAOImpl implements UploadFileDAO {
	@Inject
	private SqlSession session;
	
	private static String namespace="org.project01.mappers.BoardAttachMapper";
	
	public List<BoardAttachVO> getAttachList(Map<String, Object> map) {
		return session.selectList(namespace+".fileList",map);
	}
	
	public List<BoardAttachVO> allFiles(){
		return session.selectList(namespace+".allFiles");
	}
}

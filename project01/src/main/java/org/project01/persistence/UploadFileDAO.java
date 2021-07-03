package org.project01.persistence;

import java.util.List;
import java.util.Map;

import org.project01.domain.BoardAttachVO;

public interface UploadFileDAO {

	public List<BoardAttachVO> getAttachList(Map<String, Object> map) throws Exception;

	public List<BoardAttachVO> allFiles() throws Exception;

}

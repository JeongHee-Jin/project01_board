package org.project01.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.project01.domain.BrdVO;
import org.springframework.stereotype.Repository;

@Repository
public class BrdDAOImpl implements BrdDAO {

	@Inject
	private SqlSession sqlSession;

	private static String namespace = "org.project01.mappers.BoardMapper";

	// 선택한 홈피메뉴
	@Override
	public String tabMenuName(String nav) throws Exception {
		return sqlSession.selectOne(namespace + ".tabMenuName", nav);
	}

	// 게시판리스트1
	public List<BrdVO> listPageCri(Map<String, Object> map) throws Exception {
		if (map.get("cla").equals("1100")) {
			return sqlSession.selectList(namespace + ".listSearchTotal", map);
		} else {
			return sqlSession.selectList(namespace + ".listSearchHot", map);
		}

	}

	// 게시판리스트2
	@Override
	public List<BrdVO> listPageCri2(Map<String, Object> map) throws Exception {
		if (map.get("nav").equals("10000") && map.containsKey("mid")) {
			System.out.println("공지리스트");
			return sqlSession.selectList(namespace + ".listSearchNotice", map);
		} else if (map.get("nav").equals("10000")) {
			System.out.println("공지리스트 - 카테고리");
			return sqlSession.selectList(namespace + ".listSearchNotice2", map);
		} else if (map.get("nav").equals("20000") && map.containsKey("mid")) {
			System.out.println("게시판리스트");
			return sqlSession.selectList(namespace + ".listSearch2", map);
		} else {
			System.out.println("게시판-카테고리");
			return sqlSession.selectList(namespace + ".listSearch3", map);
		}
	}

	// 총게시물수1
	@Override
	public int listPageCnt(Map<String, Object> map) throws Exception {
		if (map.get("cla").equals("1100")) {
			return sqlSession.selectOne(namespace + ".listSearchCntTotal", map);
		} else {
			return sqlSession.selectOne(namespace + ".listSearchCntHot", map);
		}
	}

	// 총게시물수2
	@Override
	public int listPageCnt2(Map<String, Object> map) throws Exception {
		if(map.get("nav").equals("10000")) {
			if (map.containsKey("mid")) {
				return sqlSession.selectOne(namespace + ".noticeSearchCnt2", map);
			} else {
				return sqlSession.selectOne(namespace + ".noticeSearchCnt3", map);
			}
		}else {
			if (map.containsKey("mid")) {
				return sqlSession.selectOne(namespace + ".listSearchCnt2", map);
			} else {
				return sqlSession.selectOne(namespace + ".listSearchCnt3", map);
			}
		}
	}

	// 전체공지글출력
	@Override
	public List<BrdVO> noticeList(String nav) throws Exception {
		return sqlSession.selectList(namespace + ".noticeListT", nav);
	}

	// 게시판별 공지글 출력
	@Override
	public List<BrdVO> noticeList2(Map<String, Object> map) {
		return sqlSession.selectList(namespace + ".noticeListB", map);
	}

	// 글쓰기
	@Override
	public void create(BrdVO vo) throws Exception {
		System.out.println("글쓰기 ");
		if (vo.getNav() == 10000) {
			sqlSession.insert(namespace + ".createPage1", vo);
		} else if (vo.getNav() == 20000) {
			sqlSession.insert(namespace + ".createPage2", vo);
		}
		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return;
		} else {
			vo.getAttachList().forEach(attach -> {
				attach.setNav(vo.getNav());
				attach.setPostId(vo.getPostId());
				sqlSession.insert("org.project01.mappers.BoardAttachMapper" + ".insertFile", attach);
			});
		}
	}

	// 게시판 분류 list 출력
	@Override
	public List<Map<String, Object>> brdMenuList(String nav) {
		return sqlSession.selectList(namespace + ".brdMenuList", nav);
	}

	// 게시판 list 출력
	@Override
	public List<Map<String, Object>> brdNameList(String nav) throws Exception {
		return sqlSession.selectList(namespace + ".brdNameList", nav);
	}

	// 게시판 말머리 list 출력
	@Override
	public List<Map<String, Object>> cateNameList(String brdMenuKey) throws Exception {
		return sqlSession.selectList(namespace + ".cateNameList", brdMenuKey);
	}

	// 공지사항 글읽기
	@Override
	public BrdVO readNotice(Map<String, Object> map) throws Exception {
		BrdVO vo = new BrdVO();		
		// 글 수
		int a = sqlSession.selectOne(namespace + ".boardCnt1", map);
		System.out.println(map);
		// 게시물이 없을때
		if (a == 1) {
			System.out.println("게시물 없을때");
			vo = sqlSession.selectOne(namespace + ".readPage", map);
		} else if (map.containsKey("sub")) {
			if (map.get("sub").equals("")) {
				System.out.println("공지:게시판-게시물");
				vo = sqlSession.selectOne(namespace + ".readPageNotice", map);
			} else {
				System.out.println("공지:카테고리-게시물");
				vo = sqlSession.selectOne(namespace + ".readPageNoticeSub", map);
			}
		} else {
			System.out.println("게시판수정");
			vo = sqlSession.selectOne(namespace + ".readPage", map);
		}
		/*} else if (map.get("sub").equals("")) {
			System.out.println("공지:게시판-게시물");
			vo = sqlSession.selectOne(namespace + ".readPageNotice", map);
		} else {
			System.out.println("공지:카테고리-게시물");
			vo = sqlSession.selectOne(namespace + ".readPageNoticeSub", map);
		}*/
		//System.out.println("출력 :" + vo);
		return vo;

	}

	// 커뮤니티 글읽기
	@Override
	public BrdVO readCmu1(Map<String, Object> map) throws Exception {
		BrdVO vo = new BrdVO();
		// 글 수
		int cnt = sqlSession.selectOne(namespace + ".boardCnt1", map);
		// 게시물이 없을때
		if (cnt == 1) {
			vo = sqlSession.selectOne(namespace + ".readPage", map);
		} else if (map.containsKey("cla")) {
			if (map.get("cla").equals("1100")) {
				System.out.println("커뮤니티:전체-게시물"+map);	
				vo = sqlSession.selectOne(namespace + ".readPageTotal", map);
			} else {
				System.out.println("커뮤니티:인기-게시물");
				vo = sqlSession.selectOne(namespace + ".readPageHot", map);
			}
		} else {
			System.out.println("게시판수정");
			vo = sqlSession.selectOne(namespace + ".readPage", map);
		}
		System.out.println("출력 :" + vo);
		return vo;
	}

	@Override
	public BrdVO readCmu2(Map<String, Object> map) throws Exception {
		BrdVO vo = new BrdVO();
		// 글 수
		int cnt = sqlSession.selectOne(namespace + ".boardCnt2", map);
		if (cnt == 1) {
			vo = sqlSession.selectOne(namespace + ".readPage", map);
		} else if (map.containsKey("sub")) {
			if (map.get("sub").equals("")) {
				System.out.println("커뮤니티:게시판-게시물");
				vo = sqlSession.selectOne(namespace + ".readPageMid", map);
			} else {
				System.out.println("커뮤니티:카테고리-게시물");
				vo = sqlSession.selectOne(namespace + ".readPageSub", map);
			}
		} else {
			System.out.println("게시판수정");
			vo = sqlSession.selectOne(namespace + ".readPage", map);
		}
		//System.out.println("출력 :" + vo);
		return vo;
	}

	@Override
	public void hitUpdate(Map<String, Object> map) throws Exception {
		sqlSession.update(namespace + ".hitUpdate", map);
	}

	// 수정
	@Override
	public void update(BrdVO vo) throws Exception {
		sqlSession.update(namespace + ".updatePage", vo);

		// 해당 게시물의 파일 DB를 모두 삭제
		sqlSession.selectList("org.project01.mappers.BoardAttachMapper" + ".deleteFile", vo);
		// 해당 게시물의 파일 등록
		if (vo.getAttachList() != null) {
			vo.getAttachList().forEach(attach -> {
				attach.setPostId(vo.getPostId());
				attach.setNav(vo.getNav());
				System.out.println("파일등록 : " + attach);
				sqlSession.insert("org.project01.mappers.BoardAttachMapper" + ".insertFile", attach);
			});
		}
	}

	// 게시물 삭제
	@Override
	public void delete(BrdVO vo) throws Exception {
		System.out.println("글삭제 : "+vo);
		sqlSession.delete(namespace + ".deletePage", vo);
	}

	// 답글 등록
	@Override
	public void insertCommentaire(BrdVO vo) throws Exception {
		System.out.println("답글 : " + vo);
		if (vo.getStep() == 0) {
			System.out.println("step 0");
			vo.setGroup(vo.getPostId());
			sqlSession.insert(namespace + ".commentaire", vo);
		} else {
			System.out.println("step !0");
			sqlSession.insert(namespace + ".commentaire2", vo);
		}
		if (vo.getAttachList() == null || vo.getAttachList().size() <= 0) {
			return;
		} else {
			vo.getAttachList().forEach(attach -> {
				attach.setNav(vo.getNav());
				attach.setPostId(vo.getPostId());
				System.out.println("attach: " + attach);
				sqlSession.insert("org.project01.mappers.BoardAttachMapper" + ".insertFile", attach);
			});
		}
	}

	// 댓글수
	@Override
	public void updateReplyCnt(int brdId, String brdKey, int i) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("brdId", brdId);
		map.put("brdKey", brdKey);
		map.put("num", i);
		sqlSession.selectOne(namespace + ".updateReplyCnt", map);
	}

	@Override
	public List<Map<String, Object>> brdNameListCla(String mid) {
		return sqlSession.selectList(namespace + ".brdNameListCla", mid);
	}

	@Override
	public List<Map<String, Object>> cateNameListMid(String mid) {
		return sqlSession.selectList(namespace + ".cateNameListMid", mid);
	}

	// 게시판메뉴이름
	@Override
	public List<Map<String, Object>> brdMenuName(String cla) throws Exception {
		return sqlSession.selectList(namespace + ".brdMenuName", cla);
	}

	// 게시판이름
	@Override
	public List<Map<String, String>> brdName(String cla) throws Exception {
		return sqlSession.selectList(namespace + ".brdName", cla);
	}

	// 댓글수
	@Override
	public void updateReplyCnt(Map<String, Object> map) throws Exception {
		sqlSession.selectOne(namespace + ".updateReplyCnt", map);
	}
	

	@Override
	public List<Map<String, String>> brdNameListNon() throws Exception {
		return sqlSession.selectList(namespace + ".brdNameListNon");
	}

	@Override
	public List<Map<String, String>> mainHotList() throws Exception {
		return sqlSession.selectList(namespace + ".mainHotList");
	}

}

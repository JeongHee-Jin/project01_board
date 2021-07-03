package org.project01.domain;

import java.util.Date;
import java.util.List;

public class BrdVO {
	private Integer nav;		//20000:커뮤	
	private Integer postId;		//글번호PK(brdId)
	private Integer brdId;		//게시판ID(105)
	private String brdName;		//게시판 이름(일상)
	private Integer cateId;		//카테고리 ID(60)
	private String cateName;	//카테고리 이름(어학)
	private String title;		//제목
	private String content;		//내용
	private String memId; 		//작성자Id
	private String memNickName;	//작성자 닉네임
	private String notice;		//공지글여부
	private Date  regtime;		//작성날짜
	private String regtimeB;	//작성날짜
	private int	hit;			//조회수
	private int	replyCnt;		//댓글수
	private int	group;			//게시글그룹
	private int	step;			//답글순서
	private int indent;			//답글들여쓰기
	private Integer brdMenuId;	//게시판메뉴ID
	private Integer prev;
	private Integer next;
	private int fileY;
	
	private List<BoardAttachVO> attachList;	
	
	public BrdVO() {}
	public BrdVO(Integer nav,Integer postId, Integer brdMenuId, Integer brdId, String brdName, Integer cateId,
			String cateName, String title, String content, String memId, String memNickName,
			String notice, Date regtime, String regtimeB,int hit, int replyCnt, int group, int step, int indent,
			Integer prev, Integer next, int fileY,List<BoardAttachVO> attachList) {
		super();
		this.nav = nav;
		this.postId = postId;
		this.brdMenuId = brdMenuId;
		this.brdId = brdId;
		this.brdName = brdName;
		this.cateId = cateId;
		this.cateName = cateName;
		this.title = title;
		this.content = content;
		this.memId = memId;
		this.memNickName = memNickName;
		this.notice = notice;
		this.regtime = regtime;
		this.regtimeB=regtimeB;
		this.hit = hit;
		this.replyCnt = replyCnt;
		this.group = group;
		this.step = step;
		this.indent = indent;
		this.prev = prev;
		this.next = next;
		this.fileY=fileY;
		this.attachList = attachList;
	}
	
	public Integer getNav() {
		return nav;
	}
	public void setNav(Integer nav) {
		this.nav = nav;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getBrdMenuId() {
		return brdMenuId;
	}
	public void setBrdMenuId(Integer brdMenuId) {
		this.brdMenuId = brdMenuId;
	}
	public Integer getBrdId() {
		return brdId;
	}
	public void setBrdId(Integer brdId) {
		this.brdId = brdId;
	}
	public String getBrdName() {
		return brdName;
	}
	public void setBrdName(String brdName) {
		this.brdName = brdName;
	}
	public Integer getCateId() {
		return cateId;
	}
	public void setCateId(Integer cateId) {
		this.cateId = cateId;
	}
	public String getCateName() {
		return cateName;
	}
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemNickName() {
		return memNickName;
	}
	public void setMemNickName(String memNickName) {
		this.memNickName = memNickName;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public Date getRegtime() {
		return regtime;
	}
	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public int getReplyCnt() {
		return replyCnt;
	}
	public void setReplyCnt(int replyCnt) {
		this.replyCnt = replyCnt;
	}
	public int getGroup() {
		return group;
	}
	public void setGroup(int group) {
		this.group = group;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public int getIndent() {
		return indent;
	}
	public void setIndent(int indent) {
		this.indent = indent;
	}
	public Integer getPrev() {
		return prev;
	}
	public void setPrev(Integer prev) {
		this.prev = prev;
	}
	public Integer getNext() {
		return next;
	}
	public void setNext(Integer next) {
		this.next = next;
	}
	public List<BoardAttachVO> getAttachList() {
		return attachList;
	}
	public void setAttachList(List<BoardAttachVO> attachList) {
		this.attachList = attachList;
	}
	
	public String getRegtimeB() {
		return regtimeB;
	}
	public void setRegtimeB(String regtimeB) {
		this.regtimeB = regtimeB;
	}
	
	public int getFileY() {
		return fileY;
	}
	public void setFileY(int fileY) {
		this.fileY = fileY;
	}
	
	@Override
	public String toString() {
		return "BrdVO [nav=" + nav +", postId=" + postId + ", brdMenuId=" + brdMenuId + ", brdId=" + brdId
				+ ", brdName=" + brdName + ", cateId=" + cateId + ", cateName=" + cateName + ", title=" + title
				+ ", content=" + content + ", memId=" + memId + ", memNickName=" + memNickName
				+ ", notice=" + notice + ", regtime=" + regtime + ", regtimeB=" + regtimeB +", hit=" + hit 
				+ ", replyCnt=" + replyCnt + ", group="+ group + ", step=" + step + ", indent=" + indent 
				+ ", prev=" + prev + ", next="+ next + ", fileY="+fileY+", attachList=" + attachList + "]";
	}

}
package org.project01.domain;

import java.util.Date;

public class ReplyVO {
	private int nav;			//tabID
	
	private int replyId;			//댓글ID
	private int postId;				//게시물ID
	private int brdId;				//게시판ID
	private String memId;			//댓글 작성자  ID
	private String memNickName;		//댓글작성자 닉네임
	private String rpContent;		//댓글내용
	private Date rpRegtime;			//작성날짜
	private Date rpUpdatetime;		//수정날짜 
	private int	rpGroup;			//게시글그룹
	private int	rpStep;				//답글순서
	private int rpIndent;			//답글들여쓰기
	private int rpBlame;			//댓글신고수
	private String rpDel;			//댓글삭제여부
	
	public ReplyVO () {}

	public ReplyVO(int nav, int replyId, int postId, int brdId, String memId, String memNickName,
			String rpContent, Date rpRegtime, Date rpUpdatetime, int rpGroup, int rpStep, int rpIndent, int rpBlame,
			String rpDel) {
		super();
		this.nav = nav;
		this.replyId = replyId;
		this.postId = postId;
		this.brdId = brdId;
		this.memId = memId;
		this.memNickName = memNickName;
		this.rpContent = rpContent;
		this.rpRegtime = rpRegtime;
		this.rpUpdatetime = rpUpdatetime;
		this.rpGroup = rpGroup;
		this.rpStep = rpStep;
		this.rpIndent = rpIndent;
		this.rpBlame = rpBlame;
		this.rpDel = rpDel;
	}

	public int getNav() {
		return nav;
	}

	public void setNav(int nav) {
		this.nav = nav;
	}

	public int getReplyId() {
		return replyId;
	}

	public void setReplyId(int replyId) {
		this.replyId = replyId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}

	public int getBrdId() {
		return brdId;
	}

	public void setBrdId(int brdId) {
		this.brdId = brdId;
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

	public String getRpContent() {
		return rpContent;
	}

	public void setRpContent(String rpContent) {
		this.rpContent = rpContent;
	}

	public Date getRpRegtime() {
		return rpRegtime;
	}

	public void setRpRegtime(Date rpRegtime) {
		this.rpRegtime = rpRegtime;
	}

	public Date getRpUpdatetime() {
		return rpUpdatetime;
	}

	public void setRpUpdatetime(Date rpUpdatetime) {
		this.rpUpdatetime = rpUpdatetime;
	}

	public int getRpGroup() {
		return rpGroup;
	}

	public void setRpGroup(int rpGroup) {
		this.rpGroup = rpGroup;
	}

	public int getRpStep() {
		return rpStep;
	}

	public void setRpStep(int rpStep) {
		this.rpStep = rpStep;
	}

	public int getRpIndent() {
		return rpIndent;
	}

	public void setRpIndent(int rpIndent) {
		this.rpIndent = rpIndent;
	}

	public int getRpBlame() {
		return rpBlame;
	}

	public void setRpBlame(int rpBlame) {
		this.rpBlame = rpBlame;
	}

	public String getRpDel() {
		return rpDel;
	}

	public void setRpDel(String rpDel) {
		this.rpDel = rpDel;
	}

	@Override
	public String toString() {
		return "ReplyVO [nav=" + nav + ", replyId=" + replyId + ", postId=" + postId + ", brdId=" + brdId
				+ ", memId=" + memId + ", memNickName=" + memNickName + ", rpContent=" + rpContent + ", rpRegtime="
				+ rpRegtime + ", rpUpdatetime=" + rpUpdatetime + ", rpGroup=" + rpGroup + ", rpStep=" + rpStep
				+ ", rpIndent=" + rpIndent + ", rpBlame=" + rpBlame + ", rpDel=" + rpDel + "]";
	}

	
	
}
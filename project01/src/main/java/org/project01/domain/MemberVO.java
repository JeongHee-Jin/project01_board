package org.project01.domain;

import java.util.Date;

/* Spring Security 로그인을 위한 UserDetails VO 객체 */
public class MemberVO {
		
	private String userId;
	private String userPw;
	private String userNickName;
	private String phoneNum;
	private String postCode;
	private String roadAddr;
	private String detailAddr;
	private String email;
	private String regDate;
	private String role;
	private Date levDate;
	private String link;
	//private List<String> authList;
	
	public MemberVO() {}

	public MemberVO(String userId, String userPw, String userNickName, String phoneNum, String postCode,
			String roadAddr, String detailAddr, String email, String regDate, String role, Date levDate, String link) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userNickName = userNickName;
		this.phoneNum = phoneNum;
		this.postCode = postCode;
		this.roadAddr = roadAddr;
		this.detailAddr = detailAddr;
		this.email = email;
		this.regDate = regDate;
		this.role = role;
		this.levDate = levDate;
		this.link = link;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getRoadAddr() {
		return roadAddr;
	}

	public void setRoadAddr(String roadAddr) {
		this.roadAddr = roadAddr;
	}

	public String getDetailAddr() {
		return detailAddr;
	}

	public void setDetailAddr(String detailAddr) {
		this.detailAddr = detailAddr;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getLevDate() {
		return levDate;
	}

	public void setLevDate(Date levDate) {
		this.levDate = levDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "MemberVO [userId=" + userId + ", userPw=" + userPw + ", userNickName=" + userNickName + ", phoneNum="
				+ phoneNum + ", postCode=" + postCode + ", roadAddr=" + roadAddr + ", detailAddr=" + detailAddr
				+ ", email=" + email + ", regDate=" + regDate + ", role=" + role + ", levDate=" + levDate + ", link="
				+ link + "]";
	}

}

package org.project01.domain;

public class AuthVO {
	
	private String userid;
	private String role;
	public AuthVO() {}
	
	public AuthVO(String userid, String role) {
		super();
		this.userid = userid;
		this.role = role;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "AuthVO [userid=" + userid + ", role=" + role + "]";
	}

	
	  
}

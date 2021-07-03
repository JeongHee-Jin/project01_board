package org.project01.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/* Spring Security 로그인을 위한 UserDetails VO 객체 */
public class UserDetailsVO  implements UserDetails {
	// 안만들어도 상관없지만 Warning이 발생함	
	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String userPw;
	private String userNickName;
	private String role;
	private String link;
	
	public UserDetailsVO() {}
	
	public UserDetailsVO(String userId, String userPw, String userNickName, String role) {
		super();
		this.userId = userId;
		this.userPw = userPw;
		this.userNickName = userNickName;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserNickName() {
		return userNickName;
	}

	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}	

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	// ID
	public String getUsername() {
		return userId;
	}
	@Override
	// PW
	public String getPassword() {
		return userPw;
	}
	
	@Override
	// 권한
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();   
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
	}

	@Override
	// 계정이 만료 되지 않았는가?
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	// 계정이 잠기지 않았는가?
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	// 패스워드가 만료되지 않았는가?
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	// 계정이 활성화 되었는가?
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "UserDetailsVO [userId=" + userId + ", userPw=" + userPw + ", userNickName=" + userNickName + ", role="
				+ role + ", link=" + link + "]";
	}
	
	

	


	
	
//extends User
//	public UserDetailsVO(String id, String password,
//			Collection<? extends GrantedAuthority> auth) {		
//		super(id, password, auth);
//	}
//	
}
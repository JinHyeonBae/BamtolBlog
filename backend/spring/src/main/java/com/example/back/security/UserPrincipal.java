package com.example.back.security;

import com.example.back.model.user.UserInformation;
import com.example.back.service.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

//현재 로그인한 사용자
// 현재 인증된(로그인한) 사용자의 정보를 가져오는 방법

public class UserPrincipal implements UserDetails {
	private static final long serialVersionUID = 1L;

	private int id;

	@JsonIgnore
	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	// 어떤 값들을 넣어야 될까?
	public UserPrincipal(int id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;

		if (authorities == null) {
			this.authorities = null;
		} else {
			this.authorities = new ArrayList<>(authorities);
		}
	}

	public UserPrincipal(int id, String email, String password){
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public static UserPrincipal create(UserInformation user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		System.out.println("Role valueOf , Return T :" + Role.valueOf(Role.class, "MEMBER"));

		if(user.getEmail().contains("admin"))
			authorities.add(new SimpleGrantedAuthority((String)Role.ADMIN.name()));
		else
			authorities.add(new SimpleGrantedAuthority((String)Role.MEMBER.name()));

		return new UserPrincipal(user.getId(), user.getEmail(), user.getPassword(), authorities);
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities == null ? null : new ArrayList<>(authorities);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean equals(Object object) { // 같은 유저인지를 확인
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) object;
		return Objects.equals(id, that.id);
	}

	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
}

package com.servicecity.dto;

import java.util.List;

/**
 * 
 * @author Yedukondalu K
 *
 */

public class UserInfo {

	private String email;
	private String tenantId;
	private List<String> roles;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
}

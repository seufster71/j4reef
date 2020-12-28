/*
 * Copyright (C) 2016 The ToastHub Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.toasthub.j4reef.security.api;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * @author Edward H. Seufert
 */
@JsonInclude(Include.NON_NULL)
public class RolePermission extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	protected Role role;
	protected Permission permission;
	protected String rights;
	protected Instant startDate;
	protected Instant endDate;

	// transient
	protected String code;
	protected Long permissionId;
	
	
	// Constructor
	public RolePermission(){}
	
	public RolePermission(Role role, Permission permission, String rights) {
		this.setRole(role);
		this.setPermission(permission);
		this.setRights(rights);
	}
	
	public RolePermission(Long id, boolean active, String rights, Instant startDate, Instant endDate, Long permissionId) {
		this.id = id;
		this.active = active;
		this.rights = rights;
		this.startDate = startDate;
		this.endDate = endDate;
		this.permissionId = permissionId;
	}
	
	// Methods
	@JsonIgnore
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@JsonIgnore
	public Permission getPermission() {
		return permission;
	}
	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	
	public Instant getStartDate() {
		return startDate;
	}
	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}
	
	public Instant getEndDate() {
		return endDate;
	}
	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}
	
}

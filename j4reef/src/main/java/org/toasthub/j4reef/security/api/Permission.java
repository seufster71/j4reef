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

import com.fasterxml.jackson.annotation.JsonView;


public class Permission extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String rights;
	protected String code;
	protected Instant effStart;
	protected Instant effEnd;
	
	// constructors
	public Permission(){
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
	}
	
	public Permission(String code, String rights) {
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
		this.setCode(code);
		this.setRights(rights);
	}
	

	// Setters and getters
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Instant getEffStart() {
		return effStart;
	}
	public void setEffStart(Instant effStart) {
		this.effStart = effStart;
	}

	public Instant getEffEnd() {
		return effEnd;
	}
	public void setEffEnd(Instant effEnd) {
		this.effEnd = effEnd;
	}
	
}

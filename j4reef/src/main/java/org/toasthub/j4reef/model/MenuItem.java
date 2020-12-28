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

package org.toasthub.j4reef.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuItem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	protected String code;
	protected Menu menu;
	protected MenuItem parent;
	protected Long menuId;
	protected Long parentId;
	protected String permissionCode;
	protected String optionalParams;
	protected int order;
	protected Set<MenuItemValue> values;
	protected List<MenuItem> children;
	
	// Constructor
	public MenuItem() {
		super();
	}
	
	// Setter/Getter
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	@JsonIgnore
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@JsonIgnore
	public MenuItem getParent() {
		return parent;
	}
	public void setParent(MenuItem parent) {
		this.parent = parent;
	}

	public String getOptionalParams() {
		return optionalParams;
	}
	public void setOptionalParams(String optionalParams) {
		this.optionalParams = optionalParams;
	}
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}

	public Set<MenuItemValue> getValues() {
		return values;
	}
	public void setValues(Set<MenuItemValue> values) {
		this.values = values;
	}

	public List<MenuItem> getChildren() {
		return children;
	}
	public void setChildren(List<MenuItem> children) {
		this.children = children;
	}
	
	public String getPermissionCode() {
		return this.permissionCode;
	}
	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public void setMValues(Map<String,String> langMap) throws Exception{
		String field = langMap.get(GlobalConstant.FIELD);
		langMap.remove(GlobalConstant.FIELD);
		if (this.values == null) {
			this.values = new HashSet<MenuItemValue>();
		}
		// loop through langMap
		for (String key : langMap.keySet()) {
			// loop through existing values to find match
			boolean existing = false;
			for (MenuItemValue v : values){
				if (v.getLang().equals(key)){
					switch (field) {
					case "value":
						v.setValue(langMap.get(key));
						break;
					case "href":
						v.setHref(langMap.get(key));
						break;
					case "image":
						v.setImage(langMap.get(key));
						break;
					case "rendered":
						v.setRendered(Boolean.parseBoolean(langMap.get(key)));
						break;
					}
					existing = true;
					break;
				} 
			}
			if (!existing) {
				// lang does not exist create a new one
				MenuItemValue val = new MenuItemValue();
				val.setLang(key);
				//val.setOrder(0l);
				val.setMenuItem(this);
				switch (field) {
				case "value":
					val.setValue(langMap.get(key));
					break;
				case "href":
					val.setHref(langMap.get(key));
					break;
				case "image":
					val.setImage(langMap.get(key));
					break;
				case "rendered":
					val.setRendered(Boolean.parseBoolean(langMap.get(key)));
					break;
				}
				values.add(val);
			}
		}
	}
}

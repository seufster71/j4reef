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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PrefLabelValue extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	protected PrefLabelName prefLabelName;
	protected String value;
	protected String lang;
	protected Boolean rendered;
	// make output simple on preference object
	protected String name;
	protected String className;
	protected Integer tabIndex;
	protected String group;
	protected String optionalParams;
	protected int sortOrder;
		
	// Constructor
	public PrefLabelValue() {
		super();
	}
	
	public PrefLabelValue(String lang) {
		super();
		setLang(lang);
		setRendered(false);
		setActive(true);
		setArchive(false);
		setLocked(false);
	}
	
	public PrefLabelValue(Long id, String value, String lang, Boolean rendered, String name, String className, Integer tabIndex, String group, String optionalParams, int sortOrder){
		this.setId(id);
		this.setValue(value);
		this.setLang(lang);
		this.setRendered(rendered);
		this.setName(name);
		this.setClassName(className);
		this.setTabIndex(tabIndex);
		this.setGroup(group);
		this.setOptionalParams(optionalParams);
		this.setSortOrder(sortOrder);
	}
	
	// Setters/Getters
	@JsonIgnore
	public PrefLabelName getPrefLabelName() {
		return prefLabelName;
	}
	public void setPrefLabelName(PrefLabelName prefLabelName) {
		this.prefLabelName = prefLabelName;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public Boolean getRendered() {
		return rendered;
	}
	public void setRendered(Boolean rendered) {
		this.rendered = rendered;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public Integer getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public String getOptionalParams() {
		return optionalParams;
	}
	public void setOptionalParams(String optionalParams) {
		this.optionalParams = optionalParams;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

}

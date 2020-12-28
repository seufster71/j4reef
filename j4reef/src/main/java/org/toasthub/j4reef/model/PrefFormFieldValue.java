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
public class PrefFormFieldValue extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	protected PrefFormFieldName prefFormFieldName;
	protected String value;
	protected String label;
	protected String lang;
	protected Boolean rendered;
	protected Boolean required;
	protected String validation;
	protected String image;
	protected PrefFormFieldValue subElement;
	// make output simple for preference object
	protected String name;
	protected String fieldType;
	protected String htmlType;
	protected String className;
	protected String group;
	protected String subGroup;
	protected Integer tabIndex;
	protected String optionalParams;
	protected String classModel;
	protected int sortOrder;
	
	// Constructor
	public PrefFormFieldValue() {
		super();
	}
	
	public PrefFormFieldValue(String lang) {
		super();
		setLang(lang);
		setRendered(false);
		setRequired(false);
		setActive(true);
		setArchive(false);
		setLocked(false);
	}
	
	// Contructor for fields 
	public PrefFormFieldValue(Long id,String value, String label, String lang, Boolean rendered, 
			Boolean required, String validation, String image, String name, String fieldType,
			String htmlType, String className, String group, String subGroup, Integer tabIndex, 
			String optionalParams, String classModel, int sortOrder) {
		this.setId(id);
		this.setValue(value);
		this.setLabel(label);
		this.setLang(lang);
		this.setRendered(rendered);
		this.setRequired(required);
		this.setValidation(validation);
		this.setImage(image);
		// 
		this.setName(name);
		this.setFieldType(fieldType);
		this.setHtmlType(htmlType);
		this.setClassName(className);
		this.setGroup(group);
		this.setSubGroup(subGroup);
		this.setTabIndex(tabIndex);
		this.setOptionalParams(optionalParams);
		this.setClassModel(classModel);
		this.setSortOrder(sortOrder);
	}
	
	
	
	// Setters/Getters
	@JsonIgnore
	public PrefFormFieldName getPrefFormFieldName() {
		return prefFormFieldName;
	}
	public void setPrefFormFieldName(PrefFormFieldName prefFormFieldName) {
		this.prefFormFieldName = prefFormFieldName;
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
	
	public Boolean getRequired() {
		return required;
	}
	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	public String getValidation() {
		return validation;
	}
	public void setValidation(String validation) {
		this.validation = validation;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	@JsonIgnore
	public PrefFormFieldValue getSubElement() {
		return subElement;
	}
	public void setSubElement(PrefFormFieldValue subElement) {
		this.subElement = subElement;
	}

	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getHtmlType() {
		return htmlType;
	}
	public void setHtmlType(String htmlType) {
		this.htmlType = htmlType;
	}

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}

	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}

	public String getSubGroup() {
		return subGroup;
	}
	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public Integer getTabIndex() {
		return tabIndex;
	}
	public void setTabIndex(Integer tabIndex) {
		this.tabIndex = tabIndex;
	}

	public String getOptionalParams() {
		return optionalParams;
	}
	public void setOptionalParams(String optionalParams) {
		this.optionalParams = optionalParams;
	}
	
	public String getClassModel() {
		return classModel;
	}
	public void setClassModel(String classModel) {
		this.classModel = classModel;
	}

	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
}

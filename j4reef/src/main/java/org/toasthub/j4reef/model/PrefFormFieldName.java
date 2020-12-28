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
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PrefFormFieldName extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String SELECT = "SELECT";
	public static final String INPUT = "INPUT";
	public static final String CHECKBOX = "CHECKBOX";
	public static final String RADIO = "RADIO";
	
	protected PrefName prefName;
	protected String name;
	protected Text title;
	protected String fieldType;
	protected String htmlType;
	protected Integer tabIndex;
	protected Integer rows;
	protected Integer cols;
	protected String className;
	protected String group;
	protected String subGroup;
	protected String optionalParams;
	protected String classModel;
	protected int sortOrder;
	protected Set<PrefFormFieldValue> values;
	
	// Constructors
	public PrefFormFieldName () {
		super();
	}
	public PrefFormFieldName (PrefName prefName,String name,Text title,String fieldType) {
		super();
		this.setPrefName(prefName);
		this.setName(name);
		this.setTitle(title);
		this.setFieldType(fieldType);
	}
	
	public PrefFormFieldName (String name, String fieldType, String htmlType, String className, String group, String subGroup,
			 Integer tabIndex, String optionalParams, String classModel) {
		this.setName(name);
		this.setFieldType(fieldType);
		this.setHtmlType(htmlType);
		this.setClassName(className);
		this.setGroup(group);
		this.setSubGroup(subGroup);
		this.setTabIndex(tabIndex);
		this.setOptionalParams(optionalParams);
		this.setClassModel(classModel);
	}
	
	// Getters and Setters
	@JsonIgnore
	public PrefName getPrefName() {
		return prefName;
	}
	public void setPrefName(PrefName prefName) {
		this.prefName = prefName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Text getTitle() {
		return title;
	}
	public void setTitle(Text title) {
		this.title = title;
	}

	public Set<PrefFormFieldValue> getValues() {
		return values;
	}
	public void setValues(Set<PrefFormFieldValue> values) {
		this.values = values;
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
	
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getCols() {
		return cols;
	}
	public void setCols(Integer cols) {
		this.cols = cols;
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

	public void setTitleDefaultText(String defaultText){
		if (this.title != null) {
			this.title.setDefaultText(defaultText);
		} else {
			Text text = new Text();
			text.setDefaultText(defaultText);
			this.setTitle(text);
		}
	}

	public void setTitleMtext(Map<String,String> langMap){
		if (this.title != null) {
			this.title.setLangTexts(langMap);
		} else {
			Text text = new Text();
			text.setLangTexts(langMap);
			this.setTitle(text);
		}
	}
	
	public void setMValues(Map<String,String> langMap) {
		String field = langMap.get(GlobalConstant.FIELD);
		langMap.remove(GlobalConstant.FIELD);
		if (this.values == null) {
			this.values = new HashSet<PrefFormFieldValue>();
		}
		// loop through langMap
		for (String key : langMap.keySet()) {
			// loop through existing values to find match
			boolean added = false;
			for (PrefFormFieldValue v : values){
				if (v.getLang().equals(key)){
					switch (field) {
					case "value":
						v.setValue(langMap.get(key));
						break;
					case "label":
						v.setLabel(langMap.get(key));
						break;
					case "rendered":
						v.setRendered(Boolean.parseBoolean(langMap.get(key)));
						break;
					case "required":
						v.setRequired(Boolean.parseBoolean(langMap.get(key)));
						break;
					}
					added = true;
					break;
				} 
			}
			if (!added) {
				// lang does not exist create a new one
				PrefFormFieldValue val = new PrefFormFieldValue();
				val.setLang(key);
				val.setPrefFormFieldName(this);
				val.setActive(true);
				val.setArchive(false);
				val.setLocked(false);
				val.setValidation("");
				switch (field) {
				case "value":
					val.setValue(langMap.get(key));
					break;
				case "label":
					val.setLabel(langMap.get(key));
					break;
				case "rendered":
					val.setRendered(Boolean.parseBoolean(langMap.get(key)));
					break;
				case "required":
					val.setRequired(Boolean.parseBoolean(langMap.get(key)));
					break;
				}
				values.add(val);
			}
		}
	}
	
	public void addToValues(Object value) {
		PrefFormFieldValue val = (PrefFormFieldValue) value;
		val.setPrefFormFieldName(this);
		if (values == null) {
			values = new HashSet<PrefFormFieldValue>();
			values.add(val);
		} else {
			boolean exists = false;
			for (PrefFormFieldValue v : values) {
				if (v.getLang().equals(val.getLang())) {
					v.setValue(val.getValue());
					v.setLabel(val.getLabel());
					v.setRendered(val.getRendered());
					v.setRequired(val.getRequired());
					exists = true;
					break;
				}
			}
			if (exists == false) {
				values.add(val);
			}
		}
		
		
	}
	
}

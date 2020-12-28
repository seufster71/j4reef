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
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PrefOptionName extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private PrefName prefName;
	private String name;
	private Text title;
	private String valueType;
	private String defaultValue;
	private Boolean useDefault;
	private String optionalParams;
	private Set<PrefOptionValue> values;
	
	// Constructor 
	public PrefOptionName() {
		super();
	}
	
	
	// Setters/Getter
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
	
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public Boolean getUseDefault() {
		return useDefault;
	}
	public void setUseDefault(Boolean useDefault) {
		this.useDefault = useDefault;
	}

	public String getOptionalParams() {
		return optionalParams;
	}
	public void setOptionalParams(String optionalParams) {
		this.optionalParams = optionalParams;
	}
	
	public Set<PrefOptionValue> getValues() {
		return values;
	}
	public void setValues(Set<PrefOptionValue> values) {
		this.values = values;
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
	
	public void addToValues(Object value) {
		PrefOptionValue val = (PrefOptionValue) value;
		val.setPrefOptionName(this);
		if (values == null) {
			values = new HashSet<PrefOptionValue>();
			values.add(val);
		} else {
			boolean exists = false;
			for (PrefOptionValue v : values) {
				if (v.getLang().equals(val.getLang())) {
					v.setValue(val.getValue());
					v.setRendered(val.getRendered());
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

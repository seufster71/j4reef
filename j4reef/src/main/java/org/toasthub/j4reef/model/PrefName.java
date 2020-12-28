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
import java.time.Instant;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PrefName extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String name;
	protected Text title;
	protected String category;
	protected Set<PrefTextName> texts;
	protected Set<PrefFormFieldName> formFields;
	protected Set<PrefLabelName> labels;
	protected Set<PrefOptionName> options;
	
	// Transient
	protected Long prefProductId;
	
	// Constructor
	public PrefName () {
		super();
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
	}
	
	
	// Methods

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
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Set<PrefTextName> getTexts() {
		return texts;
	}
	public void setTexts(Set<PrefTextName> texts) {
		this.texts = texts;
	}

	public Set<PrefFormFieldName> getFormFields() {
		return formFields;
	}
	public void setFormFields(Set<PrefFormFieldName> formFields) {
		this.formFields = formFields;
	}

	public Set<PrefLabelName> getLabels() {
		return labels;
	}
	public void setLabels(Set<PrefLabelName> labels) {
		this.labels = labels;
	}
	
	public Set<PrefOptionName> getOptions() {
		return options;
	}
	public void setOptions(Set<PrefOptionName> options) {
		this.options = options;
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
}

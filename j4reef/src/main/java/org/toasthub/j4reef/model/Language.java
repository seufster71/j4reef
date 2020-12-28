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


public class Language extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	public static String[] columns = {"title","code","defaultLang","dir"};
	public static String[] dataTypes = {"text","string","boolean","string"};
	
	private String code;
	private Text title;
	private Boolean defaultLang;
	private String dir;

	//Constructor
	public Language() {
		super();
	}
	
	public Language(String code, Text title, Boolean defaultLang, String dir){
		this.setActive(true);
		this.setArchive(false);
		this.setLocked(false);
		this.setCreated(Instant.now());
		
		this.setCode(code);
		this.setTitle(title);
		this.setDefaultLang(defaultLang);
		this.setDir(dir);
	}
	
	public Language(String code, Boolean defaultLang, String dir, Text title) {
		this.setCode(code);
		this.setDefaultLang(defaultLang);
		this.setDir(dir);
		this.setTitle(title);
	}
	
	// Methods
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public Text getTitle() {
		return title;
	}
	public void setTitle(Text title) {
		this.title = title;
	}
	
	
	public Boolean isDefaultLang() {
		return defaultLang;
	}
	public void setDefaultLang(Boolean defaultLang) {
		this.defaultLang = defaultLang;
	}

	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
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

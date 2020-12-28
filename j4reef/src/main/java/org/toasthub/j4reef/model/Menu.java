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
import java.util.Map;
import java.util.Set;


public class Menu implements Serializable{
	private static final long serialVersionUID = 1L;

	public static String[] columns = {"title","name","apiVersion","appVersion","active"};
	public static String[] dataTypes = {"mtext","string","string","string","boolean"};
	public static final String MENU = "menu";
	public static final String MENUS = "menu";
	public static final String ID = "menuId";
	public static final String MENUNAME = "menuName";
	
	protected String code;
	protected String category;
	protected Text title;
	protected String apiVersion;
	protected String appVersion;
	protected Set<MenuItem> menuItems;
	// Constructor
	public Menu () {
		super();
	}
	// Setters/Getters
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public Text getTitle() {
		return title;
	}
	public void setTitle(Text title) {
		this.title = title;
	}
	
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
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

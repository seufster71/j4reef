package org.toasthub.j4reef.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("AppCacheMenu")
@Scope("singleton")
public class AppCacheMenu implements Serializable {

	private static final long serialVersionUID = 1L;
	// menuName-tenant-apiVerison-appVersion-lang -> order -> menuItem
	private Map<String,List<MenuItem>> menus = null;
	public static final String[] categories = {"PUBLIC","MEMBER","ADMIN"};
	
	
	// Constructor
	public AppCacheMenu(){
	}
	
	public void clearCache(){
		this.menus = null;
	}

	public void setMenus(Map<String,List<MenuItem>> menus){ 
		this.menus = menus;
	}
	
	public Map<String,List<MenuItem>> getMenus(){
		return menus;
	}

	public void addMenu(String key, List<MenuItem> menu) {
		if (this.menus != null){
			this.menus.put(key, menu);
		}
	}
	
}

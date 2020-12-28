package org.toasthub.j4reef.model;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("AppCacheMenuUtil")
public class AppCacheMenuUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PrefCacheUtil prefCacheUtil;
	
	@Autowired
	AppCacheMenu appCacheMenu;
	
	// Menus
	public List<MenuItem> getMenu(String menuName, String apiVersion, String appVersion, String lang) {
		List<MenuItem> menu = null;
		StringBuilder key = new StringBuilder();
		key.append(menuName);

		if (appCacheMenu.getMenus() != null && appCacheMenu.getMenus().containsKey(key.toString())){
			// Pull from memory
			menu = appCacheMenu.getMenus().get(key.toString());
		} else {
			// Get from DB and put in cache
			synchronized (this) {
				// this is done to catch all concurrent users during a cache reload to prevent then from all trying to reloading the cache
				// only the first shall do the reload.
				if (appCacheMenu.getMenus() != null && appCacheMenu.getMenus().containsKey(key.toString())){
					menu = appCacheMenu.getMenus().get(key.toString());
				}
			}
		}
		return menu;
	}
	
	@SuppressWarnings("unchecked")
	public void loadMenuCache(String fileName) {

		try {
			ClassPathResource res = new ClassPathResource(fileName);  
			Gson gson = new Gson();
			Type userListType = new TypeToken<ArrayList<Menu>>(){}.getType();
			ArrayList<Menu> items = gson.fromJson(new InputStreamReader(res.getInputStream()), userListType); 
		
			Map<String,List<MenuItem>> menus = new HashMap<String,List<MenuItem>>();
			
			StringBuilder key = null;
			for(Menu menu : items) {
				if (menu.getMenuItems() != null) {
					for(MenuItem i : menu.getMenuItems()) {
						key = new StringBuilder();
						key.append(menu.getCode());
						
						if (menus.containsKey(key.toString())) {
							menus.get(key.toString()).add(i);
						} else {
							List<MenuItem> vlist = new ArrayList<MenuItem>();
							vlist.add(i);
							menus.put(key.toString(), vlist);
						}
					}
				}
			}
			appCacheMenu.setMenus(menus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearCache(){
		appCacheMenu.clearCache();
	}
}

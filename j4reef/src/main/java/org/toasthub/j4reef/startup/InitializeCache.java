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

package org.toasthub.j4reef.startup;

import org.toasthub.j4reef.model.AppCacheMenuUtil;
import org.toasthub.j4reef.model.PrefCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component("InitializeCache")
public class InitializeCache {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PrefCacheUtil prefCacheUtil;
	
	@Autowired
	AppCacheMenuUtil appCacheMenuUtil;
	
	@EventListener(ContextRefreshedEvent.class)
	public void contextRefreshedEvent() {
		
			logger.info("loading language cache ");
			prefCacheUtil.loadLanguageCache("languages.json");
			
			prefCacheUtil.loadPrefCache("prefLogin.json");
			prefCacheUtil.loadPrefCache("prefRegistration.json");
			prefCacheUtil.loadPrefCache("prefPasswordChange.json");
			prefCacheUtil.loadPrefCache("prefGlobal.json");
			prefCacheUtil.loadPrefCache("prefController.json");
			prefCacheUtil.loadPrefCache("prefPlug.json");
			prefCacheUtil.loadPrefCache("prefSchedule.json");
			
			logger.info("loading menu cache ");
			appCacheMenuUtil.loadMenuCache("menus.json");
	
	}
}

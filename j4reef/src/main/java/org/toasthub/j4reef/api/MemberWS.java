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

package org.toasthub.j4reef.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.toasthub.j4reef.common.UtilSvc;
import org.toasthub.j4reef.controller.ControllerSvc;
import org.toasthub.j4reef.gpio.GPIOController;
import org.toasthub.j4reef.model.AppCacheMenuUtil;
import org.toasthub.j4reef.model.GlobalConstant;
import org.toasthub.j4reef.model.MenuItem;
import org.toasthub.j4reef.model.PrefCacheUtil;
import org.toasthub.j4reef.plug.PlugSvc;
import org.toasthub.j4reef.schedule.ScheduleSvc;
import org.toasthub.j4reef.security.api.MyUserPrincipal;
import org.toasthub.j4reef.utils.Request;
import org.toasthub.j4reef.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController()
@RequestMapping("/api/member")
public class MemberWS {

	@Autowired 
	UtilSvc utilSvc;
	
	@Autowired
	ControllerSvc controllerSvc;
	
	@Autowired
	PlugSvc plugSvc;
	
	@Autowired
	ScheduleSvc scheduleSvc;
	
	@Autowired
	PrefCacheUtil prefCacheUtil;
	
	@Autowired
	AppCacheMenuUtil appCacheMenuUtil;
	
	@Autowired
	HttpServletRequest servletRequest;
	@Autowired
	HttpServletResponse servletResponse;

	@Autowired
	GPIOController gpioController;
	
	
	@RequestMapping(value = "callService", method = RequestMethod.POST)
	public Response callService(@RequestBody Request request) {
		String service = (String) request.getParams().get("service");
		String action = (String) request.getParams().get("action");
		Response response = new Response();
		
		if (!request.containsParam(GlobalConstant.LANG)) {
			request.addParam(GlobalConstant.LANG, "en");
		}
		
		switch (service) {
		case "PLUG_SVC":
			plugSvc.process(request, response);
			break;
		case "CONTROLLER_SVC":
			controllerSvc.process(request, response);
			break;
		case "SCHEDULE_SVC":
			scheduleSvc.process(request, response);
			break;
		default:
			break;
		}
		
		// validate request
		switch (action) {
		case "INIT":
			request.addParam(PrefCacheUtil.PREFPARAMLOC, PrefCacheUtil.RESPONSE);
			prefCacheUtil.getPrefInfo(request,response);
			
			// get menus
			if (request.containsParam(GlobalConstant.MENUNAMES)){
				this.initMenu(request, response);
			}
			response.setStatus(Response.SUCCESS);
			break;
		case "BLINK":
			gpioController.blink(request, response);
			break;
		case "ON":
			gpioController.on(request, response);
			break;
		case "OFF":
			gpioController.off(request, response);
			break;
		case "LIST_PLUG":
			gpioController.listPlug(request, response);
			break;
		case "LIST_SWITCH":
			gpioController.listSwitch(request, response);
			break;
		case "LIST_LIGHT":
			gpioController.listLight(request, response);
			break;
		case "CHECK":
			response.addParam("USER", ((MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser());
			utilSvc.addStatus(Response.INFO, Response.SUCCESS, "Alive", response);
			break;
		case "LOGOUT":
			logout(request, response);
			break;
		default:
			break;
		}
		return response;
	}
	
	protected void logout(Request request, Response response) {
		// invalidate user context and terminate session
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(servletRequest, servletResponse, auth);
		}
		utilSvc.addStatus(Response.INFO, Response.SUCCESS, "Good Bye", response);
		// log user activity
		
	}
	
	public void initMenu(Request request, Response response){
		
		List<MenuItem> menu = null;
		Map<String,List<MenuItem>> menuList = new HashMap<String,List<MenuItem>>();
		//TODO: NEED to add some separation for app and domain so there is no cross over
		ArrayList<String> mylist = (ArrayList<String>) request.getParam(GlobalConstant.MENUNAMES);
		for (String menuName : mylist) {
			menu = appCacheMenuUtil.getMenu(menuName,(String)request.getParam(GlobalConstant.MENUAPIVERSION),(String)request.getParam(GlobalConstant.MENUAPPVERSION),(String)request.getParam(GlobalConstant.LANG));
			menuList.put(menuName, menu);
		}
		
		if (!menuList.isEmpty()){
			response.addParam(Response.MENUS, menuList);
		} else {
			utilSvc.addStatus(Response.INFO, Response.SUCCESS, "Menu Issue", response);
		}
	}
}

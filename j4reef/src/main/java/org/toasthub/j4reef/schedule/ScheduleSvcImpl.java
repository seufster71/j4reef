/*
 * Copyright (C) 2020 The ToastHub Project
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

package org.toasthub.j4reef.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.toasthub.j4reef.common.UtilSvc;
import org.toasthub.j4reef.gpio.GPIOController;
import org.toasthub.j4reef.model.GlobalConstant;
import org.toasthub.j4reef.model.PrefCacheUtil;
import org.toasthub.j4reef.model.Schedule;
import org.toasthub.j4reef.utils.Request;
import org.toasthub.j4reef.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ScheduleSvc")
public class ScheduleSvcImpl implements ScheduleSvc{
	
	@Autowired
	UtilSvc utilSvc;
	
	@Autowired
	GPIOController gpioController;
	
	@Autowired
	PrefCacheUtil prefCacheUtil;

	
	@Override
	public void process(Request request, Response response) {
		String action = (String) request.getParams().get(GlobalConstant.ACTION);
		List<String> global =  new ArrayList<String>(Arrays.asList("LANGUAGES"));
		
		Long count = 0l;
		switch (action) {
		case "INIT":
			request.addParam(PrefCacheUtil.PREFPARAMLOC, PrefCacheUtil.RESPONSE);
			prefCacheUtil.getPrefInfo(request,response);
			this.items(request, response);
			response.addParam(GlobalConstant.ITEMNAME, request.getParam(GlobalConstant.ITEMNAME));
			break;
		case "LIST":
			request.addParam(PrefCacheUtil.PREFPARAMLOC, PrefCacheUtil.RESPONSE);
			prefCacheUtil.getPrefInfo(request,response);
			this.items(request, response);
			response.addParam(GlobalConstant.ITEMNAME, request.getParam(GlobalConstant.ITEMNAME));
			break;
		case "ITEM":
			request.addParam(PrefCacheUtil.PREFPARAMLOC, PrefCacheUtil.RESPONSE);
			prefCacheUtil.getPrefInfo(request,response);
			this.item(request, response);
			break;
		case "DELETE":
			this.delete(request, response);
			break;
		case "SAVE":
			if (!request.containsParam(PrefCacheUtil.PREFFORMKEYS)) {
				List<String> forms =  new ArrayList<String>(Arrays.asList("SCHEDULE_FORM"));
				request.addParam(PrefCacheUtil.PREFFORMKEYS, forms);
			}
			request.addParam(PrefCacheUtil.PREFGLOBAL, global);
			prefCacheUtil.getPrefInfo(request,response);
			this.save(request, response);
			break;
		default:
			utilSvc.addStatus(Response.INFO, Response.ACTIONNOTEXIST, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_ACTION_NOT_AVAIL",prefCacheUtil.getLang(request)), response);
			break;
		}
	}
	
	@Override
	public void items(Request request, Response response) {
		try {
			gpioController.listSchedule(request, response);
			if (response.getParam("items") == null){
				utilSvc.addStatus(Response.INFO, Response.EMPTY, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_NO_ITEMS",prefCacheUtil.getLang(request)), response);
			}
		} catch (Exception e) {
			utilSvc.addStatus(Response.ERROR, Response.EXECUTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_EXECUTION_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}

	
	@Override
	public void itemCount(Request request, Response response) {
		try {
			// backlogDao.itemCount(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(Response.ERROR, Response.EXECUTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_EXECUTION_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Request request, Response response) {
		try {
			gpioController.deleteSchedule(request, response);
			utilSvc.addStatus(Response.INFO, Response.SUCCESS, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_DELETE_SUCCESS",prefCacheUtil.getLang(request)), response);
		} catch (Exception e) {
			utilSvc.addStatus(Response.ERROR, Response.ACTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_DELETE_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}
	
	@Override
	public void item(Request request, Response response) {
		try {
			gpioController.getSchedule(request, response);
		} catch (Exception e) {
			utilSvc.addStatus(Response.ERROR, Response.EXECUTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_EXECUTION_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}

	@Override
	public void save(Request request, Response response) {
		try {
			// validate
			utilSvc.validateParams(request, response);
			
			if ((Boolean) request.getParam(GlobalConstant.VALID) == false) {
				utilSvc.addStatus(Response.ERROR, Response.ACTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_VALIDATION_ERR",prefCacheUtil.getLang(request)), response);
				return;
			}
			// get existing item
			Map<String,Object> inputList = (Map<String, Object>) request.getParam(GlobalConstant.INPUTFIELDS);
			if (inputList.containsKey(GlobalConstant.ITEMID) && inputList.get(GlobalConstant.ITEMID) != null && !"".equals(inputList.get(GlobalConstant.ITEMID))) {
				request.addParam(GlobalConstant.ITEMID, inputList.get(GlobalConstant.ITEMID));
				gpioController.getSchedule(request, response);
				request.addParam(GlobalConstant.ITEM, response.getParam(GlobalConstant.ITEM));
				response.getParams().remove(GlobalConstant.ITEM);
			} else {
				Schedule schedule = new Schedule();
				request.addParam(GlobalConstant.ITEM, schedule);
				gpioController.addSchedule(request, response);
			}
			// marshall
			utilSvc.marshallFields(request, response);
		
			gpioController.clearActiveSchedule(request, response);
			// save
			gpioController.saveConfig();
			utilSvc.addStatus(Response.INFO, Response.SUCCESS, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_SAVE_SUCCESS",prefCacheUtil.getLang(request)), response);
		} catch (Exception e) {
			utilSvc.addStatus(Response.ERROR, Response.ACTIONFAILED, prefCacheUtil.getPrefText("GLOBAL_SERVICE", "GLOBAL_SERVICE_SAVE_FAIL",prefCacheUtil.getLang(request)), response);
			e.printStackTrace();
		}
	}
}

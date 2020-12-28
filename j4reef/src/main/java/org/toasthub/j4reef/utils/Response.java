package org.toasthub.j4reef.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.toasthub.j4reef.model.StatusMessage;


public class Response {
	
	public static final String STATUS = "status";
	public static final String ERROR = "ERROR";
	public static final String SUCCESS = "SUCCESS";
	public static final String INFO = "INFO";
	public static final String WARN = "WARN";
	
	public static final String REGISTER = "REGISTER";
	
	public static final String AUTHFAIL = "AUTHFAIL";
	public static final String DOESNOTEXIST = "DOESNOTEXIST";
	public static final String ACTIONNOTEXIST = "ACTIONNOTEXIST";
	public static final String PAGEOPTIONS = "PAGEOPTIONS";
	public static final String UNKNOWNTYPE = "UNKNOWNTYPE";
	public static final String MISSINGPARAM = "MISSINGPARAM";
	public static final String SERVERERROR = "SERVERERROR";
	public static final String EXECUTIONFAILED = "EXECUTIONFAILED";
	public static final String EMPTY = "EMPTY";
	public static final String ACTIONFAILED = "ACTIONFAILED";
	public static final String REGISTRATIONFAILED = "REGISTRATIONFAILED";
	public static final String ACCESSDENIED = "ACCESSDENIED";
	public static final String MENUS = "MENUS";

	protected String status;
	protected List<StatusMessage> infos;
	protected List<StatusMessage> warns;
	protected List<StatusMessage> errors;
	private Map<String,Object> params;
	
	public Response(){
		setParams(new HashMap<String,Object>());
	}

	
	// Methods
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<StatusMessage> getInfos() {
		return infos;
	}
	public void setInfos(List<StatusMessage> infos) {
		this.infos = infos;
	}

	public List<StatusMessage> getWarns() {
		return warns;
	}
	public void setWarns(List<StatusMessage> warns) {
		this.warns = warns;
	}

	public List<StatusMessage> getErrors() {
		return errors;
	}
	public void setErrors(List<StatusMessage> errors) {
		this.errors = errors;
	}
	
	public Map<String,Object> getParams() {
		return params;
	}
	public void setParams(Map<String,Object> params) {
		this.params = params;
	}
	
	public void addParam(String key, Object value){
		if (params == null){
			params = new HashMap<String,Object>();
		}
		params.put(key, value);
	}

	public Object getParam(String key){
		if (params != null && params.containsKey(key)){
			return params.get(key);
		}
		return null;
	}
	
	public boolean containsParam(String key){
		if (params != null && params.containsKey(key)){
			return true;
		}
		return false;
	}
}

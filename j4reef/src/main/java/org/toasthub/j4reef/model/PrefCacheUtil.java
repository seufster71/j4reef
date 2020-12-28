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

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.toasthub.j4reef.common.UtilSvc;
import org.toasthub.j4reef.utils.Request;
import org.toasthub.j4reef.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component("PrefCacheUtil")
public class PrefCacheUtil {
	
	public static final String RESPONSE = "response";
	public static final String REQUEST = "request";
	public static final String PREFPARAMLOC = "prefParamLoc";
	public static final String PREFFORMKEYS = "prefFormKeys";
	public static final String PREFFORMNAME = "prefFormName";
	public static final String PREFFORMFIELDS = "prefFormFields";
	public static final String PREFLABELNAME = "prefLabelName";
	public static final String PREFLABELKEYS = "prefLabelKeys";
	public static final String PREFLABELS = "prefLabels";
	public static final String PREFTEXTNAME = "prefTextName";
	public static final String PREFTEXTKEYS = "prefTextKeys";
	public static final String PREFTEXTS = "prefTexts";
	public static final String PREFOPTIONNAME = "prefOptionName";
	public static final String PREFOPTIONKEYS = "prefOptionKeys";
	public static final String PREFOPTIONS = "prefOptions";
	public static final String PREFGLOBAL = "prefGlobal";
	public static final String LANGUAGES = "LANGUAGES";
	
	/*
	@Autowired
	@Qualifier("PrefSvc")
	protected PrefSvc prefSvc;
	*/
	@Autowired	
	protected UtilSvc utilSvc;
	
	@Autowired
	PrefCache prefCache;
	
	public void clearCache(){
		prefCache.setPrefFormFields(null);
		prefCache.setPrefLabels(null);
		prefCache.setPrefOptions(null);
		prefCache.setPrefTexts(null);
		prefCache.setLanguages(null);
	}
	
	public void clearPrefCache() {
		prefCache.setPrefFormFields(null);
		prefCache.setPrefLabels(null);
		prefCache.setPrefOptions(null);
		prefCache.setPrefTexts(null);
		
	}
	
	@SuppressWarnings("unchecked")
	public void getPrefInfo(Request request, Response response) {
		if (request.containsParam(PREFFORMKEYS) && !request.getParam(PREFFORMKEYS).equals("") && request.getParam(PREFFORMKEYS) instanceof List){
			for (String item : (List<String>) request.getParam(PREFFORMKEYS)) {
				request.addParam(PREFFORMNAME, item);
				getPrefFormFields(request,response);
			}
		}
		if (request.containsParam(PREFLABELKEYS) && !request.getParam(PREFLABELKEYS).equals("") && request.getParam(PREFLABELKEYS) instanceof List){
			for (String item : (List<String>) request.getParam(PREFLABELKEYS)) {
				request.addParam(PREFLABELNAME, item);
				getPrefLabels(request,response);
			}
		}
		if (request.containsParam(PREFTEXTKEYS) && !request.getParam(PREFTEXTKEYS).equals("") && request.getParam(PREFTEXTKEYS) instanceof List){
			for (String item : (List<String>) request.getParam(PREFTEXTKEYS)) {
				request.addParam(PREFTEXTNAME, item);
				getPrefTexts(request,response);
			}
		}
		if (request.containsParam(PREFOPTIONKEYS) && !request.getParam(PREFOPTIONKEYS).equals("") && request.getParam(PREFOPTIONKEYS) instanceof List){
			for (String item : (List<String>) request.getParam(PREFOPTIONKEYS)) {
				request.addParam(PREFOPTIONNAME, item);
				getPrefOptions(request,response);
			}
		}
		if (request.containsParam(PREFGLOBAL) && !request.getParam(PREFGLOBAL).equals("") && request.getParam(PREFGLOBAL) instanceof List){
			for (String item : (List<String>) request.getParam(PREFGLOBAL)) {
				if ("LANGUAGES".equals(item)) {
					getLanguages(request,response);
				}
			}
		}
		
	}
	
	////////////////////////////// Pref form fields
	public void getPrefFormFields(Request request, Response response) {

		StringBuilder key = new StringBuilder();
		key.append(request.getParam(PREFFORMNAME));
		key.append("_");
		key.append((String)request.getParam(GlobalConstant.LANG));
		
		if (prefCache.getPrefFormFields() != null && prefCache.getPrefFormFields().containsKey(key.toString())){
			// Pull from memory cache
			prefFormFieldLoadFromMem(request,response,key.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void prefFormFieldLoadFromMem(Request request, Response response, String key) {
		// Pull from Memory
		Map<String,List<PrefFormFieldValue>> f = null;
		// add to request or response
		if (request.containsParam(PREFPARAMLOC) && RESPONSE.equals(request.getParam(PREFPARAMLOC)) ) {
			if (response.getParams().containsKey(PREFFORMFIELDS)){
				f = (Map<String, List<PrefFormFieldValue>>) response.getParam(PREFFORMFIELDS);
			} else {
				f = new ConcurrentHashMap<String,List<PrefFormFieldValue>>();
			}
			f.put((String) request.getParam(PREFFORMNAME), prefCache.getPrefFormFields().get(key));
			response.addParam(PREFFORMFIELDS,f);
		} else {
			if (request.getParams().containsKey(PREFFORMFIELDS)){
				f = (Map<String, List<PrefFormFieldValue>>) request.getParam(PREFFORMFIELDS);
			} else {
				f = new ConcurrentHashMap<String,List<PrefFormFieldValue>>();
			}
			f.put((String) request.getParam(PREFFORMNAME), prefCache.getPrefFormFields().get(key));
			request.addParam(PREFFORMFIELDS,f);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void loadPrefCache(String fileName) {
		try {
			ClassPathResource res = new ClassPathResource(fileName);  
			Gson gson = new Gson();
			Type userListType = new TypeToken<ArrayList<PrefName>>(){}.getType();
			ArrayList<PrefName> items = gson.fromJson(new InputStreamReader(res.getInputStream()), userListType); 
			
			if (prefCache.getPrefFormFields() == null) {
				prefCache.setPrefFormFields(new HashMap<String, List<PrefFormFieldValue>>());
			} 
			
			if (prefCache.getPrefLabels() == null) {
				prefCache.setPrefLabels(new HashMap<String, List<PrefLabelValue>>());
			}
			
			if (prefCache.getPrefTexts() == null) {
				prefCache.setPrefTexts(new HashMap<String, Map<String,PrefTextValue>>());
			} 
			
			if (prefCache.getPrefOptions() == null) {
				prefCache.setPrefOptions(new HashMap<String, Map<String,PrefOptionValue>>());
			}
			
			StringBuilder key = null;
			for(PrefName name : items) {
				if (name.getFormFields() != null) {
					for(PrefFormFieldName f : name.getFormFields()) {
						if (f.getValues() != null) {
							for (PrefFormFieldValue v : f.getValues()) {
								key = new StringBuilder();
								key.append(name.getName());
								key.append("_");
								key.append(v.getLang());
								
								v.setName(f.getName());
								v.setFieldType(f.getFieldType());
								v.setHtmlType(f.getHtmlType());
								v.setClassName(f.getClassName());
								v.setSubGroup(f.getSubGroup());
								v.setGroup(f.getGroup());
								v.setTabIndex(f.getTabIndex());
								v.setOptionalParams(f.getOptionalParams());
								v.setClassModel(f.getClassModel());
								v.setSortOrder(f.getSortOrder());

								if (prefCache.getPrefFormFields().containsKey(key.toString())) {
									prefCache.getPrefFormFields().get(key.toString()).add(v);
								} else {
									List<PrefFormFieldValue> vlist = new ArrayList<PrefFormFieldValue>();
									vlist.add(v);
									prefCache.getPrefFormFields().put(key.toString(), vlist);
								}
							}
						}
					}
				}
				if (name.getLabels() != null) {
					for(PrefLabelName l : name.getLabels()) {
						if (l.getValues() != null) {
							for (PrefLabelValue v : l.getValues()) {
								key = new StringBuilder();
								key.append(name.getName());
								key.append("_");
								key.append(v.getLang());
								
								v.setName(l.getName());
								v.setClassName(l.getClassName());
								v.setTabIndex(l.getTabIndex());
								v.setGroup(l.getGroup());
								v.setOptionalParams(l.getOptionalParams());
								v.setSortOrder(l.getSortOrder());
								
								if (prefCache.getPrefLabels().containsKey(key.toString())) {
									prefCache.getPrefLabels().get(key.toString()).add(v);
								} else {
									List<PrefLabelValue> vlist = new ArrayList<PrefLabelValue>();
									vlist.add(v);
									prefCache.getPrefLabels().put(key.toString(), vlist);
								}
							}
						}
					}
				}
				if (name.getTexts() != null) {
					for(PrefTextName t : name.getTexts()) {
						if (t.getValues() != null) {
							for (PrefTextValue v : t.getValues()) {
								key = new StringBuilder();
								key.append(name.getName());
								key.append("_");
								key.append(v.getLang());
								
								v.setName(t.getName());

								if (prefCache.getPrefTexts().containsKey(key.toString())) {
									prefCache.getPrefTexts().get(key.toString()).put(t.getName(), v);
								} else {
									Map<String,PrefTextValue> vMap = new HashMap<String,PrefTextValue>();
									vMap.put(t.getName(), v);
									prefCache.getPrefTexts().put(key.toString(), vMap);
								}
							}
						}
					}
				}
				if (name.getOptions() != null) {
					for(PrefOptionName o : name.getOptions()) {
						if (o.getValues() != null) {
							for (PrefOptionValue v : o.getValues()) {
								key = new StringBuilder();
								key.append(name.getName());
								key.append("_");
								key.append(v.getLang());
								
								v.setName(o.getName());

								if (prefCache.getPrefOptions().containsKey(key.toString())) {
									prefCache.getPrefOptions().get(key.toString()).put(o.getName(), v);
								} else {
									Map<String,PrefOptionValue> vMap = new HashMap<String,PrefOptionValue>();
									vMap.put(o.getName(), v);
									prefCache.getPrefOptions().put(key.toString(), vMap);
								}
							}
						}
					}
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clearPrefFormFieldCache(){
		// Clear cache immediately
		prefCache.clearPrefFormFieldCache();
	}
	
	public void clearPrefFormFieldCache(String key){
		// Clear one item in cache immediately
		prefCache.clearPrefFormFieldCache(key);
	}
	
	///////////////////////////// Pref Labels
	public void getPrefLabels(Request request, Response response) {
		StringBuilder key = new StringBuilder();
		key.append(request.getParam(PREFLABELNAME));
		key.append("_");
		key.append((String)request.getParam(GlobalConstant.LANG));
		if (prefCache.getPrefLabels() != null && prefCache.getPrefLabels().containsKey(key.toString())){
			// Pull from memory cache
			prefLabelLoadFromMem(request,response,key.toString());
		} else {
			// Get from DB and put in cache
			synchronized (this) {
				// this is done to catch all concurrent users during a cache reload to prevent then from all trying to reloading the cache
				// only the first shall do the reload.
				if (prefCache.getPrefLabels() != null && prefCache.getPrefLabels().containsKey(key.toString())){
					prefLabelLoadFromMem(request,response,key.toString());
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void prefLabelLoadFromMem(Request request, Response response, String key) {
		// Pull from Memory
		Map<String,List<PrefLabelValue>> l = null;
		// add to request or response
		if (request.containsParam(PREFPARAMLOC) && RESPONSE.equals(request.getParam(PREFPARAMLOC)) ) {
			if (response.getParams().containsKey(PREFLABELS)){
				l = (Map<String, List<PrefLabelValue>>) response.getParam(PREFLABELS);
			} else {
				l = new ConcurrentHashMap<String,List<PrefLabelValue>>();
			}
			l.put((String) request.getParam(PREFLABELNAME), prefCache.getPrefLabels().get(key));
			response.addParam(PREFLABELS,l);
		} else {
			if (request.getParams().containsKey(PREFLABELS)){
				l = (Map<String, List<PrefLabelValue>>) request.getParam(PREFLABELS);
			} else {
				l = new ConcurrentHashMap<String,List<PrefLabelValue>>();
			}
			l.put((String) request.getParam(PREFLABELNAME), prefCache.getPrefLabels().get(key));
			request.addParam(PREFLABELS,l);
		}
	}

	
	public void clearPrefLabelCache(){
		// Clear cache immediately
		prefCache.clearPrefFormFieldCache();
	}

	public void clearPrefLabelCache(String key){
		// Clear one item in cache immediately
		prefCache.clearPrefFormFieldCache(key);
	}
	
	//////////////////// Pref Options
	public void getPrefOptions(Request request, Response response) {
		StringBuilder key = new StringBuilder();
		key.append(request.getParam(PREFOPTIONNAME));
		key.append("_");
		key.append((String)request.getParam(GlobalConstant.LANG));
		if (prefCache.getPrefOptions() != null && prefCache.getPrefOptions().containsKey(key.toString())){
			prefOptionLoadFromMem(request,response,key.toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	private void prefOptionLoadFromMem(Request request, Response response, String key) {
		// Pull from Memory
		Map<String,Map<String,PrefOptionValue>> o = null;
		// add to request or response
		if (request.containsParam(PREFPARAMLOC) && RESPONSE.equals(request.getParam(PREFPARAMLOC)) ) {
			if (response.getParams().containsKey(PREFOPTIONS)){
				o = (Map<String, Map<String,PrefOptionValue>>) response.getParam(PREFOPTIONS);
			} else {
				o = new ConcurrentHashMap<String,Map<String,PrefOptionValue>>();
			}
			o.put((String) request.getParam(PREFOPTIONNAME), prefCache.getPrefOptions().get(key));
			response.addParam(PREFOPTIONS, o);
		} else {
			if (request.getParams().containsKey(PREFOPTIONS)){
				o = (Map<String, Map<String,PrefOptionValue>>) request.getParam(PREFOPTIONS);
			} else {
				o = new ConcurrentHashMap<String,Map<String,PrefOptionValue>>();
			}
			o.put((String) request.getParam(PREFOPTIONNAME), prefCache.getPrefOptions().get(key));
			request.addParam(PREFOPTIONS,o);
		}
	}
	

	
	public void clearPrefOptionCache(){
		prefCache.clearPrefOptionCache();
	}

	public void clearPrefOptionCache(String key){
		prefCache.clearPrefOptionCache(key);
	}
	
	//////////////////// Pref Texts
	public void getPrefTexts(Request request, Response response) {
		StringBuilder key = new StringBuilder();
		key.append(request.getParam(PREFTEXTNAME));
		key.append("_");
		key.append((String)request.getParam(GlobalConstant.LANG));
		if (prefCache.getPrefTexts() != null && prefCache.getPrefTexts().containsKey(key.toString())){
			// Pull from memory cache
			prefTextLoadFromMem(request,response,key.toString());
		} else {
			synchronized (this) {
				// this is done to catch all concurrent users during a cache reload to prevent then from all trying to reloading the cache
				// only the first shall do the reload.
				if (prefCache.getPrefTexts() != null && prefCache.getPrefTexts().containsKey(key.toString())){
					// Pull from memory cache
					prefTextLoadFromMem(request,response,key.toString());
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void prefTextLoadFromMem(Request request, Response response, String key) {
		// Pull from memory cache
		Map<String,Map<String,PrefTextValue>> t = null;
		if (request.containsParam(PREFPARAMLOC) && RESPONSE.equals(request.getParam(PREFPARAMLOC)) ) {
			if (response.getParams().containsKey(PREFTEXTS)){
				t = (Map<String, Map<String,PrefTextValue>>) response.getParam(PREFTEXTS);
			} else {
				t = new ConcurrentHashMap<String,Map<String,PrefTextValue>>();
			}
			t.put((String) request.getParam(PREFTEXTNAME), prefCache.getPrefTexts().get(key));
			response.addParam(PREFTEXTS, t);
		} else {
			if (request.getParams().containsKey(PREFTEXTS)){
				t = (Map<String, Map<String,PrefTextValue>>) request.getParam(PREFTEXTS);
			} else {
				t = new ConcurrentHashMap<String,Map<String,PrefTextValue>>();
			}
			t.put((String) request.getParam(PREFTEXTNAME), prefCache.getPrefTexts().get(key));
			request.addParam(PREFTEXTS,t);
		}
	}
	

	
	public void clearPrefTextCache(){
		// Clear cache immediately
		prefCache.clearPrefTextCache();
	}

	public void clearPrefTextCache(String key){
		// Clear one item in cache immediately
		prefCache.clearPrefTextCache(key);
	}

	//////////////////////// Language 
	@SuppressWarnings("unchecked")
	public void getLanguages(Request request, Response response) {
		String key = "local";
		
		if (prefCache.getLanguages() != null) {
			if (request.containsParam(PREFPARAMLOC) && RESPONSE.equals(request.getParam(PREFPARAMLOC)) ) {
				// Pull from memory cache
				response.addParam(LANGUAGES, prefCache.getLanguages());
			} else {
				// Pull from memory cache
				request.addParam(LANGUAGES, prefCache.getLanguages());
			}
			
		} else {
			synchronized (this) {
				// this is done to catch all concurrent users during a cache reload to prevent then from all trying to reloading the cache
				// only the first shall do the reload.
				if (prefCache.getLanguages() != null) {
					if (request.containsParam(PREFPARAMLOC) && RESPONSE.equals(request.getParam(PREFPARAMLOC)) ) {
						// Pull from memory cache
						response.addParam(LANGUAGES, prefCache.getLanguages());
					} else {
						// Pull from memory cache
						request.addParam(LANGUAGES, prefCache.getLanguages());
					}
				} else {
					utilSvc.addStatus(Response.INFO, Response.PAGEOPTIONS, "Languages issue", response);
				}
			}
		}
	}

	public void loadGlobalCache() {
		String lang = "en";
		
		List<String> texts =  new ArrayList<String>(Arrays.asList("GLOBAL_PAGE"));
		
		
		
	}
	
	public void setLanguages(List<Language> languages) {
		prefCache.setLanguages(languages);
	}
	
	public String getDefaultLang(){
		String lang = "en";
		/*List<Language> languages = prefCache.getLanguages().get(key);
		if (languages != null && !languages.isEmpty()) {
			for (Language language : languages) {
				if (language.isDefaultLang()){
					lang = language.getCode();
				}
			}
		}*/
		return lang;
	}
	
	public List<String> getAvailableLanguageCodes(String tenant) {
		List<String> codes = new ArrayList<String>();
		List<Language> languages = prefCache.getLanguages();
		if (languages != null && !languages.isEmpty()) {
			for (Language language : languages) {
				codes.add(language.getCode());
			}
		} else {
			codes.add("en");
		}
		
		return codes;
	}
	
	@SuppressWarnings("unchecked")
	public void loadLanguageCache(String fileName) {
		try {
			ClassPathResource res = new ClassPathResource(fileName);  
			Gson gson = new Gson();
			Type userListType = new TypeToken<ArrayList<Language>>(){}.getType();
			ArrayList<Language> languages = gson.fromJson(new InputStreamReader(res.getInputStream()), userListType); 
			prefCache.setLanguages(languages);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void clearLanguageCache(){
		// Clear cache immediately
		prefCache.clearLanguageCache();
	}
	
	public String getLang(Request request) {
		if (request.containsParam(GlobalConstant.LANG)) {
			return (String) request.getParam(GlobalConstant.LANG);
		} else {
			return getDefaultLang();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static PrefOptionValue getPrefOption(Request request, String pageName, String valueName) {
		PrefOptionValue optionValue = null;
		if (request.containsParam(PREFOPTIONS)) {
			Map<String,Object> items = (Map<String,Object>)request.getParam(PREFOPTIONS);
			if (items != null) {
				Map<String,Object> item = (Map<String,Object>)(items).get(pageName);
				if (item != null){
					optionValue = (PrefOptionValue) (item).get(valueName);
				}
			}
		}
		return optionValue;
	}
	
	@SuppressWarnings("unchecked")
	public static String getPrefText(Request request, String pageName, String valueName) {
		String textValue = "";
		if (request.containsParam(PREFTEXTS)) {
			Map<String,Object> items = (Map<String,Object>)request.getParam(PREFTEXTS);
			if (items != null) {
				Map<String,Object> item = (Map<String,Object>)(items).get(pageName);
				if (item != null){
					PrefTextValue v = (PrefTextValue) (item).get(valueName);
					textValue = v.getValue();
				}
			}
		}
		return textValue;
	}
	
	
	public String getPrefText(String pageName, String valueName, String lang) {
		String result = "";
		try {
			StringBuilder key = new StringBuilder();
			key.append(pageName);
			key.append("_");
			key.append(lang);
			if (prefCache.getPrefTexts() != null && prefCache.getPrefTexts().containsKey(key.toString())){
				Map<String,PrefTextValue> prefTextValues = prefCache.getPrefTexts().get(key.toString());
				result = prefTextValues.get(valueName).getValue();
			} else {
				synchronized (this) {
					if (prefCache.getPrefTexts() != null && prefCache.getPrefTexts().containsKey(key.toString())){
						Map<String,PrefTextValue> prefTextValues = prefCache.getPrefTexts().get(key.toString());
						result = prefTextValues.get(valueName).getValue();
					} else {
						//Map<String,PrefTextValue> prefTextValues = prefSvc.getTextsMap(pageName, lang);
						//prefCache.addPrefText(key.toString(), prefTextValues);
						//result = prefTextValues.get(valueName).getValue();
					}
				}
			}
			
		} catch (Exception e) {
			// eat error 
			StringBuilder r = new StringBuilder();
			r.append("Global text option for ").append(pageName).append(" ").append(valueName).append(" missing contact admin");
			result = r.toString();
		}
		return result;
	}
	
	
	public PrefOptionValue getPrefOption(String pageName, String valueName, String lang) {
		PrefOptionValue result = null;
		try {
			StringBuilder key = new StringBuilder();
			key.append(pageName);
			key.append("_");
			key.append(lang);
			if (prefCache.getPrefOptions() != null && prefCache.getPrefOptions().containsKey(key.toString())){
				Map<String,PrefOptionValue> prefOptionValues = prefCache.getPrefOptions().get(key.toString());
				
				result = prefOptionValues.get(valueName);
			}
		} catch (Exception e) {
			// eat error
			result = null;
		}
		return result;
	}
	
	// Add 
	@SuppressWarnings("unchecked")
	public static void addPrefForm(Request request, String... pageName) {
		if (!request.containsParam(PREFFORMKEYS)) {
			List<String> prefForms = new ArrayList<String>();
			request.addParam(PREFFORMKEYS, prefForms);
		}
		for (String item : pageName) {
			((List<String>) request.getParam(PREFFORMKEYS)).add(item);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void addPrefText(Request request, String... pageName) {
		if (!request.containsParam(PREFTEXTKEYS)) {
			List<String> prefTexts = new ArrayList<String>();
			request.addParam(PREFTEXTKEYS, prefTexts);
		}
		for (String item : pageName) {
			((List<String>) request.getParam(PREFTEXTKEYS)).add(item);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void addPrefOption(Request request, String... pageName) {
		if (!request.containsParam(PREFOPTIONKEYS)) {
			List<String> prefOptions = new ArrayList<String>();
			request.addParam(PREFOPTIONKEYS, prefOptions);
		}
		for (String item : pageName) {
			((List<String>) request.getParam(PREFOPTIONKEYS)).add(item);
		}
	}
	
	
}

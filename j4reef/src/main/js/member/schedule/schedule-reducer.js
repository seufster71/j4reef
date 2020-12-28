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
import reducerUtils from '../../core/common/reducer-utils';

export default function scheduleReducer(state = {}, action) {
	let myState = {};
	switch(action.type) {
		case 'LOAD_INIT_SCHEDULE': {
			if (action.responseJson != null && action.responseJson.params != null) {
				return Object.assign({}, state, {
					prefTexts: Object.assign({}, state.prefTexts, reducerUtils.getPrefTexts(action)),
					prefLabels: Object.assign({}, state.prefLabels, reducerUtils.getPrefLabels(action)),
					prefOptions: Object.assign({}, state.prefOptions, reducerUtils.getPrefOptions(action)),
					columns: reducerUtils.getColumns(action),
					itemCount: reducerUtils.getItemCount(action),
					items: reducerUtils.getItems(action),
					listLimit: reducerUtils.getListLimit(action),
					listStart: reducerUtils.getListStart(action),
					orderCriteria: [{'orderColumn':'SCHEDULE_TABLE_NAME','orderDir':'ASC'}],
    				searchCriteria: [{'searchValue':'','searchColumn':'SCHEDULE_TABLE_NAME'}],
    				paginationSegment: 1,
    				selected: null,
					isModifyOpen: false,
					pageName:"SCHEDULE",
					isDeleteModalOpen: false,
					errors:null,
					warns:null, 
					successes:null,
					searchValue:""
				});
			} else {
				return state;
			}
		}
		case 'LOAD_LIST_SCHEDULE': {
			if (action.responseJson != null && action.responseJson.params != null) {
				return Object.assign({}, state, {
					itemCount: reducerUtils.getItemCount(action),
					items: reducerUtils.getItems(action),
					listLimit: reducerUtils.getListLimit(action),
					listStart: reducerUtils.getListStart(action),
					selected: null,
					isModifyOpen: false,
					isDeleteModalOpen: false,
					errors:null,
					warns:null, 
					successes:null
				});
			} else {
				return state;
			}
		}
		case 'SCHEDULE_ITEM': {
			if (action.responseJson !=  null && action.responseJson.params != null) {
				// load inputFields
				let inputFields = {};
				let prefForms = reducerUtils.getPrefForms(action);
				for (let i = 0; i < prefForms.SCHEDULE_FORM.length; i++) {
					if (prefForms.SCHEDULE_FORM[i].group === "FORM1") {
						let classModel = JSON.parse(prefForms.SCHEDULE_FORM[i].classModel);
						if (action.responseJson.params.item != null && action.responseJson.params.item[classModel.field] != null) {
							inputFields[prefForms.SCHEDULE_FORM[i].name] = action.responseJson.params.item[classModel.field];
						} else {
							let result = "";
							if (prefForms.SCHEDULE_FORM[i].value != null && prefForms.SCHEDULE_FORM[i].value != ""){
								let formValue = JSON.parse(prefForms.SCHEDULE_FORM[i].value);
								if (formValue.options != null) {
									for (let j = 0; j < formValue.options.length; j++) {
										if (formValue.options[j] != null && formValue.options[j].defaultInd == true){
											result = formValue.options[j].value;
										}
									}
								} else if (formValue.referPref != null) {
									let pref = action.appPrefs.prefTexts[formValue.referPref.prefName][formValue.referPref.prefItem];
									if (pref != null && pref.value != null && pref.value != "") {
										let value = JSON.parse(pref.value);
										if (value.options != null) {
											for (let j = 0; j < value.options.length; j++) {
												if (value.options[j] != null && value.options[j].defaultInd == true){
													result = value.options[j].value;
												}
											}
										}
									}
								}
							} 
							inputFields[prefForms.SCHEDULE_FORM[i].name] = result;
						}
					}
				}
				// add id if this is existing item
				if (action.responseJson.params.item != null) {
					inputFields.itemId = action.responseJson.params.item.id;
				}
				return Object.assign({}, state, {
					prefForms: Object.assign({}, state.prefForms, reducerUtils.getPrefForms(action)),
					selected : action.responseJson.params.item,
					inputFields : inputFields,
					isModifyOpen: true
				});
			} else {
				return state;
			}
		}
		case 'SCHEDULE_INPUT_CHANGE': {
			return reducerUtils.updateInputChange(state,action);
		}
		case 'SCHEDULE_CLEAR_FIELD': {
			return reducerUtils.updateClearField(state,action);
		}
		case 'SCHEDULE_LISTLIMIT': {
			return reducerUtils.updateListLimit(state,action);
		}
		case 'SCHEDULE_SEARCH': { 
			return reducerUtils.updateSearch(state,action);
		}
		case 'SCHEDULE_SEARCH_CHANGE': { 
			
		}
		case 'SCHEDULE_ORDERBY': { 
			return reducerUtils.updateOrderBy(state,action);
		}
		case 'SCHEDULE_SELECT_CHANGE': {
			if (action.params != null) {
				let inputFields = Object.assign({}, state.inputFields);
				inputFields[action.params.field] = action.params.value;
				let clone = Object.assign({}, state);
				clone.inputFields = inputFields;
				return clone;
			} else {
		        return state;
		    }
		}
		case 'SCHEDULE_ADD_PARENT': {
			if (action.parent != null) {
				return Object.assign({}, state, {
					parent: action.parent,
					parentType: action.parentType
				});
			} else {
		        return state;
		    }
		}
		case 'SCHEDULE_CLEAR_PARENT': {
			return Object.assign({}, state, {
				parent: null,
				parentType: null
			});
		}
		case 'SCHEDULE_SET_ERRORS': {
			return Object.assign({}, state, {
				errors: action.errors
			});
		}
		case 'SCHEDULE_CLOSE_DELETE_MODAL': {
			return Object.assign({}, state, {
				isDeleteModalOpen: false
			});
		}
		case 'SCHEDULE_OPEN_DELETE_MODAL': {
			return Object.assign({}, state, {
				isDeleteModalOpen: true,
				selected: action.item
			});
		}
		default:
			return state;
	}
}



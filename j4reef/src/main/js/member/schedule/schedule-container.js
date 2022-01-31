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
'use-strict';
import React from 'react';
import {useSelector, useDispatch} from 'react-redux';
import * as scheduleActions from './schedule-actions';
import ScheduleView from './../../memberView/schedule/schedule-view.js'
import ScheduleModifyView from './../../memberView/schedule/schedule-modify-view.js'
import fuLogger from '../../core/common/fu-logger';
import utils from '../../core/common/utils';

// test
function ScheduleContainer() {
	const scheduleState = useSelector((state) => state.scheduleState);
	const appPrefs = useSelector((state) => state.appPrefs);
	const session = useSelector((state) => state.session);
	const dispatch = useDispatch();


	if (this.props.history.location.state != null && this.props.history.location.state.parent != null) {
		dispatch(scheduleActions.init({parent:this.props.history.location.state.parent,parentType:this.props.history.location.state.parentType}));
	} else {
		dispatch(scheduleActions.init({}));
	}
	
	function onListLimitChange(fieldName, event) {
		let value = 20;
		if (this.props.codeType === 'NATIVE') {
			value = event.nativeEvent.text;
		} else {
			value = event.target.value;
		}

		let listLimit = parseInt(value);
		dispatch(scheduleActions.listLimit({state:scheduleState,listLimit}));
	}

	function onPaginationClick(value) {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onPaginationClick',msg:"fieldName "+ value});
		let listStart = scheduleState.listStart;
		let paginationSegment = 1;
		let oldValue = 1;
		if (scheduleState.paginationSegment != ""){
			oldValue = scheduleState.paginationSegment;
		}
		if (value === "prev") {
			paginationSegment = oldValue - 1;
		} else if (value === "next") {
			paginationSegment = oldValue + 1;
		} else {
			paginationSegment = value;
		}
		listStart = ((paginationSegment - 1) * scheduleState.listLimit);
		
		dispatch(scheduleActions.list({state:scheduleState,listStart,paginationSegment}));
	}

	function onSearchChange(fieldName, event) {
		if (event.type === 'keypress') {
			if (event.key === 'Enter') {
				this.onSearchClick(fieldName,event);
			}
		} else {
			if (this.props.codeType === 'NATIVE') {
				dispatch(scheduleActions.searchChange({[fieldName]:event.nativeEvent.text}));
			} else {
				dispatch(scheduleActions.searchChange({[fieldName]:event.target.value}));
			}
		}
	}

	function onSearchClick(fieldName, event) {
		let searchCriteria = [];
		if (fieldName === 'SCHEDULE-SEARCHBY') {
			if (event != null) {
				for (let o = 0; o < event.length; o++) {
					let option = {};
					option.searchValue = scheduleState.searchValue;
					option.searchColumn = event[o].value;
					searchCriteria.push(option);
				}
			}
		} else {
			for (let i = 0; i < scheduleState.searchCriteria.length; i++) {
				let option = {};
				option.searchValue = scheduleState.searchValue;
				option.searchColumn = scheduleState.searchCriteria[i].searchColumn;
				searchCriteria.push(option);
			}
		}

		dispatch(scheduleActions.search({state:scheduleState,searchCriteria}));
	}

	function onOrderBy(selectedOption, event) {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onOrderBy',msg:"id " + selectedOption});
		let orderCriteria = [];
		if (event != null) {
			for (let o = 0; o < event.length; o++) {
				let option = {};
				if (event[o].label.includes("ASC")) {
					option.orderColumn = event[o].value;
					option.orderDir = "ASC";
				} else if (event[o].label.includes("DESC")){
					option.orderColumn = event[o].value;
					option.orderDir = "DESC";
				} else {
					option.orderColumn = event[o].value;
				}
				orderCriteria.push(option);
			}
		} else {
			let option = {orderColumn:"SCHEDULE_TABLE_NAME",orderDir:"ASC"};
			orderCriteria.push(option);
		}
		dispatch(scheduleActions.orderBy({state:scheduleState,orderCriteria}));
	}
	
	function onSave() {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onSave',msg:"test"});
		let errors = utils.validateFormFields(scheduleState.prefForms.SCHEDULE_FORM,scheduleState.inputFields);
		
		if (errors.isValid){
			dispatch(scheduleActions.saveItem({state:scheduleState}));
		} else {
			dispatch(scheduleActions.setErrors({errors:errors.errorMap}));
		}
	}
	
	function onModify(item) {
		let id = null;
		if (item != null && item.id != null) {
			id = item.id;
		}
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onModify',msg:"test"+id});
		dispatch(scheduleActions.modifyItem({id,parentId:scheduleState.parent.id,appPrefs:appPrefs}));
	}
	
	function onDelete(item) {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onDelete',msg:"test"});
		if (item != null && item.id != "") {
			dispatch(scheduleActions.deleteItem({state:scheduleState,id:item.id}));
		}
	}
	
	function openDeleteModal(item) {
		dispatch(scheduleActions.openDeleteModal({item}));
	}
	
	function onOption(code, item) {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onOption',msg:" code "+code});
		switch(code) {
			case 'MODIFY': {
				this.onModify(item);
				break;
			}
			case 'DELETE': {
				this.onDelete(item);
				break;
			}
		}
	}
	
	function closeModal() {
		dispatch(scheduleActions.closeDeleteModal());
	}
	
	function onCancel() {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onCancel',msg:"test"});
		dispatch(scheduleActions.list({state:scheduleState}));
	}
	
	function inputChange(type,field,value,event) {
		utils.inputChange({type,props:this.props,field,value,event});
	}

	function goBack() {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::goBack',msg:"test"});
		this.props.history.goBack();
	}

	fuLogger.log({level:'TRACE',loc:'ScheduleContainer::render',msg:"Hi there"});
	if (scheduleState.isModifyOpen) {
		return (
			<ScheduleModifyView
			itemState={scheduleState}
			appPrefs={appPrefs}
			onSave={onSave}
			onCancel={onCancel}
			onReturn={onCancel}
			inputChange={inputChange}
			onBlur={onBlur}/>
		);
	} else if (scheduleState.items != null) {
		return (
			<ScheduleView
			itemState={scheduleState}
			appPrefs={appPrefs}
			closeModal={closeModal}
			onOption={onOption}
			inputChange={inputChange}
			goBack={goBack}
			session={session}
			/>
		);
	} else {
		return (<div> Loading... </div>);
	}
}

export default ScheduleContainer;
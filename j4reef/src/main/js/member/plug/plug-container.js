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
import React, { useEffect } from 'react';
import { useLocation } from "react-router-dom";
import {useSelector, useDispatch} from 'react-redux';
import * as plugActions from './plug-actions';
import PlugView from './../../memberView/plug/plug-view.js'
import PlugModifyView from './../../memberView/plug/plug-modify-view.js'
import fuLogger from '../../core/common/fu-logger';
import utils from '../../core/common/utils';

// test
function PlugContainer() {
	const plugState = useSelector((state) => state.plugState);
	const appPrefs = useSelector((state) => state.appPrefs);
	const session = useSelector((state) => state.session);
	const dispatch = useDispatch();
	const location = useLocation();

	
	useEffect(() => {
		if (location.state != null && location.state.parent != null) {
			dispatch(plugActions.init({parent:location.state.parent,parentType:location.state.parentType}));
		} else {
			dispatch(plugActions.init({}));
		}
	}, []);
	
	
	const onListLimitChange = (fieldName, event) => {
		let value = 20;
		if (this.props.codeType === 'NATIVE') {
			value = event.nativeEvent.text;
		} else {
			value = event.target.value;
		}

		let listLimit = parseInt(value);
		dispatch(plugActions.listLimit({state:plugState,listLimit}));
	}

	const onPaginationClick = (value) => {
		fuLogger.log({level:'TRACE',loc:'PlugContainer::onPaginationClick',msg:"fieldName "+ value});
		let listStart = plugState.listStart;
		let paginationSegment = 1;
		let oldValue = 1;
		if (plugState.paginationSegment != ""){
			oldValue = plugState.paginationSegment;
		}
		if (value === "prev") {
			paginationSegment = oldValue - 1;
		} else if (value === "next") {
			paginationSegment = oldValue + 1;
		} else {
			paginationSegment = value;
		}
		listStart = ((paginationSegment - 1) * plugState.listLimit);
		
		dispatch(plugActions.list({state:plugState,listStart,paginationSegment}));
	}

	const onSearchChange = (fieldName, event) => {
		if (event.type === 'keypress') {
			if (event.key === 'Enter') {
				this.onSearchClick(fieldName,event);
			}
		} else {
			if (this.props.codeType === 'NATIVE') {
				dispatch(plugActions.searchChange({[fieldName]:event.nativeEvent.text}));
			} else {
				dispatch(plugActions.searchChange({[fieldName]:event.target.value}));
			}
		}
	}

	const onSearchClick = (fieldName, event) => {
		let searchCriteria = [];
		if (fieldName === 'PLUG-SEARCHBY') {
			if (event != null) {
				for (let o = 0; o < event.length; o++) {
					let option = {};
					option.searchValue = plugState.searchValue;
					option.searchColumn = event[o].value;
					searchCriteria.push(option);
				}
			}
		} else {
			for (let i = 0; i < plugState.searchCriteria.length; i++) {
				let option = {};
				option.searchValue = plugState.searchValue;
				option.searchColumn = plugState.searchCriteria[i].searchColumn;
				searchCriteria.push(option);
			}
		}

		dispatch(plugActions.search({state:this.props.plugState,searchCriteria}));
	}

	const onOrderBy = (selectedOption, event) => {
		fuLogger.log({level:'TRACE',loc:'PlugContainer::onOrderBy',msg:"id " + selectedOption});
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
			let option = {orderColumn:"PLUG_TABLE_NAME",orderDir:"ASC"};
			orderCriteria.push(option);
		}
		dispatch(plugActions.orderBy({state:plugState,orderCriteria}));
	}
	
	const onSave = () => {
		fuLogger.log({level:'TRACE',loc:'PlugContainer::onSave',msg:"test"});
		let errors = utils.validateFormFields(plugState.prefForms.PLUG_FORM,plugState.inputFields);
		
		if (errors.isValid){
			dispatch(plugActions.saveItem({state:plugState}));
		} else {
			dispatch(plugActions.setErrors({errors:errors.errorMap}));
		}
	}
	
	const onModify = (item) => {
		let id = null;
		if (item != null && item.id != null) {
			id = item.id;
		}
		fuLogger.log({level:'TRACE',loc:'PlugContainer::onModify',msg:"test"+id});
		dispatch(plugActions.modifyItem({id,appPrefs:appPrefs}));
	}
	
	const onDelete = (item) => {
		fuLogger.log({level:'TRACE',loc:'PlugContainer::onDelete',msg:"test"});
		if (item != null && item.id != "") {
			dispatch(plugActions.deleteItem({state:plugState,id:item.id}));
		}
	}
	
	const openDeleteModal = (item) => {
		dispatch(plugActions.openDeleteModal({item}));
	}
	
	const onOption = (code, item) => {
		fuLogger.log({level:'TRACE',loc:'PlugContainer::onOption',msg:" code "+code});
		switch(code) {
			case 'MODIFY': {
				onModify(item);
				break;
			}
			case 'SCHEDULE': {
				this.props.history.push({pathname:'/member-schedule',state:{parent:item,parentType:"PLUG"}});
				break;
			}
		}
	}
	
	const closeModal = () => {
		dispatch(plugActions.closeDeleteModal());
	}
	
	const onCancel = () => {
		fuLogger.log({level:'TRACE',loc:'PlugContainer::onCancel',msg:"test"});
		dispatch(plugActions.list({state:plugState}));
	}
	
	const inputChange = (type,field,value,event) => {
		utils.inputChange({type,props:this.props,field,value,event});
	}

	const goBack = () => {
		fuLogger.log({level:'TRACE',loc:'PlugContainer::goBack',msg:"test"});
		this.props.history.goBack();
	}
 

	fuLogger.log({level:'TRACE',loc:'PlugContainer::render',msg:"Hi there"});
	if (plugState.isModifyOpen) {
		return (
			<PlugModifyView
			itemState={plugState}
			appPrefs={appPrefs}
			onSave={onSave}
			onCancel={onCancel}
			onReturn={onCancel}
			inputChange={inputChange}/>
		);
	} else if (plugState.items != null) {
		return (
			<PlugView
			itemState={plugState}
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

export default PlugContainer;
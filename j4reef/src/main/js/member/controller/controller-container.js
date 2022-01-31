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
import { useNavigate } from "react-router-dom";
import {useSelector, useDispatch} from 'react-redux';
import * as controllerActions from './controller-actions';
import ControllerView from './../../memberView/controller/controller-view.js'
import ControllerModifyView from './../../memberView/controller/controller-modify-view.js'
import fuLogger from '../../core/common/fu-logger';

function ControllerContainer() {
	const controllerState = useSelector((state) => state.controllerState);
	const session = useSelector((state) => state.session);
	const appPrefs = useSelector((state) => state.appPrefs);
	const dispatch = useDispatch();
	const navigate = useNavigate();
	
	useEffect(() => {
		dispatch(controllerActions.init({}));
  	}, []);
	
	const onListLimitChange = (fieldName, event) => {
		let value = 20;
		if (this.props.codeType === 'NATIVE') {
			value = event.nativeEvent.text;
		} else {
			value = event.target.value;
		}

		let listLimit = parseInt(value);
		dispatch(controllerActions.listLimit({state:controllerState,listLimit}));
	}

	const onPaginationClick = (value) => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onPaginationClick',msg:"fieldName "+ value});
		let listStart = controllerState.listStart;
		let paginationSegment = 1;
		let oldValue = 1;
		if (controllerState.paginationSegment != ""){
			oldValue = controllerState.paginationSegment;
		}
		if (value === "prev") {
			paginationSegment = oldValue - 1;
		} else if (value === "next") {
			paginationSegment = oldValue + 1;
		} else {
			paginationSegment = value;
		}
		listStart = ((paginationSegment - 1) * controllerState.listLimit);
		
		dispatch(controllerActions.list({state:controllerState,listStart,paginationSegment}));
	}

	const onSearchChange = (fieldName, event) => {
		if (event.type === 'keypress') {
			if (event.key === 'Enter') {
				this.onSearchClick(fieldName,event);
			}
		} else {
			if (this.props.codeType === 'NATIVE') {
				dispatch(controllerActions.searchChange({[fieldName]:event.nativeEvent.text}));
			} else {
				dispatch(controllerActions.searchChange({[fieldName]:event.target.value}));
			}
		}
	}

	const onSearchClick = (fieldName, event) => {
		let searchCriteria = [];
		if (fieldName === 'CONTROLLER-SEARCHBY') {
			if (event != null) {
				for (let o = 0; o < event.length; o++) {
					let option = {};
					option.searchValue = controllerState.searchValue;
					option.searchColumn = event[o].value;
					searchCriteria.push(option);
				}
			}
		} else {
			for (let i = 0; i < this.props.controllerState.searchCriteria.length; i++) {
				let option = {};
				option.searchValue = controllerState.searchValue;
				option.searchColumn = controllerState.searchCriteria[i].searchColumn;
				searchCriteria.push(option);
			}
		}

		dispatch(controllerActions.search({state:controllerState,searchCriteria}));
	}

	const onOrderBy = (selectedOption, event) => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onOrderBy',msg:"id " + selectedOption});
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
			let option = {orderColumn:"CONTROLLER_TABLE_NAME",orderDir:"ASC"};
			orderCriteria.push(option);
		}
		dispatch(controllerActions.orderBy({state:controllerState,orderCriteria}));
	}
	
	const onSave = () => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onSave',msg:"test"});
		let errors = utils.validateFormFields(controllerState.prefForms.CONTROLLER_FORM,controllerState.inputFields);
		
		if (errors.isValid){
			dispatch(controllerActions.saveItem({state:controllerState}));
		} else {
			dispatch(controllerActions.setErrors({errors:errors.errorMap}));
		}
	}
	
	const onModify = (item) => {
		let id = null;
		if (item != null && item.id != null) {
			id = item.id;
		}
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onModify',msg:"test"+id});
		dispatch(controllerActions.modifyItem({id,appPrefs:appPrefs}));
	}
	
	const onDelete = (item) => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onDelete',msg:"test"});
		if (item != null && item.id != "") {
			dispatch(controllerActions.deleteItem({state:controllerState,id:item.id}));
		}
	}
	
	const openDeleteModal = (item) => {
		dispatch(controllerActions.openDeleteModal({item}));
	}
	
	const onOption = (code, item) => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onOption',msg:" code "+code});
		switch(code) {
			case 'MODIFY': {
				this.onModify(item);
				break;
			}
			case 'PLUGS': {
				navigate('/member-plug',{state:{parent:item,parentType:"CONTROLLER"}});
				// this.props.history.push({pathname:'/member-plug',state:{parent:item,parentType:"CONTROLLER"}});
				break;
			}
		}
	}
	
	const closeModal = () => {
		dispatch(controllerActions.closeDeleteModal());
	}
	
	const onCancel = () => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onCancel',msg:"test"});
		dispatch(controllerActions.list({state:controllerState}));
	}
	
	const inputChange = (type,field,value,event) => {
		utils.inputChange({type,props:this.props,field,value,event});
	}

	const goBack = () => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::goBack',msg:"test"});
		this.props.history.goBack();
	}

	fuLogger.log({level:'TRACE',loc:'ControllerContainer::render',msg:"Hi there"});
	if (controllerState.isModifyOpen) {
		return (
			<ControllerModifyView
			itemState={controllerState}
			appPrefs={appPrefs}
			onSave={onSave}
			onCancel={onCancel}
			onReturn={onCancel}
			inputChange={inputChange}
			onBlur={onBlur}/>
		);
	} else if (controllerState.items != null) {
		return (
			<ControllerView
			itemState={controllerState}
			appPrefs={appPrefs}
			closeModal={closeModal}
			onOption={onOption}
			onOrderBy={onOrderBy}
			inputChange={inputChange}
			goBack={goBack}
			session={session}
			/>
		);
	} else {
		return (<div> Loading... </div>);
	}
}

export default ControllerContainer;
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
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as controllerActions from './controller-actions';
import ControllerView from './../../memberView/controller/controller-view.js'
import ControllerModifyView from './../../memberView/controller/controller-modify-view.js'
import fuLogger from '../../core/common/fu-logger';

// test
class ControllerContainer extends Component {
	constructor(props) {
		super(props);
	}

	componentDidMount() {
		if (this.props.history.location.state != null && this.props.history.location.state.parent != null) {
			this.props.actions.init({parent:this.props.history.location.state.parent,parentType:this.props.history.location.state.parentType});
		} else {
			this.props.actions.init({});
		}
	}
	
	onListLimitChange = (fieldName, event) => {
		let value = 20;
		if (this.props.codeType === 'NATIVE') {
			value = event.nativeEvent.text;
		} else {
			value = event.target.value;
		}

		let listLimit = parseInt(value);
		this.props.actions.listLimit({state:this.props.controllerState,listLimit});
	}

	onPaginationClick = (value) => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onPaginationClick',msg:"fieldName "+ value});
		let listStart = this.props.controllerState.listStart;
		let paginationSegment = 1;
		let oldValue = 1;
		if (this.props.controllerState.paginationSegment != ""){
			oldValue = this.props.controllerState.paginationSegment;
		}
		if (value === "prev") {
			paginationSegment = oldValue - 1;
		} else if (value === "next") {
			paginationSegment = oldValue + 1;
		} else {
			paginationSegment = value;
		}
		listStart = ((paginationSegment - 1) * this.props.controllerState.listLimit);
		
		this.props.actions.list({state:this.props.controllerState,listStart,paginationSegment});
	}

	onSearchChange = (fieldName, event) => {
		if (event.type === 'keypress') {
			if (event.key === 'Enter') {
				this.onSearchClick(fieldName,event);
			}
		} else {
			if (this.props.codeType === 'NATIVE') {
				this.props.actions.searchChange({[fieldName]:event.nativeEvent.text});
			} else {
				this.props.actions.searchChange({[fieldName]:event.target.value});
			}
		}
	}

	onSearchClick = (fieldName, event) => {
		let searchCriteria = [];
		if (fieldName === 'CONTROLLER-SEARCHBY') {
			if (event != null) {
				for (let o = 0; o < event.length; o++) {
					let option = {};
					option.searchValue = this.props.controllerState.searchValue;
					option.searchColumn = event[o].value;
					searchCriteria.push(option);
				}
			}
		} else {
			for (let i = 0; i < this.props.controllerState.searchCriteria.length; i++) {
				let option = {};
				option.searchValue = this.props.controllerState.searchValue;
				option.searchColumn = this.props.controllerState.searchCriteria[i].searchColumn;
				searchCriteria.push(option);
			}
		}

		this.props.actions.search({state:this.props.controllerState,searchCriteria});
	}

	onOrderBy = (selectedOption, event) => {
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
		this.props.actions.orderBy({state:this.props.controllerState,orderCriteria});
	}
	
	onSave = () => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onSave',msg:"test"});
		let errors = utils.validateFormFields(this.props.controllerState.prefForms.CONTROLLER_FORM,this.props.controllerState.inputFields);
		
		if (errors.isValid){
			this.props.actions.saveItem({state:this.props.controllerState});
		} else {
			this.props.actions.setErrors({errors:errors.errorMap});
		}
	}
	
	onModify = (item) => {
		let id = null;
		if (item != null && item.id != null) {
			id = item.id;
		}
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onModify',msg:"test"+id});
		this.props.actions.modifyItem({id,appPrefs:this.props.appPrefs});
	}
	
	onDelete = (item) => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onDelete',msg:"test"});
		if (item != null && item.id != "") {
			this.props.actions.deleteItem({state:this.props.controllerState,id:item.id});
		}
	}
	
	openDeleteModal = (item) => {
		this.props.actions.openDeleteModal({item});
	}
	
	onOption = (code, item) => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onOption',msg:" code "+code});
		switch(code) {
			case 'MODIFY': {
				this.onModify(item);
				break;
			}
			case 'PLUGS': {
				this.props.history.push({pathname:'/member-plug',state:{parent:item,parentType:"CONTROLLER"}});
				break;
			}
		}
	}
	
	closeModal = () => {
		this.props.actions.closeDeleteModal();
	}
	
	onCancel = () => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::onCancel',msg:"test"});
		this.props.actions.list({state:this.props.controllerState});
	}
	
	inputChange = (type,field,value,event) => {
		utils.inputChange({type,props:this.props,field,value,event});
	}

	goBack = () => {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::goBack',msg:"test"});
		this.props.history.goBack();
	}

	render() {
		fuLogger.log({level:'TRACE',loc:'ControllerContainer::render',msg:"Hi there"});
		if (this.props.controllerState.isModifyOpen) {
			return (
				<ControllerModifyView
				itemState={this.props.controllerState}
				appPrefs={this.props.appPrefs}
				onSave={this.onSave}
				onCancel={this.onCancel}
				onReturn={this.onCancel}
				inputChange={this.inputChange}
				onBlur={this.onBlur}/>
			);
		} else if (this.props.controllerState.items != null) {
			return (
				<ControllerView
				itemState={this.props.controllerState}
				appPrefs={this.props.appPrefs}
				closeModal={this.closeModal}
				onOption={this.onOption}
				inputChange={this.inputChange}
				goBack={this.goBack}
				session={this.props.session}
				/>
			);
		} else {
			return (<div> Loading... </div>);
		}
	}
}

ControllerContainer.propTypes = {
		appPrefs: PropTypes.object,
		actions: PropTypes.object,
		controllerState: PropTypes.object,
		session: PropTypes.object
	};

function mapStateToProps(state, ownProps) {
	return { appPrefs:state.appPrefs, controllerState:state.controllerState, session:state.session };
}

function mapDispatchToProps(dispatch) {
	return { actions:bindActionCreators(controllerActions,dispatch) };
}
export default connect(mapStateToProps,mapDispatchToProps)(ControllerContainer);
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
import * as scheduleActions from './schedule-actions';
import ScheduleView from './../../memberView/schedule/schedule-view.js'
import ScheduleModifyView from './../../memberView/schedule/schedule-modify-view.js'
import fuLogger from '../../core/common/fu-logger';
import utils from '../../core/common/utils';

// test
class ScheduleContainer extends Component {
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
		this.props.actions.listLimit({state:this.props.scheduleState,listLimit});
	}

	onPaginationClick = (value) => {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onPaginationClick',msg:"fieldName "+ value});
		let listStart = this.props.scheduleState.listStart;
		let paginationSegment = 1;
		let oldValue = 1;
		if (this.props.scheduleState.paginationSegment != ""){
			oldValue = this.props.scheduleState.paginationSegment;
		}
		if (value === "prev") {
			paginationSegment = oldValue - 1;
		} else if (value === "next") {
			paginationSegment = oldValue + 1;
		} else {
			paginationSegment = value;
		}
		listStart = ((paginationSegment - 1) * this.props.scheduleState.listLimit);
		
		this.props.actions.list({state:this.props.scheduleState,listStart,paginationSegment});
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
		if (fieldName === 'SCHEDULE-SEARCHBY') {
			if (event != null) {
				for (let o = 0; o < event.length; o++) {
					let option = {};
					option.searchValue = this.props.scheduleState.searchValue;
					option.searchColumn = event[o].value;
					searchCriteria.push(option);
				}
			}
		} else {
			for (let i = 0; i < this.props.scheduleState.searchCriteria.length; i++) {
				let option = {};
				option.searchValue = this.props.scheduleState.searchValue;
				option.searchColumn = this.props.scheduleState.searchCriteria[i].searchColumn;
				searchCriteria.push(option);
			}
		}

		this.props.actions.search({state:this.props.scheduleState,searchCriteria});
	}

	onOrderBy = (selectedOption, event) => {
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
		this.props.actions.orderBy({state:this.props.scheduleState,orderCriteria});
	}
	
	onSave = () => {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onSave',msg:"test"});
		let errors = utils.validateFormFields(this.props.scheduleState.prefForms.SCHEDULE_FORM,this.props.scheduleState.inputFields);
		
		if (errors.isValid){
			this.props.actions.saveItem({state:this.props.scheduleState});
		} else {
			this.props.actions.setErrors({errors:errors.errorMap});
		}
	}
	
	onModify = (item) => {
		let id = null;
		if (item != null && item.id != null) {
			id = item.id;
		}
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onModify',msg:"test"+id});
		this.props.actions.modifyItem({id,parentId:this.props.scheduleState.parent.id,appPrefs:this.props.appPrefs});
	}
	
	onDelete = (item) => {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onDelete',msg:"test"});
		if (item != null && item.id != "") {
			this.props.actions.deleteItem({state:this.props.scheduleState,id:item.id});
		}
	}
	
	openDeleteModal = (item) => {
		this.props.actions.openDeleteModal({item});
	}
	
	onOption = (code, item) => {
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
	
	closeModal = () => {
		this.props.actions.closeDeleteModal();
	}
	
	onCancel = () => {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::onCancel',msg:"test"});
		this.props.actions.list({state:this.props.scheduleState});
	}
	
	inputChange = (type,field,value,event) => {
		utils.inputChange({type,props:this.props,field,value,event});
	}

	goBack = () => {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::goBack',msg:"test"});
		this.props.history.goBack();
	}

	render() {
		fuLogger.log({level:'TRACE',loc:'ScheduleContainer::render',msg:"Hi there"});
		if (this.props.scheduleState.isModifyOpen) {
			return (
				<ScheduleModifyView
				itemState={this.props.scheduleState}
				appPrefs={this.props.appPrefs}
				onSave={this.onSave}
				onCancel={this.onCancel}
				onReturn={this.onCancel}
				inputChange={this.inputChange}
				onBlur={this.onBlur}/>
			);
		} else if (this.props.scheduleState.items != null) {
			return (
				<ScheduleView
				itemState={this.props.scheduleState}
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

ScheduleContainer.propTypes = {
		appPrefs: PropTypes.object,
		actions: PropTypes.object,
		scheduleState: PropTypes.object.isRequired,
		session: PropTypes.object
	};

function mapStateToProps(state, ownProps) {
	return { appPrefs:state.appPrefs, scheduleState:state.scheduleState, session:state.session };
}

function mapDispatchToProps(dispatch) {
	return { actions:bindActionCreators(scheduleActions,dispatch) };
}
export default connect(mapStateToProps,mapDispatchToProps)(ScheduleContainer);
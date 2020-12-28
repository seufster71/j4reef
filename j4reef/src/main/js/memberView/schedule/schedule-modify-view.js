import React, {Component} from 'react';
import PropTypes from 'prop-types';
import FormBuilder from '../../coreView/common/form-builder';

export default function ScheduleModifyView({itemState, appPrefs, onSave, onCancel, inputChange, onBlur}) {

	let formName = "SCHEDULE_FORM";
	let formTitle = "Schedule";
	let formGroup = "FORM1";
    
    return (
    	<FormBuilder 
    		itemState={itemState} 
    		formName={formName} 
    		formTitle={formTitle} 
    		formGroup={formGroup} 
    		appPrefs={appPrefs} 
    		onSave={onSave}	
    		onCancel={onCancel} 
    		inputChange={inputChange}/>
    );
}


ScheduleModifyView.propTypes = {
  itemState: PropTypes.object.isRequired,
  appPrefs: PropTypes.object.isRequired,
  onSave: PropTypes.func,
  onCancel: PropTypes.func,
  inputChange: PropTypes.func,
  onBlur: PropTypes.func
};

import React, {Component} from 'react';
import PropTypes from 'prop-types';


export default function DashboardView({currentState, fields, texts, labels, onChangeLogin,
  onChangeRegistration, fieldChangeEvent, onForgotPassword,
  fieldBlurEvent, buttonClick, handleChange, changeView }) {


    return (
      <div>
        <div> Dashboard Page </div>
      </div>
    );
}


DashboardView.propTypes = {
  currentState: PropTypes.object,
	fields: PropTypes.object,
  texts: PropTypes.object,
  labels: PropTypes.object,
  onChangeLogin: PropTypes.func,
  onChangeRegistration: PropTypes.func,
  onForgotPassword: PropTypes.func,
  fieldChangeEvent: PropTypes.func,
  fieldBlurEvent: PropTypes.func,
  buttonClick: PropTypes.func,
  handleChange: PropTypes.func,
  changeView: PropTypes.func
};

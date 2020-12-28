/*
* Author Edward Seufert
*/
'use-strict';
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import { withRouter } from "react-router";
import {bindActionCreators} from 'redux';
import * as userManagementActions from '../../core/usermgnt/usermgnt-actions';
import fuLogger from '../../core/common/fu-logger';
import LogoutView from '../../memberView/logout/logout-view';

class LogoutContainer extends Component {
	constructor(props) {
		super(props);

	}

	componentDidMount() {
		fuLogger.log({level:'TRACE',loc:'LogoutContainer::mount',msg:"Logging out"});
		this.props.actions.logout();
	}

  render() {
			fuLogger.log({level:'TRACE',loc:'LogoutContainer::render',msg:"Logging out"});
      return (
				<LogoutView/>
			);
  }
}

LogoutContainer.propTypes = {
	appPrefs: PropTypes.object,
	lang: PropTypes.string,
	history: PropTypes.object,
	actions: PropTypes.object
};

function mapStateToProps(state, ownProps) {
  return {lang:state.lang, appPrefs:state.appPrefs};
}

function mapDispatchToProps(dispatch) {
  return { actions:bindActionCreators(userManagementActions,dispatch) };
}

export default withRouter(connect(mapStateToProps,mapDispatchToProps)(LogoutContainer));

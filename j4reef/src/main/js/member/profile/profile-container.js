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
'use-strict';
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as appPrefActions from '../../core/common/apppref-actions';
import fuLogger from '../../core/common/fu-logger';
import ProfileView from '../../memberView/profile/profile-view';

class ProfileContainer extends Component {
	constructor(props) {
		super(props);

	}

	componentDidMount() {
		//this.props.actions.initMember();
	}

	render() {
		fuLogger.log({level:'TRACE',loc:'ProfileContainer::render',msg:"Hi there"});
		return (
				<ProfileView
				
				/>
		);
	}
}

ProfileContainer.propTypes = {
	appPrefs: PropTypes.object,
	lang: PropTypes.string,
	actions: PropTypes.object
};

function mapStateToProps(state, ownProps) {
	return {lang:state.lang, appPrefs:state.appPrefs};
}

function mapDispatchToProps(dispatch) {
	return { actions:bindActionCreators(appPrefActions,dispatch) };
}

export default connect(mapStateToProps,mapDispatchToProps)(ProfileContainer);

/*
* Author Edward Seufert
*/
'use-strict';
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as appPrefActions from '../core/common/apppref-actions';
import fuLogger from '../core/common/fu-logger';
import PublicView from './public-view';
import NavigationView from "../coreView/navigation/navigation-view";
import StatusView from "../coreView/status/status-view";


function PublicContainer() {
	const appMenus = useSelector((state) => state.appMenus);
	const appPrefs = useSelector((state) => state.appPrefs);
	const dispatch = useDispatch();

	fuLogger.log({level:'TRACE',loc:'PublicContainer::render',msg:"Hi in public"});
	return (
  		<div>
  		<NavigationView appPrefs={appPrefs} activeTab={window.location.pathname}
        	menus={appMenus.PUBLIC_MENU_RIGHT}/>
        	<StatusView />
			<PublicView/>
			</div>
		);
}

export default PublicContainer;

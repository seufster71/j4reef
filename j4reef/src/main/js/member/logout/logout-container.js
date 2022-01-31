/*
* Author Edward Seufert
*/
'use-strict';
import React from 'react';
import {useSelector, useDispatch} from 'react-redux';
import * as userManagementActions from '../../core/usermgnt/usermgnt-actions';
import fuLogger from '../../core/common/fu-logger';
import LogoutView from '../../memberView/logout/logout-view';

function LogoutContainer() {
	const dispatch = useDispatch();
	
	fuLogger.log({level:'TRACE',loc:'LogoutContainer::mount',msg:"Logging out"});
	dispatch(userManagementActions.logout());
		
    return (
		<LogoutView/>
	);
}


export default LogoutContainer;

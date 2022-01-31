/*
* Author Edward Seufert
*/
'use strict';
import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {Routes, Route, Navigate} from "react-router-dom";
import * as memberActions from './member-actions';
import LoginContainer from '../core/usermgnt/login-container';
import StatusView from '../coreView/status/status-view';
import LoadingView from '../coreView/status/loading-view';
import NavigationView from '../coreView/navigation/navigation-view';
import ProfileContainer from './profile/profile-container';
import DashboardContainer from './dashboard/dashboard-container';
import ControllerContainer from './controller/controller-container';
import PlugContainer from './plug/plug-container';
import ScheduleContainer from './schedule/schedule-container';
import LogoutContainer from './logout/logout-container';
import MemberView from '../memberView/member-view';
import fuLogger from '../core/common/fu-logger';
import {PrivateRoute} from '../core/common/utils';

function MemberContainer() {
	const memberState = useSelector((state) => state.member);
	const session = useSelector((state) => state.session);
	const appPrefs = useSelector((state) => state.appPrefs);
	const appMenus = useSelector((state) => state.appMenus);
	const dispatch = useDispatch();

	useEffect(() => {
		dispatch(memberActions.initMember());
  	}, []);

	const changeTab = (e) => {
      	//this.setState({activeTab:code});
     	// this.props.history.replace(index);
  	}

    fuLogger.log({level:'TRACE',loc:'MemberContainer::render',msg:"path "+ window.location.pathname});

    let myMenus = [];
    if (appMenus != null && appMenus[appPrefs.memberMenu] != null) {
      myMenus = appMenus[appPrefs.memberMenu];
    }
    let profileMenu = [];
    if (appMenus != null && appMenus.MEMBER_PROFILE_MENU_TOP != null) {
    	profileMenu = appMenus.MEMBER_PROFILE_MENU_TOP;
    }
    let myPermissions = {};
    if (session != null && session.user != null && session.user.permissions != null) {
      myPermissions = session.user.permissions;
    }
    if (myMenus.length > 0) {
      return (
        <MemberView>
          <NavigationView appPrefs={appPrefs} permissions={myPermissions}
          menus={myMenus} changeTab={changeTab} activeTab={window.location.pathname} user={session.user} profileMenu={profileMenu}/>
          <StatusView/>
          <Routes>
            <Route element={<PrivateRoute permissions={myPermissions} code="MCTR" minRights="W" pathto="/access-denied" />} >
				<Route path="/member-controller/*" element={<ControllerContainer />} />
			</Route>
            <Route element={<PrivateRoute permissions={myPermissions} code="MPL" minRights="W" pathto="/access-denied" />} >
				<Route path="/member-plug/*" element={<PlugContainer />} />
			</Route>
            <Route path="/member-schedule/*" element={<PrivateRoute permissions={myPermissions} code="MPL" minRights="W" pathto="/access-denied" component="<ScheduleContainer />" />} />
            <Route path="/member-profile/*" element={<PrivateRoute permissions={myPermissions} code="MP" minRights="W" pathto="/access-denied" component="<ProfileContainer />" />} />
            <Route path="/member-logout/*" element={<PrivateRoute permissions={myPermissions} code="MLO" pathto="/access-denied" component="<LogoutContainer />" />} />
            <Route path="admin/*" render={() => ( 
              <Navigate replace to="/admin"/>
            )}/>
          </Routes>
        </MemberView>
      );
    } else {
      return (
        <MemberView> <LoadingView/>
        </MemberView>
      );
    }
}

export default MemberContainer;

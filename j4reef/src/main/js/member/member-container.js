/*
* Author Edward Seufert
*/
'use strict';
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
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

class MemberContainer extends Component {
  constructor(props) {
		super(props);

    this.changeTab = this.changeTab.bind(this);
	}

  componentDidMount() {
    this.props.actions.initMember();
  }

  changeTab(code,index) {
      //this.setState({activeTab:code});
      this.props.history.replace(index);
  }

  render() {
    fuLogger.log({level:'TRACE',loc:'MemberContainer::render',msg:"path "+ window.location.pathname});

    let myMenus = [];
    if (this.props.appMenus != null && this.props.appMenus[this.props.appPrefs.memberMenu] != null) {
      myMenus = this.props.appMenus[this.props.appPrefs.memberMenu];
    }
    let profileMenu = [];
    if (this.props.appMenus != null && this.props.appMenus.MEMBER_PROFILE_MENU_TOP != null) {
    	profileMenu = this.props.appMenus.MEMBER_PROFILE_MENU_TOP;
    }
    let myPermissions = {};
    if (this.props.session != null && this.props.session.user != null && this.props.session.user.permissions != null) {
      myPermissions = this.props.session.user.permissions;
    }
    if (myMenus.length > 0) {
      return (
        <MemberView>
          <NavigationView appPrefs={this.props.appPrefs} permissions={myPermissions}
          menus={myMenus} changeTab={this.changeTab} activeTab={window.location.pathname} user={this.props.session.user} profileMenu={profileMenu}/>
          <StatusView/>
          <Routes>
            <Route path="member-controller" element={<PrivateRoute permissions={myPermissions} code="MCTR" minRights="W" pathto="/access-denied" component="<ControllerContainer />" />} />
            <Route path="/member-plug/*" element={<PrivateRoute permissions={myPermissions} code="MPL" minRights="W" pathto="/access-denied" component="<PlugContainer />" />} />
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
}

MemberContainer.propTypes = {
	appPrefs: PropTypes.object.isRequired,
	appMenus: PropTypes.object,
	lang: PropTypes.string,
  session: PropTypes.object,
  member: PropTypes.object,
	actions: PropTypes.object,
  history: PropTypes.object
};

function mapStateToProps(state, ownProps) {
  return {appPrefs:state.appPrefs, appMenus:state.appMenus, lang:state.lang, session:state.session, member:state.member};
}

function mapDispatchToProps(dispatch) {
  return { actions:bindActionCreators(memberActions,dispatch) };
}

export default connect(mapStateToProps,mapDispatchToProps)(MemberContainer);

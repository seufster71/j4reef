import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Switch, Route, withRouter} from "react-router";
import { bindActionCreators } from "redux";
import NavigationView from "./coreView/navigation/navigation-view";
import StatusView from "./coreView/status/status-view";
import LoginContainer from "./core/usermgnt/login-container";
import PublicContainer from "./publicArea/public-container";
import MemberContainer from "./member/member-container";
import AccessDeniedContainer from "./core/usermgnt/accessdenied-container";
import PiLightsContainer from "./piSwitch/piLights/PiLightsContainer";
import fuLogger from './core/common/fu-logger';


class PageContainer extends Component {
	constructor(props) {
		super(props);
	}

	componentDidUpdate() {
		fuLogger.log({level:'TRACE',loc:'PageContainer::did update',msg:"page "+ this.props.history.location.pathname});
		if (this.props.session.sessionActive == true && this.props.session.status === 'JUST_LOGGEDIN') {
			this.props.dispatch({ type: "CLEAR_SESSION_LOGIN" });
			this.props.history.replace("/member");
		}  else if (this.props.session.sessionActive == false) {
			if (this.props.history.location.pathname === "/member-logout") {
		    	this.props.history.replace("/login");
		    } else if ( this.props.history.location.pathname === "/login" ) {
	    	} else {
	    		this.props.history.replace("/login");
	    	}
		}
	}
  
  render() {
	  fuLogger.log({level:'TRACE',loc:'PageContainer::render',msg:"page "+ this.props.history.location.pathname });
    if (this.props.session.sessionActive == true) {
     return (
      <Switch>
      	<Route exact path="/" component={MemberContainer}/>	
      	<Route path="/member" component={MemberContainer}/>
      	<Route path="/member-controller" component={MemberContainer}/>
      	<Route path="/member-plug" component={MemberContainer}/>
      	<Route path="/member-schedule" component={MemberContainer}/>
      	<Route path="/member-logout" component={MemberContainer}/>
      	<Route path="/access-denied" component={AccessDeniedContainer} />
      </Switch>

      );
    } else {
      return (
        <div>
        	<NavigationView appPrefs={this.props.appPrefs} activeTab={this.props.history.location.pathname}
        	menus={this.props.appMenus.PUBLIC_MENU_RIGHT}/>
        	<StatusView />
        	<Switch>
            	<Route exact path="/" component={PublicContainer}/>
            	<Route path="/login" component={LoginContainer}/>
            </Switch>
        </div>
      );
    }
  }
}

PageContainer.propTypes = {
  appPrefs: PropTypes.object.isRequired,
  appMenus: PropTypes.object,
  lang: PropTypes.string,
  actions: PropTypes.object,
  session: PropTypes.object,
  history: PropTypes.object
};

function mapStateToProps(state, ownProps) {
  return {
    appMenus: state.appMenus,
    lang: state.lang,
    appPrefs: state.appPrefs,
    navigation: state.navigation,
    session:state.session
  };
}

export default withRouter(connect(mapStateToProps)(PageContainer));

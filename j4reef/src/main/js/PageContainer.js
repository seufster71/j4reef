import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { Routes, Route, Navigate} from "react-router-dom";
import { bindActionCreators } from "redux";
import LoginContainer from "./core/usermgnt/login-container";
import PublicContainer from "./publicArea/public-container";
import MemberContainer from "./member/member-container";
import AccessDeniedContainer from "./core/usermgnt/accessdenied-container";
import fuLogger from './core/common/fu-logger';

class PageContainer extends Component {
    
	constructor(props) {
		super(props);
	}

	componentDidUpdate() {
		fuLogger.log({level:'TRACE',loc:'PageContainer::did update',msg:"page "});
		if (this.props.session.sessionActive == true && this.props.session.status === 'JUST_LOGGEDIN') {
			this.props.dispatch({ type: "CLEAR_SESSION_LOGIN" });
			//this.nav("/member",{replace:true});
		} 
	}
  
  render() {
	  fuLogger.log({level:'TRACE',loc:'PageContainer::render',msg:"page " });

     if (this.props.session.sessionActive == true) {
     	return (
	      <Routes>
	      	<Route exact path="/"  element={<MemberContainer />}/>	
	      	<Route path="/member/*"  element={<MemberContainer />}/>
	      	<Route path="/access-denied"  element={<AccessDeniedContainer />} />
	      </Routes>

      );
    } else {
    	if (window.location.pathname === "/member-logout") {
    		return (
      			<Routes>
      				<Route path="/member-logout" element={<Navigate replace to="/login" />} />
      			</Routes>
      		);
    	} else {
	      return (
	        	<Routes>
	            	<Route path="/*"  element={<LoginContainer />}/>
	            </Routes>
	      );
      	}
    }
  }
}

PageContainer.propTypes = {
  appPrefs: PropTypes.object.isRequired,
  appMenus: PropTypes.object,
  lang: PropTypes.string,
  actions: PropTypes.object,
  session: PropTypes.object
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


export default connect(mapStateToProps)(PageContainer);

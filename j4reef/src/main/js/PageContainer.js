import React from "react";
import {useSelector, useDispatch} from 'react-redux';
import { Routes, Route, Navigate} from "react-router-dom";
import LoginContainer from "./core/usermgnt/login-container";
import MemberContainer from "./member/member-container";
import AccessDeniedContainer from "./core/usermgnt/accessdenied-container";
import fuLogger from './core/common/fu-logger';

function PageContainer() {
	const session = useSelector((state) => state.session);
	const dispatch = useDispatch();

    
	fuLogger.log({level:'TRACE',loc:'PageContainer::did update',msg:"page "});
	if (session.sessionActive == true && session.status === 'JUST_LOGGEDIN') {
		dispatch({ type: "CLEAR_SESSION_LOGIN" });
		//this.nav("/member",{replace:true});
	}

    if (session.sessionActive == true) {
     	return (
	      <Routes>
	      	<Route path="*"  element={<MemberContainer />}/>	
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


export default PageContainer;

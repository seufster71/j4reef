import callService from '../core/api/api-call';

// actions
export function loadInitMember(responseJson) {
	return { type:'NO', responseJson };
}

// thunk
export function initMember() {
	return function(dispatch) {
		let requestParams = {};
		requestParams.action = "INIT";
		requestParams.service = "MEMBER_SVC";
		//requestParams.prefFormKeys = new Array("LOGIN_FORM","REGISTRATION_FORM","FORGOTPASSWORD_FORM","PASSWORD_CHANGE_FORM");
		requestParams.prefTextKeys = new Array("MEMBER_PAGE");
		requestParams.menuNames = new Array("MEMBER_MENU_TOP","MEMBER_PROFILE_MENU_TOP");
		let params = {};
		params.requestParams = requestParams;
		params.URI = '/api/member/callService';

		return callService(params).then( (responseJson) => {
			if (responseJson != null && responseJson.status != null && responseJson.status === "SUCCESS" && responseJson.protocalError == null && responseJson.errors == null) {
				dispatch({ type: "MEMBER_INIT", responseJson });
			} else if (responseJson != null && responseJson.errors != null) {
				responseJson.status = "ERROR";
				dispatch({type:'SHOW_STATUS_ERROR', error:[responseJson.errors]});
				dispatch({type:'PROCESS_LOGOUT'});
			} else if (responseJson != null && responseJson.protocalError != null) {
				if (responseJson.protocalError >= 401) {
					dispatch({type:'SHOW_STATUS_ERROR',error:['User or password is incorrect.']});
				} else {
					dispatch({type:'SHOW_STATUS_ERROR',error:[responseJson.protocalError]});
				}
			}
		}).catch(error => {
			throw(error);
		});

	};
}

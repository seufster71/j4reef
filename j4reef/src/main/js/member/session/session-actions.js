import callService from '../../core/api/api-call';
import actionUtils from '../../core/common/action-utils';

// actions

// thunk
export function sessionCheck() {
	return function(dispatch) {
		let requestParams = {};
		requestParams.action = "CHECK";
		requestParams.service = "MEMBER_SVC";
		let params = {};
		params.requestParams = requestParams;
		params.URI = '/api/member/callService';

		return callService(params).then( (responseJson) => {
			if (responseJson != null && responseJson.protocalError == null){
				dispatch({ type: "LOAD_SESSION_CHECK", responseJson });
			} else {
				actionUtils.checkConnectivity(responseJson,dispatch);
			}
		}).catch(error => {
			throw(error);
		});
	};
}

export function viewPortChange(width, height) {
	return function(dispatch) {
		dispatch({ type: "VIEW_PORT_CHANGE", width, height });
	};
}
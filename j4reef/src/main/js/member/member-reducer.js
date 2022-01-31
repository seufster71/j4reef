
export default function memberReducer(state = {}, action) {
  switch(action.type) {
    case 'PROCESS_LOGOUT': {
      return Object.assign({}, state, {user:null});
    }
	case 'USERMGNT_SET_VIEW': {
		if (action.params != null) {
			let clone = Object.assign({}, state);
			clone.view = action.params.value;
			return clone;
		} else {
    		return state;
		}
	}
	case 'USERMGNT_SET_FIELD': {
		if (action.params != null) {
			let inputFields = Object.assign({}, state.inputFields);
			inputFields[action.params.field] = action.params.value;
			let clone = Object.assign({}, state);
			clone.inputFields = inputFields;
			return clone;
		} else {
    		return state;
		}
	}
    default:
        return state;
    }
}

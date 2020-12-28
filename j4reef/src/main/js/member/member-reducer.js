
export default function memberReducer(state = {}, action) {
  switch(action.type) {
    case 'PROCESS_LOGOUT': {
      return Object.assign({}, state, {user:null});
    }
    default:
        return state;
    }
}

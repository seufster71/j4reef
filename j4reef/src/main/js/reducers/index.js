import {combineReducers} from 'redux';
import appPrefs from '../core/common/apppref-reducer';
import appMenus from '../core/common/appmenu-reducer';
import member from '../member/member-reducer';
import session from '../member/session/session-reducer';
import status from '../core/status/status-reducer';
import controllerState from '../member/controller/controller-reducer';
import plugState from '../member/plug/plug-reducer';
import scheduleState from '../member/schedule/schedule-reducer';

const rootReducer = combineReducers({appPrefs,appMenus,session,member,status,controllerState,plugState,scheduleState});

export default rootReducer;

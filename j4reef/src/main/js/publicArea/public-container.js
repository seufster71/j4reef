/*
* Author Edward Seufert
*/
'use-strict';
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as appPrefActions from '../core/common/apppref-actions';
import fuLogger from '../core/common/fu-logger';
import PublicView from './public-view';
import NavigationView from "../coreView/navigation/navigation-view";
import StatusView from "../coreView/status/status-view";


class PublicContainer extends Component {
	constructor(props) {
		super(props);

	}

	componentDidMount() {
		//this.props.actions.initMember();
	}

  render() {
			fuLogger.log({level:'TRACE',loc:'PublicContainer::render',msg:"Hi in public"});
      return (
      		<div>
      		<NavigationView appPrefs={this.props.appPrefs} activeTab={window.location.pathname}
	        	menus={this.props.appMenus.PUBLIC_MENU_RIGHT}/>
	        	<StatusView />
				<PublicView/>
				</div>
			);
  }
}

PublicContainer.propTypes = {
	appPrefs: PropTypes.object,
	lang: PropTypes.string,
	actions: PropTypes.object
};

function mapStateToProps(state, ownProps) {
  return {lang:state.lang, appPrefs:state.appPrefs, appMenus:state.appMenus};
}

function mapDispatchToProps(dispatch) {
  return { actions:bindActionCreators(appPrefActions,dispatch) };
}

export default connect(mapStateToProps,mapDispatchToProps)(PublicContainer);

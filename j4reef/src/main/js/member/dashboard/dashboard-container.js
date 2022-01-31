/*
* Author Edward Seufert
*/
'use-strict';
import React from 'react';
import fuLogger from '../../core/common/fu-logger';
import DashboardView from '../../memberView/dashboard/dashboard-view';

function DashboardContainer() {

	fuLogger.log({level:'TRACE',loc:'DashboardContainer::render',msg:"Hi there"});
    return ( <DashboardView/> );

}


export default DashboardContainer;

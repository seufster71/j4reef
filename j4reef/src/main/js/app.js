import React, { Component } from 'react';
import { render } from 'react-dom';
import configureStore from "./store/configureStore";
import { Provider } from "react-redux";
import { BrowserRouter } from "react-router-dom";
import { initPublic } from "./core/common/apppref-actions";
import { sessionCheck, viewPortChange } from "./member/session/session-actions";
import PageContainer from "./PageContainer.js";
import Bootstrap from 'bootstrap/dist/css/bootstrap.css';
import Theme from './theme.css';
import utils from './core/common/utils';

if (process.env.NODE_ENV !== "production") {
	  console.log("Looks like we are in development mode!");
}

const store = configureStore();
store.dispatch(initPublic());
store.dispatch(sessionCheck());
store.dispatch(viewPortChange(window.innerWidth, window.innerHeight));

const handleResize = () => {
	console.log("resized to", window.innerWidth, window.innerHeight );
	store.dispatch(viewPortChange(window.innerWidth, window.innerHeight));
}

window.addEventListener('resize', utils.debounce(handleResize,1000));

render(
	<Provider store={store}>
		<BrowserRouter>
			<PageContainer />
		</BrowserRouter>
	</Provider>,
	document.getElementById("app")
);

export function getDebugClient() {
	  let state = store.getState();
	  return state.appPrefs.debugClient;
	}
	export function getHost() {
	  return "";
	}

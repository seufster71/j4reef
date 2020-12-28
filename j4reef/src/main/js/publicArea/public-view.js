import React, {Component} from 'react';
import PropTypes from 'prop-types';


export default function PublicView({currentState, fields }) {


    return (
      <div>
        <div> Public Page </div>
      </div>
    );
}


PublicView.propTypes = {
  currentState: PropTypes.object,
	fields: PropTypes.object
};

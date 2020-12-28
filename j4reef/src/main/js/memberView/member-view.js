import React, {Component} from 'react';
import PropTypes from 'prop-types';


export default function MemberView(props) {

    return (
      <div>
        {props.children}
      </div>
    );
}


MemberView.propTypes = {
  children: PropTypes.array
};

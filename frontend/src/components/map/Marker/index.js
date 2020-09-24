import React from 'react';
import PropTypes from 'prop-types';

import MarkerStyled from './MarkerStyled';
import MarkerInGroupStyled from './MarkerInGroupStyled';
import Car from './Car';
import ArrowTop from './ArrowTop';

class Marker extends React.PureComponent {
  // eslint-disable-line react/prefer-stateless-function
  static defaultProps = {
    inGroup: false,
    text: ''
  };

  render() {
    return (
      <div>
        {this.props.inGroup
          ? <MarkerInGroupStyled>
              <Car scale="0.55" />
            </MarkerInGroupStyled>
          : <div> 
            <ArrowTop direction={this.props.text}/>
            <MarkerStyled>
              <Car scale="0.55" />
            </MarkerStyled>
            </div>
            }
          
      </div>
    );
  }
}

Marker.propTypes = {
  inGroup: PropTypes.bool,
};

export default Marker;

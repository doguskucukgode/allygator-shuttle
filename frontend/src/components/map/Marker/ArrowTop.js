import styled from 'styled-components';

const ArrowTop  = styled.div`
display: flex;
justify-content: center;
align-items: center;
border: solid transparent;
content: " ";
height: 10;
width: 10;
position: absolute;
pointer-events: none;
border-color: rgba(0, 0, 0, 0);
border-bottom-color: #000000;
border-width: 10px;
margin-top: -20px;
margin-left: 10px;
-webkit-transform:rotate(${props => (props.direction ? props.direction + 'deg' : '0deg')});
`;

export default ArrowTop;

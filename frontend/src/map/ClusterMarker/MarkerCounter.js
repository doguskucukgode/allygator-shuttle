import styled from 'styled-components';
import { COLORS } from '../../style-constants';

const MarkerCounter = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 31px;
  height: 21px;
  padding: 8px;
  margin-left: -10px;
  text-align: center;
  font-size: 14px;
  color: #fff;
  border: 2px solid #fff;
  border-radius: 50%;
  background-color: ${COLORS.gray64};
  color: black
`;

export default MarkerCounter;

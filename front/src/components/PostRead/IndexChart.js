import React from 'react';
import useIntersectionObservation from '../../hooks/useIntersectionObservation';
import RecursiveTitle from './RecursiveTitle';
import { dummyDataForTOC } from './dummydata';

const IndexChart = () => {
  useIntersectionObservation();

  return (
    <div className='IndexChart'>
      <div>
        <div>
          자습서
          <span className="svgImg">V</span>
        </div>
        <RecursiveTitle titleObjects={ dummyDataForTOC } />
      </div>
    </div>
  );
}

export default IndexChart;
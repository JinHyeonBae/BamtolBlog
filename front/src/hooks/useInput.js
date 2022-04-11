import { useState, useCallback } from 'react';

export default (initialState = null)=>{
  const [value, setValue] = useState(initialState);
  const onChangeHandler = useCallback((e) => {
    setValue(e.target.value);
  },[]);
  return [value, onChangeHandler, setValue];
}
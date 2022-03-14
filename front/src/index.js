import React from 'react';
import ReactDOM from 'react-dom';
import './index.scss';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';
import Reducer from './_reducers';
// Saga 또는 redux-thunk, promise 조합 사용.
// import promiseMiddleware from 'redux-promise';  
// import ReduxThunk from 'redux-thunk'; 

// const createStoreWithMiddleware = applyMiddleware(promiseMiddleware, ReduxThunk)(createStore);
const createStoreWithMiddleware = applyMiddleware()(createStore);

ReactDOM.render(
  <React.StrictMode>
    <Provider store={createStoreWithMiddleware(Reducer,
        window.__REDUX_DEVTOOLS_EXTENSION__ &&
        window.__REDUX_DEVTOOLS_EXTENSION__()
      )} >
    <App />
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);


// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

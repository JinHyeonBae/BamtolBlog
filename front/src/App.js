import React from 'react';
import './App.scss';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import Home from './components/Home';
import Login from './components/Login';
import SignUp from './components/SignUp/index';
import PostRead from './components/PostRead';
import PostWrite from './components/PostWrite';
import '../node_modules/antd/dist/antd.min.css';

const App = () => {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/:userNickname/posts/:postId" element={<PostRead />} />
          <Route path="/:userNickname/posts/:categoryId/write" element={<PostWrite />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;

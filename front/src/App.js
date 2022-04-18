import React from 'react';
import './App.scss';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import Home from './components/Home';
import Login from './components/Login';
import PostRead from './components/PostRead';
import PostWrite from './components/PostWrite';

const App = () => {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/:userNickname/posts/:postId" element={<PostRead />} />
          <Route path="/:userNickname/write" element={<PostWrite />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;

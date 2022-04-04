import React from 'react';
import './App.scss';
import {
  BrowserRouter as Router,
  Routes,
  Route
} from "react-router-dom";
import PostRead from './components/PostRead';
import PostWrite from './components/PostWrite';

const App = () => {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/" element={<>Home: Bot Line Blog</>} />
          <Route path="/:userNickname/posts/:postsId" element={<PostRead />} />
          <Route path="/:userNickname/write" element={<PostWrite />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;

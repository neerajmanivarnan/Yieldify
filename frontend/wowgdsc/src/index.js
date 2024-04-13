import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; // Import BrowserRouter
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import GetData from './GetData'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <Router>
      <Routes>
        <Route path="/Enter_Data" element={<GetData />} />
        <Route path="/" element={<App />} /> {/* Place App inside a Route */}
      </Routes>
    </Router>
  </React.StrictMode>
);

reportWebVitals();

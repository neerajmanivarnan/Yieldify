import React from 'react';
import { createRoot } from 'react-dom/client'; // Import createRoot from react-dom/client
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'; // Import BrowserRouter
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import GetData from './GetData';
import DataFetch from './DataFetch';

const root = createRoot(document.getElementById('root')); // Use createRoot from react-dom/client
root.render(
  <React.StrictMode>
    <Router>
      <Routes>
        <Route path="/Enter_Data" element={<GetData />} />
        <Route path="/result" element={<DataFetch />} />
        <Route path="/" element={<App />} /> {/* Place App inside a Route */}
      </Routes>
    </Router>
  </React.StrictMode>
);

reportWebVitals();

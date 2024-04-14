// DataFetchingPage.js

import React, { useState, useEffect } from 'react';
import './DataFetchingPage.css'; // Import CSS file for styling
import Header from './Header';
const DataFetchingPage = () => {
  const [optionsData, setOptionsData] = useState([]);

  useEffect(() => {
    fetch('data.json') // Assuming data.json is the JSON file containing your data
      .then((response) => response.json())
      .then((data) => setOptionsData(data))
      .catch((error) => console.error('Error fetching data:', error));
  }, []);

  return (
    <div>
    <div className='head-section'> <Header /> </div>
    <div className="data-fetching-page">
      <h1 className="page-title">Options Data</h1>
      <table className="options-table">
        <thead>
          <tr>
            <th>Sl No</th>
            <th>Option Name</th>
            <th>Expiry</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {optionsData.map((option, index) => (
            <tr key={index}>
              <td>{index + 1}</td>
              <td>{option.optionName}</td>
              <td>{option.expiry}</td>
              <td>{option.price}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <button className="place-order-button">Place Order</button>
    </div>
    </div>
  );
};

export default DataFetchingPage;

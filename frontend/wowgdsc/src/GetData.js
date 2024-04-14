import React, { useState } from 'react';
import './GetData.css';

const DateInputPage = () => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');

  const handleStartDateChange = (event) => {
    setStartDate(event.target.value);
  };

  const handleEndDateChange = (event) => {
    setEndDate(event.target.value);
  };

  const handleGetData = () => {
    // Send startDate and endDate to the backend server
    fetch('http://localhost:8080/getData', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ startDate, endDate }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Response from server:', data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  };

  return (
    <div className='date-input-container'>
      <h1 className='heading'>Select Date Range</h1>
      <div className='date-pickers'>
        <div className='date-picker'>
          <label htmlFor="startDate">Start Date:</label>
          <input
            type="date"
            id="startDate"
            value={startDate}
            onChange={handleStartDateChange}
          />
        </div>
        <div className='date-picker'>
          <label htmlFor="endDate">End Date:</label>
          <input
            type="date"
            id="endDate"
            value={endDate}
            onChange={handleEndDateChange}
          />
        </div>
      </div>
      <button className='get-data-button' onClick={handleGetData}>
        Get Data
      </button>
    </div>
  );
};

export default DateInputPage;

import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Header from './Header';

import './GetData.css';

const DateInputPage = () => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [selectedThursday, setSelectedThursday] = useState('');
  const [toDateValue, setToDateValue] = useState(''); // State to hold toDate value
  const [fromDateValue, setFromDateValue] = useState(''); // State to hold fromDate value
  const [numberOfExpiryDays, setNumberOfExpiryDays] = useState(''); // State to hold numberOfExpiryDays value
  const navigate = useNavigate(); // Initialize useNavigate hook

  // Function to get the dates of the next Thursdays
  const getNextThursdays = () => {
    const startDate = new Date('2024-04-18'); // Start from 18th April 2024
    const nextThursdays = [];
  
    for (let i = 0; i < 10; i++) { // Get dates for the next 10 Thursdays
      const nextThursday = new Date(startDate);
      nextThursday.setDate(startDate.getDate() + (i * 7));
      nextThursdays.push(nextThursday.toISOString().split('T')[0]); // Format the date to yyyy-mm-dd
    }
  
    return nextThursdays;
  };
  

  const handleStartDateChange = (event) => {
    setStartDate(event.target.value);
  };

  const handleEndDateChange = (event) => {
    setEndDate(event.target.value);
  };

  const handleThursdayChange = (event) => {
    setSelectedThursday(event.target.value);
  };

  const handleGetData = () => {
    // Calculate the fromDate and toDate
    const fromDate = startDate;
    const toDate = endDate;
  
    // Calculate the number of days left until the selected Thursday
    const today = new Date();
    const selectedDate = new Date(selectedThursday);
    const differenceInTime = selectedDate.getTime() - today.getTime();
    const numberOfDaysToExpiry = Math.ceil(differenceInTime / (1000 * 3600 * 24));
  
    // Set the state values
    setFromDateValue(fromDate);
    setToDateValue(toDate);
    setNumberOfExpiryDays(numberOfDaysToExpiry);
  
    // Prepare the data object in the specified JSON format
    const dataToSend = {
      fromDate,
      toDate,
      numberOfDaysToExpiry
    };

    // Send the data to the backend server
    fetch('http://localhost:8080/historical-data/NSE_INDEX%7CNifty%205', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(dataToSend),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Response from server:', data);
      })
      .catch((error) => {
        console.error('Error:', error);
      });
  
    // Navigate to the result page
    navigate('/result');
  };
  
  

  return (
    <div>
      <div className='head-section'> <Header /> </div>
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
          <div className='date-picker'>
            <label htmlFor="thursday">Expiry Date</label>
            <select id="thursday" value={selectedThursday} onChange={handleThursdayChange}>
              <option value="">Select</option>
              {getNextThursdays().map((date) => (
                <option key={date} value={date}>{date}</option>
              ))}
            </select>
          </div>
        </div>
        <div>
          <p>From Date: {fromDateValue}</p>
          <p>To Date: {toDateValue}</p>
          <p>Number of Expiry Days: {numberOfExpiryDays}</p>
        </div>
        <button className='get-data-button' onClick={handleGetData}>
          Get Strategy
        </button>
      </div>
    </div>
  );
};

export default DateInputPage;

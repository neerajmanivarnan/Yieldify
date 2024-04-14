// JsonDataDisplay.js
import React from 'react';

const JsonDataDisplay = ({ jsonData }) => {
  return (
    <div>
      <h2>JSON Data:</h2>
      <pre>{JSON.stringify(jsonData, null, 2)}</pre>
    </div>
  );
};

export default JsonDataDisplay;

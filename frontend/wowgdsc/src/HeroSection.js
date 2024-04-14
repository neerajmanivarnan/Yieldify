import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HeroSection.css'; // Import CSS file for styling

const HeroSection = () => {
    const navigate = useNavigate();
    const handleCreateAccount = () => {
        navigate('/Enter_Data');
      };

  return (
    <div className="hero">
      <h1>Welcome to YIELDIFY</h1>
      <p>Automated market analysis and option recommendations</p>
    < div className='btn-con' >      <button className="cta-btn" type="button" onClick={handleCreateAccount}>Make Yield</button></div>
    </div>
  );
}

export default HeroSection;

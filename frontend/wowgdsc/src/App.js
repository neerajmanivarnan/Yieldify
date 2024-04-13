// App.js
import React from 'react';
import Header from './Header';
import HeroSection from './HeroSection';
import MarketAnalysisSection from './MarketAnalysisSection';
import Footer from './Footer';
import './App.css';

function App() {
  return (
    <div className="App">
      <Header />
      <HeroSection />
      <MarketAnalysisSection />
      {/* Add more sections/components here */}
      <Footer />
    </div>
  );
}

export default App;

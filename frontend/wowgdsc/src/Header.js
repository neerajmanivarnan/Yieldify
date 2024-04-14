// Navbar.js

import React from 'react';
import './Header.css';

const Navbar = () => {
  return (
    <nav className="navbar">
        <span className="navbar-brand">YIELDIFY</span>
      <ul className="nav-links">
        <li><button className="nav-button">Login</button></li>
        <li><button className="nav-button">Signup</button></li>
      </ul>
    </nav>
  );
};

export default Navbar;
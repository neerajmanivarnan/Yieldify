import React from 'react';
import './Footer.css';

function Footer() {
  return (
    <footer className="footer">
      <div className="w-full text-center">
        <div className="w-full justify-between sm:flex sm:items-center sm:justify-between">
          
          
          <div className="footer-links">
            <a href="#" className="footer-link">About</a>
            <a href="#" className="footer-link">Privacy Policy</a>
            <a href="#" className="footer-link">Licensing</a>
            <a href="#" className="footer-link">Contact</a>
          </div>
        </div>
        <hr className="footer-divider" />
        <p className="footer-copyright">© Yieldify™ 2022</p>
      </div>
    </footer>
  );
}

export default Footer;
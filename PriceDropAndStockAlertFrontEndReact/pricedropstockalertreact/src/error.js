import React from 'react';
import './style.css'; // Ensure your styles are in this file
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

const ErrorPage = ({ title }) => {
  return (
    <div className="error-container d-flex align-items-center justify-content-center">
      <div className="text-center">
        <h1 className="big-text">Oops!</h1>
        <h2 className="small-text">404 - PAGE NOT FOUND</h2>
        <p>The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.</p>
        <a href="/" className="button button-dark-blue iq-mt-15 text-center md-1">GOTO HOME PAGE</a>
      </div>
    </div>
  );
};

export default ErrorPage;

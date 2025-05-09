import React from 'react';
import './style.css'; // Ensure you have your styles in this file
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

const Dashboard = () => {
  return (
    <div className="container text-center mt-5">
      <h1>Dashboard</h1>
      <div className="mt-4">
        <a href="/user/add-product" className="btn btn-primary mx-2">Add Product</a>
        <a href="/user/product-list" className="btn btn-secondary mx-2">Product List</a>
      </div>
    </div>
  );
};

export default Dashboard;

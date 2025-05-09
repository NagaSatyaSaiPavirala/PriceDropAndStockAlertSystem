import React, { useState } from 'react';
import './style.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const AddProduct = () => {
  const [productUrl, setProductUrl] = useState('');
  const [thresholdPrice, setThresholdPrice] = useState('');
  const [message, setMessage] = useState(null);

  const handleSubmit = (e) => {
    e.preventDefault();
    // Here you would typically send the form data to your backend
    // For example, using fetch or axios
    // Clear the input fields after submission
    setProductUrl('');
    setThresholdPrice('');
    // Set a success message (example)
    setMessage({ content: 'Product added successfully!', type: 'alert-success' });
  };

  const handleReset = () => {
    setProductUrl('');
    setThresholdPrice('');
    setMessage(null);
  };

  return (
    <section style={{ margin: 'auto', width: '400px', marginTop: '100px' }}>
      {message && (
        <div className={`alert ${message.type} text-center`} role="alert">
          <p className="text-center">{message.content}</p>
        </div>
      )}
      <br />
      <b>Add Product</b>
      <br />
      <form onSubmit={handleSubmit}>
        <label>Product Url: </label>
        <input
          type="text"
          name="p_url"
          required
          value={productUrl}
          onChange={(e) => setProductUrl(e.target.value)}
          className="form-control"
        />
        <br />

        <label>Threshold Price: </label>
        <input
          type="number"
          name="t_price"
          required
          value={thresholdPrice}
          onChange={(e) => setThresholdPrice(e.target.value)}
          className="form-control"
        />
        <br />

        <button type="submit" className="btn btn-primary">Submit</button>
        <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
      </form>
      <br />
      <br />
      <a href="/user/dashboard">Dashboard</a>
    </section>
  );
};

export default AddProduct;

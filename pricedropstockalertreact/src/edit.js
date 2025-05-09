// import React from 'react';
// import './style.css'; // Ensure your styles are in this file
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

// const EditProduct = ({ user, sessionMessage, product, errors, removeMessage }) => {
//   const handleSearch = (e) => {
//     // Implement your search logic here
//     console.log(e.target.value);
//   };

//   return (
//     <div>
//       <nav className="navbar">
//         <div className="container">
//           <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span>
//           <ul>
//             <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//             <li className="nav-item"><a href="/logout">Logout</a></li>
//           </ul>
//         </div>
//       </nav>

//       <div className="dashboard h-100">
//         <div className="dashboard-header text-center">
//           <h1 id="welcome-heading">Edit Product Info</h1>
//         </div>
//         <div className="dashboard-content">
//           <div id="add-product" className="tab-content active">
//             {sessionMessage && (
//               <div className={`alert alert-primary text-center mt-3 ${sessionMessage.type}`} role="alert">
//                 <p className="text-center">{sessionMessage.content}</p>
//                 {removeMessage && <div>{removeMessage()}</div>}
//               </div>
//             )}

//             <div className="search-container my-3 mt-3">
//               <input
//                 type="text"
//                 onKeyUp={handleSearch}
//                 className="form-control fs-4 p-3"
//                 placeholder="Search product"
//               />
//               <div className="search-result">
//                 {/* Results go here */}
//               </div>
//             </div>

//             {/* Form for editing the product */}
//             <form id="add-product-form" action="/user/edit-product" method="post">
//               <input type="hidden" name="p_id" value={product.p_id} />
//               <br />
//               <div className="form-group fs-4">
//                 <label htmlFor="product-name">Product Name :</label>
//                 <input
//                   type="text"
//                   className={`w-100 ${errors.p_name ? 'is-invalid' : ''}`}
//                   id="product-name"
//                   name="p_name"
//                   defaultValue={product.p_name}
//                   placeholder="Enter Amazon Product Name"
//                 />
//                 {errors.p_name && (
//                   <div className="invalid-feedback">
//                     {errors.p_name.map((error, index) => (
//                       <div key={index}>{error}</div>
//                     ))}
//                   </div>
//                 )}
//               </div>

//               <div className="form-group fs-4">
//                 <label htmlFor="product-url">Product URL :</label>
//                 <input
//                   type="url"
//                   className={`w-100 ${errors.p_url ? 'is-invalid' : ''}`}
//                   id="product-url"
//                   name="p_url"
//                   defaultValue={product.p_url}
//                   placeholder="Enter Amazon Product URL"
//                 />
//                 {errors.p_url && (
//                   <div className="invalid-feedback">
//                     {errors.p_url.map((error, index) => (
//                       <div key={index}>{error}</div>
//                     ))}
//                   </div>
//                 )}
//               </div>

//               <div className="form-group fs-4">
//                 <label htmlFor="threshold-price">Threshold Price :</label>
//                 <input
//                   type="number"
//                   className={`w-100 ${errors.t_price ? 'is-invalid' : ''}`}
//                   id="threshold-price"
//                   name="t_price"
//                   defaultValue={product.t_price}
//                   placeholder="Enter Threshold Price"
//                   required
//                 />
//                 {errors.t_price && (
//                   <div className="invalid-feedback">
//                     {errors.t_price.map((error, index) => (
//                       <div key={index}>{error}</div>
//                     ))}
//                   </div>
//                 )}
//               </div>

//               <div className="form-group fs-5 text-center m-3">
//                 <button type="submit" className="btn-submit">Submit</button>
//                 <button type="reset" className="btn-reset">Reset</button>
//               </div>
//             </form>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default EditProduct;








// import React, { useEffect, useState } from 'react';
// import './style.css'; 
// import 'bootstrap/dist/css/bootstrap.min.css'; 

// const EditProduct = ({ user, sessionMessage, removeMessage }) => {
//   const [product, setProduct] = useState({});
//   const [errors, setErrors] = useState({});

//   useEffect(() => {
//     const fetchProduct = async () => {
//       try {
//         const productId = 1; // Replace with the actual ID you want to edit
//         const response = await fetch(`/user/editproduct/${productId}`);
//         if (!response.ok) {
//           throw new Error('Product not found or you are not authorized');
//         }
//         const productData = await response.json();
//         setProduct(productData);
//       } catch (error) {
//         console.error('Error fetching product:', error);
//       }
//     };

//     fetchProduct();
//   }, []);

//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       const response = await fetch('/user/edit-product', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify(product),
//       });

//       if (!response.ok) {
//         throw new Error('Error updating product');
//       }

//       const message = await response.json();
//       // Handle success message
//     } catch (error) {
//       console.error('Error submitting form:', error);
//     }
//   };

//   return (
//     <div>
//       <nav className="navbar">
//         <div className="container">
//           <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span>
//           <ul>
//             <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//             <li className="nav-item"><a href="/logout">Logout</a></li>
//           </ul>
//         </div>
//       </nav>

//       <div className="dashboard h-100">
//         <div className="dashboard-header text-center">
//           <h1 id="welcome-heading">Edit Product Info</h1>
//         </div>
//         <div className="dashboard-content">
//           {sessionMessage && (
//             <div className={`alert alert-primary text-center mt-3`} role="alert">
//               <p className="text-center">{sessionMessage.content}</p>
//               {removeMessage && <div>{removeMessage()}</div>}
//             </div>
//           )}

//           <form id="add-product-form" onSubmit={handleSubmit}>
//             <input type="hidden" name="p_id" value={product.p_id} />
//             <div className="form-group fs-4">
//               <label htmlFor="product-name">Product Name :</label>
//               <input
//                 type="text"
//                 className={`w-100 ${errors.p_name ? 'is-invalid' : ''}`}
//                 id="product-name"
//                 name="p_name"
//                 value={product.p_name || ''}
//                 onChange={(e) => setProduct({ ...product, p_name: e.target.value })}
//                 placeholder="Enter Amazon Product Name"
//                 required
//               />
//               {errors.p_name && (
//                 <div className="invalid-feedback">
//                   {errors.p_name.map((error, index) => (
//                     <div key={index}>{error}</div>
//                   ))}
//                 </div>
//               )}
//             </div>

//             <div className="form-group fs-4">
//               <label htmlFor="product-url">Product URL :</label>
//               <input
//                 type="url"
//                 className={`w-100 ${errors.p_url ? 'is-invalid' : ''}`}
//                 id="product-url"
//                 name="p_url"
//                 value={product.p_url || ''}
//                 onChange={(e) => setProduct({ ...product, p_url: e.target.value })}
//                 placeholder="Enter Amazon Product URL"
//                 required
//               />
//               {errors.p_url && (
//                 <div className="invalid-feedback">
//                   {errors.p_url.map((error, index) => (
//                     <div key={index}>{error}</div>
//                   ))}
//                 </div>
//               )}
//             </div>

//             <div className="form-group fs-4">
//               <label htmlFor="threshold-price">Threshold Price :</label>
//               <input
//                 type="number"
//                 className={`w-100 ${errors.t_price ? 'is-invalid' : ''}`}
//                 id="threshold-price"
//                 name="t_price"
//                 value={product.t_price || ''}
//                 onChange={(e) => setProduct({ ...product, t_price: e.target.value })}
//                 placeholder="Enter Threshold Price"
//                 required
//               />
//               {errors.t_price && (
//                 <div className="invalid-feedback">
//                   {errors.t_price.map((error, index) => (
//                     <div key={index}>{error}</div>
//                   ))}
//                 </div>
//               )}
//             </div>

//             <div className="form-group fs-5 text-center m-3">
//               <button type="submit" className="btn-submit">Submit</button>
//               <button type="reset" className="btn-reset">Reset</button>
//             </div>
//           </form>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default EditProduct;







// import React, { useState } from 'react';
// import PropTypes from 'prop-types';
// import './style.css';
// import 'bootstrap/dist/css/bootstrap.min.css';

// //const EditProduct = ({ user, sessionMessage, product = {}, errors = {}, removeMessage }) => {
//   const EditProduct = ({session, product = {}, errors = {} }) => {
//     const { sessionData, password } = session;
//   const [formData, setFormData] = useState({
//     p_id: product.p_id || '',
//     p_name: product.p_name || '',
//     p_url: product.p_url || '',
//     t_price: product.t_price || '',
//   });

//   const handleChange = (e) => {
//     const { name, value } = e.target;
//     setFormData({ ...formData, [name]: value });
//   };

//   const handleSubmit = (e) => {
//     e.preventDefault();
//     console.log('Form data submitted:', formData);
//   };

//   const handleSearch = (e) => {
//     console.log(e.target.value);
//   };

//   if (!product.p_id) {
//     return <div>Loading...</div>; // Handle loading state
//   }

//   return (
//     <div>
//       {/* Navigation and other components here */}
//       <form onSubmit={handleSubmit}>
//         <input type="hidden" name="p_id" value={formData.p_id} />
//         <div className="form-group">
//           <label htmlFor="product-name">Product Name :</label>
//           <input
//             type="text"
//             id="product-name"
//             name="p_name"
//             value={formData.p_name}
//             onChange={handleChange}
//             className={`form-control ${errors.p_name ? 'is-invalid' : ''}`}
//           />
//           {errors.p_name && <div className="invalid-feedback">{errors.p_name.join(', ')}</div>}
//         </div>
//         {/* Other input fields here */}
//         <button type="submit">Submit</button>
//       </form>
//     </div>
//   );
// };

// EditProduct.propTypes = {
//   user: PropTypes.object.isRequired,
//   sessionMessage: PropTypes.object,
//   product: PropTypes.shape({
//     p_id: PropTypes.number,
//     p_name: PropTypes.string,
//     p_url: PropTypes.string,
//     t_price: PropTypes.number,
//   }),
//   errors: PropTypes.object,
//   removeMessage: PropTypes.func,
// };

// export default EditProduct;







// import React, { useEffect, useState } from 'react';
// import axios from 'axios';

// const EditProduct = ({ productId, user }) => {
//     const [product, setProduct] = useState({
//         p_id: '',
//         p_name: '',
//         p_url: '',
//         t_price: ''
//     });
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         // Fetch product details using productId
//         const fetchProduct = async () => {
//             try {
//                 const response = await axios.get(`/api/products/${productId}`); // Adjust the endpoint as necessary
//                 setProduct(response.data);
//             } catch (error) {
//                 console.error('Error fetching product data:', error);
//             }
//         };
//         fetchProduct();
//     }, [productId]);

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setProduct((prevProduct) => ({
//             ...prevProduct,
//             [name]: value
//         }));
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             await axios.post('/user/edit-product', product);
//             setMessage({ content: 'Product updated successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Error updating product.', type: 'alert-danger' });
//         }
//     };

//     return (
//         <div className="dashboard h-100">
//             <div style={{ textAlign: 'center', width: '100%', marginLeft: '13%' }} className="dashboard-header">
//                 <h1 id="welcome-heading">Edit Product Info</h1>
//             </div>
//             <div className="dashboard-content">
//                 {message && (
//                     <div className={`alert ${message.type} text-center mt-3`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}
//                 <form id="add-product-form" onSubmit={handleSubmit}>
//                     <input type="hidden" name="p_id" value={product.p_id} />
//                     <div className="form-group fs-4">
//                         <label htmlFor="product-name">Product Name:</label>
//                         <input
//                             type="text"
//                             className={`w-100 ${!product.p_name ? 'is-invalid' : ''}`}
//                             id="product-name"
//                             name="p_name"
//                             value={product.p_name}
//                             onChange={handleChange}
//                             placeholder="Enter Amazon Product Name"
//                         />
//                         {!product.p_name && <div className="invalid-feedback">Product name is required.</div>}
//                     </div>

//                     <div className="form-group fs-4">
//                         <label htmlFor="product-url">Product URL:</label>
//                         <input
//                             type="url"
//                             className={`w-100 ${!product.p_url ? 'is-invalid' : ''}`}
//                             id="product-url"
//                             name="p_url"
//                             value={product.p_url}
//                             onChange={handleChange}
//                             placeholder="Enter Amazon Product URL"
//                         />
//                         {!product.p_url && <div className="invalid-feedback">Product URL is required.</div>}
//                     </div>

//                     <div className="form-group fs-4">
//                         <label htmlFor="threshold-price">Threshold Price:</label>
//                         <input
//                             type="number"
//                             className={`w-100 ${!product.t_price ? 'is-invalid' : ''}`}
//                             id="threshold-price"
//                             name="t_price"
//                             value={product.t_price}
//                             onChange={handleChange}
//                             placeholder="Enter Threshold Price"
//                             required
//                         />
//                         {!product.t_price && <div className="invalid-feedback">Threshold price is required.</div>}
//                     </div>

//                     <div className="form-group fs-5 text-center m-3">
//                         <button type="submit" className="btn-submit">Submit</button>
//                         <button type="reset" className="btn-reset" onClick={() => setProduct({ p_id: '', p_name: '', p_url: '', t_price: '' })}>Reset</button>
//                     </div>
//                 </form>
//             </div>
//         </div>
//     );
// };

// export default EditProduct;



// import React, { useEffect, useState } from 'react';
// import axios from 'axios';

// const EditProduct = ({session, productId, user }) => {
//   const { sessionData, password } = session;
//   const username=session.username;
//     const [product, setProduct] = useState({
//         p_id: '',
//         p_name: '',
//         p_url: '',
//         t_price: ''
//     });
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         const fetchProduct = async () => {
//             try {
//                 const response = await axios.get(`/api/products/${productId}`); // Adjust the endpoint as necessary
//                 setProduct(response.data);
//             } catch (error) {
//                 console.error('Error fetching product data:', error);
//             }
//         };
//         fetchProduct();
//     }, [productId]);

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setProduct((prevProduct) => ({
//             ...prevProduct,
//             [name]: value
//         }));
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             await axios.post('/user/edit-product', product);
//             setMessage({ content: 'Product updated successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Error updating product.', type: 'alert-danger' });
//         }
//     };

//     return (
//         <div>
//             {/* Navbar */}
//             <nav className="navbar">
//                 <div className="container">
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="dashboard h-100">
//                 <div style={{ textAlign: 'center', width: '100%', marginLeft: '13%' }} className="dashboard-header">
//                     <h1 id="welcome-heading">Edit Product Info</h1>
//                 </div>
//                 <div className="dashboard-content">
//                     {message && (
//                         <div className={`alert ${message.type} text-center mt-3`} role="alert">
//                             <p className="text-center">{message.content}</p>
//                         </div>
//                     )}
//                     <form id="add-product-form" onSubmit={handleSubmit}>
//                         <input type="hidden" name="p_id" value={product.p_id} />
//                         <div className="form-group fs-4">
//                             <label htmlFor="product-name">Product Name:</label>
//                             <input
//                                 type="text"
//                                 className={`w-100 ${!product.p_name ? 'is-invalid' : ''}`}
//                                 id="product-name"
//                                 name="p_name"
//                                 value={product.p_name}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product Name"
//                             />
//                             {!product.p_name && <div className="invalid-feedback">Product name is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="product-url">Product URL:</label>
//                             <input
//                                 type="url"
//                                 className={`w-100 ${!product.p_url ? 'is-invalid' : ''}`}
//                                 id="product-url"
//                                 name="p_url"
//                                 value={product.p_url}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product URL"
//                             />
//                             {!product.p_url && <div className="invalid-feedback">Product URL is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="threshold-price">Threshold Price:</label>
//                             <input
//                                 type="number"
//                                 className={`w-100 ${!product.t_price ? 'is-invalid' : ''}`}
//                                 id="threshold-price"
//                                 name="t_price"
//                                 value={product.t_price}
//                                 onChange={handleChange}
//                                 placeholder="Enter Threshold Price"
//                                 required
//                             />
//                             {!product.t_price && <div className="invalid-feedback">Threshold price is required.</div>}
//                         </div>

//                         <div className="form-group fs-5 text-center m-3">
//                             <button type="submit" className="btn-submit">Submit</button>
//                             <button type="reset" className="btn-reset" onClick={() => setProduct({ p_id: '', p_name: '', p_url: '', t_price: '' })}>Reset</button>
//                         </div>
//                     </form>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default EditProduct;







// import React, { useEffect, useState } from 'react';
// import axios from 'axios';

// const EditProduct = ({ session, productId, user }) => {
//     const { sessionData, password } = session;
//     const username = session.username;
//     const [product, setProduct] = useState({
//         p_id: '',
//         p_name: '',
//         p_url: '',
//         t_price: ''
//     });
//     const [message, setMessage] = useState(null);
//     const [searchTerm, setSearchTerm] = useState('');

//     useEffect(() => {
//         const fetchProduct = async () => {
//             try {
//                 const response = await axios.get(`/api/products/${productId}`, {
//                   // Add any necessary headers or authentication here
//                   auth: {
//                       username: session.email,
//                       password:password
//                   }
//               });
//                 setProduct(response.data);
//             } catch (error) {
//                 console.error('Error fetching product data:', error);
//             }
//         };
//         fetchProduct();
//     }, [productId]);

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setProduct((prevProduct) => ({
//             ...prevProduct,
//             [name]: value
//         }));
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             await axios.post('/user/edit-product', product);
//             setMessage({ content: 'Product updated successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Error updating product.', type: 'alert-danger' });
//         }
//     };

//     const handleSearchChange = (e) => {
//         setSearchTerm(e.target.value);
//         // Implement your search logic here
//     };

//     return (
//         <div>
//            <header className="header">
//             <nav className="navbar">
//                 <div className="container">
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="dashboard h-100">
//                 <div style={{ textAlign: 'center', width: '100%', marginLeft: '13%' }} className="dashboard-header">
//                     <h1 id="welcome-heading">Edit Product Info</h1>
//                 </div>
//                 <div className="dashboard-content">
//                     {/* Search Bar */}
//                     <div className="search-container my-3">
//                         <input
//                             type="text"
//                             value={searchTerm}
//                             onChange={handleSearchChange}
//                             className="form-control fs-4 p-3"
//                             placeholder="Search product"
//                         />
//                     </div>

//                     {message && (
//                         <div className={`alert ${message.type} text-center mt-3`} role="alert">
//                             <p className="text-center">{message.content}</p>
//                         </div>
//                     )}

//                     <form id="add-product-form" onSubmit={handleSubmit} style={{ textAlign: 'center' }}>
//                         <input type="hidden" name="p_id" value={product.p_id} />
//                         <div className="form-group fs-4">
//                             <label htmlFor="product-name">Product Name:</label>
//                             <input
//                                 type="text"
//                                 className={`w-50 ${!product.p_name ? 'is-invalid' : ''}`} // Adjusted width for better alignment
//                                 id="product-name"
//                                 name="p_name"
//                                 value={product.p_name}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product Name"
//                             />
//                             {!product.p_name && <div className="invalid-feedback">Product name is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="product-url">Product URL:</label>
//                             <input
//                                 type="url"
//                                 className={`w-50 ${!product.p_url ? 'is-invalid' : ''}`} // Adjusted width for better alignment
//                                 id="product-url"
//                                 name="p_url"
//                                 value={product.p_url}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product URL"
//                             />
//                             {!product.p_url && <div className="invalid-feedback">Product URL is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="threshold-price">Threshold Price:</label>
//                             <input
//                                 type="number"
//                                 className={`w-50 ${!product.t_price ? 'is-invalid' : ''}`} // Adjusted width for better alignment
//                                 id="threshold-price"
//                                 name="t_price"
//                                 value={product.t_price}
//                                 onChange={handleChange}
//                                 placeholder="Enter Threshold Price"
//                                 required
//                             />
//                             {!product.t_price && <div className="invalid-feedback">Threshold price is required.</div>}
//                         </div>

//                         <div className="form-group fs-5 text-center m-3">
//                             <button type="submit" className="btn-submit">Submit</button>
//                             <button type="reset" className="btn-reset" onClick={() => setProduct({ p_id: '', p_name: '', p_url: '', t_price: '' })}>Reset</button>
//                         </div>
//                     </form>
//                 </div>
//             </div>
//             </header>
//         </div>
//     );
// };

// export default EditProduct;




// import React, { useEffect, useState } from 'react';
// import axios from 'axios';

// const EditProduct = ({ session, productId }) => {
//     const { password } = session;
//     const username = session.username;
//     const [product, setProduct] = useState({
//         p_id: '',
//         p_name: '',
//         p_url: '',
//         t_price: ''
//     });
//     const [message, setMessage] = useState(null);
//     const [searchTerm, setSearchTerm] = useState('');

//     useEffect(() => {
//         const fetchProduct = async () => {
//             try {
//                 const response = await axios.get(`/api/products/${productId}`, {
//                     auth: {
//                         username: session.email,
//                         password: password
//                     }
//                 });
//                 setProduct(response.data);
//             } catch (error) {
//                 console.error('Error fetching product data:', error);
//             }
//         };
//         fetchProduct();
//     }, [productId, session.email, password]);

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setProduct((prevProduct) => ({
//             ...prevProduct,
//             [name]: value
//         }));
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             await axios.post('/user/edit-product', product);
//             setMessage({ content: 'Product updated successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Error updating product.', type: 'alert-danger' });
//         }
//     };

//     const handleSearchChange = (e) => {
//         setSearchTerm(e.target.value);
//         // Implement your search logic here
//     };

//     return (
//         <div style={{ background: '#f8f9fa', padding: '20px' }}>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
//      x

//             <div className="dashboard h-100 d-flex flex-column align-items-center justify-content-center">
//                 <div className="dashboard-header">
//                     <h1 id="welcome-heading">Edit Product Info</h1>
//                 </div>
//                 <div style={{ 
//                     width: '100%', 
//                     maxWidth: '800px', 
//                     background: 'white', 
//                     borderRadius: '8px', 
//                     boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)', 
//                     padding: '20px' 
//                 }}>
//                     {/* Search Bar */}
//                     <div className="search-container my-3">
//                         <input
//                             type="text"
//                             value={searchTerm}
//                             onChange={handleSearchChange}
//                             className="form-control fs-4 p-3"
//                             placeholder="Search product"
//                         />
//                     </div>

//                     {message && (
//                         <div className={`alert ${message.type} text-center mt-3`} role="alert">
//                             <p className="text-center">{message.content}</p>
//                         </div>
//                     )}

//                     <form 
//                         id="edit-product-form" 
//                         onSubmit={handleSubmit} 
//                         style={{ textAlign: 'center' }}
//                     >
//                         <input type="hidden" name="p_id" value={product.p_id} />
//                         <div className="form-group fs-4">
//                             <label htmlFor="product-name">Product Name:</label>
//                             <input
//                                 type="text"
//                                 className={`form-control ${!product.p_name ? 'is-invalid' : ''}`}
//                                 id="product-name"
//                                 name="p_name"
//                                 value={product.p_name}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product Name"
//                                 required
//                                 style={{ width: '50%' }}
//                             />
//                             {!product.p_name && <div className="invalid-feedback">Product name is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="product-url">Product URL:</label>
//                             <input
//                                 type="url"
//                                 className={`form-control ${!product.p_url ? 'is-invalid' : ''}`}
//                                 id="product-url"
//                                 name="p_url"
//                                 value={product.p_url}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product URL"
//                                 required
//                                 style={{ width: '50%' }}
//                             />
//                             {!product.p_url && <div className="invalid-feedback">Product URL is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="threshold-price">Threshold Price:</label>
//                             <input
//                                 type="number"
//                                 className={`form-control ${!product.t_price ? 'is-invalid' : ''}`}
//                                 id="threshold-price"
//                                 name="t_price"
//                                 value={product.t_price}
//                                 onChange={handleChange}
//                                 placeholder="Enter Threshold Price"
//                                 required
//                                 style={{ width: '50%' }}
//                             />
//                             {!product.t_price && <div className="invalid-feedback">Threshold price is required.</div>}
//                         </div>

//                         <div className="form-group fs-5 text-center m-3">
//                             <button type="submit" 
//                                 className="btn-submit" 
//                                 style={{ margin: '0 5px', backgroundColor: '#007bff', color: 'white' }}
//                             >
//                                 Submit
//                             </button>
//                             <button type="reset" 
//                                 className="btn-reset" 
//                                 style={{ margin: '0 5px', backgroundColor: '#6c757d', color: 'white' }}
//                                 onClick={() => setProduct({ p_id: '', p_name: '', p_url: '', t_price: '' })}
//                             >
//                                 Reset
//                             </button>
//                         </div>
//                     </form>
//                 </div>
//             </div>
//             </header>
//         </div>
//     );
// };

// export default EditProduct;


// import React, { useEffect, useState } from 'react';
// import axios from 'axios';

// const EditProduct = ({ session, productId }) => {
//     const { password } = session;
//     const username = session.username;
//     const [product, setProduct] = useState({
//         p_id: '',
//         p_name: '',
//         p_url: '',
//         t_price: ''
//     });
//     const [message, setMessage] = useState(null);
//     const [searchTerm, setSearchTerm] = useState('');

//     useEffect(() => {
//         const fetchProduct = async () => {
//             try {
//                 const response = await axios.get(`/api/products/${productId}`, {
//                     auth: {
//                         username: session.email,
//                         password: password
//                     }
//                 });
//                 setProduct(response.data);
//             } catch (error) {
//                 console.error('Error fetching product data:', error);
//             }
//         };
//         fetchProduct();
//     }, [productId, session.email, password]);

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setProduct((prevProduct) => ({
//             ...prevProduct,
//             [name]: value
//         }));
//     };

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             await axios.post('/user/edit-product', product);
//             setMessage({ content: 'Product updated successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Error updating product.', type: 'alert-danger' });
//         }
//     };

//     const handleSearchChange = (e) => {
//         setSearchTerm(e.target.value);
//         // Implement your search logic here
//     };

//     return (
//         <div style={{ background: '#f8f9fa', padding: '20px' }}>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
        

//             <div className="dashboard h-100 d-flex flex-column align-items-center justify-content-center">
//                 <div className="dashboard-header">
//                     <h1 id="welcome-heading">Edit Product Info</h1>
//                 </div>
//                 <div style={{ 
//                     width: '400px', // Set a fixed width for the box
//                     background: 'white', 
//                     borderRadius: '8px', 
//                     boxShadow: '0 2px 10px rgba(0, 0, 0, 0.1)', 
//                     padding: '20px' 
//                 }}>
//                     {/* Search Bar */}
//                     <div className="search-container my-3">
//                         <input
//                             type="text"
//                             value={searchTerm}
//                             onChange={handleSearchChange}
//                             className="form-control fs-4 p-3"
//                             placeholder="Search product"
//                         />
//                     </div>

//                     {message && (
//                         <div className={`alert ${message.type} text-center mt-3`} role="alert">
//                             <p className="text-center">{message.content}</p>
//                         </div>
//                     )}

//                     <form 
//                         id="edit-product-form" 
//                         onSubmit={handleSubmit} 
//                         style={{ textAlign: 'center' }}
//                     >
//                         <input type="hidden" name="p_id" value={product.p_id} />
//                         <div className="form-group fs-4">
//                             <label htmlFor="product-name">Product Name:</label>
//                             <input
//                                 type="text"
//                                 className={`form-control ${!product.p_name ? 'is-invalid' : ''}`}
//                                 id="product-name"
//                                 name="p_name"
//                                 value={product.p_name}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product Name"
//                                 required
//                                 style={{ width: '100%' }} // Use full width for input
//                             />
//                             {!product.p_name && <div className="invalid-feedback">Product name is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="product-url">Product URL:</label>
//                             <input
//                                 type="url"
//                                 className={`form-control ${!product.p_url ? 'is-invalid' : ''}`}
//                                 id="product-url"
//                                 name="p_url"
//                                 value={product.p_url}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product URL"
//                                 required
//                                 style={{ width: '100%' }} // Use full width for input
//                             />
//                             {!product.p_url && <div className="invalid-feedback">Product URL is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="threshold-price">Threshold Price:</label>
//                             <input
//                                 type="number"
//                                 className={`form-control ${!product.t_price ? 'is-invalid' : ''}`}
//                                 id="threshold-price"
//                                 name="t_price"
//                                 value={product.t_price}
//                                 onChange={handleChange}
//                                 placeholder="Enter Threshold Price"
//                                 required
//                                 style={{ width: '100%' }} // Use full width for input
//                             />
//                             {!product.t_price && <div className="invalid-feedback">Threshold price is required.</div>}
//                         </div>

//                         <div className="form-group fs-5 d-flex justify-content-center m-3">
//                             <button type="submit" 
//                                 className="btn-submit" 
//                                 style={{ margin: '0 5px', backgroundColor: '#007bff', color: 'white' }}
//                             >
//                                 Submit
//                             </button>
//                             <button type="reset" 
//                                 className="btn-reset" 
//                                 style={{ margin: '0 5px', backgroundColor: '#6c757d', color: 'white' }}
//                                 onClick={() => setProduct({ p_id: '', p_name: '', p_url: '', t_price: '' })}
//                             >
//                                 Reset
//                             </button>
//                         </div>
//                     </form>
//                 </div>
//             </div>
//             </header>
//         </div>
//     );
// };

// export default EditProduct;




// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Import the CSS file

// const EditProduct = ({ session, productId }) => {
//     const { password } = session;
//     const username = session.username;
//     const [product, setProduct] = useState({
//         p_id: '',
//         p_name: '',
//         p_url: '',
//         t_price: ''
//     });
//     const [message, setMessage] = useState(null);
//     const [searchTerm, setSearchTerm] = useState('');

//     useEffect(() => {
//         const fetchProduct = async () => {
//             try {
//                 const response = await axios.get(`http://localhost:8080/user/edit-products/${productId}`, {
//                     auth: {
//                         username: session.email,
//                         password: password
//                     }
//                 });
//                 setProduct(response.data);
//             } catch (error) {
//                 console.error('Error fetching product data:', error);
//             }
//         };
//         fetchProduct();
//     }, [productId, session.email, password]);

//     const handleChange = (e) => {
//         const { name, value } = e.target;
//         setProduct((prevProduct) => ({
//             ...prevProduct,
//             [name]: value
//         }));
//     };

//     // const handleSubmit = async (e) => {
//     //     e.preventDefault();
//     //     try {
//     //         await axios.post('http://localhost:8080/user/edit-products', product);
//     //         setMessage({ content: 'Product updated successfully!', type: 'alert-success' });
//     //     } catch (error) {
//     //         setMessage({ content: 'Error updating product.', type: 'alert-danger' });
//     //     }
//     // };



//   //   const handleSubmit = async (e) => {
//   //     e.preventDefault(); // Prevent the default form submission behavior
//   //     try {
//   //         const response = await axios.post(`http://localhost:8080/user/edit-products`, product, {
//   //             auth: {
//   //                 username: session.email,
//   //                 password: password,
//   //             },
//   //         });
//   //         // Update the message state with the response message
//   //         setMessage({ content: response.data.content, type: response.data.type });
//   //     } catch (error) {
//   //         console.error('Error updating product:', error);
//   //         setMessage({ content: error.response ? error.response.data : 'Error updating product.', type: 'alert-danger' });
//   //     }
//   // };



//   const handleSubmit = async (e) => {
//     e.preventDefault();
//     try {
//       const response = await axios.post('/edit-product', formData, {
//         headers: {
//           'Content-Type': 'application/json',
//         },
//       });

//       if (response.status === 200) {
//         setMessage('Product successfully updated.');
//       }
//     } catch (error) {
//       if (error.response) {
//         // Handle HTTP error codes
//         if (error.response.status === 403) {
//           setMessage('You are not authorized to modify this product.');
//         } else if (error.response.status === 500) {
//           setMessage('An error occurred while modifying the product.');
//         } else {
//           setMessage('Something went wrong! Please try again.');
//         }
//       } else {
//         setMessage('Network error, please try again later.');
//       }
//     }
//   };


  
  
//     const handleSearchChange = (e) => {
//         setSearchTerm(e.target.value);
//         // Implement your search logic here
//     };

//     return (
//         <div className="edit-product-container">
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                             <li className="nav-item"><a href="/user/product_list1">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
        

//             <div className="dashboard">
//                 <div className="dashboard-header">
//                     <h1 id="welcome-heading">Edit Product Info</h1>
//                 </div>
//                 <div className="form-container">
//                     {/* Search Bar */}
//                     <div className="search-container my-3">
//                         <input
//                             type="text"
//                             value={searchTerm}
//                             onChange={handleSearchChange}
//                             className="form-control fs-4 p-3"
//                             placeholder="Search product"
//                         />
//                     </div>

//                     {message && (
//                         <div className={`alert ${message.type} text-center mt-3`} role="alert">
//                             <p className="text-center">{message.content}</p>
//                         </div>
//                     )}

//                     <form id="edit-product-form" onSubmit={handleSubmit} style={{ textAlign: 'center' }}>
//                         <input type="hidden" name="p_id" value={product.p_id} />
//                         <div className="form-group fs-4">
//                             <label htmlFor="product-name">Product Name:</label>
//                             <input
//                                 type="text"
//                                 className={`form-control ${!product.p_name ? 'is-invalid' : ''}`}
//                                 id="product-name"
//                                 name="p_name"
//                                 value={product.p_name}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product Name"
//                                 required
//                             />
//                             {!product.p_name && <div className="invalid-feedback">Product name is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="product-url">Product URL:</label>
//                             <input
//                                 type="url"
//                                 className={`form-control ${!product.p_url ? 'is-invalid' : ''}`}
//                                 id="product-url"
//                                 name="p_url"
//                                 value={product.p_url}
//                                 onChange={handleChange}
//                                 placeholder="Enter Amazon Product URL"
//                                 required
//                             />
//                             {!product.p_url && <div className="invalid-feedback">Product URL is required.</div>}
//                         </div>

//                         <div className="form-group fs-4">
//                             <label htmlFor="threshold-price">Threshold Price:</label>
//                             <input
//                                 type="number"
//                                 className={`form-control ${!product.t_price ? 'is-invalid' : ''}`}
//                                 id="threshold-price"
//                                 name="t_price"
//                                 value={product.t_price}
//                                 onChange={handleChange}
//                                 placeholder="Enter Threshold Price"
//                                 required
//                             />
//                             {!product.t_price && <div className="invalid-feedback">Threshold price is required.</div>}
//                         </div>

//                         <div className="form-group fs-5 d-flex justify-content-center m-3">
//                             <button type="submit" className="btn btn-primary">Submit</button>
//                             <button type="reset" className="btn btn-secondary" onClick={() => setProduct({ p_id: '', p_name: '', p_url: '', t_price: '' })}>
//                                 Reset
//                             </button>
//                         </div>
//                     </form>
//                 </div>
//             </div>
//             </header>
//         </div>
//     );
// };

// export default EditProduct;






import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './style.css'; // Import the CSS file

// const EditProduct = ({ session, productId }) => {
    const EditProduct = ({ session, productId, productPrice }) => {

    const { password } = session;
    const username = session.username;
    const baseUrl = process.env.REACT_APP_API_BASE_URL;
    console.log("API base URL:", baseUrl); // Debug log

    const [product, setProduct] = useState({
        p_id: '',
        p_name: '',
        p_url: '',
        t_price: ''
    });
    const [message, setMessage] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        const fetchProduct = async () => {
            try {
                //const response = await axios.get(`http://localhost:8080/user/edit-products/${productId}`, {
                   // const response = await axios.get(`http://localhost:8080/user/edit-products/${productId}/${productPrice}`, {
                    const response = await axios.get(`${baseUrl}/user/edit-products/${productId}/${productPrice}`, {
                    auth: {
                        username: session.email,
                        password: password
                    },
                    withCredentials: true, // Include credentials (cookies) with the request
                });
                setProduct(response.data);
            } catch (error) {
                console.error('Error fetching product data:', error);
            }
        };
        fetchProduct();
    }, [productId, session.email, password]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setProduct((prevProduct) => ({
            ...prevProduct,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            //const response = await axios.post(`http://localhost:8080/user/edit-products/`, product, {
                //const response = await axios.post(`http://localhost:8080/user/edit-products/${productPrice}`, product, {
                    const response = await axios.post(`${baseUrl}/user/edit-products/${productPrice}`, product, {
                auth: {
                    username: session.email,
                    password: password
                }
            });

            if (response.status === 200) {
                setMessage({ content: 'Product successfully updated.', type: 'alert-success' });
            }
        } catch (error) {
            if (error.response) {
                // Handle HTTP error codes
                if (error.response.status === 403) {
                  console.log('useremail',session.email);
                  console.log('passwd',password);
                    setMessage({ content: 'You are not authorized to modify this product.', type: 'alert-danger' });
                } else if (error.response.status === 500) {
                  console.log(error.response.data);
                  console.log(product);
                    setMessage({ content: 'An error occurred while modifying the product.', type: 'alert-danger' });
                } else {
                    setMessage({ content: 'Something went wrong! Please try again.', type: 'alert-danger' });
                }
            } else {
                setMessage({ content: 'Network error, please try again later.', type: 'alert-danger' });
            }
        }
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        // Implement your search logic here (optional)
    
    };

    return (
        <div className="edit-product-container">
            <header className="header">
                <nav className="navbar">
                    <div className="container">
                        <span className="logo lg-heading mt-3">{username}</span>
                        <ul>
                            <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
                            <li className="nav-item"><a href="/user/product_list1">Product List</a></li>
                            <li className="nav-item"><a href="/logout">Logout</a></li>
                        </ul>
                    </div>
                </nav>

                <div className="dashboard">
                    <div className="dashboard-header">
                        <h1 id="welcome-heading">Edit Product Info</h1>
                    </div>
                    <div className="form-container">
                        {/* Search Bar */}
                        {/* <div className="search-container my-3">
                            <input
                                type="text"
                                value={searchTerm}
                                onChange={handleSearchChange}
                                className="form-control fs-4 p-3"
                                placeholder="Search product"
                            />
                        </div> */}

                        {message && (
                            <div className={`alert ${message.type} text-center mt-3`} role="alert">
                                <p className="text-center">{message.content}</p>
                            </div>
                        )}

                        <form id="edit-product-form" onSubmit={handleSubmit} style={{ textAlign: 'center' }}>
                            <input type="hidden" name="p_id" value={product.p_id} />
                            <div className="form-group fs-4">
                                <label htmlFor="product-name">Product Name:</label>
                                <input
                                    type="text"
                                    className={`form-control ${!product.p_name ? 'is-invalid' : ''}`}
                                    id="product-name"
                                    name="p_name"
                                    value={product.p_name}
                                    onChange={handleChange}
                                    placeholder="Enter Product Name"
                                    required
                                />
                                {!product.p_name && <div className="invalid-feedback">Product name is required.</div>}
                            </div>

                            <div className="form-group fs-4">
                                <label htmlFor="product-url">Product URL:</label>
                                <input
                                    type="url"
                                    className={`form-control ${!product.p_url ? 'is-invalid' : ''}`}
                                    id="product-url"
                                    name="p_url"
                                    value={product.p_url}
                                    onChange={handleChange}
                                    placeholder="Enter Product URL"
                                    required
                                />
                                {!product.p_url && <div className="invalid-feedback">Product URL is required.</div>}
                            </div>

                            <div className="form-group fs-4">
                                <label htmlFor="threshold-price">Threshold Price:</label>
                                <input
                                    type="number"
                                    className={`form-control ${!product.t_price ? 'is-invalid' : ''}`}
                                    id="threshold-price"
                                    name="t_price"
                                    value={product.t_price}
                                    onChange={handleChange}
                                    placeholder="Enter Threshold Price"
                                    required
                                />
                                {!product.t_price && <div className="invalid-feedback">Threshold price is required.</div>}
                            </div>

                            <div className="form-group fs-5 d-flex justify-content-center m-3">
                                <button type="submit" className="btn btn-primary">Submit</button>
                                <button type="reset" className="btn btn-secondary" onClick={() => setProduct({ p_id: '', p_name: '', p_url: '', t_price: '' })}>
                                    Reset
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </header>
        </div>
    );
};

export default EditProduct;

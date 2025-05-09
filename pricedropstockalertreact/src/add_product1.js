// import React, { useState } from 'react';
// import './style.css';
// import 'bootstrap/dist/css/bootstrap.min.css';

// const AddProduct1 = ({ user }) => {
//   const [productUrl, setProductUrl] = useState('');
//   const [thresholdPrice, setThresholdPrice] = useState('');
//   const [message, setMessage] = useState(null);

//   const handleSubmit = (e) => {
//     e.preventDefault();
//     // Here you would typically send the form data to your backend
//     // Example: using fetch or axios
//     // For demonstration, we'll just set a success message
//     setMessage({ content: 'Product added successfully!', type: 'alert-success' });
//     // Clear the input fields after submission
//     setProductUrl('');
//     setThresholdPrice('');
//   };

//   const handleReset = () => {
//     setProductUrl('');
//     setThresholdPrice('');
//     setMessage(null);
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
//         <div style={{ textAlign: 'center', width: '100%', marginLeft: '13%' }} className="dashboard-header">
//           <h1 id="welcome-heading">Add Product</h1>
//         </div>
//         <div className="dashboard-content">
//           <div id="add-product" className="tab-content active">
//             {message && (
//               <div className={`alert ${message.type} text-center`} role="alert">
//                 <p className="text-center">{message.content}</p>
//               </div>
//             )}
//             <form id="add-product-form" onSubmit={handleSubmit}>
//               <br />
//               <div className="form-group fs-4">
//                 <label htmlFor="product-url">Product URL:</label>
//                 <input
//                   type="url"
//                   className="w-100"
//                   id="product-url"
//                   name="p_url"
//                   value={productUrl}
//                   onChange={(e) => setProductUrl(e.target.value)}
//                   placeholder="Enter Amazon Product URL"
//                   required
//                 />
//               </div>

//               <div className="form-group fs-4">
//                 <label htmlFor="threshold-price">Threshold Price:</label>
//                 <input
//                   type="number"
//                   className="w-100"
//                   id="threshold-price"
//                   name="t_price"
//                   value={thresholdPrice}
//                   onChange={(e) => setThresholdPrice(e.target.value)}
//                   placeholder="Enter Threshold Price"
//                   required
//                 />
//               </div>

//               <div className="form-group fs-5 text-center m-3">
//                 <button type="submit" className="btn btn-primary">Submit</button>
//                 <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
//               </div>
//             </form>
//           </div>
//         </div>
//       </div>
//     </div>
//   );
// };

// export default AddProduct1;







// import React, { useState } from 'react';
// import axios from 'axios';
// import './style.css';
// import 'bootstrap/dist/css/bootstrap.min.css';

// const AddProduct1 = ({ user }) => {
//     const [productUrl, setProductUrl] = useState('');
//     const [thresholdPrice, setThresholdPrice] = useState('');
//     const [message, setMessage] = useState(null);

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             // Send product data to backend
//             await axios.post('/user/added-product', {
//                 p_url: productUrl,
//                 t_price: thresholdPrice,
//             });
//             setMessage({ content: 'Product added successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Failed to add product. Please try again.', type: 'alert-danger' });
//         }
//         // Clear the input fields after submission
//         setProductUrl('');
//         setThresholdPrice('');
//     };

//     const handleReset = () => {
//         setProductUrl('');
//         setThresholdPrice('');
//         setMessage(null);
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container">
//                     <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="dashboard h-100">
//                 <div style={{ textAlign: 'center', width: '100%', marginLeft: '13%' }} className="dashboard-header">
//                     <h1 id="welcome-heading">Add Product</h1>
//                 </div>
//                 <div className="dashboard-content">
//                     <div id="add-product" className="tab-content active">
//                         {message && (
//                             <div className={`alert ${message.type} text-center`} role="alert">
//                                 <p className="text-center">{message.content}</p>
//                             </div>
//                         )}
//                         <form id="add-product-form" onSubmit={handleSubmit}>
//                             <br />
//                             <div className="form-group fs-4">
//                                 <label htmlFor="product-url">Product URL:</label>
//                                 <input
//                                     type="url"
//                                     className="w-100"
//                                     id="product-url"
//                                     name="p_url"
//                                     value={productUrl}
//                                     onChange={(e) => setProductUrl(e.target.value)}
//                                     placeholder="Enter Product URL"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-4">
//                                 <label htmlFor="threshold-price">Threshold Price:</label>
//                                 <input
//                                     type="number"
//                                     className="w-100"
//                                     id="threshold-price"
//                                     name="t_price"
//                                     value={thresholdPrice}
//                                     onChange={(e) => setThresholdPrice(e.target.value)}
//                                     placeholder="Enter Threshold Price"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-5 text-center m-3">
//                                 <button type="submit" className="btn btn-primary">Submit</button>
//                                 <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
//                             </div>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default AddProduct1;









// import React, { useState } from 'react';
// import axios from 'axios';
// import './style.css';
// import 'bootstrap/dist/css/bootstrap.min.css';

// const AddProduct1 = ({ user= {} }) => {
//     const username = user?.username ? user.username.toUpperCase() : 'Guest';

//     const [productUrl, setProductUrl] = useState('');
//     const [thresholdPrice, setThresholdPrice] = useState('');
//     // const[username, setUsername] = useState('');
//     const [message, setMessage] = useState(null);

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             // Send product data to backend
//             const response = await axios.post('/user/added-product', {
//                 p_url: productUrl,
//                 t_price: thresholdPrice,
//             });
//             setMessage({ content: 'Product added successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Failed to add product. Please try again.', type: 'alert-danger' });
//         }
//         // Clear the input fields after submission
//         setProductUrl('');
//         setThresholdPrice('');
//     };

//     const handleReset = () => {
//         setProductUrl('');
//         setThresholdPrice('');
//         setMessage(null);
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container">
//                     {/* <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span> */}
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
//                     <h1 id="welcome-heading">Add Product</h1>
//                 </div>
//                 <div className="dashboard-content">
//                     <div id="add-product" className="tab-content active">
//                         {message && (
//                             <div className={`alert ${message.type} text-center`} role="alert">
//                                 <p className="text-center">{message.content}</p>
//                             </div>
//                         )}
//                         <form id="add-product-form" onSubmit={handleSubmit}>
//                             <br />
//                             <div className="form-group fs-4">
//                                 <label htmlFor="product-url">Product URL:</label>
//                                 <input
//                                     type="url"
//                                     className="w-100"
//                                     id="product-url"
//                                     name="p_url"
//                                     value={productUrl}
//                                     onChange={(e) => setProductUrl(e.target.value)}
//                                     placeholder="Enter Amazon Product URL"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-4">
//                                 <label htmlFor="threshold-price">Threshold Price:</label>
//                                 <input
//                                     type="number"
//                                     className="w-100"
//                                     id="threshold-price"
//                                     name="t_price"
//                                     value={thresholdPrice}
//                                     onChange={(e) => setThresholdPrice(e.target.value)}
//                                     placeholder="Enter Threshold Price"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-5 text-center m-3">
//                                 <button type="submit" className="btn btn-primary">Submit</button>
//                                 <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
//                             </div>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default AddProduct1;








// import React, { useState } from 'react';
// import axios from 'axios';
// import './style.css';
// import 'bootstrap/dist/css/bootstrap.min.css';

// const AddProduct1 = ({ user = {} }) => {
//     const username = user?.username ? user.username.toUpperCase() : 'Guest';

//     const [productUrl, setProductUrl] = useState('');
//     const [thresholdPrice, setThresholdPrice] = useState('');
//     const [message, setMessage] = useState(null);

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             const response = await axios.post('http://localhost:8080/user/added-product', {
//                 p_url: productUrl,
//                 t_price: thresholdPrice,
//             }, {
//                 auth: {
//                     username: user.username,
//                     password: user.password // Ensure you pass the password correctly from the session
//                 }
//             });

//             setMessage({ content: 'Product added successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Failed to add product. Please try again.', type: 'alert-danger' });
//         }
        
//         // Clear the input fields after submission
//         setProductUrl('');
//         setThresholdPrice('');
//     };

//     const handleReset = () => {
//         setProductUrl('');
//         setThresholdPrice('');
//         setMessage(null);
//     };

//     return (
//         <div>
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
//                     <h1 id="welcome-heading">Add Product</h1>
//                 </div>
//                 <div className="dashboard-content">
//                     <div id="add-product" className="tab-content active">
//                         {message && (
//                             <div className={`alert ${message.type} text-center`} role="alert">
//                                 <p className="text-center">{message.content}</p>
//                             </div>
//                         )}
//                         <form id="add-product-form" onSubmit={handleSubmit}>
//                             <br />
//                             <div className="form-group fs-4">
//                                 <label htmlFor="product-url">Product URL:</label>
//                                 <input
//                                     type="url"
//                                     className="w-100"
//                                     id="product-url"
//                                     name="p_url"
//                                     value={productUrl}
//                                     onChange={(e) => setProductUrl(e.target.value)}
//                                     placeholder="Enter Amazon Product URL"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-4">
//                                 <label htmlFor="threshold-price">Threshold Price:</label>
//                                 <input
//                                     type="number"
//                                     className="w-100"
//                                     id="threshold-price"
//                                     name="t_price"
//                                     value={thresholdPrice}
//                                     onChange={(e) => setThresholdPrice(e.target.value)}
//                                     placeholder="Enter Threshold Price"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-5 text-center m-3">
//                                 <button type="submit" className="btn btn-primary">Add Product</button>
//                                 <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
//                             </div>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default AddProduct1;






// import React, { useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Ensure you import your styles
// import 'bootstrap/dist/css/bootstrap.min.css';

// const AddProduct1 = ({ user = {} }) => {
//     const username = user?.username ? user.username.toUpperCase() : 'Guest';

//     const [productUrl, setProductUrl] = useState('');
//     const [thresholdPrice, setThresholdPrice] = useState('');
//     const [message, setMessage] = useState(null);

//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             const response = await axios.post('http://localhost:8080/user/added-product', {
//                 p_url: productUrl,
//                 t_price: thresholdPrice,
//             }, {
//                 auth: {
//                     username: user.username,
//                     password: user.password // Ensure you pass the password correctly from the session
//                 }
//             });

//             setMessage({ content: 'Product added successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Failed to add product. Please try again.', type: 'alert-danger' });
//         }
        
//         // Clear the input fields after submission
//         setProductUrl('');
//         setThresholdPrice('');
//     };

//     const handleReset = () => {
//         setProductUrl('');
//         setThresholdPrice('');
//         setMessage(null);
//     };

//     return (
//         <div>
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
//                     <h1 id="welcome-heading">Add Product</h1>
//                 </div>
//                 <div className="dashboard-content">
//                     <div id="add-product" className="tab-content active">
//                         {message && (
//                             <div className={`alert ${message.type} text-center`} role="alert">
//                                 <p className="text-center">{message.content}</p>
//                             </div>
//                         )}
//                         <form id="add-product-form" onSubmit={handleSubmit}>
//                             <br />
//                             <div className="form-group fs-4">
//                                 <label htmlFor="product-url">Product URL:</label>
//                                 <input
//                                     type="url"
//                                     className="w-100"
//                                     id="product-url"
//                                     name="p_url"
//                                     value={productUrl}
//                                     onChange={(e) => setProductUrl(e.target.value)}
//                                     placeholder="Enter Amazon Product URL"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-4">
//                                 <label htmlFor="threshold-price">Threshold Price:</label>
//                                 <input
//                                     type="number"
//                                     className="w-100"
//                                     id="threshold-price"
//                                     name="t_price"
//                                     value={thresholdPrice}
//                                     onChange={(e) => setThresholdPrice(e.target.value)}
//                                     placeholder="Enter Threshold Price"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-5 text-center m-3">
//                                 <button type="submit" className="btn btn-primary">Add Product</button>
//                                 <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
//                             </div>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default AddProduct1;



// import React, { useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Assuming you have the corresponding CSS file
// import 'bootstrap/dist/css/bootstrap.min.css';
// //import bgimg from './img/bgimg.jpg'; // Adjust the path as needed

// const AddProduct1 = ({ session }) => { // Accept session prop
//     const [sessionMessage, setSessionMessage] = useState(null);
//     const [productUrl, setProductUrl] = useState('');
//     const [thresholdPrice, setThresholdPrice] = useState('');
//     const [message, setMessage] = useState(null);

//     const removeMessage = () => {
//         setSessionMessage(null);
    

//     // Extract the username from the session data passed as a prop
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'

//     //const username = user?.username ? user.username.toUpperCase() : 'Guest';


//     const handleSubmit = async (e) => {
//         e.preventDefault();
//         try {
//             const response = await axios.post('http://localhost:8080/user/added_product', {
//                 p_url: productUrl,
//                 t_price: thresholdPrice,
//             }, {
//                 auth: {
//                     username: user.username,
//                     password: user.password // Ensure you pass the password correctly from the session
//                 }
//             });

//             setMessage({ content: 'Product added successfully!', type: 'alert-success' });
//         } catch (error) {
//             setMessage({ content: 'Failed to add product. Please try again.', type: 'alert-danger' });
//         }
        
//         // Clear the input fields after submission
//         setProductUrl('');
//         setThresholdPrice('');
//     };

//     const handleReset = () => {
//         setProductUrl('');
//         setThresholdPrice('');
//         setMessage(null);
//     };

//     return (
//         <div style={{ 
//             background: 'linear-gradient(rgba(0,0,0,0.05), rgba(0,0,0,0.05)), url(./img/bgimg.jpg)', 
//             backgroundSize: 'cover',
//             backgroundPosition: 'center',
//             height: '100vh' // Adjust as necessary
//         }}>
//             <nav className="navbar">
//                 <div className="container">
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/product_list1">Product List</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="dashboard h-100">
//                 <div style={{ textAlign: 'center', width: '100%', marginLeft: '13%' }} className="dashboard-header">
//                     <h1 id="welcome-heading">Add Product</h1>
//                 </div>
//                 <div className="dashboard-content">
//                     <div id="add-product" className="tab-content active">
//                         {message && (
//                             <div className={`alert ${message.type} text-center`} role="alert">
//                                 <p className="text-center">{message.content}</p>
//                             </div>
//                         )}
//                         <form id="add-product-form" onSubmit={handleSubmit}>
//                             <br />
//                             <div className="form-group fs-4">
//                                 <label htmlFor="product-url">Product URL:</label>
//                                 <input
//                                     type="url"
//                                     className="w-100"
//                                     id="product-url"
//                                     name="p_url"
//                                     value={productUrl}
//                                     onChange={(e) => setProductUrl(e.target.value)}
//                                     placeholder="Enter Amazon Product URL"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-4">
//                                 <label htmlFor="threshold-price">Threshold Price:</label>
//                                 <input
//                                     type="number"
//                                     className="w-100"
//                                     id="threshold-price"
//                                     name="t_price"
//                                     value={thresholdPrice}
//                                     onChange={(e) => setThresholdPrice(e.target.value)}
//                                     placeholder="Enter Threshold Price"
//                                     required
//                                 />
//                             </div>

//                             <div className="form-group fs-5 text-center m-3">
//                                 <button type="submit" className="btn btn-primary">Add Product</button>
//                                 <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
//                             </div>
//                         </form>
//                     </div>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default AddProduct1;




import React, { useState } from 'react';
import axios from 'axios';
import './style.css'; // Assuming you have the corresponding CSS file
import 'bootstrap/dist/css/bootstrap.min.css';

const AddProduct1 = ({ session }) => { // Accept session prop
    const { sessionData, password } = session;
    const [productUrl, setProductUrl] = useState('');
    const [thresholdPrice, setThresholdPrice] = useState('');
    const baseUrl = process.env.REACT_APP_API_BASE_URL;
    console.log("API base URL:", baseUrl); // Debug log
    const [message, setMessage] = useState(null);
 
    console.log(session);


    // Extract the username from the session data passed as a prop
    const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//console.log(session);
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
           // const response = await axios.post('http://localhost:8080/user/added-products', {
            const response = await axios.post(`${baseUrl}/user/added-products`, {
                p_url: productUrl,
                t_price: thresholdPrice,
            }, {
                auth: {
                    username: session.email, // Use username from session
                    password: password, // Ensure you pass the password correctly from the session
                },
            });

            setMessage({ content: 'Product added successfully!', type: 'alert-success' });
        } catch (error) {
            setMessage({ content: 'Failed to add product. Please try again.', type: 'alert-danger' });
        }
        
        // Clear the input fields after submission
        setProductUrl('');
        setThresholdPrice('');
    };

    const handleReset = () => {
        setProductUrl('');
        setThresholdPrice('');
        setMessage(null);
    };

    return (
        <div>
             <header className="header">
            <nav className="navbar">
                <div className="container" >
                    <span className="logo lg-heading mt-3">{username}</span>
                    <ul>
                        <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
                        <li className="nav-item"><a href="/user/product_list1">Product List</a></li>
                        <li className="nav-item"><a href="/logout">Logout</a></li>
                    </ul>
                </div>
            </nav>

            <div className="dashboard h-50">
                <div style={{ textAlign: 'center', width: '100%', marginLeft: '13%' }} className="dashboard-header">
                    <h1 id="welcome-heading">Add Product</h1>
                </div>
                <div className="dashboard-content">
                    <div id="add-product" className="tab-content active">
                        {message && (
                            <div className={`alert ${message.type} text-center`} role="alert">
                                <p className="text-center">{message.content}</p>
                            </div>
                        )}
                        <form id="add-product-form" onSubmit={handleSubmit}>
                            <br />
                            <div className="form-group fs-4">
                                <label htmlFor="product-url">Product URL:</label>
                                <input
                                    type="url"
                                    className="w-100"
                                    id="product-url"
                                    name="p_url"
                                    value={productUrl}
                                    onChange={(e) => setProductUrl(e.target.value)}
                                    placeholder="Enter Amazon Product URL"
                                    required
                                />
                            </div>

                            <div className="form-group fs-4">
                                <label htmlFor="threshold-price">Threshold Price:</label>
                                <input
                                    type="number"
                                    className="w-100"
                                    id="threshold-price"
                                    name="t_price"
                                    value={thresholdPrice}
                                    onChange={(e) => setThresholdPrice(e.target.value)}
                                    placeholder="Enter Threshold Price"
                                    required
                                />
                            </div>

                            {/* <div className="form-group fs-5 text-center m-3">
                                <button type="submit" className="btn btn-primary">Add Product</button>
                                <button type="button" onClick={handleReset} className="btn btn-secondary">Reset</button>
                            </div> */}
   <div className="form-group d-flex justify-content-center m-3">
    {/* <button type="submit" className="btn btn-success me-2">Add Product</button>
    <button type="button" onClick={handleReset} className="btn btn-danger">Reset</button> */}
    <button type="submit" style={{ backgroundColor: '#28a745', color: '#fff' }} className="btn me-2">Add Product</button>
<button type="button" onClick={handleReset} style={{ backgroundColor: '#dc3545', color: '#fff' }} className="btn">Reset</button>

</div>


                        </form>
                    </div>
                </div>
            </div>
            </header>
        </div>
    );
};

export default AddProduct1;

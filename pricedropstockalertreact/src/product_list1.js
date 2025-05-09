// import React from 'react';
// import './style.css'; // Assuming you have the corresponding CSS file

// //function ProductList({ products, user,session }) 
// function ProductList1({ products,user, session }) 
// {
//     const handleDelete = (productId) => {
//         // Implement your delete logic here
//         console.log(`Delete product with ID: ${productId}`);
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container">
//                    {/* <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span>  */}
//                   <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span> 
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {session?.message && (
//                   <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
//                         <p className="text-center">{session.message.content}</p>
//                     </div>
//                 )
//             }
//                 <div className="product-list-wrapper">
//                     {products.map((u_list) => (
//                         <div key={u_list.p_id} className="product-card">
//                             <img
//                                // src={productService.getImageUrl(UrlCoding.extractProductName(u_list.p_url))}
//                                 alt="Product Image"
//                             />
//                             <h3>{u_list.p_name}</h3>
//                             <h4 className="text-success">{u_list.t_price}</h4>
//                             <s>
//                                 {/* <h4 className="text-danger">{productService.getCurrentPrice(UrlCoding.extractProductName(u_list.p_url))}</h4> */}
//                             </s>
//                             <div className="btn-container">
//                                 <a className="btn btn-primary btn-block w-50" href={`/user/editproduct/${u_list.p_id}`}>Edit</a>
//                                 <button
//                                     className="btn btn-danger btn-block w-50"
//                                     onClick={() => handleDelete(u_list.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// }

// export default ProductList1;




// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Add your styles here
// import 'bootstrap/dist/css/bootstrap.min.css';

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     //console.log(session);
//     const [productList, setProductList] = useState([]);
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         fetchProducts();
//     }, []);

//     const fetchProducts = async () => {
//         try {
//             const response = await axios.get('/user/product-list'); // Adjust the endpoint as necessary
//             setProductList(response.data);
//         } catch (error) {
//             setMessage({ content: 'Failed to fetch product list.', type: 'alert-danger' });
//         }
//     };

//     const handleDelete = async (productId) => {
//         try {
//             await axios.delete(`/user/deleteproduct/${productId}`);
//             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
//             fetchProducts(); // Refresh product list after deletion
//         } catch (error) {
//             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     {/* <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span> */}
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}
//                 <div className="product-list-wrapper">
//                     {productList.map((product) => (
//                         <div key={product.p_id} className="product-card">
//                             <img
//                                 //src={productService.getImageUrl(UrlCoding.extractProductName(product.p_url))}
//                                 alt="Product"
//                             />
//                             <h3>{product.p_name}</h3>
//                             <h4 className="text-success">{product.t_price}</h4>
//                             {/* <s><h4 className="text-danger">{productService.getCurrentPrice(UrlCoding.extractProductName(product.p_url))}</h4></s> */}
//                             <div className="btn-container">
//                                 <a className="btn btn-primary btn btn-block w-50" href={`/user/editproduct/${product.p_id}`}>Edit</a>
//                                 <button
//                                     className="btn btn-danger btn btn-block w-50"
//                                     onClick={() => handleDelete(product.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;





// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Add your styles here
// import 'bootstrap/dist/css/bootstrap.min.css';

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     const [productList, setProductList] = useState(session.product || []); // Initialize from session data
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         // Fetch products only if there are no products in the session data
//         if (productList.length === 0) {
//             fetchProducts();
//         }
//     }, [productList]);

//     const fetchProducts = async () => {
//         try {
//             const response = await axios.get('/user/product-list'); // Adjust the endpoint as necessary
//             setProductList(response.data);
//         } catch (error) {
//             setMessage({ content: 'Failed to fetch product list.', type: 'alert-danger' });
//         }
//     };

//     const handleDelete = async (productId) => {
//         try {
//             await axios.delete(`/user/deleteproduct/${productId}`);
//             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
//             fetchProducts(); // Refresh product list after deletion
//         } catch (error) {
//             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}
//                 <div className="product-list-wrapper">
//                     {productList.map((product) => (
//                         <div key={product.p_id} className="product-card">
//                             <img
//                                 src={product.p_url} // Use product image URL if needed
//                                 alt="Product"
//                             />
//                             <h3>{product.p_name}</h3>
//                             <h4 className="text-success">{product.t_price}</h4>
//                             <div className="btn-container">
//                                 <a className="btn btn-primary btn btn-block w-50" href={`/user/editproduct/${product.p_id}`}>Edit</a>
//                                 <button
//                                     className="btn btn-danger btn btn-block w-50"
//                                     onClick={() => handleDelete(product.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;



// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Add your styles here
// import 'bootstrap/dist/css/bootstrap.min.css';

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     const [productList, setProductList] = useState(session.product || []); // Initialize from session data
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         // Fetch products only if there are no products in the session data
//         if (productList.length === 0) {
//             fetchProducts();
//         }
//     }, [productList]);

//     const fetchProducts = async () => {
//         try {
//             const response = await axios.get('http://localhost:8080/user/product-list'); // Adjust the endpoint as necessary
//             setProductList(response.data);
//         } catch (error) {
//             setMessage({ content: 'Failed to fetch product list.', type: 'alert-danger' });
//         }
//     };

//     const handleDelete = async (productId) => {
//         try {
//             await axios.delete(`/user/deleteproduct/${productId}`);
//             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
//             fetchProducts(); // Refresh product list after deletion
//         } catch (error) {
//             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}
//                 <div className="product-list-wrapper">
//                     {productList.map((product) => (
//                         <div key={product.p_id} className="product-card">
//                             <img
//                                 src={product.p_url} // Use product image URL from the response
//                                 alt={product.p_name} // Set the alt attribute for accessibility
//                             />
//                             <h3>{product.p_name}</h3>
//                             <h4 className="text-success">{product.t_price}</h4>
//                             <div className="btn-container">
//                                 <a className="btn btn-primary btn btn-block w-50" href={`/user/editproduct/${product.p_id}`}>Edit</a>
//                                 <button
//                                     className="btn btn-danger btn btn-block w-50"
//                                     onClick={() => handleDelete(product.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;





// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Add your styles here
// import 'bootstrap/dist/css/bootstrap.min.css';

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     const [productList, setProductList] = useState(session.product || []); // Initialize from session data
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         // Fetch products only if there are no products in the session data
//         if (productList.length === 0) {
//             fetchProducts();
//         }
//     }, [productList]);

//     const fetchProducts = async () => {
//         try {
//             const response = await axios.get('http://localhost:8080/user/product-list'); // Adjust the endpoint as necessary
//             setProductList(response.data);
//         } catch (error) {
//             setMessage({ content: 'Failed to fetch product list.', type: 'alert-danger' });
//         }
//     };

//     const handleDelete = async (productId) => {
//         try {
//             await axios.delete(`/user/deleteproduct/${productId}`);
//             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
//             fetchProducts(); // Refresh product list after deletion
//         } catch (error) {
//             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}
//                 <div className="product-list-wrapper">
//                     {productList.map((product) => (
//                         <div key={product.p_id} className="product-card">
//                             <img
//                                 src={product.product_image} // Use product_image from the database
//                                 alt={product.p_name} // Set the alt attribute for accessibility
//                             />
//                             <h3>{product.p_name}</h3>
//                             <h4 className="text-success">{product.product_price}</h4> {/* Use product_price */}
//                             <div className="btn-container">
//                                 <a className="btn btn-primary btn btn-block w-50" href={`/user/editproduct/${product.p_id}`}>Edit</a>
//                                 <button
//                                     className="btn btn-danger btn btn-block w-50"
//                                     onClick={() => handleDelete(product.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;






// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Add your custom styles
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     const [productList, setProductList] = useState([]); // Initialize with an empty array
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         fetchProducts();
//     }, []);

//     const fetchProducts = async () => {
      
//             // Fetch the list of products from the backend
            
//             try {
//                             const response = await axios.get('http://localhost:8080/user/product-list'); // Adjust the endpoint as necessary
//                             setProductList(response.data);
                        

//             // Fetch price and image for each product in parallel
//             const updatedProducts = await Promise.all(response.data.map(async (product) => {
//                 try {
//                     const productResponse = await axios.get(`http://localhost:8080/admin/getproductbyurl?url=${product.p_url}`, {
//                         auth: {
//                             username: 'p.nagasatyasai.123@gmail.com',
//                             password: 'satya'
//                         }
//                     });

//                     const { product_price, product_image } = productResponse.data;
//                     return {
//                         ...product,
//                         t_price: product_price, // Add product price to the product object
//                         p_image: product_image // Add product image URL to the product object
//                     };
//                 } catch (error) {
//                     console.error('Failed to fetch product details:', error);
//                     return product; // In case of error, return the original product data
//                 }
//             }));

//             setProductList(updatedProducts); // Set the updated product list
//         } catch (error) {
//             setMessage({ content: 'Failed to fetch product list.', type: 'alert-danger' });
//         }
//     };

//     const handleDelete = async (productId) => {
//         try {
//             await axios.delete(`http://localhost:8080/user/deleteproduct/${productId}`, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com', // Use your username
//                     password: 'satya' // Use your password
//                 }
//             });
//             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
//             fetchProducts(); // Refresh the product list after deletion
//         } catch (error) {
//             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}

//                 <div className="product-list-wrapper">
//                     {productList.map((product) => (
//                         <div key={product.p_id} className="product-card">
//                             <img
//                                 src={product.p_image} // Use the product image URL
//                                 alt="Product"
//                                 style={{ maxHeight: '250px', width: '100%' }}
//                             />
//                             <h3>{product.p_name}</h3>
//                             <h4 className="text-success">{product.t_price}</h4> {/* Current price */}
//                             <s><h4 className="text-danger">{product.t_price}</h4></s> {/* Placeholder for old price, if needed */}
//                             <div className="btn-container">
//                                 <a className="btn btn-primary btn-block w-50" href={`/user/editproduct/${product.p_id}`}>Edit</a>
//                                 <button
//                                     className="btn btn-danger btn-block w-50"
//                                     onClick={() => handleDelete(product.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;




    // import React, { useEffect, useState } from 'react';
    // import axios from 'axios';
    // import './style.css'; // Add your custom styles
    // import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

    // // Import your service methods if they are in another file
    // import { getCurrentPrice, getImageUrl } from 'c:\Users\pnaga\Downloads\Price_drop_alert-master\Price drop alert\src\main\java\com\pricedrop\services\ProductService.java'; // Adjust the import path

    // const ProductList1 = ({ session }) => {
    //     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
    //     const [productList, setProductList] = useState(session.product || []); // Use product data from session
    //     const [message, setMessage] = useState(null);

    //     useEffect(() => {
    //         // Fetch the product image and current price for each product
    //         const updatedProducts = productList.map((product) => ({
    //             ...product,
    //             currentPrice: getCurrentPrice(product.p_url), // Call the service to get current price
    //             imageUrl: getImageUrl(product.p_url) // Call the service to get product image URL
    //         }));
    //         setProductList(updatedProducts);
    //     }, [productList]);

    //     const handleDelete = async (productId) => {
    //         try {
    //             await axios.delete(`http://localhost:8080/user/deleteproduct/${productId}`, {
    //                 auth: {
    //                     username: 'p.nagasatyasai.123@gmail.com', // Use your username
    //                     password: 'satya' // Use your password
    //                 }
    //             });
    //             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
    //             // Refresh product list from session
    //             setProductList(session.product || []);
    //         } catch (error) {
    //             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
    //         }
    //     };

    //     return (
    //         <div>
    //             <nav className="navbar">
    //                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
    //                     <span className="logo lg-heading mt-3">{username}</span>
    //                     <ul>
    //                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
    //                         <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
    //                         <li className="nav-item"><a href="/logout">Logout</a></li>
    //                     </ul>
    //                 </div>
    //             </nav>

    //             <div className="container w-125">
    //                 <br />
    //                 {message && (
    //                     <div className={`alert ${message.type} text-center`} role="alert">
    //                         <p className="text-center">{message.content}</p>
    //                     </div>
    //                 )}

    //                 <div className="product-list-wrapper">
    //                     {productList.map((product) => (
    //                         <div key={product.p_id} className="product-card">
    //                             <img
    //                                 src={getImageUrl(product.p_url)} // Use the service method to get the image URL
    //                                 alt="Product"
    //                                 style={{ maxHeight: '250px', width: '100%' }}
    //                             />
    //                             <h3>{product.p_name}</h3>
    //                             <h4 className="text-success">{product.t_price}</h4> {/* Current price */}
    //                             <s><h4 className="text-danger">{getCurrentPrice(product.p_url)}</h4></s> {/* Placeholder for old price, if needed */}
    //                             <div className="btn-container">
    //                                 <a className="btn btn-primary btn-block w-50" href={`/user/editproduct/${product.p_id}`}>Edit</a>
    //                                 <button
    //                                     className="btn btn-danger btn-block w-50"
    //                                     onClick={() => handleDelete(product.p_id)}
    //                                 >
    //                                     Delete
    //                                 </button>
    //                             </div>
    //                         </div>
    //                     ))}
    //                 </div>
    //             </div>
    //         </div>
    //     );
    // };

    // export default ProductList1;




//     import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Import your custom styles
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     const password = session.password;
//     const [productList, setProductList] = useState(session.product || []); // Use product data from session
//     const [message, setMessage] = useState(null);

//     useEffect(() => {
//         // Fetch the current price and image URL for each product
//         const fetchProductDetails = async () => {
//             const updatedProducts = await Promise.all(productList.map(async (product) => {
//                 const currentPrice = await getCurrentPrice(product.p_url);
//                 const imageUrl = await getImageUrl(product.p_url);
//                 return { ...product, currentPrice, imageUrl };
//             }));
//             setProductList(updatedProducts);
//         };

//         fetchProductDetails();
//     }, [productList]);
//     const fetchProductList = async () => {
//         try {
//             const response = await axios.get('http://localhost:8080/user/product-list', {
//                 // auth: {
//                 //     username: session.username,
//                 //     password: session.password
//                 // }
//             });
//             setProductList(response.data); // Update product list with fresh data
//         } catch (error) {
//             console.error('Failed to fetch product list:', error);
//         }
//     };

//     const handleDelete = async (productId) => {
//         try {
//             await axios.delete(`http://localhost:8080/user/deleteproduct/${productId}`, {
//                 // auth: {
//                 //     username,
//                 //     password
//                 // }
//             });
//             console.log('username:',username);
//             console.log('password:',password);
//             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
//             // Refresh product list from session
//             // setProductList(session.product || []);
//              // Fetch updated product list
//         await fetchProductList();
//         } catch (error) {
//             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//         }
//     };

//     // Fetch current price from backend
//     const getCurrentPrice = async (productUrl) => {
//         try {
//             const response = await axios.get(`http://localhost:8080/admin/getcurrentprice?url=${encodeURIComponent(productUrl)}`, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com',
//                     password: 'satya'
//                 }
//             });
//             return response.data; // Assuming the response contains the price as a string
//         } catch (error) {
//             console.error("Error fetching current price:", error);
//             return "Error"; // Handle errors appropriately
//         }
//     };

//     // Fetch image URL from backend
//     const getImageUrl = async (productUrl) => {
//         try {
//             const response = await axios.get(`http://localhost:8080/admin/getimageurl?url=${encodeURIComponent(productUrl)}`, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com',
//                     password: 'satya'
//                 }
//             });
//             return response.data; // Assuming the response contains the image URL
//         } catch (error) {
//             console.error("Error fetching image URL:", error);
//             return "Error"; // Handle errors appropriately
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add_product1">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}

//                 <div className="product-list-wrapper">
//                     {productList.map((product) => (
//                         <div key={product.p_id} className="product-card">
//                             <img
//                                 src={product.imageUrl} // Use the image URL fetched from the backend
//                                 alt="Product"
//                                 style={{ maxHeight: '250px', width: '100%' }}
//                             />
//                             <h3>{product.p_name}</h3>
//                             <h4 className="text-success">{product.t_price}</h4>
//                             <s><h4 className="text-danger">{product.currentPrice}</h4></s> {/* Display the current price */}
//                             <div className="btn-container">
//                                 <a className="btn btn-primary btn-block w-50" href={`http://localhost:8080/user/editproduct/${product.p_id}`}>Edit</a>
//                                 <button
//                                     className="btn btn-danger btn-block w-50"
//                                     onClick={() => handleDelete(product.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </div>
//                         </div>
//                     ))}
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;





// import React, { useEffect, useState } from 'react';
// import axios from 'axios';
// import './style.css'; // Import your custom styles
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     const [productList, setProductList] = useState(Array.isArray(session.product) ? session.product : []); // Use product data from session, default to empty array
//     const [message, setMessage] = useState(null);

//     // useEffect(() => {
//     //     fetchProductList(); // Fetch product list when the component mounts
//     // }, []);

//     useEffect(() => {
//         const fetchProductDetails = async () => {
//             // Log to check what productList is
//             console.log('Current productList:', productList);
            
//             if (!Array.isArray(productList)) {
//                 console.error('productList is not an array:', productList);
//                 return; // Exit if it's not an array
//             }

//             const updatedProducts = await Promise.all(productList.map(async (product) => {
//                 const currentPrice = await getCurrentPrice(product.p_url);
//                 const imageUrl = await getImageUrl(product.p_url);
//                 return { ...product, currentPrice, imageUrl };
//             }));
//             setProductList(updatedProducts);
//         };

//         if (productList.length > 0) { // Ensure there's data to process
//             fetchProductDetails();
//         }
//     }, [productList]); // This will run whenever productList updates

  
// // Initialize product list only once on mount
// useEffect(() => {
//     if (productList.length === 0) {
//         setProductList(session.product || []);
//     }
// }, [session.product]); // Only run when session.product changes

// const handleDelete = async (productId) => {
//     console.log('Before deletion:', productList);
//     try {
//         await axios.delete(`http://localhost:8080/user/deleteproduct/${productId}`);
//         console.log('Deleted product ID:', productId);
//         setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });

//         // Update local product list
//         setProductList(prevList => {
//             const updatedList = prevList.filter(product => product.p_id !== productId);
//             console.log('After deletion:', updatedList);
//             return updatedList;
//         });
//     } catch (error) {
//         console.error('Delete error:', error);
//         setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//     }
// };


//     // Fetch current price from backend
//     const getCurrentPrice = async (productUrl) => {
//         try {
//             const response = await axios.get(`http://localhost:8080/admin/getcurrentprice?url=${encodeURIComponent(productUrl)}`, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com',
//                     password: 'satya'
//                 }
//             });
//             return response.data; // Assuming the response contains the price as a string
//         } catch (error) {
//             console.error("Error fetching current price:", error);
//             return "Error"; // Handle errors appropriately
//         }
//     };

//     // Fetch image URL from backend
//     const getImageUrl = async (productUrl) => {
//         try {
//             const response = await axios.get(`http://localhost:8080/admin/getimageurl?url=${encodeURIComponent(productUrl)}`, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com',
//                     password: 'satya'
//                 }
//             });
//             return response.data; // Assuming the response contains the image URL
//         } catch (error) {
//             console.error("Error fetching image URL:", error);
//             return "Error"; // Handle errors appropriately
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add_product1">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}

//                 <div className="product-list-wrapper">
//                     {Array.isArray(productList) && productList.length > 0 ? (
//                         productList.map((product) => (
//                             <div key={product.p_id} className="product-card">
//                                 <img
//                                     src={product.imageUrl} // Use the image URL fetched from the backend
//                                     alt="Product"
//                                     style={{ maxHeight: '250px', width: '100%' }}
//                                 />
//                                 <h3>{product.p_name}</h3>
//                                 <h4 className="text-success">{product.t_price}</h4>
//                                 <s><h4 className="text-danger">{product.currentPrice}</h4></s> {/* Display the current price */}
//                                 <div className="btn-container">
//                                     <a className="btn btn-primary btn-block w-50" href={`http://localhost:8080/user/editproduct/${product.p_id}`}>Edit</a>
//                                     <button
//                                         className="btn btn-danger btn-block w-50"
//                                         onClick={() => handleDelete(product.p_id)}
//                                     >
//                                         Delete
//                                     </button>
//                                 </div>
//                             </div>
//                         ))
//                     ) : 
//                     (
//                          <p>Loading...</p>
//                     )
//                     }
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;




// import React, { useEffect, useState, useRef } from 'react';
// import axios from 'axios';
// import './style.css'; // Import your custom styles
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

// const ProductList1 = ({ session }) => {
//     const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
//     const [productList, setProductList] = useState([]); // Initialize product list
//     const [message, setMessage] = useState(null);
//     const [loading, setLoading] = useState(true); // Loading state
//     const productsRef = useRef(session.product || []); // Use ref for stable reference

//     // Initialize product list once on mount
//     useEffect(() => {
//         productsRef.current = session.product || [];
//         setProductList(productsRef.current);
//         setLoading(false); // Set loading to false after loading the products
//     }, [session.product]); // Only run when session.product changes

//     // Fetch current prices and images for products
//     const fetchProductDetails = async () => {
//         setLoading(true); // Start loading
//         const updatedProducts = await Promise.all(productsRef.current.map(async (product) => {
//             const currentPrice = await getCurrentPrice(product.p_url);
//             const imageUrl = await getImageUrl(product.p_url);
//             return { ...product, currentPrice, imageUrl };
//         }));

//         setProductList(updatedProducts); // Update the state with fetched details
//         setLoading(false); // End loading
//     };

//     // Call fetchProductDetails once on component mount
//     useEffect(() => {
//         if (productsRef.current.length > 0) {
//             fetchProductDetails(); // Call the fetch function
//         }
//     }, []); // Only run on mount

//     // Handle product deletion
//     const handleDelete = async (productId) => {
//         console.log('Before deletion:', productList);
//         try {
//             await axios.delete(`http://localhost:8080/user/deleteproduct/${productId}`);
//             console.log('Deleted product ID:', productId);
//             setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });

//             // Update local product list after deletion
//             setProductList(prevList => {
//                 const updatedList = prevList.filter(product => product.p_id !== productId);
//                 console.log('After deletion:', updatedList);
//                 productsRef.current = updatedList; // Update the ref
//                 return updatedList;
//             });
//         } catch (error) {
//             console.error('Delete error:', error);
//             setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
//         }
//     };

//     const getCurrentPrice = async (productUrl) => {
//         try {
//             const response = await axios.get(`http://localhost:8080/admin/getcurrentprice?url=${encodeURIComponent(productUrl)}`, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com',
//                     password: 'satya'
//                 }
//             });
//             return response.data; // Assuming the response contains the price
//         } catch (error) {
//             console.error("Error fetching current price:", error);
//             return "Error"; // Handle errors appropriately
//         }
//     };

//     const getImageUrl = async (productUrl) => {
//         try {
//             const response = await axios.get(`http://localhost:8080/admin/getimageurl?url=${encodeURIComponent(productUrl)}`, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com',
//                     password: 'satya'
//                 }
//             });
//             return response.data; // Assuming the response contains the image URL
//         } catch (error) {
//             console.error("Error fetching image URL:", error);
//             return "Error"; // Handle errors appropriately
//         }
//     };

//     return (
//         <div>
//             <nav className="navbar">
//                 <div className="container" style={{ backgroundColor: '#90EE90' }}>
//                     <span className="logo lg-heading mt-3">{username}</span>
//                     <ul>
//                         <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
//                         <li className="nav-item"><a href="/user/add_product1">Add Product</a></li>
//                         <li className="nav-item"><a href="/logout">Logout</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             <div className="container w-125">
//                 <br />
//                 {message && (
//                     <div className={`alert ${message.type} text-center`} role="alert">
//                         <p className="text-center">{message.content}</p>
//                     </div>
//                 )}

//                 <div className="product-list-wrapper">
//                     {loading ? ( // Show loading state
//                         <p>Loading...</p>
//                     ) : (
//                         Array.isArray(productList) && productList.length > 0 ? (
//                             productList.map((product) => (
//                                 <div key={product.p_id} className="product-card">
//                                     <img
//                                         src={product.imageUrl} // Use the image URL fetched from the backend
//                                         alt="Product"
//                                         style={{ maxHeight: '250px', width: '100%' }}
//                                     />
//                                     <h3>{product.p_name}</h3>
//                                     <h4 className="text-success">{product.t_price}</h4>
//                                     <s><h4 className="text-danger">{product.currentPrice}</h4></s> {/* Display the current price */}
//                                     <div className="btn-container">
//                                         <a className="btn btn-primary btn-block w-50" href={`http://localhost:8080/user/editproduct/${product.p_id}`}>Edit</a>
//                                         <button
//                                             className="btn btn-danger btn-block w-50"
//                                             onClick={() => handleDelete(product.p_id)}
//                                         >
//                                             Delete
//                                         </button>
//                                     </div>
//                                 </div>
//                             ))
//                         ) : (
//                             <p>No products available.</p>
//                         )
//                     )}
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default ProductList1;









import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import './style.css'; // Import your custom styles
import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

const ProductList1 = ({ session }) => {
        // Destructure username and password from session prop
        const { sessionData, password } = session;

        const baseUrl = process.env.REACT_APP_API_BASE_URL;
        console.log("API base URL:", baseUrl); // Debug log
    

    const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'
    const [productList, setProductList] = useState([]); // Initialize product list
    const [message, setMessage] = useState(null);
    const [loading, setLoading] = useState(true); // Loading state
    const productsRef = useRef([]); // Use ref for stable reference


const fetchUserData = async () => {
    try {
        //const response = await axios.get('http://localhost:8080/user/products-list', {
            const response = await axios.get(`${baseUrl}/user/products-list`, {
            // Add any necessary headers or authentication here
            // auth: {
            //     username: 'nagasatyasaipavirala@gmail.com',
            //     password: 'User@123'
            // }
            auth: {
                username: session.email,
               //
                password: password
            
            }
        });

        // Log the entire response to the console
        console.log("Response from API:", response.data);

        const userProducts = response.data || []; // Extract products from the response
        productsRef.current = userProducts; // Store in ref
        setProductList(userProducts); // Set the state
    } catch (error) {
        console.log("username:",session.username);
        console.log("email:",session.email)

        console.log("password is",password);
        console.error("Error fetching user data:", error);
        setMessage({ content: 'Failed to fetch products. Please try again.', type: 'alert-danger' });
    } finally {
        setLoading(false); // End loading
    }
};


    // Fetch current prices and images for products
    const fetchProductDetails = async () => {
        const updatedProducts = await Promise.all(productsRef.current.map(async (product) => {
            const currentPrice = await getCurrentPrice(product.p_url);
            const imageUrl = await getImageUrl(product.p_url);
            return { ...product, currentPrice, imageUrl };
        }));

        setProductList(updatedProducts); // Update the state with fetched details
    };

 
    // const handleDelete = async (productId) => {
    //     console.log('Before deletion:', productList);
    //     try {
    //         const response = await axios.delete(`http://localhost:8080/user/deleteproducts/${productId}`, {
    //             // Add any necessary headers or authentication here
    //             auth: {
    //                 username: 'nagasatyasaipavirala@gmail.com',
    //                 password: 'User@123'
    //             }
    //         });
    //         console.log('Response from API:', response.data);
    //         console.log('Deleted product ID:', productId);
    //         setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
    
    //         // Update the product list with the response data
    //         setProductList(response.data); // Response contains the updated product list
    
    //     } catch (error) {
    //         console.error('Delete error:', error);
    //         setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
    //     }
    // };
    
    // const handleDelete = async (productId) => {
    //     console.log('Before deletion:', productList);
    //     try {
    //         const response = await axios.delete(`http://localhost:8080/user/deleteproducts/${productId}`, {
    //             // Add any necessary headers or authentication here
    //             auth: {
    //                 username: session.email,
    //                 password:password
    //             }
    //         });
    //         console.log('Response from API:', response.data);
    //         console.log('Deleted product ID:', productId);
    //         setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
    
    //         // Refresh the product list by fetching user data again
    //         await fetchUserData(); // Re-fetch the updated product list after deletion
    
    //     } catch (error) {
    //         console.error('Delete error:', error);
    //         setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
    //     }
    // };
    const handleDelete = async (productId,price) => {
        console.log('Before deletion:', productList);
        try {
            //const response = await axios.delete(`http://localhost:8080/user/deleteproducts/${productId}`, {
               // const response = await axios.delete(`http://localhost:8080/user/deleteproducts/${productId}/${price}`, {
                const response = await axios.delete(`${baseUrl}/user/deleteproducts/${productId}/${price}`, {
                // Add any necessary headers or authentication here
                auth: {
                    username: session.email,
                    password:password
                }
            });
            console.log('Response from API:', response.data);
            console.log('Deleted product ID:', productId);
            setMessage({ content: 'Product deleted successfully!', type: 'alert-success' });
    
            // Refresh the product list by fetching user data again
            await fetchUserData(); // Re-fetch the updated product list after deletion
    
        } catch (error) {
            console.error('Delete error:', error);
            setMessage({ content: 'Failed to delete product. Please try again.', type: 'alert-danger' });
        }
    };
    

    const getCurrentPrice = async (productUrl) => {
        try {
            //const response = await axios.get(`http://localhost:8080/admin/getcurrentprice?url=${encodeURIComponent(productUrl)}`, {
                const response = await axios.get(`${baseUrl}/admin/getcurrentprice?url=${encodeURIComponent(productUrl)}`, {
                auth: {
                    username: 'p.nagasatyasai.123@gmail.com',
                    password: 'satya'
                }
            });
            return response.data; // Assuming the response contains the price
        } catch (error) {
            console.error("Error fetching current price:", error);
            return "Error"; // Handle errors appropriately
        }
    };

    const getImageUrl = async (productUrl) => {
        try {
            //const response = await axios.get(`http://localhost:8080/admin/getimageurl?url=${encodeURIComponent(productUrl)}`, {
                const response = await axios.get(`${baseUrl}/admin/getimageurl?url=${encodeURIComponent(productUrl)}`, {
                auth: {
                    username: 'p.nagasatyasai.123@gmail.com',
                    password: 'satya'
                },
                 withCredentials: true, // Include credentials (cookies) with the request
            });
            return response.data; // Assuming the response contains the image URL
        } catch (error) {
            console.error("Error fetching image URL:", error);
            return "Error"; // Handle errors appropriately
        }
    };

    // Initialize product list on mount
    useEffect(() => {
        fetchUserData(); // Fetch user data on component mount
    }, []); // Only run on mount

    // Call fetchProductDetails once productList is updated
    useEffect(() => {
        if (productList.length > 0) {
            fetchProductDetails(); // Call the fetch function
        }
    }, [productList]); // Only run when productList changes

    return (
        <div>
            <nav className="navbar">
                <div className="container" style={{ backgroundColor: '#90EE90' }}>
                    <span className="logo lg-heading mt-3">{username}</span>
                    <ul>
                        <li className="nav-item"><a href="/user/dashboard">Dashboard</a></li>
                        <li className="nav-item"><a href="/user/add_product1">Add Product</a></li>
                        <li className="nav-item"><a href="/logout">Logout</a></li>
                    </ul>
                </div>
            </nav>

            <div className="container w-125">
                <br />
                {message && (
                    <div className={`alert ${message.type} text-center`} role="alert">
                        <p className="text-center">{message.content}</p>
                    </div>
                )}

                <div className="product-list-wrapper">
                    {loading ? ( // Show loading state
                        <p>Loading...</p>
                    ) : (
                        Array.isArray(productList) && productList.length > 0 ? (
                            productList.map((product) => (
                                <div key={product.p_id} className="product-card">
                                    <img
                                        src={product.imageUrl} // Use the image URL fetched from the backend
                                        alt="Product"
                                        style={{ maxHeight: '250px', width: '100%' }}
                                    />
                                    <h3>{product.p_name}</h3>
                                    <h4 className="text-success">{product.t_price}</h4>
                                    <s><h4 className="text-danger">{product.currentPrice}</h4></s> {/* Display the current price */}
                                    <div className="btn-container">
                                        {/* <a className="btn btn-primary btn-block w-50" href={`editproduct/${product.p_id}`}>Edit</a> */}
                                        <a className="btn btn-primary btn-block w-50" href={`editproduct/${product.p_id}/${product.t_price}`}>Edit</a>
                                        <button
                                            className="btn btn-danger btn-block w-50"
                                           // onClick={() => handleDelete(product.p_id)}
                                           onClick={() => handleDelete(product.p_id,product.t_price)}
                                        >
                                            Delete
                                        </button>
                                    </div>
                                </div>
                            ))
                        ) : (
                            <p>No products available.</p>
                        )
                    )}
                </div>
            </div>
        </div>
    );
};

export default ProductList1;
















// import axios from 'axios';
// import React from 'react';
// import { useNavigate } from 'react-router-dom';

// import './style.css'; // Assuming you have the corresponding CSS file

// function ProductList({ products }) {
//     const navigate = useNavigate();

//     // const deleteProduct = (productId) => {
//     //     // Implement your delete logic here, e.g., an API call to delete the product
//     //     // After deletion, refresh the product list or redirect as needed
//     //     console.log(`Delete product with ID: ${productId}`);
//     // };
//     const deleteProduct = (productId) => {
//         axios
//             .delete(`/user/deleteproduct/${productId}`)
//             .then((response) => {
//                 console.log('Product deleted successfully');
//                 // Optionally refresh the product list or navigate
//                 // For example, you can redirect to the product list page:
//                 navigate('/user/product-list');
//             })
//             .catch((error) => {
//                 console.error('Error:', error);
//             });
//     };
//     return (
//         <div>
//             <h1>The list of items of user</h1>
//             <br />
//             <h1>USER LIST</h1>
//             <h4>
//                 <a href="/user/add-product">Add item</a>
//             </h4>
//             <h4>
//                 <a href="/user/dashboard">Dashboard</a>
//             </h4>
//             <table>
//                 <thead>
//                     <tr>
//                         <td>Image</td>
//                         <td>Id</td>
//                         <td>URL</td>
//                         <td>Price</td>
//                         <td>Action</td>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {products.map((u_list) => (
//                         <tr key={u_list.p_id}>
//                             <td>
//                                 <img
//                                     className="product-img"
//                                   //  src={productService.getImageUrl(UrlCoding.extractProductName(u_list.p_url))}
//                                     alt="Product"
//                                 />
//                             </td>
//                             <td>{u_list.p_id}</td>
//                             <td>{u_list.p_url}</td>
//                             <td>{u_list.t_price}</td>
//                             <td>
//                                 <button
//                                     className="btn btn-primary"
//                                     onClick={() => deleteProduct(u_list.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// }

// export default ProductList;




// import axios from 'axios';
// import React, { useEffect, useState } from 'react';

// import './style.css'; // Assuming you have the corresponding CSS file

// function ProductList() {
//     const [products, setProducts] = useState([]);

//     // Fetch the product list when the component mounts
//     useEffect(() => {
//         // Fetch the products from your API
//         const fetchProducts = async () => {
//             try {
//                 const response = await axios.get('/user/product-list'); // Adjust this endpoint as necessary
//                 setProducts(response.data);
//             } catch (error) {
//                 console.error('Error fetching products:', error);
//             }
//         };

//         fetchProducts();
//     }, []);

//     const deleteProduct = async (productId) => {
//         try {
//             await axios.delete(`/user/deleteproduct/${productId}`);
//             console.log('Product deleted successfully');
//             // Refresh the product list after deletion
//             setProducts(products.filter(product => product.p_id !== productId));
//         } catch (error) {
//             console.error('Error deleting product:', error);
//         }
//     };

//     return (
//         <div>
//             <h1>The list of items of user</h1>
//             <br />
//             <h1>USER LIST</h1>
//             <h4>
//                 <a href="/user/add-product">Add item</a>
//             </h4>
//             <h4>
//                 <a href="/user/dashboard">Dashboard</a>
//             </h4>
//             <table>
//                 <thead>
//                     <tr>
//                         <td>Image</td>
//                         <td>Id</td>
//                         <td>URL</td>
//                         <td>Price</td>
//                         <td>Action</td>
//                     </tr>
//                 </thead>
//                 <tbody>
//                     {products.map((u_list) => (
//                         <tr key={u_list.p_id}>
//                             <td>
//                                 <img
//                                     className="product-img"
//                                     alt="Product"
//                                 />
//                             </td>
//                             <td>{u_list.p_id}</td>
//                             <td>{u_list.p_url}</td>
//                             <td>{u_list.t_price}</td>
//                             <td>
//                                 <button
//                                     className="btn btn-primary"
//                                     onClick={() => deleteProduct(u_list.p_id)}
//                                 >
//                                     Delete
//                                 </button>
//                             </td>
//                         </tr>
//                     ))}
//                 </tbody>
//             </table>
//         </div>
//     );
// }

// export default ProductList;










// import axios from 'axios';
// import React, { useEffect, useState } from 'react';
// import './style.css'; // Assuming you have the corresponding CSS file

// function ProductList() {
//     const [products, setProducts] = useState([]);
//     const [loading, setLoading] = useState(true);

//     // Fetch the product list when the component mounts
//     useEffect(() => {
//         const fetchProducts = async () => {
//             try {
//                 const response = await axios.get('/user/product-list'); // Adjust this endpoint as necessary
//                 setProducts(response.data);
//             } catch (error) {
//                 console.error('Error fetching products:', error);
//             } finally {
//                 setLoading(false);
//             }
//         };

//         fetchProducts();
//     }, []);

//     const deleteProduct = async (productId) => {
//         try {
//             await axios.delete(`/user/deleteproduct/${productId}`);
//             console.log('Product deleted successfully');
//             // Refresh the product list after deletion
//             setProducts(products.filter(product => product.p_id !== productId));
//         } catch (error) {
//             console.error('Error deleting product:', error);
//         }
//     };

//     return (
//         <div>
//             <h1>The list of items of user</h1>
//             <br />
//             <h1>USER LIST</h1>
//             <h4>
//                 <a href="/user/add-product">Add item</a>
//             </h4>
//             <h4>
//                 <a href="/user/dashboard">Dashboard</a>
//             </h4>
//             {loading ? (
//                 <p>Loading products...</p>
//             ) : (
//                 <table>
//                     <thead>
//                         <tr>
//                             <td>Image</td>
//                             <td>Id</td>
//                             <td>URL</td>
//                             <td>Price</td>
//                             <td>Action</td>
//                         </tr>
//                     </thead>
//                     <tbody>
//                         {products.map((u_list) => (
//                             <tr key={u_list.p_id}>
//                                 <td>
//                                     <img
//                                         className="product-img"
//                                         alt="Product"
//                                         src={u_list.product_image_url} // Ensure this field exists in your product data
//                                     />
//                                 </td>
//                                 <td>{u_list.p_id}</td>
//                                 <td>{u_list.p_url}</td>
//                                 <td>{u_list.t_price}</td>
//                                 <td>
//                                     <button
//                                         className="btn btn-primary"
//                                         onClick={() => deleteProduct(u_list.p_id)}
//                                     >
//                                         Delete
//                                     </button>
//                                 </td>
//                             </tr>
//                         ))}
//                     </tbody>
//                 </table>
//             )}
//         </div>
//     );
// }

// export default ProductList;





import axios from 'axios'; 
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './style.css'; // Assuming you have the corresponding CSS file

function ProductList({ session }) { // Accept session as a prop
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const baseUrl = process.env.REACT_APP_API_BASE_URL;
    console.log("API base URL:", baseUrl); // Debug log


    // Fetch the product list when the component mounts
    useEffect(() => {
        const fetchProducts = async () => {
            try {
               // const response = await axios.get('http://localhost:8080/user/api/products', {
                const response = await axios.get(`${baseUrl}/user/api/products`, {
                    auth: {
                        username: session.username, // Replace with your session management
                        password: session.password
                    }
                });
                setProducts(response.data);
            } catch (error) {
                console.error('Error fetching products:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchProducts();
    }, [session]);

    const deleteProduct = async (productId) => {
        try {
           // await axios.delete(`http://localhost:8080/user/deleteproduct/${productId}`);
           await axios.delete(`${baseUrl}/user/deleteproduct/${productId}`);
            console.log('Product deleted successfully');
            // Refresh the product list after deletion
            setProducts(products.filter(product => product.p_id !== productId));
        } catch (error) {
            console.error('Error deleting product:', error);
        }
    };

    return (
        <div>
            <h1>The list of items of user</h1>
            <br />
            <h1>USER LIST</h1>
            <h4>
                <Link to="/user/add_product">Add item</Link>
            </h4>
            <h4>
                <Link to="/user/dashboard">Dashboard</Link>
            </h4>
            {loading ? (
                <p>Loading products...</p>
            ) : (
                <table>
                    <thead>
                        <tr>
                            <td>Image</td>
                            <td>Id</td>
                            <td>URL</td>
                            <td>Price</td>
                            <td>Action</td>
                        </tr>
                    </thead>
                    <tbody>
                        {products.map((u_list) => (
                            <tr key={u_list.p_id}>
                                <td>
                                    <img
                                        className="product-img"
                                        alt="Product"
                                        src={u_list.product_image_url} // Ensure this field exists in your product data
                                    />
                                </td>
                                <td>{u_list.p_id}</td>
                                <td>{u_list.p_url}</td>
                                <td>{u_list.t_price}</td>
                                <td>
                                    <button
                                        className="btn btn-primary"
                                        onClick={() => deleteProduct(u_list.p_id)}
                                    >
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default ProductList;

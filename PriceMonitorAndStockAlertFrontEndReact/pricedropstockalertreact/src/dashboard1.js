// import React from 'react';
// import './style.css'; // Ensure your styles are in this file
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

// const Dashboard1 = ({ user, sessionMessage, removeMessage }) => {
//   return (
//     <div>
//       <header className="header">
//         <nav className="navbar">
//           <div className="container">
//             <span className="logo lg-heading mt-3">{user.username.toUpperCase()}</span>
//             <ul>
//               <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//               <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//               <li className="nav-item"><a href="/logout">Logout</a></li>
//             </ul>
//           </div>
//         </nav>
//       </header>

//       {/* Dashboard Content */}
//       <div className="dashboardheader text-center">
//         {sessionMessage && (
//           <div className={`alert alert-primary text-center ${sessionMessage.type}`} role="alert">
//             <p>{sessionMessage.content}</p>
//             {removeMessage && <div>{removeMessage()}</div>}
//           </div>
//         )}

//         <h1 className="dashboard-heading text-black">
//           Welcome to Price Drop And Stock Alert System Dashboard
//         </h1>
//         <p className="dashboardmid-heading">
//           "Get instant notifications on price drops and stock alerts for your favorite products with our website"
//         </p>

//         <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add-product">
//           Add Product
//         </a>
//         <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product-list">
//           Product Lists
//         </a>
//       </div>
//     </div>
//   );
// };

// export default Dashboard1;



// import React, { useEffect, useState } from 'react';
// import './style.css'; // Ensure your styles are in this file
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling
// import { getUserDashboard, getSessionMessage } from './UserController';

// const Dashboard1 = () => {
//     const [user, setUser] = useState({});
//     const [sessionMessage, setSessionMessage] = useState(null);

//     useEffect(() => {
//         const fetchData = async () => {
//             try {
//                 const userData = await getUserDashboard();
//                 const messageData = await getSessionMessage();
//                 setUser(userData);
//                 setSessionMessage(messageData);
//             } catch (error) {
//                 console.error('Error fetching data:', error);
//             }
//         };

//         fetchData();
//     }, []);

//     const removeMessage = () => {
//         setSessionMessage(null);
//     };

//     const username = user?.username ? user.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
//             </header>

//             {/* Dashboard Content */}
//             <div className="dashboardheader text-center">
//                 {sessionMessage && (
//                     <div className={`alert alert-primary text-center ${sessionMessage.type}`} role="alert">
//                         <p>{sessionMessage.content}</p>
//                         {removeMessage && <div>{removeMessage()}</div>}
//                     </div>
//                 )}

//                 <h1 className="dashboard-heading text-black">
//                     Welcome to Price Drop And Stock Alert System Dashboard
//                 </h1>
//                 <p className="dashboardmid-heading">
//                     "Get instant notifications on price drops and stock alerts for your favorite products with our website"
//                 </p>

//                 <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add-product">
//                     Add Product
//                 </a>
//                 <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product-list">
//                     Product Lists
//                 </a>
//             </div>
//         </div>
//     );
// };

// export default Dashboard1;












// import React from 'react';
// import './style.css'; // Ensure your styles are in this file
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling
// //import 'jquery'; // Import jQuery if necessary

// const Dashboard1 = ({ user, sessionMessage, removeMessage }) => {
//     const username = user?.username ? user.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
//             </header>

//             {/* Dashboard Content */}
//             <div className="dashboardheader">
//                 {sessionMessage && (
//                     <div className={`alert alert-primary text-center ${sessionMessage.type}`} role="alert">
//                         <p className="text-center">{sessionMessage.content}</p>
//                         <div>
//                             {/* Call the function to remove message from session */}
//                             {removeMessage && <button onClick={removeMessage} className="btn btn-secondary">Dismiss</button>}
//                         </div>
//                     </div>
//                 )}

//                 <h1 className="dashboard-heading text-black">
//                     Welcome to Price Drop And Stock Alert System Dashboard
//                 </h1>
//                 <p className="dashboardmid-heading">
//                     "Get instant notifications on price drops and stock alerts for your favorite products with our website"
//                 </p>

//                 <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add-product">Add Product</a>
//                 <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product-list">Product Lists</a>
//             </div>
//         </div>
//     );
// };

// export default Dashboard1;








// import React, { useEffect, useState } from 'react';
// import './style.css'; // Ensure your styles are in this file
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling
// import { getUserDashboard, getSessionMessage } from './UserController'; // Adjust the import path as needed

// const Dashboard1 = () => {
//     const [user, setUser] = useState({});
//     const [sessionMessage, setSessionMessage] = useState(null);

//     useEffect(() => {
//         const fetchData = async () => {
//             try {
//                 const userData = await getUserDashboard();
//                 const messageData = await getSessionMessage();
//                 setUser(userData);
//                 setSessionMessage(messageData);
//             } catch (error) {
//                 console.error('Error fetching data:', error);
//             }
//         };

//         fetchData();
//     }, []);

//     const removeMessage = () => {
//         setSessionMessage(null);
//     };

//     const username = user?.username ? user.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
//             </header>

//             {/* Dashboard Content */}
//             <div className="dashboardheader text-center">
//                 {sessionMessage && (
//                     <div className={`alert alert-primary text-center ${sessionMessage.type}`} role="alert">
//                         <p>{sessionMessage.content}</p>
//                         <div>
//                             {/* Call the function to remove message from session */}
//                             {removeMessage && <button onClick={removeMessage} className="btn btn-secondary">Dismiss</button>}
//                         </div>
//                     </div>
//                 )}

//                 <h1 className="dashboard-heading text-black">
//                     Welcome to Price Drop And Stock Alert System Dashboard
//                 </h1>
//                 <p className="dashboardmid-heading">
//                     "Get instant notifications on price drops and stock alerts for your favorite products with our website"
//                 </p>

//                 <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add-product">
//                     Add Product
//                 </a>
//                 <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product-list">
//                     Product Lists
//                 </a>
//             </div>
//         </div>
//     );
// };

// export default Dashboard1;










// import React, { useEffect, useState } from 'react';
// import './style.css'; // Ensure your styles are in this file
// import 'bootstrap/dist/css/bootstrap.min.css'; // Import Bootstrap for styling

// const Dashboard1 = () => {
//     const [user, setUser] = useState({});
//     const [sessionMessage, setSessionMessage] = useState(null);

//     useEffect(() => {
//         const fetchData = async () => {
//             try {
//                 const userResponse = await fetch('/user/dashboard'); // Adjust the endpoint as needed
//                 const userData = await userResponse.json();
//                 setUser(userData);

//                 // Fetch session message (assuming you have an endpoint for this)
//                 const messageResponse = await fetch('/user/session-message'); // Adjust the endpoint as needed
//                 const messageData = await messageResponse.json();
//                 setSessionMessage(messageData);
//             } catch (error) {
//                 console.error('Error fetching data:', error);
//             }
//         };

//         fetchData();
//     }, []);

//     const removeMessage = () => {
//         setSessionMessage(null);
//     };

//     const username = user?.username ? user.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'


//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar" style={{ backgroundColor: '#90EE90' }}>
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
//             </header>
    
//             <div className="dashboardheader text-center">
//                 {sessionMessage && (
//                     <div className={`alert alert-primary text-center`} role="alert">
//                         <p>{sessionMessage}</p>
//                         <div>
//                             <button onClick={removeMessage} className="btn btn-secondary">Dismiss</button>
//                         </div>
//                     </div>
//                 )}
    
//                 <h1 className="dashboard-heading text-black">
//                     Welcome to Price Drop And Stock Alert System Dashboard
//                 </h1>
//                 <p className="dashboardmid-heading">
//                     "Get instant notifications on price drops and stock alerts for your favorite products with our website"
//                 </p>
    
//                 <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add-product">
//                     Add Product
//                 </a>
//                 <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product-list">
//                     Product Lists
//                 </a>
//             </div>
//         </div>
//     );
// };

// export default Dashboard1;






// import React, { useEffect, useState } from 'react';
// import './style.css';
// import 'bootstrap/dist/css/bootstrap.min.css';

// const Dashboard1 = () => {
//     const [user, setUser] = useState({});
//     const [sessionMessage, setSessionMessage] = useState(null);

//     useEffect(() => {
//         const fetchUserData = async () => {
//             try {
//                // const response = await fetch('/user/api/user'); // Call the new API endpoint
//                const response = await fetch('http://localhost:8080/user/api/user'); // Call the new API endpoint
//                 if (response.ok) {
//                     const userData = await response.json(); // Expect JSON response
//                     setUser(userData);
//                 } else {
//                     console.error('Failed to fetch user data');
//                 }
//             } catch (error) {
//                 console.error('Error fetching user data:', error);
//             }
//         };

//         fetchUserData();
//     }, []);

//     const removeMessage = () => {
//         setSessionMessage(null);
//     };

//     const username = user?.username ? user.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar" style={{ backgroundColor: '#90EE90' }}>
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
//             </header>

//             <div className="dashboardheader text-center">
//                 {sessionMessage && (
//                     <div className={`alert alert-primary text-center`} role="alert">
//                         <p>{sessionMessage}</p>
//                         <div>
//                             <button onClick={removeMessage} className="btn btn-secondary">Dismiss</button>
//                         </div>
//                     </div>
//                 )}

//                 <h1 className="dashboard-heading text-black">
//                     Welcome to Price Drop And Stock Alert System Dashboard
//                 </h1>
//                 <p className="dashboardmid-heading">
//                     "Get instant notifications on price drops and stock alerts for your favorite products with our website"
//                 </p>

//                 <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add-product">
//                     Add Product
//                 </a>
//                 <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product-list">
//                     Product Lists
//                 </a>
//             </div>
//         </div>
//     );
// };

// export default Dashboard1;




// import React, { useEffect, useState } from 'react';
// import './style.css';
// import 'bootstrap/dist/css/bootstrap.min.css';

// const Dashboard1 = () => {
//     const [user, setUser] = useState(null);
//     const [loading, setLoading] = useState(true);
//     const [error, setError] = useState(null);
//     const [sessionMessage, setSessionMessage] = useState(null);

//     useEffect(() => {
//         const fetchUserData = async () => {
//             setLoading(true);
//             setError(null); // Reset error on new fetch attempt
//             try {
//                 const response = await fetch('http://localhost:8080/user/api/user'); // Call the new API endpoint
//                 if (response.ok) {
//                     const userData = await response.json(); // Expect JSON response
//                     setUser(userData);
//                 } else {
//                     throw new Error('Failed to fetch user data');
//                 }
//             } catch (error) {
//                 console.error('Error fetching user data:', error);
//                 setError(error.message); // Set error message
//             } finally {
//                 setLoading(false); // End loading state
//             }
//         };

//         fetchUserData();
//     }, []);

//     const removeMessage = () => {
//         setSessionMessage(null);
//     };

//     const username = user?.username ? user.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'

//     if (loading) {
//         return (
//             <div className="text-center">
//                 <h2>Loading...</h2>
//             </div>
//         );
//     }

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar" style={{ backgroundColor: '#90EE90' }}>
//                     <div className="container">
//                         <span className="logo lg-heading mt-3">{username}</span>
//                         <ul>
//                             <li className="nav-item"><a href="/user/add-product">Add Product</a></li>
//                             <li className="nav-item"><a href="/user/product-list">Product List</a></li>
//                             <li className="nav-item"><a href="/logout">Logout</a></li>
//                         </ul>
//                     </div>
//                 </nav>
//             </header>

//             <div className="dashboardheader text-center">
//                 {sessionMessage && (
//                     <div className={`alert alert-primary text-center`} role="alert">
//                         <p>{sessionMessage}</p>
//                         <div>
//                             <button onClick={removeMessage} className="btn btn-secondary">Dismiss</button>
//                         </div>
//                     </div>
//                 )}
                
//                 {error && (
//                     <div className={`alert alert-danger text-center`} role="alert">
//                         <p>{error}</p>
//                     </div>
//                 )}

//                 <h1 className="dashboard-heading text-black">
//                     Welcome to Price Drop And Stock Alert System Dashboard
//                 </h1>
//                 <p className="dashboardmid-heading">
//                     "Get instant notifications on price drops and stock alerts for your favorite products with our website"
//                 </p>

//                 <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add-product">
//                     Add Product
//                 </a>
//                 <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product-list">
//                     Product Lists
//                 </a>
//             </div>
//         </div>
//     );
// };

// export default Dashboard1;









import React, { useState } from 'react';
import './style.css';
import 'bootstrap/dist/css/bootstrap.min.css';

const Dashboard1 = ({ session }) => { // Accept session prop
    const [sessionMessage, setSessionMessage] = useState(null);

    const removeMessage = () => {
        setSessionMessage(null);
    };

    // Extract the username from the session data passed as a prop
    const username = session?.username ? session.username.toUpperCase() : 'Guest'; // Fallback to 'Guest'

    return (
        <div>
            <header className="header">
                <nav className="navbar" >
                    <div className="container">
                        <span className="logo lg-heading mt-3">{username}</span>
                        <ul>
                            <li className="nav-item"><a href="/user/add_product1">Add Product</a></li>
                            <li className="nav-item"><a href="/user/product_list1">Product List</a></li>
                            <li className="nav-item"><a href="/logout">Logout</a></li>
                        </ul>
                    </div>
                </nav>
            </header>

            <div className="dashboardheader text-center">
                {sessionMessage && (
                    <div className={`alert alert-primary text-center`} role="alert">
                        <p>{sessionMessage}</p>
                        <div>
                            <button onClick={removeMessage} className="btn btn-secondary">Dismiss</button>
                        </div>
                    </div>
                )}

                <h1 className="dashboard-heading text-black">
                    Welcome to Price Drop And Stock Alert System Dashboard
                </h1>
                <p className="dashboardmid-heading">
                    "Get instant notifications on price drops and stock alerts for your favorite products with our website"
                </p>

                <a className="text-black dashboardbtn btn-dashboard left-heading" href="/user/add_product1">
                    Add Product
                </a>
                <a className="text-black dashboardbtn btn-dashboard right-heading" href="/user/product_list1">
                    Product Lists
                </a>
            </div>
        </div>
    );
};

export default Dashboard1;


// import React from 'react';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignIn({ session }) {
//     const handleLogin = (event) => {
//         event.preventDefault();
//         // Implement login logic here, such as calling an API
//     };

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                         <ul>
//                             <li className="nav-item"><a href="/">Home</a></li>
//                             <li className="nav-item"><a href="/about">About</a></li>
//                             <li className="nav-item"><a href="/signin">Login</a></li>
//                             <li className="nav-item"><a href="/signup">Register</a></li>
//                         </ul>
//                     </div>
//                 </nav>

//                 {/* Login Form */}
//                 <div className="form-container">
//                     <form id="loginForm" onSubmit={handleLogin} className="form">
//                         <h2 className="login-intro">Login</h2>
// {/* 
//                         {session.logout && (
//                             <div style={{ width: '90%', textAlign: 'center', marginBottom: '2%', marginTop: '-20px' }} className="alert alert-success">
//                                 <h1 style={{ fontWeight: 'bold', paddingTop: '10px' }}>You Have Been Logout !!</h1>
//                             </div>
//                         )} */}

// {session?.logout && (
//   <div style={{ width: '90%', textAlign: 'center', marginBottom: '2%', marginTop: '-20px' }} className="alert alert-success">
//     <h1 style={{ fontWeight: 'bold', paddingTop: '10px' }}>You Have Been Logged Out !!</h1>
//   </div>
// )}


//                         {session?.message && (
//                             <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
//                                 <p className="text-center">{session.message.content}</p>
//                                 {/* You can consume the message here */}
//                             </div>
//                         )}

//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Email:</label>
//                             <input
//                                 type="email"
//                                 name="username"
//                                 id="username"
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your email"
//                                 required
//                             />
//                         </div>
//                         <div className="form-group1">
//                              <label style={{ marginLeft: 'auto' }}>Password:</label> 
                            
//                             <input
//                                 type="password"
//                                 name="password"
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your password"
//                                 style={{ marginLeft: '20px', width: '250px' }}
                               
//                                 required
//                             />
//                         </div>
//                         {/* <button style={{ width: '50%' }} type="submit">Login</button> */}
//                         <button className="btn btn-success w-50" type="submit">Login</button>


//                         <div className="form-group1">
//                             <p id="Intro2">Not a member? <a href="/signup" id="Intr                        <br />o2sign">Sign up</a></p>
//                         </div>
//                     </form>
//                 </div>
//             </header>
//         </div>
//     );
// }

// export default SignIn;






// import React, { useState } from 'react';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignIn({ session }) {
//     const [formData, setFormData] = useState({ username: '', password: '' });
//     const [dashboardData, setDashboardData] = useState(null);
//     const [error, setError] = useState('');

//     const handleInputChange = (e) => {
//         setFormData({
//             ...formData,
//             [e.target.name]: e.target.value
//         });
//     };

//     // const handleLogin = async (event) => {
//     //     event.preventDefault();
//     //     setError(''); // Reset error message
//     //     try {
//     //         // Implement login logic here (assuming you have a login API)
//     //         const response = await fetch('http://localhost:8080/login', {
//     //             method: 'POST',
//     //             headers: {
//     //                 'Content-Type': 'application/json',
//     //             },
//     //             body: JSON.stringify(formData),
//     //         });

//     //         if (response.ok) {
//     //             // Assuming login success, now fetch the dashboard data
//     //             const dashboardResponse = await fetch('http://localhost:8080/user/dashboard', {
//     //                 headers: {
//     //                     // Send the token or any authentication info if needed
//     //                     'Authorization': `Bearer ${session.token}`,
//     //                 },
//     //             });

//     //             if (dashboardResponse.ok) {
//     //                 const data = await dashboardResponse.json();
//     //                 setDashboardData(data); // Store the dashboard data in the state
//     //             } else {
//     //                 setError('Failed to load dashboard data');
//     //             }
//     //         } else {
//     //             setError('Login failed. Please check your credentials.');
//     //         }
//     //     } catch (error) {
//     //         setError('An error occurred during login. Please try again.');
//     //     }
//     // };

//     const handleLogin = async (event) => {
//         event.preventDefault();
//         setError(''); // Reset error message
//         try {
//             const response = await fetch('http://localhost:8080/signin', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(formData),
//             });
    
//             const responseData = await response.json(); // Get response data
    
//             if (response.ok) {
//                 // Handle successful login
//                 // Fetch dashboard data here if needed
//             } else {
//                 console.error(responseData); // Log the response data for debugging
//                 setError(`Login failed: ${responseData.message || 'Please check your credentials.'}`);
//             }
//         } catch (error) {
//             console.error('Error during login:', error);
//             setError('An error occurred during login. Please try again.');
//         }
//     };
    

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                         <ul>
//                             <li className="nav-item"><a href="/">Home</a></li>
//                             <li className="nav-item"><a href="/about">About</a></li>
//                             <li className="nav-item"><a href="/signin">Login</a></li>
//                             <li className="nav-item"><a href="/signup">Register</a></li>
//                         </ul>
//                     </div>
//                 </nav>

//                 {/* Login Form */}
//                 <div className="form-container">
//                     <form id="loginForm" onSubmit={handleLogin} className="form">
//                         <h2 className="login-intro">Login</h2>

//                         {session?.logout && (
//                             <div style={{ width: '90%', textAlign: 'center', marginBottom: '2%', marginTop: '-20px' }} className="alert alert-success">
//                                 <h1 style={{ fontWeight: 'bold', paddingTop: '10px' }}>You Have Been Logged Out !!</h1>
//                             </div>
//                         )}

//                         {session?.message && (
//                             <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
//                                 <p className="text-center">{session.message.content}</p>
//                             </div>
//                         )}

//                         {error && <div className="alert alert-danger">{error}</div>}

//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Email:</label>
//                             <input
//                                 type="email"
//                                 name="username"
//                                 id="username"
//                                 value={formData.username}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your email"
//                                 required
//                             />
//                         </div>
//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Password:</label>
//                             <input
//                                 type="password"
//                                 name="password"
//                                 value={formData.password}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your password"
//                                 required
//                             />
//                         </div>
//                         <button className="btn btn-success w-50" type="submit">Login</button>

//                         <div className="form-group1">
//                             <p id="Intro2">Not a member? <a href="/signup" id="Intro2sign">Sign up</a></p>
//                         </div>
//                     </form>

//                     {/* Display dashboard data after successful login */}
//                     {dashboardData && (
//                         <div className="dashboard">
//                             <h2>Dashboard Data</h2>
//                             <pre>{JSON.stringify(dashboardData, null, 2)}</pre>
//                         </div>
//                     )}
//                 </div>
//             </header>
//         </div>
//     );
// }

// export default SignIn;







// import React, { useState } from 'react';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignIn({ session }) {
//     const [formData, setFormData] = useState({ username: '', password: '' });
//     const [dashboardData, setDashboardData] = useState(null);
//     const [error, setError] = useState('');

//     const handleInputChange = (e) => {
//         setFormData({
//             ...formData,
//             [e.target.name]: e.target.value
//         });
//     };

//     const handleLogin = async (event) => {
//         event.preventDefault();
//         setError(''); // Reset error message
//         try {
//             // Use the correct endpoint for signing in
//             const response = await fetch('http://localhost:8080/signin', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(formData),
//             });

//             if (response.ok) {
//                 // Assuming login success, now fetch the dashboard data
//                 const dashboardResponse = await fetch('http://localhost:8080/user/dashboard', {
//                     headers: {
//                         // Send the token or any authentication info if needed
//                     },
//                 });

//                 if (dashboardResponse.ok) {
//                     const data = await dashboardResponse.json();
//                     setDashboardData(data); // Store the dashboard data in the state
//                 } else {
//                     setError('Failed to load dashboard data');
//                 }
//             } else {
//                 setError('Login failed. Please check your credentials.');
//             }
//         } catch (error) {
//             setError('An error occurred during login. Please try again.');
//         }
//     };

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                         <ul>
//                             <li className="nav-item"><a href="/">Home</a></li>
//                             <li className="nav-item"><a href="/about">About</a></li>
//                             <li className="nav-item"><a href="/signin">Login</a></li>
//                             <li className="nav-item"><a href="/signup">Register</a></li>
//                         </ul>
//                     </div>
//                 </nav>

//                 {/* Login Form */}
//                 <div className="form-container">
//                     <form id="loginForm" onSubmit={handleLogin} className="form">
//                         <h2 className="login-intro">Login</h2>

//                         {session?.logout && (
//                             <div style={{ width: '90%', textAlign: 'center', marginBottom: '2%', marginTop: '-20px' }} className="alert alert-success">
//                                 <h1 style={{ fontWeight: 'bold', paddingTop: '10px' }}>You Have Been Logged Out !!</h1>
//                             </div>
//                         )}

//                         {session?.message && (
//                             <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
//                                 <p className="text-center">{session.message.content}</p>
//                             </div>
//                         )}

//                         {error && <div className="alert alert-danger">{error}</div>}

//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Email:</label>
//                             <input
//                                 type="email"
//                                 name="username"
//                                 id="username"
//                                 value={formData.username}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your email"
//                                 required
//                             />
//                         </div>
//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Password:</label>
//                             <input
//                                 type="password"
//                                 name="password"
//                                 value={formData.password}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your password"
//                                 required
//                             />
//                         </div>
//                         <button className="btn btn-success w-50" type="submit">Login</button>

//                         <div className="form-group1">
//                             <p id="Intro2">Not a member? <a href="/signup" id="Intro2sign">Sign up</a></p>
//                         </div>
//                     </form>

//                     {/* Display dashboard data after successful login */}
//                     {dashboardData && (
//                         <div className="dashboard">
//                             <h2>Dashboard Data</h2>
//                             <pre>{JSON.stringify(dashboardData, null, 2)}</pre>
//                         </div>
//                     )}
//                 </div>
//             </header>
//         </div>
//     );
// }

// export default SignIn;









// import React, { useState } from 'react';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignIn({ onLogin }) { // Accept onLogin as a prop
//     const [formData, setFormData] = useState({ username: '', password: '' });
//     const [error, setError] = useState('');

//     const handleInputChange = (e) => {
//         setFormData({
//             ...formData,
//             [e.target.name]: e.target.value
//         });
//     };



//     const handleLogin = async (event) => {
//         event.preventDefault();
//         setError(''); // Reset error message
//         try {
//             const response = await fetch('http://localhost:8080/signin', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(formData),
//             });
    
//             if (response.ok) {
//                 const sessionData = await response.json(); // Assuming the response contains session data
//                 console.log('Session Data:', sessionData); // Log the session data for debugging
//                 onLogin(sessionData); // Call onLogin with session data to update authentication state in App.js
//             } else {
//                 const errorData = await response.json(); // Get the error response
//                 console.error('Error response:', errorData); // Log the error response
//                 setError(errorData.message || 'Login failed. Please check your credentials.'); // Display error message if available
//             }
//         } catch (error) {
//             console.error('Fetch error:', error); // Log the error
//             setError('An error occurred during login. Please try again.');
//         }
//     };
    

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                         <ul>
//                             <li className="nav-item"><a href="/">Home</a></li>
//                             <li className="nav-item"><a href="/about">About</a></li>
//                             <li className="nav-item"><a href="/signin">Login</a></li>
//                             <li className="nav-item"><a href="/signup">Register</a></li>
//                         </ul>
//                     </div>
//                 </nav>

//                 {/* Login Form */}
//                 <div className="form-container">
//                     <form id="loginForm" onSubmit={handleLogin} className="form">
//                         <h2 className="login-intro">Login</h2>

//                         {error && <div className="alert alert-danger">{error}</div>}

//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Email:</label>
//                             <input
//                                 type="email"
//                                 name="username"
//                                 id="username"
//                                 value={formData.username}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your email"
//                                 required
//                             />
//                         </div>
//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Password:</label>
//                             <input
//                                 type="password"
//                                 name="password"
//                                 value={formData.password}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your password"
//                                 required
//                             />
//                         </div>
//                         <button className="btn btn-success w-50" type="submit">Login</button>

//                         <div className="form-group1">
//                             <p id="Intro2">Not a member? <a href="/signup" id="Intro2sign">Sign up</a></p>
//                         </div>
//                     </form>
//                 </div>
//             </header>
//         </div>
//     );
// }

// export default SignIn;







// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignIn({ onLogin }) {
//     const [formData, setFormData] = useState({ username: '', password: '' });
//     const [error, setError] = useState('');
//     const navigate = useNavigate(); // Initialize the navigate function

//     const handleInputChange = (e) => {
//         setFormData({
//             ...formData,
//             [e.target.name]: e.target.value,
//         });
//     };

//     const handleLogin = async (event) => {
//         event.preventDefault();
//         setError(''); // Reset error message
//         try {
//             // Use the correct endpoint for signing in
//             const response = await fetch('http://localhost:8080/signin', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(formData),
//             });

//             if (response.ok) {
//                 //const sessionData = await response.json(); // Assuming the response contains session data
//                 //onLogin(sessionData); // Pass session data to parent component
//                 onLogin();
//                 navigate('/user/dashboard'); // Navigate to the dashboard on successful login
//             } else {
//                 setError('Login failed. Please check your credentials.');
//             }
//         } catch (error) {
//             setError('An error occurred during login. Please try again.');
//         }
//     };

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                         <ul>
//                             <li className="nav-item"><a href="/">Home</a></li>
//                             <li className="nav-item"><a href="/about">About</a></li>
//                             <li className="nav-item"><a href="/signin">Login</a></li>
//                             <li className="nav-item"><a href="/signup">Register</a></li>
//                         </ul>
//                     </div>
//                 </nav>

//                 {/* Login Form */}
//                 <div className="form-container">
//                     <form id="loginForm" onSubmit={handleLogin} className="form">
//                         <h2 className="login-intro">Login</h2>

//                         {error && <div className="alert alert-danger">{error}</div>}

//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Email:</label>
//                             <input
//                                 type="email"
//                                 name="username"
//                                 id="username"
//                                 value={formData.username}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your email"
//                                 required
//                             />
//                         </div>
//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Password:</label>
//                             <input
//                                 type="password"
//                                 name="password"
//                                 value={formData.password}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your password"
//                                 required
//                             />
//                         </div>
//                         <button className="btn btn-success w-50" type="submit">Login</button>

//                         <div className="form-group1">
//                             <p id="Intro2">Not a member? <a href="/signup" id="Intro2sign">Sign up</a></p>
//                         </div>
//                     </form>
//                 </div>
//             </header>
//         </div>
//     );
// }

// export default SignIn;




import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './style.css'; // Assuming you have a CSS file for styling

function SignIn({ onLogin }) {
    const [formData, setFormData] = useState({ username: '', password: '' });
    const [error, setError] = useState('');
    const baseUrl = process.env.REACT_APP_API_BASE_URL;
    console.log("API base URL:", baseUrl); // Debug log


    const navigate = useNavigate(); // Initialize the navigate function

    const handleInputChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    // const handleLogin = async (event) => {
    //     event.preventDefault();
    //     setError(''); // Reset error message

    //     try {
    //         // Use the correct endpoint for signing in
    //         const response = await fetch('http://localhost:8080/signin', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/json',
    //             },
    //             body: JSON.stringify(formData), // Send the form data
    //         });
    //         console.log(formData);
    //         console.log(response);

    //         if (response.status === 200) {
    //             const userData = await fetch('http://localhost:8080/user/api/user'); // Assuming the response contains user data
    //             onLogin(userData); // Pass the user data to the parent component
    //             //onLogin(); // Call the onLogin function to update the parent state
    //             navigate('/user/dashboard'); // Navigate to the dashboard on successful login
    //         } else {
    //             setError('Login failed. Please check your credentials.');
    //         }
    //     } catch (error) {
    //         setError('An error occurred during login. Please try again.');
    //     }
    // };



    // const handleLogin = async (event) => {
    //     event.preventDefault();
    //     setError(''); // Reset error message
    
    //     try {
    //         const response = await fetch('http://localhost:8080/signin', {
    //             method: 'POST',
    //             headers: {
    //                 'Content-Type': 'application/json',
    //             },
    //             body: JSON.stringify(formData), // Send the form data
    //         });
    
    //         const loginData = await fetch('http://localhost:8080/user/api/user'); // Parse the response JSON
    //         console.log(loginData);
    
    //         // Check if the login was successful by validating the response data
    //         if (response.ok && loginData.token) { // Assuming the backend returns a token on success
    //             // Successful login
    //             onLogin(loginData); // Call the onLogin handler with user data
    //             navigate('/user/dashboard'); // Navigate to the dashboard on successful login
    //         } else {
    //             // Login failed based on the content of the response
    //             setError('Login failed. Invalid credentials.');
    //         }
    //     } catch (error) {
    //         // Handle any network or unexpected errors
    //         setError('An error occurred during login. Please try again.');
    //     }
    // };
    

    const handleLogin = async (event) => {
        event.preventDefault();
        setError(''); // Reset error message
        
    
        try {
            // Encode username and password in base64
            const credentials = btoa(`${formData.username}:${formData.password}`);
    
            // Send request with Basic Authentication
            //const response = await fetch('http://localhost:8080/signin', {
                const response = await fetch(`${baseUrl}/signin`, {
                method: 'POST',
                headers: {
                    'Authorization': `Basic ${credentials}`, // Set the Authorization header
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData), // Send the form data if necessary
            });
    
            // Check if the response is okay (status 200)
            if (response.ok) {
                // Assuming the response contains user data
             //   const userData = await fetch('http://localhost:8080/user/api/user', {
                const userData = await fetch(`${baseUrl}/user/api/user`, {
                    method: 'GET', // Typically a GET request for user info
                    headers: {
                        'Authorization': `Basic ${credentials}`, // Include the same credentials
                    },
                });
    
                 const userDataResult = await userData.json(); // Parse user data
            //     console.log("User Data:", userDataResult);
            //    // console.log("Password",formData.password);
    
            //     // Pass the user data to the parent component
            //    // onLogin(userDataResult);
            //    onLogin(userDataResult, formData.password);
               // Modify userDataResult to include the plain text password instead of the encrypted one
    const { password: encryptedPassword, ...restOfUserData } = userDataResult; // Exclude the encrypted password
    const modifiedUserData = { ...restOfUserData, password: formData.password }; // Add plain text password

    // Pass the modified user data to the parent component
    onLogin(modifiedUserData,formData.password); // No need to pass password separately if included in modifiedUserData
                navigate('/user/dashboard'); // Navigate to the dashboard on successful login
            } else {
                setError('Login failed. Invalid credentials.');
            }
        } catch (error) {
            // Handle any network or unexpected errors
            setError('An error occurred during login. Please try again.');
            console.error('Error:', error);
        }
    };
    



    return (
        <div>
            <header className="header">
                <nav className="navbar">
                    <div className="container">
                        <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
                        <ul>
                            <li className="nav-item"><a href="/">Home</a></li>
                            <li className="nav-item"><a href="/about">About</a></li>
                            <li className="nav-item"><a href="/signin">Login</a></li>
                            <li className="nav-item"><a href="/signup">Register</a></li>
                        </ul>
                    </div>
                </nav>

                {/* Login Form */}
                <div className="form-container">
                    <form id="loginForm" onSubmit={handleLogin} className="form">
                        <h2 className="login-intro">Login</h2>

                        {error && <div className="alert alert-danger">{error}</div>}

                        <div className="form-group1">
                            <label style={{ marginLeft: '10%' }}>Email:</label>
                            <input
                                type="email"
                                name="username"
                                id="username"
                                value={formData.username}
                                onChange={handleInputChange}
                                style={{ marginLeft: '10px' }}
                                placeholder="Enter your email"
                                required
                            />
                        </div>
                        <div className="form-group1">
                            <label style={{ marginLeft: '10%' }}>Password:</label>
                            <input
                                type="password"
                                name="password"
                                value={formData.password}
                                onChange={handleInputChange}
                                style={{ marginLeft: '10px' }}
                                placeholder="Enter your password"
                                required
                            />
                        </div>
                        <button className="btn btn-success w-50" type="submit">Login</button>

                        <div className="form-group1">
                            <p id="Intro2">Not a member? <a href="/signup" id="Intro2sign">Sign up</a></p>
                        </div>
                    </form>
                </div>
            </header>
        </div>
    );
}

export default SignIn;












// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignIn({ onLogin }) {
//     const [formData, setFormData] = useState({ username: '', password: '' });
//     const [error, setError] = useState('');
//     const navigate = useNavigate(); // Initialize the navigate function

//     const handleInputChange = (e) => {
//         setFormData({
//             ...formData,
//             [e.target.name]: e.target.value,
//         });
//     };

//     const handleLogin = async (event) => {
//         event.preventDefault();
//         setError(''); // Reset error message

//         try {
//             // Use the correct endpoint for signing in
//             const response = await fetch('http://localhost:8080/signin', {
//                 method: 'POST',
//                 headers: {
//                     'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(formData), // Send the form data
//             });

//             if (response.ok) {
//                 // Fetch user data only if the login is successful
//                 const userData = await response.json(); // Parse login response (user data or token)
//                 onLogin(userData); // Pass the user data to the parent component

//                 navigate('/user/dashboard'); // Navigate to the dashboard on successful login
//             } else {
//                 // Handle unsuccessful login
//                 setError('Login failed. Please check your credentials.');
//             }
//         } catch (error) {
//             setError('An error occurred during login. Please try again.');
//         }
//     };

//     return (
//         <div>
//             <header className="header">
//                 <nav className="navbar">
//                     <div className="container">
//                         <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                         <ul>
//                             <li className="nav-item"><a href="/">Home</a></li>
//                             <li className="nav-item"><a href="/about">About</a></li>
//                             <li className="nav-item"><a href="/signin">Login</a></li>
//                             <li className="nav-item"><a href="/signup">Register</a></li>
//                         </ul>
//                     </div>
//                 </nav>

//                 {/* Login Form */}
//                 <div className="form-container">
//                     <form id="loginForm" onSubmit={handleLogin} className="form">
//                         <h2 className="login-intro">Login</h2>

//                         {error && <div className="alert alert-danger">{error}</div>}

//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Email:</label>
//                             <input
//                                 type="email"
//                                 name="username"
//                                 id="username"
//                                 value={formData.username}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your email"
//                                 required
//                             />
//                         </div>
//                         <div className="form-group1">
//                             <label style={{ marginLeft: '10%' }}>Password:</label>
//                             <input
//                                 type="password"
//                                 name="password"
//                                 value={formData.password}
//                                 onChange={handleInputChange}
//                                 style={{ marginLeft: '10px' }}
//                                 placeholder="Enter your password"
//                                 required
//                             />
//                         </div>
//                         <button className="btn btn-success w-50" type="submit">Login</button>

//                         <div className="form-group1">
//                             <p id="Intro2">Not a member? <a href="/signup" id="Intro2sign">Sign up</a></p>
//                         </div>
//                     </form>
//                 </div>
//             </header>
//         </div>
//     );
// }

// export default SignIn;

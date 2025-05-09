// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignUp1({ session }) {
//     const [username, setUsername] = useState('');
//     const [mobile, setMobile] = useState('');
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');
//     const [message, setMessage] = useState('');
//     const navigate = useNavigate(); // Use useNavigate instead of useHistory


//     const handleSubmit = async (event) => {
//         event.preventDefault();
//         try {
//             // Make the API call to send OTP
//             const response = await axios.post('http://localhost:8080/sendOTP', {
//                 email: email,
//                 password: password // Include other fields as needed
//             });

//             // Handle success response
//             if (response.data) {
//                 setMessage("OTP sent to your email. Please check your inbox.");
                
//                 // Redirect to OTP verification page
//                 navigate('/verify_otp1'); 
//             }
//         } catch (error) {
//             // Handle error
//             setMessage("Error sending OTP: " + error.response.data);
//         }
//     };

//     return (
//         <header className="header">
//             <nav className="navbar">
//                 <div className="container">
//                     <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                     <ul>
//                         <li className="nav-item"><a href="/">Home</a></li>
//                         <li className="nav-item"><a href="/about">About</a></li>
//                         <li className="nav-item"><a href="/signin">Login</a></li>
//                         <li className="nav-item"><a href="/signup">Register</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             {/* Registration Form */}
//             <div className="form-container">
//                 <form onSubmit={handleSubmit} className="forms">
//                     <h2 className="login-intro">Register</h2>
//                     {/* Display message about OTP sent */}
//                     {session?.message && (
//                         <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
//                             <p className="text-center">{session.message.content}</p>
//                             {/* You can consume the message here */}
//                         </div>
//                     )}

//                     <div className="form-group1">
//                         <label>Name:</label>
//                         <input
//                             type="text"
//                             className="in"
//                             value={username}
//                             onChange={(e) => setUsername(e.target.value)}
//                             placeholder="Enter your Name"
//                             required
//                         />
//                     </div>

//                     <div className="form-group1">
//                         <label>Mobile no.:</label>
//                         <input
//                             type="number"
//                             className="in"
//                             value={mobile}
//                             onChange={(e) => setMobile(e.target.value)}
//                             placeholder="Enter your Phone"
//                             required
//                         />
//                     </div>

//                     <div className="form-group1">
//                         <label>Email:</label>
//                         <input
//                             type="email"
//                             className="in"
//                             value={email}
//                             onChange={(e) => setEmail(e.target.value)}
//                             placeholder="Enter your E-mail"
//                             required
//                         />
//                     </div>

//                     <div className="form-group1">
//                         <label>Password:</label>
//                         <input
//                             type="password"
//                             className="in"
//                             value={password}
//                             onChange={(e) => setPassword(e.target.value)}
//                             placeholder="Enter your password"
//                             required
//                         />
//                     </div>

//                     {/* Uncomment if you want to include the terms checkbox */}
//                     {/* <div className="form-group1">
//                         <input type="checkbox" id="termsCheckbox" required />
//                         <label htmlFor="termsCheckbox">I agree to the Terms and Conditions</label>
//                     </div> */}
                    
//                     {/* <button className="bu" type="submit">Register</button> */}
//                     {/* <div className="d-flex justify-content-center">
//     <button className="btn btn-success" type="submit">Register</button>
// </div> */}

// <div className="d-flex justify-content-center">
//     <button className="btn btn-success w-50" type="submit">Register</button>
// </div>


//                 </form>
//             </div>
//         </header>
//     );
// }

// export default SignUp1;




// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignUp1({ session }) {
//     const [username, setUsername] = useState('');
//     const [mno, setMobile] = useState('');
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');
//     const [message, setMessage] = useState('');
//     const navigate = useNavigate(); // Use useNavigate instead of useHistory

//     // const handleSubmit = async (event) => {
//     //     event.preventDefault();
//     //     try {
//     //         // const user = {
//     //         //     username: username,
//     //         //     mno: mobile, // Assuming mobile is in the 'mobile' state
//     //         //     email: email, 
//     //         //     password: password 
//     //         // };
//     //         const user = { username, mno, email, password };
//     //         // Make the API call to send OTP along with user details
//     //         const response = await axios.post('http://localhost:8080/sendsOTP',user);

//     //         // Handle success response
//     //         if (response.data) {
//     //             console.log(response.data);
//     //             setMessage("OTP sent to your email. Please check your inbox.");
                
//     //             // Redirect to OTP verification page
//     //             navigate('/verify_otp1'); 
//     //         }
//     //     } catch (error) {
//     //         // Handle error
//     //         setMessage("Error sending OTP: " + (error.response?.data || error.message));
//     //     }
//     // };

//     const handleSubmit = async (event) => {
//         event.preventDefault();
//         try {
//             const formData = new URLSearchParams();  // Use URLSearchParams for form-encoded data
//             formData.append('username', username);
//             formData.append('mno', mno);
//             formData.append('email', email);
//             formData.append('password', password);
            
//             const response = await axios.post('http://localhost:8080/sendsOTP', formData, {
//                 headers: {
//                     'Content-Type': 'application/x-www-form-urlencoded'
//                 }
//             });
    
//             if (response.data) {
//                 console.log(response.data);
//                 setMessage("OTP sent to your email. Please check your inbox.");
//                 navigate('/verify_otp1');
//             }
//         } catch (error) {
//             setMessage("Error sending OTP: " + (error.response?.data || error.message));
//         }
//     };
    

//     return (
//         <header className="header">
//             <nav className="navbar">
//                 <div className="container">
//                     <h1 className="logo lg-heading">PRICE DROP AND STOCK ALERT SYSTEM</h1>
//                     <ul>
//                         <li className="nav-item"><a href="/">Home</a></li>
//                         <li className="nav-item"><a href="/about">About</a></li>
//                         <li className="nav-item"><a href="/signin">Login</a></li>
//                         <li className="nav-item"><a href="/signup">Register</a></li>
//                     </ul>
//                 </div>
//             </nav>

//             {/* Registration Form */}
//             <div className="form-container">
//                 <form onSubmit={handleSubmit} className="forms">
//                     <h2 className="login-intro">Register</h2>
//                     {/* Display message about OTP sent */}
//                     {session?.message && (
//                         <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
//                             <p className="text-center">{session.message.content}</p>
//                         </div>
//                     )}

//                     <div className="form-group1">
//                         <label>Name:</label>
//                         <input
//                             type="text"
//                             className="in"
//                             value={username}
//                             onChange={(e) => setUsername(e.target.value)}
//                             placeholder="Enter your Name"
//                             required
//                         />
//                     </div>

//                     <div className="form-group1">
//                         <label>Mobile no.:</label>
//                         <input
//                             type="number"
//                             className="in"
//                             value={mno}
//                             onChange={(e) => setMobile(e.target.value)}
//                             placeholder="Enter your Phone"
//                             required
//                         />
//                     </div>

//                     <div className="form-group1">
//                         <label>Email:</label>
//                         <input
//                             type="email"
//                             className="in"
//                             value={email}
//                             onChange={(e) => setEmail(e.target.value)}
//                             placeholder="Enter your E-mail"
//                             required
//                         />
//                     </div>

//                     <div className="form-group1">
//                         <label>Password:</label>
//                         <input
//                             type="password"
//                             className="in"
//                             value={password}
//                             onChange={(e) => setPassword(e.target.value)}
//                             placeholder="Enter your password"
//                             required
//                         />
//                     </div>

//                     <div className="d-flex justify-content-center">
//                         <button className="btn btn-success w-50" type="submit">Register</button>
//                     </div>
//                 </form>
//             </div>
//         </header>
//     );
// }

// export default SignUp1;





// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import './style.css'; // Assuming you have a CSS file for styling

// function SignUp() {
//     const [username, setUsername] = useState('');
//     const [mno, setMobile] = useState('');
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');
//     const [message, setMessage] = useState('');
//     const navigate = useNavigate();

//     // const handleSubmit = async (event) => {
//     //     event.preventDefault();
//     //     try {
//     //         const user = { username, mno, email, password };
//     //         const response = await axios.post('http://localhost:8080/user/sendsOTP', user, {
//     //             headers: {
//     //                 'Content-Type': 'application/json',
//     //                 'Authorization': 'Basic ' + btoa(`${nagasatyasaipavirala@gmail.com}:${satya}`) // Encode credentials
//     //             }
//     //         });

//     //         if (response.data) {
//     //             console.log(response.data);
//     //             setMessage("OTP sent to your email. Please check your inbox.");
//     //             navigate('/verify_otp1'); // Redirect to OTP verification page
//     //         }
//     //     } catch (error) {
//     //         setMessage("Error sending OTP: " + (error.response?.data || error.message));
//     //     }
//     // };

//     // const handleSubmit = async (event) => {
//     //     event.preventDefault();
//     //     try {
//     //         const user = { username, mno, email, password };
            
//     //         // Basic Authentication
//     //         const response = await axios.post('http://localhost:8080/user/sendsOTP', user, {
//     //             headers: {
//     //                 'Content-Type': 'application/json',
//     //                 'Authorization': 'Basic ' + btoa(`${'p.nagasatyasai.123@gmail.com'}:${'satya'}`) // Encode credentials
//     //             }
//     //         });
    
//     //         if (response.data) {
//     //             console.log(response.data);
//     //             setMessage("OTP sent to your email. Please check your inbox.");
//     //             navigate('/verify_otp1'); // Redirect to OTP verification page
//     //         }
//     //     } catch (error) {
//     //         setMessage("Error sending OTP: " + (error.response?.data || error.message));
//     //     }
//     // };

//     const handleSubmit = async (event) => {
//         event.preventDefault();
//         try {
//             const user = {
//                 username,
//                 mno,
//                 email,
//                 password,
//             };
    
//             const response = await axios.post('http://localhost:8080/user/sendsOTP', user);
    
//             // Add authentication information here
//             const auth = {
//                 username: 'p.nagasatyasai.123@gmail.com', // Your username
//                 password: 'satya' // Your password
//             };
    
//             // If you need to use `auth` for some logic after the Axios call, you can do it here
    
//             if (response.data) {
//                 console.log(response.data);
//                 setMessage("OTP sent to your email. Please check your inbox.");
//                 navigate('/verify_otp1'); // Redirect to OTP verification page
//             }
//         } catch (error) {
//             setMessage("Error sending OTP: " + (error.response?.data || error.message));
//         }
//     };
    
    
//     return (
//         <div className="form-container">
//             <form onSubmit={handleSubmit} className="forms">
//                 <h2 className="login-intro">Register</h2>
//                 {message && <div className="alert alert-primary">{message}</div>}

//                 <div className="form-group">
//                     <label>Name:</label>
//                     <input
//                         type="text"
//                         className="in"
//                         value={username}
//                         onChange={(e) => setUsername(e.target.value)}
//                         placeholder="Enter your Name"
//                         required
//                     />
//                 </div>

//                 <div className="form-group">
//                     <label>Mobile no.:</label>
//                     <input
//                         type="text" // Change to text to handle leading zeros
//                         className="in"
//                         value={mno}
//                         onChange={(e) => setMobile(e.target.value)}
//                         placeholder="Enter your Phone"
//                         required
//                     />
//                 </div>

//                 <div className="form-group">
//                     <label>Email:</label>
//                     <input
//                         type="email"
//                         className="in"
//                         value={email}
//                         onChange={(e) => setEmail(e.target.value)}
//                         placeholder="Enter your E-mail"
//                         required
//                     />
//                 </div>

//                 <div className="form-group">
//                     <label>Password:</label>
//                     <input
//                         type="password"
//                         className="in"
//                         value={password}
//                         onChange={(e) => setPassword(e.target.value)}
//                         placeholder="Enter your password"
//                         required
//                     />
//                 </div>

//                 <button className="btn btn-success" type="submit">Register</button>
//             </form>
//         </div>
//     );
// }

// export default SignUp;



import React, { useState } from 'react'; 
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './style.css'; 

function SignUp1() {
    const [username, setUsername] = useState('');
    const [mno, setMobile] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const baseUrl = process.env.REACT_APP_API_BASE_URL;
    console.log("API base URL:", baseUrl); // Debug log
    const navigate = useNavigate(); // Use useNavigate for navigation

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            //const response = await axios.post('http://localhost:8080/sendsOTP', {
                const response = await axios.post(`${baseUrl}/sendsOTP`, {
                username,
                mno,
                email,
                password,
            }, {
                auth: {
                    username: 'p.nagasatyasai.123@gmail.com',  // Replace with your actual username
                    password: 'satya'     // Replace with your actual password
                },
                withCredentials: true, // Include credentials (cookies) with the request
            });

            // Handle success response
            if (response.data) {
                console.log(response.data);
                setMessage("OTP sent to your email. Please check your inbox.");
                navigate('/verify_otp1'); // Redirect to OTP verification page
            }
        } catch (error) {
            setMessage("Error sending OTP: " + (error.response?.data || error.message));
        }
    };

    return (
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

            {/* Registration Form */}
            <div className="form-container">
                <form onSubmit={handleSubmit} className="forms">
                    <h2 className="login-intro">Register</h2>
                    {/* Display message about OTP sent */}
                    {message && (
                        <div className="alert alert-primary text-center" role="alert">
                            <p className="text-center">{message}</p>
                        </div>
                    )}

                    <div className="form-group1">
                        <label>Name:</label>
                        <input
                            type="text"
                            className="in"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter your Name"
                            required
                        />
                    </div>

                    <div className="form-group1">
                        <label>Mobile no.:</label>
                        <input
                            type="text" // Use text for leading zeros or any non-numeric characters
                            className="in"
                            value={mno}
                            onChange={(e) => setMobile(e.target.value)}
                            placeholder="Enter your Phone"
                            required
                        />
                    </div>

                    <div className="form-group1">
                        <label>Email:</label>
                        <input
                            type="email"
                            className="in"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Enter your E-mail"
                            required
                        />
                    </div>

                    <div className="form-group1">
                        <label>Password:</label>
                        <input
                            type="password"
                            className="in"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                            required
                        />
                    </div>

                    <div className="d-flex justify-content-center">
                        <button className="btn btn-success w-50" type="submit">Register</button>
                    </div>
                </form>
            </div>
        </header>
    );
}

export default SignUp1;



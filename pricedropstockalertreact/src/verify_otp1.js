// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom';
// import axios from 'axios';
// import './style.css'; // Assuming you have a CSS file for styling

// function VerifyOTP1({ session }) {
//     const [otp, setOtp] = useState('');
//     const navigate = useNavigate();
//     const [message, setMessage] = useState('');

//     // const handleSubmit = (event) => {
//     //     event.preventDefault();
//     //     // Implement the logic to verify OTP
//     //     // You might call an API here to verify the OTP
//     // };

//     // const handleSubmit = async (event) => {
//     //     event.preventDefault();
//     //     try {
//     //         const response = await axios.post('http://localhost:8080/user/verifys-otp', otp, {
//     //             auth: {
//     //                 username: 'p.nagasatyasai.123@gmail.com',  // Replace with your actual username
//     //                 password: 'satya'     // Replace with your actual password
//     //             }
//     //         });

//     //         if (response.data) {
//     //             console.log(response.data);
//     //             setMessage("Successfully registered!"); // Display success message
//     //             navigate('/signup1'); // Redirect to another page
//     //         }
//     //     } catch (error) {
//     //         setMessage("Error verifying OTP: " + (error.response?.data || error.message)); // Handle error message
//     //         navigate('/verify_otp1');
//     //     }
//     // };



//     // const handleSubmit = async (event) => {
//     //     event.preventDefault();
//     //     try {
//     //         // Create a payload object with the OTP
//     //         const payload = { otp }; // Send OTP as part of an object
    
//     //         const response = await axios.post('http://localhost:8080/verifys-otp', payload, {
//     //             auth: {
//     //                 username: 'p.nagasatyasai.123@gmail.com',  // Replace with your actual username
//     //                 password: 'satya'     // Replace with your actual password
//     //             }
//     //         });
    
//     //         if (response.data) {
//     //             console.log(response.data);
//     //             setMessage("Successfully registered!"); // Display success message
//     //             navigate('/signup1'); // Redirect to another page
//     //         }
//     //     } catch (error) {
//     //         setMessage("Error verifying OTP: " + (error.response?.data || error.message)); // Handle error message
//     //         navigate('/verify_otp1'); // Optionally navigate back to OTP verification page on error
//     //     }
//     // };
    

//     // const handleSubmit = async (event) => {
//     //     event.preventDefault();
//     //     try {
//     //         const otpData = { otp };   // Assuming `otp` is the state holding the OTP entered by the user
//     //         const response = await axios.post('http://localhost:8080/verifys-otp',otpData, {
//     //             auth: {
//     //                 username: 'p.nagasatyasai.123@gmail.com', // Replace with your actual username
//     //                 password: 'satya' // Replace with your actual password
//     //             },
//     //             withCredentials: true, // Include credentials (cookies) with the request
//     //             headers: {
//     //                 'Content-Type': 'application/json' // Specify the content type
//     //             }
//     //         });
    
//     //         if (response.data) {
//     //             console.log(response.data);
//     //             setMessage("Successfully registered!"); // Display success message
//     //             navigate('/signin'); // Redirect to another page
//     //         }
//     //     } catch (error) {
//     //         setMessage("Error verifying OTP: " + (error.response?.data || error.message)); // Handle error message
//     //         navigate('/verify_otp1');
//     //     }
//     // };


//     const handleSubmit = async (event) => {
//         event.preventDefault();
//         try {
//             const otpData = { otp };   // Assuming `otp` is the state holding the OTP entered by the user
//             const response = await axios.post('http://localhost:8080/verifys-otp', otpData, {
//                 auth: {
//                     username: 'p.nagasatyasai.123@gmail.com', // Replace with your actual username
//                     password: 'satya' // Replace with your actual password
//                 },
//                 withCredentials: true, // Include credentials (cookies) with the request
//                 headers: {
//                     'Content-Type': 'application/json' // Specify the content type
//                 }
//             });

//             if (response.data) {
//                 console.log(response.data);
//                 setMessage("Successfully registered!"); // Set success message
//                 setMessageType('success'); // Set message type to success
//                 setTimeout(() => navigate('/signin'), 2000); // Redirect after 2 seconds
//             }
//         } catch (error) {
//             const errorMessage = "Error verifying OTP: " + (error.response?.data || error.message); // Handle error message
//             setMessage(errorMessage);
//             setMessageType('danger'); // Set message type to error
//             setTimeout(() => navigate('/verify_otp1'), 2000); // Redirect after 2 seconds
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
//                         </ul>
//                     </div>
//                 </nav>

//                 <div className="form-container">
//                     {session?.message && (
//                         <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
//                             <p className="text-center">{session.message.content}</p>
//                             {/* You can consume the message here */}
//                         </div>
//                     )}
//                     <form id="loginForm" className="form" onSubmit={handleSubmit}>
//                         <h1 className="otp">OTP Verification</h1>
//                         <h3 className="otp_text">We have sent an OTP to your email</h3>

//                         <div className="form-group1">
//                             <label style={{ fontSize: '17px', paddingBottom: '1px' }}>Enter OTP:</label>
//                             <input
//                                 type="number"
//                                 name="otp"
//                                 style={{ width: '350px' }}
//                                 id="enter_otp"
//                                 placeholder="Enter your OTP"
//                                 value={otp}
//                                 onChange={(e) => setOtp(e.target.value)}
//                                 required
//                             />
//                             <br />
//                         </div>
//                         <button className="verify-btn" type="submit">Verify OTP</button>
//                     </form>
//                 </div>
//             </header>
//         </div>
//     );
// }

// export default VerifyOTP1;



import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './style.css'; // Assuming you have a CSS file for styling

function VerifyOTP1({ session }) {
    const [otp, setOtp] = useState('');
    const [message, setMessage] = useState('');
    const [messageType, setMessageType] = useState(''); // To manage success/error message types
    const baseUrl = process.env.REACT_APP_API_BASE_URL;
    console.log("API base URL:", baseUrl); // Debug log
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const otpData = { otp };   // Create a payload object with the OTP
           // const response = await axios.post('http://localhost:8080/verifys-otp', otpData, {
            const response = await axios.post(`${baseUrl}/verifys-otp`, otpData, {
                auth: {
                    username: 'p.nagasatyasai.123@gmail.com', // Replace with your actual username
                    password: 'satya' // Replace with your actual password
                },
                withCredentials: true, // Include credentials (cookies) with the request
                headers: {
                    'Content-Type': 'application/json' // Specify the content type
                }
            });

            if (response.data) {
                console.log(response.data);
                setMessage("Successfully registered!"); // Set success message
                setMessageType('success'); // Set message type to success
                setTimeout(() => navigate('/signin'), 2000); // Redirect after 2 seconds
            }
        } catch (error) {
            const errorMessage = "Error verifying OTP: " + (error.response?.data || error.message); // Handle error message
            setMessage(errorMessage);
            setMessageType('danger'); // Set message type to error
            setTimeout(() => navigate('/verify_otp1'), 2000); // Redirect after 2 seconds
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
                        </ul>
                    </div>
                </nav>

                <div className="form-container">
                    {message && (
                        <div className={`alert alert-${messageType} text-center`} role="alert">
                            {message}
                        </div>
                    )}
                    <form id="loginForm" className="form" onSubmit={handleSubmit}>
                        <h1 className="otp">OTP Verification</h1>
                        <h3 className="otp_text">We have sent an OTP to your email</h3>

                        <div className="form-group1">
                            <label style={{ fontSize: '17px', paddingBottom: '1px' }}>Enter OTP:</label>
                            <input
                                type="number"
                                name="otp"
                                style={{ width: '350px' }}
                                id="enter_otp"
                                placeholder="Enter your OTP"
                                value={otp}
                                onChange={(e) => setOtp(e.target.value)}
                                required
                            />
                            <br />
                        </div>
                        <button className="verify-btn" type="submit">Verify OTP</button>
                    </form>
                </div>
            </header>
        </div>
    );
}

export default VerifyOTP1;

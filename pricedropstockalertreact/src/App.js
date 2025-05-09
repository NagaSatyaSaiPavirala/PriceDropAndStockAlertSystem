// import logo from './logo.svg';
// import './App.css';

// function App() {
//   return (
//     <div className="App">
//       <header className="App-header">
//         <img src={logo} className="App-logo" alt="logo" />
//      <p>Hello from Satya</p>
//       </header>
//     </div>
//   );
// }

// export default App;



// import React from 'react';
// import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
// import Home from './home1'; // Import your Home component
// import About from './about1'; // Import your About component
// import Signin from './signin'; // Import your Signin component
// import Signup from './signup1'; // Import your Signup component

// function App() {
//   return (
//     <Router>
//       <Routes>
//         <Route path="/" element={<Home />} />
//         <Route path="/about" element={<About />} />
//         <Route path="/signin" element={<Signin />} />
//         <Route path="/signup" element={<Signup />} />
//       </Routes>
//     </Router>
//   );
// }

// export default App;






// import React, { useState } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
// import Home from './home1'; // Import your Home component
// import About from './about1'; // Import your About component
// import Signin from './signin'; // Import your Signin component
// import Signup from './signup1'; // Import your Signup component
// import Dashboard1 from './dashboard1'; // Import your Dashboard1 component

// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(false);
//     const [session, setSession] = useState({}); // Store session data if needed

//     const handleLogin = (sessionData) => {
//         setIsAuthenticated(true);
//         setSession(sessionData); // Store session or user info
//     };

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated ? <Dashboard1 /> : <Navigate to="/signin" />} 
//                 />
//             </Routes>
//         </Router>
//     );
// }

// export default App;







// import React, { useState } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
// import Home from './home1'; // Import your Home component
// import About from './about1'; // Import your About component
// import Signin from './signin'; // Import your Signin component
// import Signup from './signup1'; // Import your Signup component
// import Dashboard1 from './dashboard1'; // Import your Dashboard1 component

// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(false);
//     const [session, setSession] = useState({}); // Store session data

//     const handleLogin = (sessionData) => {
//         setIsAuthenticated(true);
//         setSession(sessionData); // Store session or user info
//     };

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated 
//                         ? <Dashboard1 session={session} />  // Pass session data to Dashboard1
//                         : <Navigate to="/signin" />
//                     } 
//                 />
//             </Routes>
//         </Router>
//     );
// }

// export default App;




// import React, { useState } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
// import Home from './home1'; // Import your Home component
// import About from './about1'; // Import your About component
// import Signin from './signin'; // Import your Signin component
// import Signup from './signup1'; // Import your Signup component
// import Dashboard1 from './dashboard1'; // Import your Dashboard1 component
// import AddProduct from './add_product'; // Import your AddProduct component
// import ProductList from './product_list'; // Import your ProductList component

// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(false);
//     const [session, setSession] = useState({}); // Store session data if needed

//     const handleLogin = (sessionData) => {
//         setIsAuthenticated(true);
//         setSession(sessionData); // Store session or user info
//     };

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 {/* <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated ? <Dashboard1 /> : <Navigate to="/signin" />} 
//                 /> */}
//                   <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated 
//                         ? <Dashboard1 session={session} />  // Pass session data to Dashboard1
//                        : <Navigate to="/signin" />
//                      } 
//                  />
//                 <Route 
//                     path="/user/add_product" 
//                     element={isAuthenticated ? <AddProduct session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/product_list" 
//                     element={isAuthenticated ? <ProductList session={session} /> : <Navigate to="/signin" />} 
//                 />
//             </Routes>
//         </Router>
//     );
// }

// export default App;



// import React, { useState, useEffect } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
// import Home from './home1'; 
// import About from './about1'; 
// import Signin from './signin'; 
// import Signup from './signup1'; 
// import Dashboard1 from './dashboard1'; 
// import AddProduct1 from './add_product1'; 
// import ProductList1 from './product_list1'; 

// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(false);
//     const [session, setSession] = useState({});

//     useEffect(() => {
//         const auth = localStorage.getItem('isAuthenticated') === 'true';
//         const sessionData = JSON.parse(localStorage.getItem('session'));
//         setIsAuthenticated(auth);
//         setSession(sessionData || {});
//     }, []);

//     // const handleLogin = (sessionData) => {
//     //     localStorage.setItem('isAuthenticated', true);
//     //     localStorage.setItem('session', JSON.stringify(sessionData));
//     //     setIsAuthenticated(true);
//     //     setSession(sessionData);
//     // };


//     const handleLogin = (sessionData) => {
//       console.log('User logged in with session data:', sessionData);
//       localStorage.setItem('isAuthenticated', true);
//       localStorage.setItem('session', JSON.stringify(sessionData));
//       setIsAuthenticated(true);
//       setSession(sessionData);
//   };
  

//     console.log("Is authenticated:", isAuthenticated); // Debugging line

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated ? <Dashboard1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/add_product" 
//                     element={isAuthenticated ? <AddProduct1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/product-list" 
//                     element={isAuthenticated ? <ProductList1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//             </Routes>
//         </Router>
//     );
// }

// export default App;



// import React, { useState, useEffect } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
// import Home from './home1';
// import About from './about1';
// import Signin from './signin';
// import Signup from './signup1';
// import Dashboard1 from './dashboard1';
// import AddProduct1 from './add_product1';
// import ProductList1 from './product_list1';
// import Logout from './Logout'; // Import the Logout component

// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(() => {
//         return localStorage.getItem('isAuthenticated') === 'true';
//     });
//     const [session, setSession] = useState({});

//     const handleLogin = (sessionData) => {
//         console.log('User logged in with session data:', sessionData);
//         localStorage.setItem('isAuthenticated', true);
//        // localStorage.setItem('session', JSON.stringify(sessionData));
//        localStorage.setItem('session',sessionData);

//         setIsAuthenticated(true);
//         setSession(sessionData);
//         console.log('Session is:', session);
//     };

//     const handleLogout = () => {
//         localStorage.removeItem('isAuthenticated');
//         localStorage.removeItem('session');
//         setIsAuthenticated(false);
//         setSession({});
//     };

//     useEffect(() => {
//         console.log('Is authenticated:', isAuthenticated);
//     }, [isAuthenticated]);

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated ? <Dashboard1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/add_product1" 
//                     //element={isAuthenticated ? <AddProduct1 session={{ username: 'NAGA SATYA SAI PAVIRALA', password: 'User@123' }}   /> : <Navigate to="/signin" />} 
//                     element={isAuthenticated ? <AddProduct1 session={session}   /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/product_list1" 
//                     element={isAuthenticated ? <ProductList1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route path="/logout" element={<Logout onLogout={handleLogout} />} />
//             </Routes>
//         </Router>
//     );
// }

// export default App;




// import React, { useState, useEffect } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate,useNavigate } from 'react-router-dom';
// import Home from './home1';
// import About from './about1';
// import Signin from './signin';
// import Signup from './signup1';
// import Dashboard1 from './dashboard1';
// import AddProduct1 from './add_product1';
// import ProductList1 from './product_list1';
// import Logout from './logout'; // Import the Logout component

// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(() => {
//         return localStorage.getItem('isAuthenticated') === 'true';
//     });
//    // const [session, setSession] = useState({});
//    const [session, setSession] = useState(() => {
//     const storedSession = localStorage.getItem('session');
//     return storedSession ? JSON.parse(storedSession) : {}; // Initialize from localStorage
// });


//     const handleLogin = (sessionData) => {
//         console.log('User logged in with session data:', sessionData);
//         localStorage.setItem('isAuthenticated', true);
//         localStorage.setItem('session', JSON.stringify(sessionData)); // Make sure to stringify
//         setIsAuthenticated(true);
//         setSession(sessionData);
//         console.log('Session is:', session); // Debugging line
//     };

//     const handleLogout = () => {
//         localStorage.removeItem('isAuthenticated');
//         localStorage.removeItem('session');
//         setIsAuthenticated(false);
//         setSession({});
//         Navigate('/signin');
//     };

//     useEffect(() => {
//         console.log('Is authenticated:', isAuthenticated);
//     }, [isAuthenticated]);

//     useEffect(() => {
//         console.log('Updated session state:', session);
//     }, [session]);

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 console.log('before calling dashboard',session);
//                 <Route 
//                     path="/user/dashboard" 
                    
//                     element={isAuthenticated ? <Dashboard1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/add_product1" 
//                     element={isAuthenticated ? <AddProduct1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/product_list1" 
//                     element={isAuthenticated ? <ProductList1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route path="/logout" element={<Logout onLogout={handleLogout} />} />
//             </Routes>
//         </Router>
//     );
// }

// export default App;









// import React, { useState, useEffect } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
// import Home from './home1';
// import About from './about1';
// import Signin from './signin';
// import Signup from './signup1';
// import Dashboard1 from './dashboard1';
// import AddProduct1 from './add_product1';
// import ProductList1 from './product_list1';
// //import Logout from './logout'; // Import the Logout component

// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(() => {
//         return localStorage.getItem('isAuthenticated') === 'true';
//     });
//     const [session, setSession] = useState(() => {
//         const storedSession = localStorage.getItem('session');
//         return storedSession ? JSON.parse(storedSession) : {}; // Initialize from localStorage
//     });

//     const handleLogin = (sessionData) => {
//         console.log('User logged in with session data:', sessionData);
//         localStorage.setItem('isAuthenticated', true);
//         localStorage.setItem('session', JSON.stringify(sessionData)); // Make sure to stringify
//         setIsAuthenticated(true);
//         setSession(sessionData);
//         console.log('Session is:', session); // Debugging line
//     };

//     const handleLogout = (navigate) => {
//         localStorage.removeItem('isAuthenticated');
//         localStorage.removeItem('session');
//         setIsAuthenticated(false);
//         setSession({});
//         //navigate('/signin'); // Navigate to sign-in after logout
//     };

//     useEffect(() => {
//         console.log('Is authenticated:', isAuthenticated);
//     }, [isAuthenticated]);

//     useEffect(() => {
//         console.log('Updated session state:', session);
//     }, [session]);

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated ? <Dashboard1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/add_product1" 
//                     element={isAuthenticated ? <AddProduct1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/product_list1" 
//                     element={isAuthenticated ? <ProductList1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                      <Route 
//                     path="/logout" 
//                     element={
//                         <>
//                             {handleLogout()}
//                             <Navigate to="/signin" />
//                         </>
//                     } 
//                 />
//             </Routes>
//         </Router>
//     );
// }

// export default App;



// import React, { useState, useEffect } from 'react';
// import { BrowserRouter as Router, Route, Routes, Navigate, useNavigate,useParams } from 'react-router-dom';
// import Home from './home1';
// import About from './about1';
// import Signin from './signin';
// import Signup from './signup1';
// import Dashboard1 from './dashboard1';
// import AddProduct1 from './add_product1';
// import ProductList1 from './product_list1';
// import EditProduct from './edit';
// function App() {
//     const [isAuthenticated, setIsAuthenticated] = useState(() => {
//         return localStorage.getItem('isAuthenticated') === 'true';
//     });
    
//     const [session, setSession] = useState(() => {
//         const storedSession = localStorage.getItem('session');
//         return storedSession ? JSON.parse(storedSession) : {}; // Initialize from localStorage
//     });

//     // const handleLogin = (sessionData) => {
//     //     console.log('User logged in with session data:', sessionData);
//     //     localStorage.setItem('isAuthenticated', true);
//     //     localStorage.setItem('session', JSON.stringify(sessionData)); // Make sure to stringify
//     //     setIsAuthenticated(true);
//     //     setSession(sessionData);
//     // };
//     const handleLogin = (sessionData, password) => {
//         console.log('User logged in with session data:', sessionData);
//         console.log("Password:", password);
//         localStorage.setItem('isAuthenticated', true);
//         // localStorage.setItem('session', JSON.stringify(sessionData,password)); // Make sure to stringify
//         localStorage.setItem('session', JSON.stringify({ ...sessionData, password })); // Combine into one object
//         setIsAuthenticated(true);
//         setSession({ ...sessionData, password });
//     };


//     const handleLogout = () => {
//         localStorage.removeItem('isAuthenticated');
//         localStorage.removeItem('session');
//         setIsAuthenticated(false);
//         setSession({});
//     };

//     useEffect(() => {
//         console.log('Is authenticated:', isAuthenticated);
//     }, [isAuthenticated]);

//     useEffect(() => {
//         console.log('Updated session state:', session);
//     }, [session]);

//     return (
//         <Router>
//             <Routes>
//                 <Route path="/" element={<Home />} />
//                 <Route path="/about" element={<About />} />
//                 <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
//                 <Route path="/signup" element={<Signup />} />
//                 <Route 
//                     path="/user/dashboard" 
//                     element={isAuthenticated ? <Dashboard1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/add_product1" 
//                     element={isAuthenticated ? <AddProduct1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/user/product_list1" 
//                     element={isAuthenticated ? <ProductList1 session={session} /> : <Navigate to="/signin" />} 
//                 />
//                  <Route 
//                     path="/user/editproduct/:id" 
//                     element={isAuthenticated ? <EditProduct session={session} productId={useParams().id} /> : <Navigate to="/signin" />} 
//                 />
//                 <Route 
//                     path="/logout" 
//                     element={
//                         <Logout handleLogout={handleLogout} />
//                     } 
//                 />
//             </Routes>
//         </Router>
//     );
// }

// // Logout component to handle logout and navigation
// const Logout = ({ handleLogout }) => {
//     const navigate = useNavigate();

//     useEffect(() => {
//         handleLogout();
//         navigate('/signin'); // Navigate to sign-in after logout
//     }, [handleLogout, navigate]);

//     return null; // You can also render a loading state if you want
// };

// export default App;







import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate, useNavigate, useParams } from 'react-router-dom';
import Home from './home1';
import About from './about1';
import Signin from './signin';
import Signup from './signup1';
import Dashboard1 from './dashboard1';
import AddProduct1 from './add_product1';
import ProductList1 from './product_list1';
import EditProduct from './edit';
import VerifyOtp1 from './verify_otp1';
import ErrorPage from './error';

function App() {
    const [isAuthenticated, setIsAuthenticated] = useState(() => {
        return localStorage.getItem('isAuthenticated') === 'true';
    });
    
    const [session, setSession] = useState(() => {
        const storedSession = localStorage.getItem('session');
        return storedSession ? JSON.parse(storedSession) : {}; // Initialize from localStorage
    });

    const handleLogin = (sessionData, password) => {
        console.log('User logged in with session data:', sessionData);
        console.log("Password:", password);
        localStorage.setItem('isAuthenticated', true);
        localStorage.setItem('session', JSON.stringify({ ...sessionData, password })); // Combine into one object
        setIsAuthenticated(true);
        setSession({ ...sessionData, password });
    };

    const handleLogout = () => {
        localStorage.removeItem('isAuthenticated');
        localStorage.removeItem('session');
        setIsAuthenticated(false);
        setSession({});
    };

    useEffect(() => {
        console.log('Is authenticated:', isAuthenticated);
    }, [isAuthenticated]);

    useEffect(() => {
        console.log('Updated session state:', session);
    }, [session]);

    return (
        <Router>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/about" element={<About />} />
                <Route path="/signin" element={<Signin onLogin={handleLogin} />} />
                <Route path="/signup" element={<Signup />} />
                <Route 
                    path="/user/dashboard" 
                    element={isAuthenticated ? <Dashboard1 session={session} /> : <Navigate to="/signin" />} 
                />
                <Route 
                    path="/user/add_product1" 
                    element={isAuthenticated ? <AddProduct1 session={session} /> : <Navigate to="/signin" />} 
                />
                <Route 
                    path="/user/product_list1" 
                    element={isAuthenticated ? <ProductList1 session={session} /> : <Navigate to="/signin" />} 
                />
                {/* <Route 
                    path="/user/editproduct/:id" 
                    element={<EditProductWrapper session={session} isAuthenticated={isAuthenticated} />} 
                /> */}
               <Route 
    path="/user/editproduct/:id/:price" 
    element={<EditProductWrapper session={session} isAuthenticated={isAuthenticated} />} 
/>

                 <Route path="/verify_otp1" element={<VerifyOtp1 />} />
                <Route 
                    path="/logout" 
                    element={
                        <Logout handleLogout={handleLogout} />
                    } 
                />
                <Route path="*" element={<ErrorPage />} />
            </Routes>
        </Router>
    );
}

// Create a wrapper component for EditProduct
// const EditProductWrapper = ({ session, isAuthenticated }) => {
//     const { id } = useParams(); // Extract the id here

//     return isAuthenticated ? <EditProduct session={session} productId={id} /> : <Navigate to="/signin" />;
// };


const EditProductWrapper = ({ session, isAuthenticated }) => {
    const { id, price } = useParams(); // Extract id and price from URL

    return isAuthenticated 
        ? <EditProduct session={session} productId={id} productPrice={price} /> 
        : <Navigate to="/signin" />;
};



// Logout component to handle logout and navigation
const Logout = ({ handleLogout }) => {
    const navigate = useNavigate();

    useEffect(() => {
        handleLogout();
        navigate('/signin'); // Navigate to sign-in after logout
    }, [handleLogout, navigate]);

    return null; // You can also render a loading state if you want
};

export default App;

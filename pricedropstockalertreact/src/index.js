import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import CBC from './CBC';
import FBC from './FBC';
import About from './about1';
import AddProduct from './add_product';
import AddProduct1 from './add_product1';
import Dashboard from './dashboard';
import Dashboard1 from './dashboard1';
import EditProduct from './edit';
import ErrorPage from './error';
import Home from './home';
import Home1 from './home1';
import ProductList from './product_list';
import ProductList1 from './product_list1';
import SignIn from './signin';
import SignUp from './signup';
import SignUp1 from './signup1';
import VerifyOTP from './verify_otp';
import VerifyOTP1 from './verify_otp1';
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
    {/* <Home/> */}
    {/* <Home1/> */}
     {/* <About/> */}
    {/* <AddProduct />
     <AddProduct1/>  */}
     {/*
    <Dashboard/>
 <Dashboard1/> 
       <EditProduct/> 
    <ErrorPage/>
 
     <ProductList/> 
  <ProductList1/> 
 <SignIn/> 
 <SignUp/> 
 <SignUp1/> 
 <VerifyOTP/> 
 <VerifyOTP1/> 
    <CBC value="PNSS"/>
    <FBC topic="React"/> */}
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();

import React from 'react';
import './style.css'; // Assuming you have the corresponding CSS file
import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css'; // FontAwesome


function Home1() {
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
                <div className="header-content">
                <h1 className="lg-heading text-black">Price Drop And Stock Alert</h1>
                <p className="text-black">"Get instant notifications on price drops and stock alerts for your favorite products."</p>
                <a className="text-blue btn btn-primary med-heading" href="#explore-places1">More Info</a>
            </div>
            </header>

         

            <section className="showcase" id="explore-places1">
                <div className="container">
                    <div className="row row1">
                        <div className="imgbox">
                            <img src={require('./img/fam.png')} alt="img" />
                        </div>
                        <div className="textbox">
                            <h1 className="med-heading text-black">Get Price Drop And Stock Alerts</h1>
                            <p className="text-black">
                                Do you want to get the lowest overall price or stock alert on your next big purchase? <br />
                                Get price alerts when retailers slash their prices to maximize your savings.<br />
                                Our price watcher tools use data to ensure you get the best price.
                            </p>
                        </div>
                    </div>
                </div>

                <section id="products">
                    <h2 className="product-title">Set Price Drop and Stock Alerts for Products</h2>
                    <div className="product-container container">
                        <div className="product-box">
                            <div className="star-rating">
                                {[...Array(5)].map((_, i) => (
                                    <span key={i} className="fa fa-star checked"></span>
                                ))}
                            </div>
                            <p className="product-text">Apple iPhone 16</p>
                            <div className="product-details">
                                <div className="product-photo">
                                    <img src={require('./img/pd1.jpg')} alt="img" />
                                    <p className="product-price">Price Now: ₹79,900</p>
                                    <p className="product-price">Price Alert: ₹70,000</p>
                                </div>
                            </div>
                        </div>

                        <div className="product-box">
                            <div className="star-rating">
                                {[...Array(5)].map((_, i) => (
                                    <span key={i} className="fa fa-star checked"></span>
                                ))}
                            </div>
                            <p className="product-text">Surf Excel Matic</p>
                            <div className="product-details">
                                <div className="product-photo">
                                    <img src={require('./img/pd2.jpg')} alt="" />
                                    <p className="product-price">Price Now: ₹399</p>
                                    <p className="product-price">Price Alert: ₹349</p>
                                </div>
                            </div>
                        </div>

                        <div className="product-box">
                            <div className="star-rating">
                                {[...Array(5)].map((_, i) => (
                                    <span key={i} className="fa fa-star checked"></span>
                                ))}
                            </div>
                            <p className="product-text">Soundcore Life Q10</p>
                            <div className="product-details">
                                <div className="product-photo">
                                    <img src={require('./img/pd3.jpg')} alt="" />
                                    <p className="product-price">Price Now: ₹4,999</p>
                                    <p className="product-price">Price Alert: ₹3,999</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </section>

            <footer>
                <div className="footer-row">
                    <div className="col">
                        <p id="footerp">
                            Dynamic Price Monitoring And Alerting System is a free tool to check price history charts for millions of products.
                        </p>
                    </div>
                    <div className="col2">
                        <h3>DYNAMIC PRICE MONITORING AND ALERTING SYSTEM</h3>
                        <p>Features</p>
                        <p className="email-id">dynamicpricemonitoringalerting@gmail.com</p>
                        <h4>+91-9998887779</h4>
                    </div>
                    <div className="col">
                        <h3 style={{ fontWeight: 'bold' }}>Links</h3>
                        <ul>
                            <li><a href="/">Home</a></li>
                            <li><a href="/about">About Us</a></li>
                            <li><a href="/contactus">Contact Us</a></li>
                            <li><a href="/">Terms of Service</a></li>
                        </ul>
                    </div>
                    <div className="col">
                        <div className="social-icons">
                            <i className="fab fa-facebook-f"></i>
                            <i className="fab fa-twitter"></i>
                            <i className="fab fa-whatsapp"></i>
                        </div>
                    </div>
                </div>
                <hr />
                <p className="copyright">
                    Dynamic Price Monitoring And Alerting System © 2024 - All Rights Reserved
                </p>
            </footer>
        </div>
    );
}

export default Home1;

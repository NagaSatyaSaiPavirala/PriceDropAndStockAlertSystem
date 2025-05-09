import React, { useState } from 'react';
import './style.css'; 

function SignUp({ session }) {
    const [email, setEmail] = useState('');
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [mobile, setMobile] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        // Implement the logic to send OTP
        // For example, i might call an API here with email, username, password, and mobile
    };

    return (
        <section style={{ margin: 'auto', width: '400px', marginTop: '100px' }}>
            {/* Display message about OTP sent */}
            {session?.message && (
                <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
                    <p className="text-center">{session.message.content}</p>
                    {/* You can consume the message here */}
                </div>
            )}
            <br />

            <b>SIGN UP</b>
            <br />
            <form onSubmit={handleSubmit}>
                <label>Email - Id :- </label>
                <input
                    type="email"
                    name="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <br />
                <label>Username :- </label>
                <input
                    type="text"
                    name="username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    required
                />
                <br />
                <label>Password :- </label>
                <input
                    type="password"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />
                <br />
                <label>Mobile no :- </label>
                <input
                    type="number"
                    name="mno"
                    value={mobile}
                    onChange={(e) => setMobile(e.target.value)}
                    required
                />
                <br />
                <button type="submit" name="send_otp">Send OTP</button> {/* Button to send OTP */}
            </form>
            <br />
            <br />
            <a href="/">Home</a>
        </section>
    );
}

export default SignUp;

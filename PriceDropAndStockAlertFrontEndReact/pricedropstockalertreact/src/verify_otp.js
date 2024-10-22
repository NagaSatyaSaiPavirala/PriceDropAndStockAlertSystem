import React, { useState } from 'react';
import './style.css'; // Assuming you have a CSS file for styling

function VerifyOTP({ session }) {
    const [otp, setOtp] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        // Implement the logic to verify OTP
        // You might call an API here to verify the OTP
    };

    return (
        <div>
            <h1>OTP Verification</h1>
            <br />

            {session?.message && (
                <div className={`alert alert-primary text-center ${session.message.type}`} role="alert">
                    <p className="text-center">{session.message.content}</p>
                    {/* You can consume the message here */}
                </div>
            )}

            <br />
            <h4>Enter OTP</h4>
            <br />
            <form onSubmit={handleSubmit}>
                <input
                    type="number"
                    name="otp"
                    placeholder="Enter OTP"
                    value={otp}
                    onChange={(e) => setOtp(e.target.value)}
                    required
                />
                <button type="submit">VERIFY OTP</button>
            </form>
        </div>
    );
}

export default VerifyOTP;

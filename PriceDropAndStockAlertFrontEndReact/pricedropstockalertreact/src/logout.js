// // Assuming you have a Logout component like this
// import React from 'react';

// const Logout = ({ onLogout }) => {
//     const handleLogout = () => {
//         onLogout();
//     };

//     return (
//         <div>
//             <h2>Logout</h2>
//             <button onClick={handleLogout}>Logout</button>
//         </div>
//     );
// };

// export default Logout; // Ensure it's exported as default


// import React from 'react';
// import { useNavigate } from 'react-router-dom';

// const LogoutWrapper = ({ onLogout }) => {
//     const navigate = useNavigate();

//     const handleLogout = () => {
//         onLogout(navigate); // Call the onLogout function with navigate
//     };

//     return (
//         <div>
//             <h2>Logout</h2>
//             <button onClick={handleLogout}>Logout</button>
//         </div>
//     );
// };

// export default LogoutWrapper;



import React from 'react';
import { useNavigate } from 'react-router-dom';

const Logout = ({ onLogout }) => {
    const navigate = useNavigate(); // Get the navigate function

    const handleLogout = () => {
        onLogout(navigate); // Call onLogout with navigate
    };

    return (
        <div>
            <h2>Logout</h2>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
};

export default Logout; // Ensure it's exported as default


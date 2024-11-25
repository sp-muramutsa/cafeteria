import React from 'react';

const RegisterConfirmationPage: React.FC = () => {
    return (
        <div className="h-screen flex">

            {/* Right Side - Text */}
            <div className="w-1/2 bg-white flex flex-col justify-center items-center p-8">
                <h1 className="text-4xl font-bold text-blue-800 mb-4">
                    Thank You!
                </h1>
                <p className="text-lg text-gray-600">
                    Thank you for registering to Rowdy's Cafeteria! We’ve sent you a confirmation email.
                    Please click the link in the email to verify your account.
                </p>
            </div>
            {/* Left Side - Picture of Cafeteria or food */}
            <div className="w-1/2 bg-gradient-to-r from-customDarkGreen to-customLightYellow flex justify-center items-center">
                <img
                    src="https://via.placeholder.com/300"
                    alt="Thank you illustration"
                    className="max-w-full max-h-full rounded-lg shadow-lg"
                />
            </div>

        </div>
    );
};

export default RegisterConfirmationPage;

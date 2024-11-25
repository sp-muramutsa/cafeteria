import React, { useState } from 'react';
import { Outlet } from 'react-router-dom';
import SignUpForm from "../components/LoginComponents/Forms/SignUpForm";
import LoginForm from "../components/LoginComponents/Forms/LoginForm";
import RightOverlayContent from "../components/LoginComponents/Overlays/RightOverlayContent";
import LeftOverlayContent from "../components/LoginComponents/Overlays/LeftOverlayContent";

const LoginPage = () => {
    const [isAnimated, setIsAnimated] = useState(false);
    const overlayBg =
        "bg-gradient-to-r from-blue-800 via-customLightYellow to-customLightGreen";
    return (
        <div className="h-screen w-screen bg-white relative overflow-hidden rounded-lg">
            {/* Sign In Form */}
            <div
                className={`bg-white absolute top-0 left-0 h-full w-1/2 flex justify-center items-center transition-all duration-700 ease-in-out z-20 ${
                    isAnimated ? "translate-x-full opacity-0" : ""
                }`}
            >
                <LoginForm />
            </div>

            {/* Sign Up Form */}
            <div
                className={`absolute top-0 left-0 h-full w-1/2 flex justify-center items-center transition-all duration-700 ease-in-out ${
                    isAnimated
                        ? "translate-x-full opacity-100 z-50 animate-show"
                        : "opacity-0 z-10"
                }`}
            >
                <div className="h-full w-full flex justify-center items-center">
                    <SignUpForm />
                </div>
            </div>

            {/* Overlay Container */}
            <div
                className={`absolute top-0 left-1/2 w-1/2 h-full overflow-hidden transition-transform duration-700 ease-in-out z-100 ${
                    isAnimated ? "-translate-x-full" : ""
                }`}
            >
                <div
                    className={`${overlayBg} relative -left-full h-full w-[200%] transform transition-transform duration-700 ease-in-out ${
                        isAnimated ? "translate-x-1/2" : "translate-x-0"
                    }`}
                >
                    <div
                        className={`w-1/2 h-full absolute flex justify-center items-center top-0 transform -translate-x-[20%] transition-transform duration-700 ease-in-out ${
                            isAnimated ? "translate-x-0" : "-translate-x-[20%]"
                        }`}
                    >
                        <LeftOverlayContent
                            isAnimated={isAnimated}
                            setIsAnimated={setIsAnimated}
                        />
                    </div>
                    <div
                        className={`w-1/2 h-full absolute flex justify-center items-center top-0 right-0 transform transition-transform duration-700 ease-in-out ${
                            isAnimated ? "translate-x-[20%]" : "translate-x-0"
                        }`}
                    >
                        <RightOverlayContent
                            isAnimated={isAnimated}
                            setIsAnimated={setIsAnimated}
                        />
                    </div>
                </div>
            </div>
            <Outlet />
        </div>
    );
};

export default LoginPage;

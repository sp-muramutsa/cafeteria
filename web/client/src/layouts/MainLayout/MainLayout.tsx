import React from 'react';
import { Outlet } from 'react-router-dom';

const MainLayout = () => {
    return (
        <div className="min-h-screen bg-gray-1000 flex items-center justify-center">
            <div className="w-full max-w-md p-6 bg-gray-100 rounded-lg shadow-md">
                <Outlet />
            </div>
        </div>
    );
}

export default MainLayout;

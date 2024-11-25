import React, { useState } from 'react';
import { EyeIcon } from '@heroicons/react/24/solid';
import { EyeSlashIcon } from '@heroicons/react/24/solid';

interface PasswordFieldProps {
    password: string;
    setPassword: React.Dispatch<React.SetStateAction<string>>;
}
const PasswordField: React.FC<PasswordFieldProps> = ({ password, setPassword }) => {
    const [isPasswordVisible, setIsPasswordVisible] = useState<boolean>(false);

    const togglePassword = () => {
        setIsPasswordVisible(!isPasswordVisible);
    };

    return (
        <div className="mt-8 relative">
            <input
                id="password"
                type={isPasswordVisible ? 'text' : 'password'}
                name="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                className="peer h-10 w-full border-b-2 border-gray-300 text-gray-900 placeholder-transparent focus:outline-none focus:border-indigo-600"
                placeholder="Password"
            />

            <div
                className="absolute right-0 top-1/2 transform -translate-y-1/2 cursor-pointer"
                onClick={togglePassword}
            >
                {isPasswordVisible ? (
                    <EyeSlashIcon className="h-5 w-5 text-gray-600" />
                ) : (
                    <EyeIcon className="h-5 w-5 text-gray-600" />
                )}
            </div>

            <label
                htmlFor="password"
                className="absolute left-0 -top-3.5 text-gray-600 text-sm transition-all peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-400 peer-placeholder-shown:top-2 peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm"
            >
                Password
            </label>
        </div>
    );
};

export default PasswordField;

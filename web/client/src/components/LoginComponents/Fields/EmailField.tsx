import React from 'react';

interface EmailFieldProps {
    email: string;
    setEmail: React.Dispatch<React.SetStateAction<string>>;
}

const EmailField: React.FC<EmailFieldProps> = ({ email, setEmail})=> {

    return (
        <div className="mt-8 relative">
            <input
                id="email"
                name="email"
                type="email"
                pattern="^.+@mcneese\.edu$"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="peer h-10 w-full border-b-2 border-gray-300 text-gray-900 placeholder-transparent focus:outline-none focus:border-indigo-600"
                placeholder="johnmcneese@mcneese.edu"
            />
            <label
                htmlFor="email"
                className="absolute left-0 -top-3.5 text-gray-600 text-sm transition-all peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-400 peer-placeholder-shown:top-2 peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm"
            >
                McNeese Email
            </label>
        </div>
    );
};

export default EmailField;

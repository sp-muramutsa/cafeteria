import React from 'react';

interface NameFieldProps {
    nameType: string;
    name: string;
    setName: React.Dispatch<React.SetStateAction<string>>;
}
const NameField: React.FC<NameFieldProps> = ({nameType, name, setName}) => {
    return (
        <div className="mt-8 relative">
            <input
                id={nameType}
                name={nameType}
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="peer h-10 w-full border-b-2 border-gray-300 text-gray-900 placeholder-transparent focus:outline-none focus:border-indigo-600"
                placeholder={nameType}
            />
        <label
            htmlFor={nameType}
        className="absolute left-0 -top-3.5 text-gray-600 text-sm transition-all peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-400 peer-placeholder-shown:top-2 peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm"
        >
            {nameType === 'firstName' ? 'First Name' : 'Last Name'}
        </label>
        </div>
    );
};
export default NameField;
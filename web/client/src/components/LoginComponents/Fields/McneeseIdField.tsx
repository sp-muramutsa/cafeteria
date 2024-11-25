import React from 'react';

interface McNeeseIdFieldProps {
    mcneeseId: string;
    setMcneeseId: React.Dispatch<React.SetStateAction<string>>;
}
const McneeseIdField : React.FC<McNeeseIdFieldProps> = ({mcneeseId, setMcneeseId}) => {
    return (
        <div className="mt-8 relative">
            <input
                id="mcneeseId"
                name="mcneeseId"
                type="text"
                minLength={9}
                maxLength={9}
                value={mcneeseId}
                onChange={(e) => setMcneeseId(e.target.value)}
                className="peer h-10 w-full border-b-2 border-gray-300 text-gray-900 placeholder-transparent focus:outline-none focus:border-indigo-600"
                placeholder="000333999"
            />
            <label
                htmlFor="mcneeseId"
                className="absolute left-0 -top-3.5 text-gray-600 text-sm transition-all peer-placeholder-shown:text-base peer-placeholder-shown:text-gray-400 peer-placeholder-shown:top-2 peer-focus:-top-3.5 peer-focus:text-gray-600 peer-focus:text-sm"
            >
                McNeese ID
            </label>
        </div>
    )
};

export default McneeseIdField;
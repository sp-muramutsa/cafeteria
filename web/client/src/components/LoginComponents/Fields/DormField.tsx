import React from 'react';

interface DormFieldProps {
    dorm: string;
    setDorm: React.Dispatch<React.SetStateAction<string>>;
}

const DormField: React.FC<DormFieldProps> = ({dorm, setDorm}) =>{
    return (
        <div
            className="mt-8 relative">
            <select
                className="peer h-10 w-full border-b-2 border-gray-300 rounded-md text-gray-900 focus:outline-none focus:border-indigo-600"
                value={dorm}
                onChange={(e) => setDorm(e.target.value)}
            >
                <option value="">Dorm</option>
                <option value="COLLETTE">Collette</option>
                <option value="BURTON">Burton</option>
                <option value="GARDEN">Belle Gardens</option>
                <option value="KING">King</option>
                <option value="WATKINS">Watkins</option>
                <option value="ZIGLER">Zigler</option>
            </select>
        </div>
    )
}

export default DormField;
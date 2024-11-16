import React from 'react';

export default function Coursebutton({ text }) {
    return (
        <button
            className="bg-white bg-opacity-20 text-black py-2 px-8 rounded-md shadow-md border border-gray-300 hover:bg-opacity-30 transition duration-200"
        >
            {text}
        </button>
    );
}

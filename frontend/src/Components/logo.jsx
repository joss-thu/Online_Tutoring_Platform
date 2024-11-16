
import React from 'react';
export default function logo () {
    return (
        <div className="flex justify-between items-center w-full max-w-screen-xl mx-auto py-4 px-6">
            <div className="flex items-center">
                <img src="/logo512.png" alt="Logo" className="w-10 h-10" />
                <span className="ml-3 font-bold text-xl text-[#171a1f]">THUtorial</span>
            </div>
        </div>
    );
};

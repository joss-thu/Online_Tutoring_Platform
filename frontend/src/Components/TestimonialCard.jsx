import React from 'react';
import profileIcon from '../assets/profile.png'; // Update to the correct path of the avatar icon

export default function TestimonialCard({ name, rating, quote }) {
    return (
        <div className="w-full max-w-md bg-white border rounded-lg shadow-md p-6 mx-auto flex flex-col items-center relative">
            {/* Centered Avatars */}
            <div className="flex justify-center gap-4 mb-4">
                <img className="w-12 h-12 rounded-full" alt="Avatar" src={profileIcon} />
                <img className="w-12 h-12 rounded-full" alt="Avatar" src={profileIcon} />
                <img className="w-12 h-12 rounded-full" alt="Avatar" src={profileIcon} />
            </div>

            {/* User Info and Rating */}
            <div className="text-center mb-4">
                <span className="block font-semibold text-lg text-[#171a1f]">{name}</span>
                <span className="block font-semibold text-gray-600 text-sm">Rating: {rating}</span>
            </div>

            {/* Quote with Quotation Marks */}
            <div className="mt-4 flex items-center justify-center text-center px-4">
                <span className="text-[#bcc1ca] text-4xl font-bold mr-2">“</span>
                <p className="text-gray-700 text-sm font-merriweather">{quote}</p>
                <span className="text-[#bcc1ca] text-4xl font-bold ml-2">”</span>
            </div>
        </div>
    );
}

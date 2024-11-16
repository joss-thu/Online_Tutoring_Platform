import React from 'react';

export default function Statisticsection() {
    const statsData = [
        { number: "870", text: "expert tutors" },
        { number: "200+", text: "hours tutored" },
        { number: "298", text: "Subjects and courses" },
        { number: "72920", text: "Active students" },
    ];

    return (
        <div className="w-full bg-primary text-white py-6 font-sans">
            {/* Centered container with responsive grid layout */}
            <div className="max-w-screen-xl mx-auto grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-8 text-center">
                {statsData.map((item, index) => (
                    <div key={index}>
                        <h4 className="text-3xl font-bold">{item.number}</h4>
                        <p className="text-lg">{item.text}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};



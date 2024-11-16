import React from 'react';

export default function BenefitCard({ icon, title, description }) {
    return (
        <div className="text-center p-6 border rounded-lg shadow-md">
            <img src={icon} alt={title} className="w-12 h-12 mx-auto mb-4" />
            <h4 className="font-bold text-xl mb-2">{title}</h4>
            <p className="text-gray-600 text-sm">{description}</p>
        </div>
    );
};


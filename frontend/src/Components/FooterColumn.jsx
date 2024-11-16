import React from 'react';

export default function FooterColumn({ title, items }) {
    return (
        <div>
            <h4 className="font-bold text-lg mb-4">{title}</h4>
            <ul>
                {items.map((item, index) => (
                    <li key={index} className="text-gray-700 mb-2">{item}</li>
                ))}
            </ul>
        </div>
    );
}

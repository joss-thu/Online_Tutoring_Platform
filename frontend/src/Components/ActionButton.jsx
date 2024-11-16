
import React from 'react';

export default function ActionButton({ text, onClick, className = '', ...props }) {
    return (
        <button
            className={`bg-primary text-white rounded-full py-2 px-6 shadow-md hover:bg-primary-dark ${className}`}
            onClick={onClick}
            {...props}
        >
            {text}
        </button>
    );
}

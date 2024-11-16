import React from 'react';
import BenefitCard from './BenefitCard';
import studentIcon from '../assets/studentIcon.svg'; // Update path to icon
import fulltimeIcon from '../assets/fulltimeIcon.svg'; // Update path to icon
import whiteboardIcon from '../assets/whiteboardIcon.svg'; // Update path to icon
import moneyIcon from '../assets/moneyIcon.svg'; // Update path to icon

export default function BenefitsSection() {
    const benefitsData = [
        {
            icon: studentIcon,
            title: "One-on-one teaching",
            description: "velit. Eu elit cillum eiusmod sint adipisicing exercitation. Lorem reprehenderit deserunt in enim occae",
        },
        {
            icon: fulltimeIcon,
            title: "24/7 availability",
            description: "velit. Eu elit cillum eiusmod sint adipisicing exercitation. Lorem reprehenderit deserunt in enim occae",
        },
        {
            icon: whiteboardIcon,
            title: "Interactive Whiteboard",
            description: "velit. Eu elit cillum eiusmod sint adipisicing exercitation. Lorem reprehenderit deserunt in enim occae",
        },
        {
            icon: moneyIcon,
            title: "Affordable Prices",
            description: "velit. Eu elit cillum eiusmod sint adipisicing exercitation. Lorem reprehenderit deserunt in enim occae",
        },
    ];

    return (
        <div className="py-16 px-6">
            <div className="max-w-screen-xl mx-auto text-center">
                <h2 className="text-yellow-600 font-bold text-lg">Why choose us?</h2>
                <h3 className="text-4xl font-semibold text-gray-800 mt-4 mb-8">
                    Benefits of online tutoring with us
                </h3>
                <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-8">
                    {benefitsData.map((benefit, index) => (
                        <BenefitCard
                            key={index}
                            icon={benefit.icon}
                            title={benefit.title}
                            description={benefit.description}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
};


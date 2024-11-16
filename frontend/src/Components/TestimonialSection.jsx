import React from 'react';
import TestimonialCard from './TestimonialCard';

export default function TestimonialSection() {
    const testimonials = [
        {
            name: "Annie Haley",
            rating: "4.5",
            quote: "Nostrud excepteur magna id est quis in aliqua consequat. Exercitation enim eiusmod elit sint laborum",
        },
    ];

    return (
        <div className="flex flex-col items-center gap-6 py-16">
            {/* Testimonials Heading */}
            <div className="text-center">
                <h2 className="text-[#ffa000] font-merriweather text-base">
                    Our Testimonials
                </h2>
                <h3 className="font-lato font-semibold text-[#171a1f] text-2xl mt-2">
                    What Our Students Say About Us
                </h3>
            </div>

            {/* Testimonials Display */}
            {testimonials.map((testimonial, index) => (
                <TestimonialCard
                    key={index}
                    name={testimonial.name}
                    rating={testimonial.rating}
                    quote={testimonial.quote}
                />
            ))}
        </div>
    );
}

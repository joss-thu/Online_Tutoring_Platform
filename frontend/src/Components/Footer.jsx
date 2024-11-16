import React from 'react';
import FooterColumn from './FooterColumn';


export default function Footer() {
    return (
        <div className="bg-[#e0e0e0] py-10">
            <div className="max-w-screen-xl mx-auto grid grid-cols-1 md:grid-cols-4 gap-8 px-6">
                {/* Branding Section */}
                <div>
                    <div className="flex items-center mb-4">
                        <img src="../assets/logo512.png" alt="THUtorium Logo" className="w-10 h-10" />
                        <h4 className="ml-3 text-2xl font-bold">THUtorium</h4>
                    </div>
                    <p className="text-gray-600">&copy; 2022 Brand, Inc.</p>
                </div>

                {/* Footer Columns */}
                <FooterColumn title="Product" items={["Features", "Pricing"]} />
                <FooterColumn title="Resources" items={["Blog", "User guides", "Webinars"]} />
                <FooterColumn title="Company" items={["About", "Join us"]} />

                {/* Newsletter Subscription Section */}
                <div className="md:col-span-2 lg:col-span-1 mt-6 md:mt-0">
                    <h4 className="font-bold text-lg mb-4">Subscribe to our newsletter</h4>
                    <p className="text-gray-700 mb-4">For product announcements and exclusive insights</p>
                    <div className="flex">
                        <input
                            type="email"
                            placeholder="Input your email"
                            className="w-full px-4 py-2 border border-gray-400 rounded-l"
                        />
                        <button className="bg-primary text-white px-4 rounded-r">
                            Subscribe
                        </button>
                    </div>
                </div>
            </div>

            {/* Footer Bottom Section */}
            <div className="text-center mt-6 text-gray-600">
                <p>Privacy • Terms • Sitemap</p>
                <div className="flex justify-center space-x-4 mt-2">
                    <span className="text-gray-600">© 2022 Brand, Inc.</span>
                </div>
            </div>
        </div>
    );
}

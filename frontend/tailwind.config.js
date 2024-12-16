// tailwind.config.js
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}", // Adjust paths as needed
    "./public/index.html",
  ],
  theme: {
    extend: {
      colors: {
        primary: '#003366',
        'primary-dark': '#002244',
        secondary: '#FFA000',
      },
      fontFamily: {
        sans: ['Lato', 'sans-serif'],          // For general sans-serif text
        serif: ['Merriweather', 'serif'],      // For general serif text
      },
    },
  },
  plugins: [],
};

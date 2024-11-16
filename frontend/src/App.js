
import "./App.css";
import React, { useEffect, useState } from "react";
import ActionButton from "./Components/ActionButton";
import SearchBar from "./Components/SearchBar";
import Coursebutton from "./Components/coursebutton";
import Statisticsection from "./Components/Statisticsection";
import BenefitsSection from "./Components/BenefitsSection";
import TestimonialSection from "./Components/TestimonialSection";
import Footer from "./Components/Footer";

//all are examples used for trials, the positioning has to be done!
function App() {
    const [count, setCount] = useState(0);
    const [searchQuery, setSearchQuery] = useState('');
    const categories = ['Engineering', 'Programming', 'Languages', 'Mathematics', 'See All'];

    const handleRegisterClick = () => {
        console.log("Register clicked");
    };

    const handleLoginClick = () => {
        console.log("Login clicked");
    };
    const handleClick = async () => {
    setCount(count + 1);
  };
    const handleButtonClick = () => {
        alert(`Searching for: ${searchQuery}`);
    };
  useEffect(() => {}, []);

  const handleSearchChange = (e) => {
      setSearchQuery(e.target.value);
    };
  return (

    <div className="app font-sans text-gray-800">
      <header className="app-header app-header flex justify-between items-center p-6">
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="app-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      <ActionButton text={count} onClick={handleClick} />
        <ActionButton text="Register" onClick={handleRegisterClick}  className="bg-primary text-white hover:bg-primary-dark" />
        <ActionButton text="Login" onClick={handleLoginClick} className="bg-primary hover:bg-primary-dark" />
      <ActionButton
          text="Search"
          onClick={handleButtonClick}
          className="mt-4"
      />
      <SearchBar
          placeholder="Search for tutors"
          onChange={handleSearchChange}
          className="mt-6"
      />
        <div className="flex justify-center gap-8 mt-8">
            {categories.map((category) => (
                <Coursebutton key={category} text={category} />
            ))}
        </div>
        <div className="app">
            <Statisticsection />
        </div>
        <div className="font-sans text-gray-800">
            <h1 className="text-3xl font-bold">This is Lato (Sans-Serif)</h1>
            <p className="font-serif text-lg">This is Merriweather (Serif)</p>
        </div>
        <div className="app">
            {/* Display the BenefitsSection component */}
            <BenefitsSection />
        </div>
        <div className="app">
            <TestimonialSection />
            <div className="app">
                <Footer />
            </div>
        </div>
    </div>

  );
}

export default App;

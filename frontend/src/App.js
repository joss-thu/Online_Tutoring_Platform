import logo from "./logo.svg";
import "./App.css";
import React, { useEffect, useState } from "react";
import ActionButton from "./Components/ActionButton";

function App() {
  const [count, setCount] = useState(0);

  const handleClick = async () => {
    setCount(count + 1);
  };

  useEffect(() => {}, []);

  return (
    <div className="app">
      <header className="app-header">
        <img src={logo} className="app-logo" alt="logo" />
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
    </div>
  );
}

export default App;

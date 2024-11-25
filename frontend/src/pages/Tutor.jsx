import NavBar from "../components/Navbar";
import React from "react";

function Tutor() {
  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar isLoggedIn={true} currentPage="/" />
    </div>
  );
}

export default Tutor;

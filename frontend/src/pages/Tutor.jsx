import NavBar from "../components/Navbar";
import React, { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

function Tutor() {
  const navigate = useNavigate();
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const id = query.get("id");
  const fetchTutorDetails = async () => {
    const res = await fetch("http://localhost:8080/tutor?tutorId=" + id);
    const data = await res.json();
    console.log(data);
    return data?.data;
  };
  useEffect(() => {
    fetchTutorDetails();
  });
  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar isLoggedIn={true} currentPage="/" />
    </div>
  );
}

export default Tutor;

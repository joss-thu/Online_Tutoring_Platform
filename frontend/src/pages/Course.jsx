import { useLocation, useNavigate } from "react-router-dom";
import NavBar from "../components/Navbar";
import React from "react";
import { Tooltip } from "react-tooltip";

function Course() {
  const navigate = useNavigate();
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const id = query.get("id");
  const categoryName = "Philosophy";
  const tutorId = 8;
  const isLoggedIn = false;

  // const fetchCourseDetails = async () => {
  //   const res = await fetch("http://localhost:8000/courses/" + id);
  //   const data = await res.json();
  // };

  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar isLoggedIn={false} currentPage="/" />
      <div className="mt-[120px] w-full max-w-6xl font-merriweather_sans mb-10">
        <div className="flex items-center">
          <div className="flex flex-col w-5/6">
            <div className="flex bg-white overflow-hidden text-sm">
              <span className="cursor-pointer" onClick={() => navigate("/")}>
                Home
              </span>
              <span className="mx-1">/</span>
              <span
                className="cursor-pointer"
                onClick={() => navigate("/search?categoryName=" + categoryName)}
              >
                Philosophy
              </span>
              <span className="mx-1">/</span>
              <span
                className="cursor-pointer"
                onClick={() => navigate("/course?id=" + id)}
              >
                Introduction to Philosophy
              </span>
            </div>
            <div className="inline-flex items-center w-full mt-5">
              <div className="text-4xl">Introduction to Philosophy</div>
              <span
                onClick={() => {
                  navigator.clipboard.writeText(window.location.href);
                }}
                className="copy_link_anchor_element material-symbols-rounded text-3xl ml-3 cursor-pointer text-blue-600"
              >
                link
              </span>
            </div>
            <Tooltip
              anchorSelect=".copy_link_anchor_element"
              place="top"
              openOnClick="true"
            >
              Link copied!
            </Tooltip>
            <div
              onClick={() => navigate("/tutor?id=" + tutorId)}
              className="text-xl text-gray-800 cursor-pointer w-auto self-start"
            >
              By Hannah Davis
            </div>
            <div className="text-sm text-gray-500 mt-3">
              Average Rating: 3 (1)
            </div>
          </div>
          <button
            onClick={() => {
              if (isLoggedIn) {
                navigate("/");
              }
            }}
            className={
              isLoggedIn
                ? "bg-blue-800 ml-5 max-h-12 rounded-full text-white py-2 px-4"
                : "reserve_now_anchor_element bg-blue-800 ml-5 max-h-12 rounded-full text-white py-2 px-4"
            }
          >
            Enroll now
          </button>
          <Tooltip
            anchorSelect=".reserve_now_anchor_element"
            place="top"
            openOnClick={true}
          >
            Log in first!
          </Tooltip>
        </div>

        <div className="flex max-h-120 mt-5">
          <img
            className="w-2/3 bg-gray-100 h-120 mr-2 rounded-md object-cover object-center"
            alt="img-1"
            src="https://images.unsplash.com/photo-1714190448490-99afc6b03e4e?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cGhpbG9zb3BoeXxlbnwwfDB8MHx8fDI%3D"
          />
          <div className="w-1/3 flex flex-col ml-2">
            <img
              className="bg-gray-100 h-60 mb-2 rounded-md object-cover object-center"
              alt="img-2"
              src="https://images.unsplash.com/photo-1488994038434-e995b7a4ba35?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8cGhpbG9zb3BoeXxlbnwwfDB8MHx8fDI%3D"
            />
            <img
              className="bg-gray-100 h-60 mt-2 rounded-md object-cover object-center"
              alt="img-3"
              src="https://images.unsplash.com/photo-1620662736427-b8a198f52a4d?w=700&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MjB8fHBoaWxvc29waHl8ZW58MHwwfDB8fHwy"
            />
          </div>
        </div>
        <div className="flex flex-col">
          <div className="mt-10 text-xl text-gray-800">Class Description</div>
          <div className="mt-1 text-sm text-gray-600">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Lorem lorem
            aliquam sed lacinia quis. Nibh dictumst vulputate odio pellentesque
            sit quis ac, sit ipsum. Sit rhoncus velit in sed massa arcu sit eu.
            Vitae et vitae eget lorem non dui. Sollicitudin ut mi adipiscing
            duis. Convallis in semper laoreet nibh leo. Vivamus malesuada ipsum
            pulvinar non rutrum risus dui, risus. Purus massa velit iaculis
            tincidunt tortor, risus, scelerisque risus. In at lorem pellentesque
            orci aenean dictum dignissim in. Aenean pulvinar diam interdum
            ullamcorper. Vel urna, tortor, massa metus purus metus. Maecenas
            mollis in velit auctor cursus scelerisque eget. Nibh faucibus purus
            elementum ultrices elementum, urna.{" "}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Course;

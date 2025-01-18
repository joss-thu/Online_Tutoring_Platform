import { useLocation, useNavigate } from "react-router-dom";
import NavBar from "../components/Navbar";
import React, { useCallback, useEffect, useState } from "react";
import { Tooltip } from "react-tooltip";
import { Rating, StickerStar } from "@smastrom/react-rating";
import { useAuth } from "../services/AuthContext";
import { BACKEND_URL } from "../config";

const ratingStyle = {
  itemShapes: StickerStar,
  activeFillColor: "#1e40af",
  inactiveFillColor: "#bcbcbc",
};

function Course() {
  const navigate = useNavigate();
  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const id = query.get("id");
  const { isAuthenticated } = useAuth();
  const [course, setCourse] = useState(false);
  const [ratings, setRatings] = useState(null);

  const fetchCourseDetails = useCallback(async () => {
    const res = await fetch(`${BACKEND_URL}/search/get-course/${id}`);
    const data = await res.json();
    setCourse(data);
  }, [course, id]);

  useEffect(() => {
    if (!course) {
      fetchCourseDetails();
    }
  }, [course, fetchCourseDetails]);

  const fetchCourseRatings = useCallback(async () => {
    const res = await fetch(`${BACKEND_URL}/user/get-course-ratings/${id}`);
    const data = await res.json();
    setRatings(data);
  }, [ratings, id]);

  useEffect(() => {
    if (course) {
      fetchCourseRatings();
    }
  }, [course, fetchCourseDetails]);

  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar isLoggedIn={false} currentPage="/" />
      {course ? (
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
                  onClick={() =>
                    navigate(
                      "/search?categoryName=" +
                        course.courseCategories[0]?.categoryName,
                    )
                  }
                >
                  {course.courseCategories[0]?.categoryName}
                </span>
                <span className="mx-1">/</span>
                <span
                  className="cursor-pointer"
                  onClick={() => navigate("/course?id=" + id)}
                >
                  {course.courseName}
                </span>
              </div>
              <div className="inline-flex items-center w-full mt-5">
                <div className="text-4xl">{course.courseName}</div>
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
                onClick={() => navigate("/tutor?id=" + course.tutorId)}
                className="text-xl text-gray-800 cursor-pointer w-auto self-start"
              >
                By {course.tutorName}
              </div>
              <Rating
                readOnly={true}
                style={{ maxWidth: 100 }}
                value={course.averageRating}
                itemStyles={ratingStyle}
              />
            </div>
            <button
              onClick={() => {
                if (isAuthenticated) {
                  navigate("/");
                }
              }}
              className={
                isAuthenticated
                  ? "bg-blue-800 ml-10 max-h-12 rounded-full text-white py-2 px-4"
                  : "enroll_now_anchor_element bg-blue-800 ml-10 max-h-12 rounded-full text-white py-2 px-4"
              }
            >
              Enroll now
            </button>
            <Tooltip
              anchorSelect=".enroll_now_anchor_element"
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
          <div className="flex mt-10">
            <div className="flex flex-col w-3/4 pr-10">
              <div className="text-xl text-gray-800">Class Description</div>
              <div className="mt-1 text-sm text-gray-600">
                <i>
                  <strong>{course.descriptionShort}</strong>
                </i>
                <br />
                {course.descriptionLong}
              </div>
            </div>
            <div className="flex flex-col bg-gray-200 rounded-xl w-1/4 p-4 self-start h-auto">
              <div className="text-xl rounded-md text-black self-start w-auto">
                Reviews
              </div>
              {ratings?.length > 0 ? (
                ratings?.map((result) => {
                  return (
                    <>
                      <div className="text-sm mt-4">{result.studentName}</div>
                      <Rating
                        key={ratings.ratingId}
                        readOnly={true}
                        style={{ maxWidth: 100 }}
                        value={result.points}
                        itemStyles={ratingStyle}
                      />
                      <div className="text-sm text-gray-600">
                        {result.review}
                      </div>
                    </>
                  );
                })
              ) : (
                <div className="font-merriweather_sans flex flex-col items-center bg-gray-200 p-4">
                  No reviews yet
                </div>
              )}
            </div>
          </div>
        </div>
      ) : (
        <div className="mt-[120px] w-full max-w-6xl font-merriweather_sans text-xl"></div>
      )}
    </div>
  );
}

export default Course;

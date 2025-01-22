import React, { useCallback, useEffect, useState } from "react";
import NavBar from "../components/Navbar";
import { BACKEND_URL } from "../config";
import { useAuth } from "../services/AuthContext";
import CourseSearchResultItem from "../components/CourseSearchResultItem";
import { useNavigate } from "react-router-dom";
import ActionButton from "../components/ActionButton";

function TutorCentre() {
  const navigate = useNavigate();
  const [courses, setCourses] = useState([]);
  const { user } = useAuth();
  const fetchTutorCourses = useCallback(async () => {
    try {
      const res = await fetch(`${BACKEND_URL}/user/get-course/${user.id}`);
      const data = await res.json();
      setCourses(data);
    } catch (error) {
      console.error("Error fetching tutor courses:", error);
    }
  }, [user]);
  useEffect(() => {
    if (user) {
      fetchTutorCourses();
    }
  }, [user, fetchTutorCourses]);
  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden font-merriweather_sans">
      <NavBar currentPage="/tutor-centre" />
      <div className="w-full max-w-6xl mt-[120px] mb-10">
        <div className="flex items-center justify-between">
          <div className="flex flex-col">
            <div className="inline-flex items-center w-full">
              <div className="text-xl">Courses You Offer</div>
            </div>
          </div>
          <div className="flex">
            <ActionButton
              onClick={() => {
                navigate("/tutor-meetings");
              }}
              text={"View All Meetings"}
              icon={"calendar_month"}
              design={"neutral"}
            />
            <ActionButton
              className={"ml-4"}
              onClick={() => {
                navigate("/create-course");
              }}
              text={"Create a New Course"}
              icon={"add"}
              design={"action"}
            />
          </div>
        </div>
        {courses?.map((result, index) => {
          return <CourseSearchResultItem course={result} key={index} />;
        })}
      </div>
    </div>
  );
}

export default TutorCentre;

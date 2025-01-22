import React, { useEffect, useState } from "react";
import MeetingCard from "../components/MeetingCard"; // Create a separate MeetingCard component for cleaner code
import ActionButton from "../components/ActionButton";
import NavBar from "../components/Navbar";
import { useAuth } from "../services/AuthContext";
import { BACKEND_URL } from "../config";
import emptyMeeting from "../assets/empty_meetings.svg";

const StudentMeetings = () => {
  const [meetings, setMeetings] = useState([]);
  const [sortedBy, setSortedBy] = useState("course"); // Default sorting
  const { user } = useAuth();
  const [loading, setLoading] = useState(true);

  const fetchMeetings = async () => {
    if (user) {
      setLoading(true);
      // Fetch courses
      fetch(`${BACKEND_URL}/user/get-meetings/${user.id}`)
        .then((res) => res.json())
        .then((data) => {
          setMeetings(data.filter((meeting) => meeting.tutorId !== user.id));
        })
        .catch((err) => console.error(err))
        .finally(() => setLoading(false));
    }
  };

  useEffect(() => {
    fetchMeetings();
  }, [user]);

  const sortedMeetings =
    sortedBy === "course"
      ? meetings.sort((a, b) => a.courseName.localeCompare(b.courseName))
      : meetings.sort((a, b) =>
          sortedBy === "earliest"
            ? new Date(a.startTime) - new Date(b.startTime)
            : new Date(b.startTime) - new Date(a.startTime),
        );

  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar currentPage="/student-centre" />
      <div className="mt-[120px] w-full max-w-6xl font-merriweather_sans mb-10">
        {!loading && sortedMeetings && sortedMeetings.length > 0 && (
          <>
            <div className="text-4xl">My Meetings</div>
            <div className="flex mt-5">
              <ActionButton
                text="Sort by Course"
                onClick={() => setSortedBy("course")}
                design={sortedBy === "course" ? "selected" : "neutral"}
              />
              <ActionButton
                text="Sort by Earliest"
                className="mx-4"
                onClick={() => setSortedBy("earliest")}
                design={sortedBy === "earliest" ? "selected" : "neutral"}
              />
              <ActionButton
                text="Sort by Latest"
                onClick={() => setSortedBy("latest")}
                design={sortedBy === "latest" ? "selected" : "neutral"}
              />
            </div>
            <div className="mt-5">
              {sortedMeetings.map((meeting) => (
                <MeetingCard
                  key={meeting.meetingId}
                  meeting={meeting}
                  isTutor={false}
                  refreshMeetings={fetchMeetings}
                />
              ))}
            </div>
          </>
        )}{" "}
        {!loading && sortedMeetings && sortedMeetings.length === 0 && (
          <div className="flex flex-col justify-center items-center w-full mt-[12%]">
            <img
              src={emptyMeeting}
              alt="No meetings booked"
              className="w-2/5 h-auto"
            />
            <p className={"text-xl"}>No meetings booked</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default StudentMeetings;

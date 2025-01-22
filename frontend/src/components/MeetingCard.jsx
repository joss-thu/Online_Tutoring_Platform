import React, { useEffect, useState } from "react";
import { BACKEND_URL } from "../config";
import FormatDateTime from "../helpers/FormatDateTime";
import ActionButton from "./ActionButton";
import { useNavigate } from "react-router-dom";

const MeetingCard = ({ meeting }) => {
  const [participants, setParticipants] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetch(`${BACKEND_URL}/user/meetings/${meeting.meetingId}/participants`)
      .then((res) => res.json())
      .then((data) => setParticipants(data))
      .catch((err) => console.error(err));
  }, [meeting.meetingId]);

  return (
    <div className="bg-gray-900 p-5 rounded-2xl shadow-md mt-4">
      <div className="flex justify-between items-center">
        <div className="text-lg text-white font-semibold">
          {meeting.courseName}
        </div>
        {/* Edit Button */}
        <ActionButton
          onClick={() => {
            navigate(
              `/create-meeting?edit=true&courseId=${meeting.courseId}&meetingId=${meeting.meetingId}?ref=meetings`,
            );
          }}
          icon={"edit_calendar"}
          text={"Edit"}
          design={"neutral"}
        />
      </div>
      <div className="text-gray-300 mt-1">
        {FormatDateTime(meeting.startTime)} {"\u00A0"} - {"\u00A0"}{" "}
        {FormatDateTime(meeting.endTime)}
      </div>
      <div className="mt-2 italic text-gray-400 flex items-center">
        <span
          style={{ fontSize: "1.15rem" }}
          className="material-symbols-rounded mr-2"
        >
          info
        </span>
        {meeting.meetingType === "ONLINE"
          ? `Online Meeting`
          : `Room: ${meeting.roomNum}, ${meeting.universityName} (${meeting.campusName})`}
      </div>
      <div className="mt-4">
        {participants.length > 0 && (
          <>
            <div className="text-gray-200">
              Participants ({participants.length}):
            </div>
          </>
        )}
        <div className="mt-2">
          {participants.length > 0 ? (
            participants.map((participant) => (
              <div
                key={participant.id}
                className="text-sm text-gray-100 mt-1.5 bg-gray-800 p-4 rounded-xl transition-all ease-in-out duration-300"
              >
                {participant.firstName} {participant.lastName} (
                {participant.email})
              </div>
            ))
          ) : (
            <div className="text-red-700 italic">
              No students booked for this meeting yet
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default MeetingCard;

import React from "react";
import emptyMeetings from "../assets/empty_meetings.svg";
import MeetingItem from "./MeetingItem";

const Meetings = ({ meetings }) => {
  return (
    <div className="flex flex-col pt-2 overflow-y-scroll self-start h-auto scrollbar-hide">
      {meetings && meetings.length > 0 ? (
        meetings.map((result, index) => (
          <div key={index}>
            <MeetingItem result={result} />
          </div>
        ))
      ) : (
        <div className="flex justify-center items-center w-full mt-16">
          <img
            src={emptyMeetings}
            alt="No meetings yet"
            className="w-1/2 h-auto"
          />
        </div>
      )}
    </div>
  );
};

export default Meetings;

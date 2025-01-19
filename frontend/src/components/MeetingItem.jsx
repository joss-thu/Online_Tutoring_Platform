import FormatDateTime from "../helpers/FormatDateTime";
import { format } from "date-fns";

const MeetingItem = ({ result }) => {
  const { meetingType, startTime, endTime, duration_in_minutes, roomNum } =
    result;

  const handleBooking = () => {
    alert("Meeting booked successfully!"); // Replace with actual booking logic
  };

  return (
    <div className="flex justify-between items-center text-sm text-gray-100 mt-1.5 bg-gray-800 p-3 cursor-pointer rounded-xl transition-all ease-in-out duration-300">
      <div>
        <span className="font-semibold">{meetingType}</span>
        {(meetingType === "OFFLINE" || meetingType === "OFFLINE") && (
          <p className="text-gray-400 text-xs mt-1">
            {roomNum ? `Room: ${roomNum}` : "Location not available"}
          </p>
        )}
      </div>
      <span>
        {FormatDateTime(startTime)} - {format(new Date(endTime), "HH:mm")}
      </span>
      <span className="text-gray-400">{duration_in_minutes} min</span>
      <button
        onClick={handleBooking}
        className="ml-3 px-3 py-1 bg-blue-600 hover:bg-blue-500 text-white font-medium rounded-lg transition"
      >
        Book Meeting
      </button>
    </div>
  );
};

export default MeetingItem;

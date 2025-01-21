import React, { useEffect, useState } from "react";
import InputField from "../components/InputField";
import SelectField from "../components/SelectField";
import { useAuth } from "../services/AuthContext";
import apiClient from "../services/AxiosConfig";
import NavBar from "../components/Navbar";
import { BACKEND_URL } from "../config";
import { useNavigate, useSearchParams } from "react-router-dom";
import DateTimePicker from "../components/DateTimePicker";
import ConfirmationDialog from "../components/ConfirmationDialog";

const CreateMeeting = () => {
  const meetingTypes = ["ONLINE", "OFFLINE", "HYBRID"];
  const navigate = useNavigate();
  const { user } = useAuth();
  const [searchParams] = useSearchParams();
  const [isOpen, setIsOpen] = useState(false);

  const courseId = searchParams.get("courseId");
  const meetingId = searchParams.get("meetingId");
  const isEditMode = searchParams.get("edit") === "true";

  const [meetingDetails, setMeetingDetails] = useState({});
  const [addresses, setAddresses] = useState(null);
  const [validationError, setValidationError] = useState("");

  const ensureSeconds = (dateTime) => {
    if (!dateTime) return "";
    const dateTimeParts = dateTime.split("T");
    if (dateTimeParts.length !== 2) return dateTime;

    const [date, time] = dateTimeParts;
    const timeParts = time.split(":");

    if (timeParts.length === 2) {
      return `${date}T${time}:00`;
    }
    return dateTime;
  };

  useEffect(() => {
    const getAddresses = async () => {
      try {
        const res = await fetch(`${BACKEND_URL}/user/get-addresses`);
        if (res.status === 200) {
          const data = await res.json();
          setAddresses(data);
        }
      } catch (err) {
        console.log("Error fetching addresses:", err);
      }
    };
    getAddresses();
  }, []);

  const handleDelete = async () => {
    if (
      isEditMode &&
      courseId &&
      meetingId &&
      user &&
      meetingDetails &&
      meetingDetails?.tutorId === user?.id &&
      meetingDetails?.courseId === courseId
    ) {
      try {
        const res = await apiClient.delete(
          `/tutor/delete-meeting/${meetingId}`,
        );
        if (res.status === 204) {
          setIsOpen(false);
          navigate("/course?id=" + courseId);
        }
      } catch (error) {
        console.log(error);
      }
    } else {
      setIsOpen(false);
      navigate("/course?id=" + courseId);
    }
  };

  useEffect(() => {
    const fetchMeetingDetails = async () => {
      if (isEditMode && meetingId && courseId && user) {
        try {
          const res = await apiClient.get(`user/meetings/${meetingId}`);
          if (res.status === 200) {
            if (
              res.data.tutorId === user.id &&
              res.data.courseId === Number(courseId)
            ) {
              setMeetingDetails(res.data);
            } else {
              navigate("/course?id=" + courseId);
            }
          }
        } catch (err) {
          console.error("Error fetching meeting details:", err);
        }
      }
    };
    fetchMeetingDetails();
  }, [isEditMode, meetingId, courseId, user]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setMeetingDetails((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!courseId && !meetingId) return;
    setValidationError("");

    const startDate = new Date(meetingDetails.startTime);
    const endDate = new Date(meetingDetails.endTime);

    if (startDate >= endDate) {
      setValidationError("End date must be after start date.");
      return;
    }

    let requestBody = {
      tutorId: user?.id,
      courseId: Number(courseId),
      startTime: ensureSeconds(meetingDetails.startTime),
      endTime: ensureSeconds(meetingDetails.endTime),
      meetingType: meetingDetails.meetingType,
    };

    if (meetingDetails.meetingType !== "ONLINE") {
      requestBody.roomNum = meetingDetails.roomNum;
      requestBody.addressId = meetingDetails.addressId;
    }

    try {
      if (isEditMode) {
        await apiClient.put(`/tutor/update-meeting/${meetingId}`, requestBody);
      } else {
        await apiClient.post("/tutor/create-meeting", requestBody);
      }
      navigate(`/course?id=${courseId}`);
    } catch (error) {
      if (error.response?.status === 409) {
        alert("A meeting with similar details already exists.");
      } else {
        console.error("Error submitting data:", error);
        alert("An error occurred. Please try again.");
      }
    }
  };

  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden font-merriweather_sans">
      <NavBar currentPage="/tutor-centre" />
      <div className="mb-6 mt-[120px]">
        <h1 className="text-3xl font-semibold text-gray-800 mb-2">
          {isEditMode ? "Edit Your Meeting" : "Create a New Meeting"}
        </h1>
      </div>
      <form onSubmit={handleSubmit} className="w-full max-w-4xl">
        {validationError && (
          <div className="text-red-500 text-sm">{validationError}</div>
        )}

        <SelectField
          label="Meeting Type *"
          name="meetingType"
          value={meetingDetails.meetingType || ""}
          onChange={handleChange}
          options={meetingTypes}
          required={true}
        />
        {(meetingDetails.meetingType === "ONLINE" ||
          meetingDetails.meetingType === "HYBRID") && (
          <div className="mt-2 font-semibold italic text-gray-500">
            ** Contact the tutor for the meeting link **
          </div>
        )}
        {(meetingDetails.meetingType === "OFFLINE" ||
          meetingDetails.meetingType === "HYBRID") && (
          <>
            <div className="mt-4" />

            <SelectField
              label="Address *"
              name="addressId"
              value={meetingDetails.addressId || ""}
              onChange={handleChange}
              options={addresses}
              required={true}
            />

            <InputField
              label="Room Number *"
              placeholder="A108"
              name="roomNum"
              value={meetingDetails.roomNum || ""}
              onChange={handleChange}
              required={true}
            />
          </>
        )}

        <DateTimePicker
          label="Start Date/Time *"
          onChange={handleChange}
          required={true}
          name="startTime"
          value={meetingDetails.startTime || ""}
        />

        <DateTimePicker
          label="End Date/Time *"
          onChange={handleChange}
          required={true}
          name="endTime"
          value={meetingDetails.endTime || ""}
        />

        <button
          type="submit"
          className="bg-blue-800 max-h-12 my-4 rounded-full text-white py-2 px-6"
        >
          {isEditMode ? "Update Meeting" : "Create Meeting"}
        </button>
        {isEditMode && meetingDetails && meetingId && courseId && user && (
          <button
            type="button"
            onClick={(e) => {
              e.preventDefault();
              setIsOpen(true);
            }}
            className="bg-red-800 ml-4 max-h-12 rounded-full text-white py-2 px-6"
          >
            Delete Meeting
          </button>
        )}
      </form>
      <ConfirmationDialog
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        title="Delete Meeting?"
        message="Are you sure you want to delete this meeting? All associated data will be removed."
        confirmText="Delete"
        onConfirm={handleDelete}
      />
    </div>
  );
};

export default CreateMeeting;

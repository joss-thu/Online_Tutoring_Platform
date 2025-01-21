import React, { useEffect, useState } from "react";
import NavBar from "../components/Navbar";
import CapitalizeFirstLetter from "../helpers/CapitalizeFirstLetter";
import { useAuth } from "../services/AuthContext";
import apiClient from "../services/AxiosConfig";
import { useNavigate } from "react-router-dom";
import { STUDENT_ROLE, TUTOR_ROLE } from "../config";
import ConfirmationDialog from "../components/ConfirmationDialog";

function Profile() {
  const { user, logout, checkRole } = useAuth();
  const navigate = useNavigate();
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [roles, setRoles] = useState([]);
  const [initials, setInitials] = useState(null);
  const [coursesCountTutor, setCoursesCountTutor] = useState(0);
  const [coursesCountStudent, setCoursesCountStudent] = useState(0);
  const [meetings, setMeetings] = useState([]);
  const [isOpen, setIsOpen] = useState(false);
  const [shortDescriptionLength, setShortDescriptionLength] = useState(0);
  const MAX_SHORT_DESC = 100;

  const [isEditing, setIsEditing] = useState(false);
  const [editedProfile, setEditedProfile] = useState({
    firstName: "",
    lastName: "",
    description: "",
  });

  useEffect(() => {
    const fetchProfile = async () => {
      if (!user) return;
      try {
        const { data } = await apiClient.get(`/user/get-user/${user.id}`);
        setRoles(user.roles);
        setProfile(data);
        setInitials(getInitials(data.fullName));
        setEditedProfile(data);
      } catch (err) {
        setError("Failed to fetch profile data.");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, [user]);

  useEffect(() => {
    const fetchData = async () => {
      if (!user) return;

      try {
        if (checkRole(TUTOR_ROLE)) {
          const { data } = await apiClient.get(`/user/get-course/${user.id}`);
          setCoursesCountTutor(data.length);
        }

        if (checkRole(STUDENT_ROLE)) {
          const { data } = await apiClient.get(`/student/enrolled-courses`);
          setCoursesCountStudent(data.length);
        }

        const { data: meetingsData } = await apiClient.get(
          `/user/get-meetings/${user.id}`,
        );
        setMeetings(meetingsData || []);
      } catch (err) {
        setError("Failed to fetch additional profile data.");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [profile]);

  useEffect(() => {
    setShortDescriptionLength(editedProfile.description?.length || 0);
  }, [editedProfile]);

  const getInitials = (name) =>
    name
      .split(" ")
      .map((part) => part.charAt(0))
      .join("")
      .toUpperCase();

  const handleEditToggle = () => {
    setIsEditing(!isEditing);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    if (name === "description") {
      if (value.length <= MAX_SHORT_DESC) {
        console.log(name, value);
        setShortDescriptionLength(value.length);
        setEditedProfile((prev) => ({ ...prev, [name]: value }));
      }
    } else {
      setEditedProfile((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSave = async () => {
    try {
      await apiClient.put(`/user/update-user/${user.id}`, editedProfile);
      setProfile((prev) => ({
        ...prev,
        firstName: editedProfile.firstName,
        lastName: editedProfile.lastName,
        description: editedProfile.description,
      }));
      setIsEditing(false);
    } catch (err) {
      setError("Failed to update profile.");
      console.error(err);
    }
  };

  const handleDelete = async () => {
    try {
      await apiClient.delete(`/user/delete-my-account`);
      logout();
      navigate("/login");
    } catch (err) {
      setError("Failed to delete account.");
      console.error(err);
    }
  };

  if (loading)
    return (
      <div className="flex justify-center items-center h-screen text-lg">
        Loading...
      </div>
    );
  if (error)
    return <div className="text-red-600 text-center mt-10">{error}</div>;

  return (
    <div className="flex flex-col items-center w-full min-h-screen">
      <NavBar currentPage="/profile" />

      <div className="flex flex-col w-full max-w-5xl font-merriweather_sans mt-[150px] bg-gray-50 p-8 rounded-2xl border border-gray-300">
        {/* Profile Header */}
        <div className="flex items-center gap-6">
          <div className="w-24 h-24 bg-gray-200 rounded-full flex items-center justify-center font-bold text-gray-600 text-3xl">
            {initials}
          </div>
          <div>
            {isEditing ? (
              <div>
                <input
                  type="text"
                  name="firstName"
                  value={editedProfile.firstName}
                  onChange={handleInputChange}
                  className="border p-2 rounded w-full mb-2"
                />
                <input
                  type="text"
                  name="lastName"
                  value={editedProfile.lastName}
                  onChange={handleInputChange}
                  className="border p-2 rounded w-full mb-2"
                />
                <textarea
                  name="description"
                  value={editedProfile.description}
                  onChange={handleInputChange}
                  rows={1}
                  className="border p-2 rounded w-full resize-none"
                />
                <p
                  className={`text-xs mb-4 ${
                    shortDescriptionLength === MAX_SHORT_DESC
                      ? "text-red-500"
                      : ""
                  }`}
                >
                  {shortDescriptionLength}/{MAX_SHORT_DESC}
                </p>
              </div>
            ) : (
              <>
                <h1 className="text-black text-3xl font-semibold">
                  {profile.firstName} {profile.lastName}
                </h1>
                <p className="text-gray-600 text-sm">{profile.email}</p>
                <p className="text-gray-500 mt-2">
                  {profile.description || "No description added yet"}
                </p>
              </>
            )}
          </div>
        </div>

        {/* User Roles */}
        <div className="flex flex-wrap gap-2 mt-4">
          {roles.map((role, index) => (
            <span
              key={index}
              className="px-6 py-1 bg-blue-800 text-white rounded-full text-sm"
            >
              {CapitalizeFirstLetter(role.split("_")[1].toLowerCase())}
            </span>
          ))}
        </div>

        {/* Quick Stats */}
        <div
          className={`grid gap-20 bg-gray-900 p-4 rounded-xl mt-6 text-center ${coursesCountStudent && coursesCountTutor ? "grid-cols-3" : "grid-cols-2"}`}
        >
          {checkRole(TUTOR_ROLE) && (
            <div
              className="cursor-pointer hover:bg-gray-800 rounded-md py-2"
              onClick={() => navigate("/tutor-centre")}
            >
              <p className=" text-2xl font-bold text-blue-300">
                {coursesCountTutor}
              </p>
              <p className="text-sm text-gray-300">Courses Offered</p>
            </div>
          )}
          {checkRole(STUDENT_ROLE) && (
            <div
              className="cursor-pointer hover:bg-gray-800 rounded-md py-2"
              onClick={() => navigate("/my-courses")}
            >
              <p className=" text-2xl font-bold text-blue-300">
                {coursesCountStudent}
              </p>
              <p className="text-sm text-gray-300">Courses Enrolled</p>
            </div>
          )}
          {meetings && (
            <div className="hover:bg-gray-800 rounded-md py-2">
              <p className="text-2xl font-bold text-blue-300">
                {meetings.length}
              </p>
              <p className="text-sm text-gray-300">Upcoming Meetings</p>
            </div>
          )}
        </div>

        {/* Action Buttons */}
        <div className="mt-8 flex flex-col md:flex-row gap-3">
          {isEditing ? (
            <>
              <button
                onClick={handleSave}
                className="bg-green-600 text-white py-2 px-4 rounded-md hover:bg-green-500 transition"
              >
                Save
              </button>
              <button
                onClick={handleEditToggle}
                className="bg-gray-500 text-white py-2 px-4 rounded-md hover:bg-gray-400 transition"
              >
                Cancel
              </button>
            </>
          ) : (
            <button
              onClick={handleEditToggle}
              className="bg-gray-700 text-white py-2 px-4 rounded-md hover:bg-gray-600 transition"
            >
              Edit Profile
            </button>
          )}
          <button
            onClick={logout}
            className="bg-blue-800 text-white py-2 px-4 rounded-md hover:bg-blue-700 transition"
          >
            Log out
          </button>
          <button
            onClick={(e) => {
              e.preventDefault();
              setIsOpen(true);
            }}
            className="bg-red-600 text-white py-2 px-4 rounded-md hover:bg-red-500 transition"
          >
            Delete Account
          </button>
          <ConfirmationDialog
            isOpen={isOpen}
            setIsOpen={setIsOpen}
            title="Delete Account?"
            message="Are you sure you want to delete your account? All associated data will be removed."
            confirmText="Delete"
            onConfirm={handleDelete}
          />
        </div>
      </div>
    </div>
  );
}

export default Profile;

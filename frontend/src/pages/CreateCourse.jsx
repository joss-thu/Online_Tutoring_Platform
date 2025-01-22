import React, { useCallback, useEffect, useState } from "react";
import InputField from "../components/InputField";
import TextareaField from "../components/TextareaField";
import SelectField from "../components/SelectField";
import { useAuth } from "../services/AuthContext";
import apiClient from "../services/AxiosConfig";
import NavBar from "../components/Navbar";
import { BACKEND_URL } from "../config";
import { useNavigate, useSearchParams } from "react-router-dom";
import ConfirmationDialog from "../components/ConfirmationDialog";
import ActionButton from "../components/ActionButton";

const CreateCourse = () => {
  const navigate = useNavigate();
  const [courseDetails, setCourseDetails] = useState({});
  const { user } = useAuth();
  const [validationError, setValidationError] = useState("");
  const [courseNameLength, setCourseNameLength] = useState(0);
  const [shortDescriptionLength, setShortDescriptionLength] = useState(0);
  const [longDescriptionLength, setLongDescriptionLength] = useState(0);
  const [course, setCourse] = useState(null);
  const [isOpen, setIsOpen] = useState(false);

  const [categories, setCategories] = useState([]);
  const [searchParams] = useSearchParams();
  const editParam = searchParams.get("edit");
  const isEditMode = editParam ? editParam === "true" : false;
  const courseId = searchParams.get("courseId");

  const fetchCategories = async () => {
    const response = await fetch(`${BACKEND_URL}/search/categories`);
    const data = await response.json();
    setCategories(data);
  };

  const handleDelete = async () => {
    if (
      isEditMode &&
      courseId &&
      course &&
      user &&
      course?.tutorId === user?.id
    ) {
      try {
        const res = await apiClient.delete(`/tutor/delete-course/${courseId}`);
        if (res.status === 204) {
          setIsOpen(false);
          navigate("/tutor-centre");
        }
      } catch (error) {
        console.log(error);
      }
    } else {
      setIsOpen(false);
      navigate("/course?id=" + courseId);
    }
  };

  const fetchCourseDetails = useCallback(async () => {
    if (!courseId || !user) return;
    try {
      const res = await fetch(`${BACKEND_URL}/search/get-course/${courseId}`);
      const data = await res.json();
      if (data.tutorId === user.id) {
        setCourse(data);
        setCourseDetails({
          courseName: data.courseName || "",
          shortDescription: data.descriptionShort || "",
          longDescription: data.descriptionLong || "",
          category: data.courseCategories?.[0]?.categoryName || "",
          startDate: data.startDate || "",
          endDate: data.endDate || "",
        });
      } else {
        navigate("/course?id=" + courseId);
      }
    } catch (error) {
      console.error("Error fetching course details:", error);
    }
  }, [courseId, user]);

  useEffect(() => {
    fetchCategories();
  }, []);

  useEffect(() => {
    if (user && courseId) {
      fetchCourseDetails();
    }
  }, [courseId, user]);

  useEffect(() => {
    if (isEditMode && course) {
      setCourseDetails({
        courseName: course.courseName || "",
        shortDescription: course.descriptionShort || "",
        longDescription: course.descriptionLong || "",
        category: course.courseCategories?.[0]?.categoryName || "",
        startDate: course.startDate || "",
        endDate: course.endDate || "",
      });
    }
  }, [course, isEditMode]);

  useEffect(() => {
    setCourseNameLength(courseDetails.courseName?.length || 0);
    setShortDescriptionLength(courseDetails.shortDescription?.length || 0);
    setLongDescriptionLength(courseDetails.longDescription?.length || 0);
  }, [courseDetails]);

  const MAX_COURSE_NAME = 50;
  const MAX_SHORT_DESC = 150;
  const MAX_LONG_DESC = 2000;

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "shortDescription") {
      if (value.length <= MAX_SHORT_DESC) {
        setShortDescriptionLength(value.length);
        setCourseDetails({ ...courseDetails, [name]: value });
      }
    } else if (name === "longDescription") {
      if (value.length <= MAX_LONG_DESC) {
        setLongDescriptionLength(value.length);
        setCourseDetails({ ...courseDetails, [name]: value });
      }
    } else if (name === "courseName") {
      if (value.length <= MAX_COURSE_NAME) {
        setCourseNameLength(value.length);
        setCourseDetails({ ...courseDetails, [name]: value });
      }
    } else {
      setCourseDetails({ ...courseDetails, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setValidationError("");

    const startDate = new Date(courseDetails.startDate);
    const endDate = new Date(courseDetails.endDate);

    if (startDate >= endDate) {
      setValidationError("End date must be after start date.");
      return;
    }

    const requestBody = {
      courseName: courseDetails.courseName,
      tutorId: user?.id,
      descriptionShort: courseDetails.shortDescription,
      descriptionLong: courseDetails.longDescription,
      startDate: courseDetails.startDate,
      endDate: courseDetails.endDate,
      averageRating: course?.averageRating || 0,
      courseCategories: [
        {
          categoryId:
            categories.find(
              (category) => category.name === courseDetails.category,
            )?.id || 0,
          categoryName: courseDetails.category,
        },
      ],
    };

    try {
      if (isEditMode && courseId) {
        await apiClient.put(`/tutor/update-course/${courseId}`, requestBody);
      } else {
        await apiClient.post("/tutor/course/create", requestBody);
      }

      navigate("/tutor-centre");
    } catch (error) {
      if (error.response?.status === 409) {
        alert("A course with similar details already exists.");
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
          {isEditMode ? "Edit Your Course" : "Create a New Course"}
        </h1>
      </div>
      <form onSubmit={handleSubmit} className="w-full max-w-4xl">
        {validationError && (
          <div className="text-red-500 text-sm">{validationError}</div>
        )}

        <InputField
          label="Course Name *"
          placeholder="Software Engineering"
          name="courseName"
          value={courseDetails.courseName || ""}
          onChange={handleChange}
          required={true}
        />
        <p
          className={`text-xs mb-4 ${
            courseNameLength === MAX_COURSE_NAME ? "text-red-500" : ""
          }`}
        >
          {courseNameLength}/{MAX_COURSE_NAME}
        </p>

        <TextareaField
          label="Short Description *"
          placeholder="Enter a short description"
          name="shortDescription"
          value={courseDetails.shortDescription || ""}
          onChange={handleChange}
          rows={2}
          maxLength={MAX_SHORT_DESC}
          hint={`Max ${MAX_SHORT_DESC} characters.`}
          required={true}
        />
        <p
          className={`text-xs mb-4 ${
            shortDescriptionLength === MAX_SHORT_DESC ? "text-red-500" : ""
          }`}
        >
          {shortDescriptionLength}/{MAX_SHORT_DESC}
        </p>

        <TextareaField
          label="Long Description *"
          placeholder="Enter a detailed description"
          name="longDescription"
          rows={6}
          value={courseDetails.longDescription || ""}
          onChange={handleChange}
          maxLength={MAX_LONG_DESC}
          hint={`Max ${MAX_LONG_DESC} characters.`}
          required={true}
        />
        <p
          className={`text-xs mb-4 ${
            longDescriptionLength === MAX_LONG_DESC ? "text-red-500" : ""
          }`}
        >
          {longDescriptionLength}/{MAX_LONG_DESC}
        </p>

        <SelectField
          label="Category *"
          name="category"
          value={courseDetails.category || ""}
          onChange={handleChange}
          options={categories}
          required={true}
        />

        <InputField
          label="Start Date *"
          type="date"
          name="startDate"
          value={courseDetails.startDate || ""}
          onChange={handleChange}
          required={true}
        />

        <InputField
          label="End Date *"
          type="date"
          name="endDate"
          value={courseDetails.endDate || ""}
          onChange={handleChange}
          required={true}
        />
        <ActionButton
          type={"submit"}
          onClick={() => {}}
          icon={isEditMode ? "sync" : "add_circle"}
          className="mb-4"
          text={isEditMode ? "Update Course" : "Create Course"}
          design={"action"}
        />
        {isEditMode && course && courseId && user && (
          <ActionButton
            onClick={(e) => {
              e.preventDefault();
              setIsOpen(true);
            }}
            icon={"delete"}
            className="ml-4"
            text={"Delete Course"}
            design={"alert"}
          />
        )}
      </form>
      <ConfirmationDialog
        isOpen={isOpen}
        setIsOpen={setIsOpen}
        title="Delete Course?"
        message="Are you sure you want to delete this course? All associated data will be removed."
        confirmText="Delete"
        confirmIcon={"delete"}
        onConfirm={handleDelete}
      />
    </div>
  );
};

export default CreateCourse;

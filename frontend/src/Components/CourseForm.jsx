import React, { useState } from "react";
import InputField from "../Components/InputField";
import TextareaField from "../Components/TextareaField";
import SelectField from "../Components/SelectField";
import FileUpload from "../Components/FileUpload";
import ActionButton from "./ActionButton";

const CourseForm = () => {
    const [courseDetails, setCourseDetails] = useState({
        courseName: "",
        shortDescription: "",
        longDescription: "",
        category: "",
        startDate: "",
        endDate: "",
        image: null,
    });

    const predefinedCategories = ["Science", "Math", "Art", "Technology"];

    const handleChange = (e) => {
        const { name, value } = e.target;

        // Implement character limits for descriptions
        if (name === "shortDescription" && value.length > 100) return; // Limit to 100 chars
        if (name === "longDescription" && value.length > 500) return; // Limit to 500 chars

        setCourseDetails({ ...courseDetails, [name]: value });
    };

    const handleImageUpload = (e) => {
        setCourseDetails({ ...courseDetails, image: e.target.files[0] });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (validateForm()) {
            console.log("Course Details:", courseDetails);
            alert("Course successfully submitted!");
        }
    };

    const validateForm = () => {
        const { courseName, shortDescription, longDescription, category } =
            courseDetails;
        if (!courseName || !shortDescription || !longDescription || !category) {
            alert("Please fill out all required fields.");
            return false;
        }
        return true;
    };

    return (

        <div className="flex flex-col justify-center items-center w-full h-full">
            <div className="mb-6">
                <h1 className="text-3xl font-semibold text-gray-800 mb-2">
                    Tell us about your course
                </h1>
                <p className="text-gray-600">
                    We'll use this information to customize your course. You can change it any time.
                </p>
            </div>
                <form onSubmit={handleSubmit} className="w-full max-w-4xl space-y-4">
                    <div>
                        <InputField
                            label="Course Title"
                            placeholder="Give it a name"
                            value={courseDetails.courseName}
                            onChange={handleChange}
                            name="courseName"
                        />
                    </div>

                    <div>
                        <TextareaField
                            label="Short Description"
                            placeholder="Write a short overview of your course."
                            value={courseDetails.shortDescription}
                            onChange={handleChange}
                            name="shortDescription"
                            maxLength={100}
                            counter
                        />
                        <p className="text-sm text-gray-500">
                            {courseDetails.shortDescription.length}/100 characters
                        </p>
                    </div>


                    <div>
                        <TextareaField
                            label="Long Description"
                            placeholder="Write a thorough description of what your course will contain."
                            value={courseDetails.longDescription}
                            onChange={handleChange}
                            name="longDescription"
                            maxLength={500}
                            counter
                        />
                        <p className="text-sm text-gray-500">
                            {courseDetails.longDescription.length}/500 characters
                        </p>
                    </div>

                    {/* Category Selection */}
                    <SelectField
                        label="Category"
                        name="category"
                        options={predefinedCategories}
                        value={courseDetails.category}
                        onChange={handleChange}
                    />


                    {/* Start and End Dates */}
                    <div className="grid grid-cols-2 gap-4">
                        <InputField
                            label="Start Date"
                            type="date"
                            value={courseDetails.startDate}
                            onChange={handleChange}
                            name="startDate"
                        />
                        <InputField
                            label="End Date"
                            type="date"
                            value={courseDetails.endDate}
                            onChange={handleChange}
                            name="endDate"
                        />
                    </div>

                    {/* Image Upload */}
                    <FileUpload
                        label="Upload a thumbnail image"
                        onFileSelect={handleImageUpload}
                    />

                    {/* Submit Button */}
                    <ActionButton
                        type="submit"
                        text="Submit Course"
                        className="w-full bg-blue-600 text-white py-3 px-6 rounded-lg text-xl font-bold hover:bg-blue-700 focus:ring-4 focus:ring-blue-300 focus:outline-none"
                        onClick={() => console.log("Submit button clicked")}
                    >
                    </ActionButton>

                </form>
        </div>
    );
};

export default CourseForm;

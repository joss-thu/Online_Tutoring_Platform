import { format } from "date-fns";

function formatDate(dateString) {
  const date = new Date(dateString);

  // Add ordinal suffix to the day
  const ordinal = (n) => {
    const s = ["th", "st", "nd", "rd"];
    const v = n % 100;
    return n + (s[(v - 20) % 10] || s[v] || s[0]);
  };

  const day = ordinal(date.getDate());
  const month = format(date, "MMM"); // 'Jan', 'Feb', etc.
  const year = format(date, "yyyy");

  return `${day} ${month} ${year}`;
}

function CourseSearchResultItem({ course }) {
  return (
    <li
      key={course.courseId}
      className="cursor-pointer flex rounded-2xl shadow-gray-300 shadow-sm bg-gray-900 p-7 m-5"
    >
      <div>
        <span className="text-blue-100 py-1 px-2 rounded-sm font-merriweather_sans bg-blue-600 bg-opacity-40 text-xs">
          {course.category.categoryName}
        </span>
        <div className="font-merriweather_sans text-xl mt-[5px] text-white">
          {course.courseName}
        </div>
        <div className="font-merriweather_sans text-sm text-white mb-[10px]">
          {course.descriptionShort}
        </div>
        <span className="inline-flex items-center font-merriweather_sans text-sm bg-white text-black px-2 rounded-md">
          By {course.tutor.firstName} {course.tutor.lastName}
          {course.tutor.isVerified && (
            <span className="material-symbols-rounded ml-1 text-xl">
              verified
            </span>
          )}
        </span>
      </div>
      <div className="flex flex-col justify-center ml-auto items-end font-merriweather_sans text-sm text-white">
        <div className="bg-gray-800 py-2 px-4 rounded-full text-center w-40">
          <span className="block text-gray-400">From</span>
          <span className="text-green-300">{formatDate(course.startDate)}</span>
        </div>
        <div className="bg-gray-800 py-2 px-4 rounded-full text-center w-40 mt-2">
          <span className="block text-gray-400">To</span>
          <span className="text-red-300">{formatDate(course.endDate)}</span>
        </div>
      </div>
    </li>
  );
}

export default CourseSearchResultItem;

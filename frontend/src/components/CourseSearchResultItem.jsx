function formatDate(dateString) {
  const date = new Date(dateString);

  const day = date.getDate();
  const month = date.toLocaleString("default", { month: "short" }); // 'Jan'
  const year = date.getFullYear();

  // Add ordinal suffix to the day
  const ordinal = (n) => {
    const s = ["th", "st", "nd", "rd"];
    const v = n % 100;
    return n + (s[(v - 20) % 10] || s[v] || s[0]);
  };

  return `${ordinal(day)} ${month} ${year}`;
}

function CourseSearchResultItem({ course }) {
  return (
    <li
      key={course.courseId}
      className="cursor-pointer flex rounded-2xl border border-black shadow-gray-300 shadow-sm bg-white p-7 m-5 hover:bg-gray-50"
    >
      <div>
        <span className="text-white py-1 px-2 rounded-md font-merriweather_sans bg-blue-800 text-xs">
          {course.category.categoryName}
        </span>
        <div className="font-merriweather_sans text-xl mt-[5px]">
          {course.courseName}
        </div>
        <div className="font-merriweather_sans text-sm">
          {course.description}
        </div>
      </div>
      <div className="flex flex-col justify-center ml-auto font-merriweather_sans">
        <div className="flex items-center">
          <span>From</span>
          <span className="bg-green-100 text-green-800 py-1 px-2 rounded ml-2">
            {formatDate(course.startDate)}
          </span>
        </div>
        <div className="flex items-center mt-2">
          <span>To</span>
          <span className="bg-red-100 text-red-800 py-1 px-2 rounded ml-2">
            {formatDate(course.endDate)}
          </span>
        </div>
      </div>
    </li>
  );
}

export default CourseSearchResultItem;

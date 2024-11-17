import React from "react";
import NavBar from "../components/Navbar";
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import CourseSearchResultItem from "../components/CourseSearchResultItem";

function Search() {
  const [searchResults, setSearchResults] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const location = useLocation();
  const query = new URLSearchParams(location.search);
  const courseName = query.get("courseName");
  const tutorName = query.get("tutorName");

  const fetchSearchResults = async () => {
    let data;
    if (courseName || tutorName) {
      try {
        setLoading(true);
        setError(null);

        const queryParams = new URLSearchParams();
        if (courseName) queryParams.append("courseName", courseName);
        if (tutorName) queryParams.append("tutorName", tutorName);

        const response = await fetch(
          `http://localhost:8080/search?${queryParams}`,
        );
        data = await response.json();
        console.log(data);
        if (response.ok) {
          setSearchResults(data);
        } else {
          setError("Error fetching search results.");
        }
      } catch (err) {
        setError("An error occurred while fetching the search results.");
      } finally {
        setLoading(false);
      }
      return data;
    }
  };

  useEffect(() => {
    fetchSearchResults();
  }, [courseName, tutorName]);

  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar isLoggedIn={false} currentPage="/" />
      {loading && <div>Loading...</div>}
      {error && <div>{error}</div>}
      {searchResults &&
        searchResults.length > 0 &&
        courseName &&
        !tutorName && (
          <ul className="w-full max-w-4xl mt-[150px]">
            {searchResults.map((result, index) => (
              <CourseSearchResultItem course={result} key={index} />
            ))}
          </ul>
        )}
    </div>
  );
}

export default Search;

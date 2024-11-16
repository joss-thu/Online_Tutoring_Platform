import React from "react";
import NavBar from "../components/Navbar";
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

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
        if (!response.ok) {
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
    fetchSearchResults().then((r) => setSearchResults(r));
  }, [courseName, tutorName]);

  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar isLoggedIn={false} currentPage="/" />
      {loading && <div>Loading...</div>}
      {error && <div>{error}</div>}
      {searchResults && searchResults.length > 0 ? (
        <ul>
          {searchResults.map((result, index) => (
            <li key={index}>{result.name}</li>
          ))}
        </ul>
      ) : (
        <div>No results found for "{courseName}"</div>
      )}
    </div>
  );
}

export default Search;

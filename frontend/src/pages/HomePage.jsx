import logo from "../assets/logo.png";
import { useNavigate } from "react-router-dom";

const NavBar = (props) => {
  const navigate = useNavigate();
  return (
    <div className="flex justify-between items-center w-full bg-white">
      <div className="flex justify-between items-center w-full bg-white">
        <div className="flex items-center m-5">
          <img src={logo} alt="Logo" className="h-8 w-auto mr-4" />
          <span className="text-xl font-lato">THUtorium</span>
        </div>
        <div className="flex space-x-4">
          <button
            className="px-4 py-2 text-sm font-merriweather_sans"
            onClick={() => navigate("/")}
          >
            Home
          </button>
          <button
            className="px-4 py-2 text-sm font-merriweather_sans"
            onClick={() => navigate("/my-courses")}
          >
            My Courses
          </button>
          <button
            className="px-4 py-2 text-sm font-merriweather_sans"
            onClick={() => navigate("/tutor-centre")}
          >
            Tutor Centre
          </button>
          <button
            className="px-4 py-2 text-sm font-merriweather_sans"
            onClick={() => navigate("/messages")}
          >
            Messages
          </button>
        </div>
        <div className="flex items-center px-2">
          {props.isLoggedIn ? (
            <button className="px-4 py-2 text-sm">Account</button>
          ) : (
            <div className="flex space-x-2">
              <button className="bg-black text-sm dark:bg-dark-2 border-dark dark:border-dark-2 border rounded-full inline-flex items-center justify-center py-2 px-4 text-center font-merriweather_sans text-white hover:bg-body-color hover:border-body-color disabled:bg-gray-3 disabled:border-gray-3 disabled:text-dark-5'">
                Log In
              </button>
              <button className="border-black dark:border-dark-2 border rounded-full inline-flex items-center justify-center py-2 px-4 text-center text-sm font-merriweather_sans text-black hover:bg-gray-4 dark:hover:bg-dark-3 disabled:bg-gray-3 disabled:border-gray-3 disabled:text-dark-5'">
                Sign Up
              </button>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

const SearchSection = (props) => {
  return (
    <>
      <div className="mt-[50px] mb-[20px] block font-merriweather_sans text-dark text-center text-4xl">
        Find your <br /> perfect tutor
      </div>
      <div className="relative w-full max-w-2xl">
        <input
          type="text"
          placeholder="Search"
          className="font-merriweather_sans w-full bg-transparent rounded-md border border-stroke border-black py-[10px] pr-3 pl-12 text-dark-6 outline-none transition focus:border-primary active:border-primary disabled:cursor-default disabled:bg-gray-2"
        />
        <span className="absolute top-1/2 left-4 -translate-y-1/2 material-symbols-rounded">
          search
        </span>
      </div>

      <div className=" mt-[30px] flex justify-center items-center w-full bg-white space-x-10">
        <button className="bg-gray-200 rounded-full px-4 py-2 text-md font-merriweather_sans">
          Engineering
        </button>
        <button className="bg-gray-200 rounded-full px-4 py-2 text-md font-merriweather_sans">
          Mathematics
        </button>
        <button className="bg-gray-200 rounded-full px-4 py-2 text-md font-merriweather_sans">
          Computer Science
        </button>
        <button className="bg-gray-200 rounded-full px-4 py-2 text-md font-merriweather_sans">
          Languages
        </button>
        <button className="bg-black text-white rounded-full px-4 py-2 text-md font-merriweather_sans">
          See All
        </button>
      </div>
    </>
  );
};

const StatsSection = (props) => {
  return (
    <>
      <div className="mt-[75px] mb-[20px] flex w-full bg-blue-900 py-5">
        <div className="flex flex-col justify-center items-center w-1/3">
          <span className="text-white text-2xl font-merriweather_sans">
            {props.students}
          </span>
          <span className="text-white text-xl font-merriweather_sans">
            students
          </span>
        </div>
        <div className="flex flex-col justify-center items-center w-1/3">
          <span className="text-white text-2xl font-merriweather_sans">
            {props.courses}
          </span>
          <span className="text-white text-xl font-merriweather_sans">
            courses
          </span>
        </div>
        <div className="flex flex-col justify-center items-center w-1/3">
          <span className="text-white text-2xl font-merriweather_sans">
            {props.tutors}
          </span>
          <span className="text-white text-xl font-merriweather_sans">
            tutors
          </span>
        </div>
      </div>
    </>
  );
};

function HomePage() {
  return (
    <div className="flex flex-col items-center w-full bg-white overflow-hidden">
      <NavBar isLoggedIn={false} />
      <SearchSection />
      <StatsSection students={1000} courses={100} tutors={80} />
    </div>
  );
}

export default HomePage;

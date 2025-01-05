import "./App.css";
import "@smastrom/react-rating/style.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import MyCourses from "./pages/MyCourses";
import TutorCentre from "./pages/TutorCentre";
import Messages from "./pages/Messages";
import Search from "./pages/Search";
import Login from "./pages/Login";
import SignUp from "./pages/SignUp";
import Course from "./pages/Course";
import Tutor from "./pages/Tutor";
<<<<<<< HEAD
import CourseForm from "./components/CourseForm";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/createcourse" element={<CourseForm />} />
        <Route path="/my-courses" element={<MyCourses />} />
        <Route path="/tutor-centre" element={<TutorCentre />} />
        <Route path="/messages" element={<Messages />} />
        <Route path="/search" element={<Search />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/course" element={<Course />} />
        <Route path="/tutor" element={<Tutor />} />
      </Routes>
    </Router>
=======
import Profile from "./pages/Profile";
import ProtectedRoutes from "./utils/ProtectedRoutes";
import Call from "./pages/Call";
import { AuthProvider } from "./services/AuthContext";
import PublicRoute from "./utils/PublicRoute";

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/my-courses" element={<MyCourses />} />
          <Route
            path="/my-courses"
            element={
              <ProtectedRoutes roles={["ROLE_STUDENT"]}>
                <MyCourses />
              </ProtectedRoutes>
            }
          />
          <Route
            path="/tutor-centre"
            element={
              <ProtectedRoutes roles={["ROLE_TUTOR"]}>
                <TutorCentre />
              </ProtectedRoutes>
            }
          />
          <Route
            path="/messages"
            element={
              <ProtectedRoutes roles={["ROLE_TUTOR", "ROLE_STUDENT"]}>
                <Messages />
              </ProtectedRoutes>
            }
          />
          <Route path="/search" element={<Search />} />
          <Route
            path="/login"
            element={
              <PublicRoute>
                <Login />
              </PublicRoute>
            }
          />
          <Route
            path="/signup"
            element={
              <PublicRoute>
                <SignUp />
              </PublicRoute>
            }
          />
          <Route path="/course" element={<Course />} />
          <Route path="/tutor" element={<Tutor />} />
          <Route path="/call" element={<Call />} />

          <Route
            path="/profile"
            element={
              <ProtectedRoutes>
                <Profile />
              </ProtectedRoutes>
            }
          ></Route>
        </Routes>
      </Router>
    </AuthProvider>
>>>>>>> a1058f4743cba68bb1b0df4feb79e4f9a74f3239
  );
}

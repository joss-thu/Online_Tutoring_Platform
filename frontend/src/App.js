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
import Profile from "./pages/Profile";
import ProtectedRoutes from "./utils/ProtectedRoutes";
import { AuthProvider } from "./services/AuthContext";
import PublicRoute from "./utils/PublicRoute";
import CourseForm from "./components/CourseForm";
import Call2 from "./pages/Call";
import { STUDENT_ROLE, TUTOR_ROLE } from "./config";
import { SocketProvider } from "./services/SocketContext";
import CallNotification from "./components/CallNotification";

export default function App() {
  return (
    <AuthProvider>
      <Router>
        <SocketProvider>
          <CallNotification />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route
              path="/createcourse"
              element={
                <ProtectedRoutes roles={[TUTOR_ROLE]}>
                  <CourseForm />
                </ProtectedRoutes>
              }
            />
            <Route
              path="/my-courses"
              element={
                <ProtectedRoutes roles={[STUDENT_ROLE]}>
                  <MyCourses />
                </ProtectedRoutes>
              }
            />
            <Route
              path="/tutor-centre"
              element={
                <ProtectedRoutes roles={[TUTOR_ROLE]}>
                  <TutorCentre />
                </ProtectedRoutes>
              }
            />
            <Route
              path="/messages"
              element={
                <ProtectedRoutes roles={[TUTOR_ROLE, STUDENT_ROLE]}>
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
            <Route path="/call" element={<Call2 />} />
            <Route
              path="/profile"
              element={
                <ProtectedRoutes>
                  <Profile />
                </ProtectedRoutes>
              }
            ></Route>
          </Routes>
        </SocketProvider>
      </Router>
    </AuthProvider>
  );
}

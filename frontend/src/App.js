import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import MyCourses from "./pages/MyCourses";
import TutorCentre from "./pages/TutorCentre";
import Messages from "./pages/Messages";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/my-courses" element={<MyCourses />} />
        <Route path="/tutor-centre" element={<TutorCentre />} />
        <Route path="/messages" element={<Messages />} />
      </Routes>
    </Router>
  );
}

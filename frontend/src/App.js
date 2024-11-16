import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import MyCourses from "./pages/MyCourses";
import TutorCentre from "./pages/TutorCentre";
import Messages from "./pages/Messages";
import Search from "./pages/Search";

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/my-courses" element={<MyCourses />} />
        <Route path="/tutor-centre" element={<TutorCentre />} />
        <Route path="/messages" element={<Messages />} />
        <Route path="/search" element={<Search />} />
      </Routes>
    </Router>
  );
}

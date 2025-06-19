import React from "react";
import { BrowserRouter as Router, Routes, Route, Link } from "react-router-dom";
import SpotList from "./pages/SpotList";
import SpotDetail from "./pages/SpotDetail";
import FavoriteList from "./pages/FavoriteList";
import CheckinList from "./pages/CheckinList";

function App() {
  return (
    <Router>
      <nav>
        <Link to="/">スポット一覧</Link> |{" "}
        <Link to="/favorites">お気に入り</Link> |{" "}
        <Link to="/checkins">チェックイン履歴</Link>
      </nav>
      <Routes>
        <Route path="/" element={<SpotList />} />
        <Route path="/spots/:id" element={<SpotDetail />} />
        <Route path="/favorites" element={<FavoriteList />} />
        <Route path="/checkins" element={<CheckinList />} />
      </Routes>
    </Router>
  );
}

export default App;
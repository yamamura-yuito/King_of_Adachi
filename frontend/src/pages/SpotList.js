import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

function SpotList() {
  const [spots, setSpots] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/spots")
      .then((res) => res.json())
      .then((data) => setSpots(data));
  }, []);

  return (
    <div>
      <h2>スポット一覧</h2>
      <ul>
        {spots.map((spot) => (
          <li key={spot.id}>
            <Link to={`/spots/${spot.id}`}>{spot.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default SpotList;

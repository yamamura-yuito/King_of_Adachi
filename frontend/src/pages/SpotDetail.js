import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function SpotDetail() {
  const { id } = useParams();
  const [spot, setSpot] = useState(null);

  useEffect(() => {
    fetch(`http://localhost:8080/api/spots/${id}`)
      .then((res) => res.json())
      .then((data) => setSpot(data));
  }, [id]);

  if (!spot) return <div>Loading...</div>;

  return (
    <div>
      <h2>{spot.name}</h2>
      <p>{spot.description}</p>
      <p>緯度: {spot.latitude}, 経度: {spot.longitude}</p>
    </div>
  );
}

export default SpotDetail;

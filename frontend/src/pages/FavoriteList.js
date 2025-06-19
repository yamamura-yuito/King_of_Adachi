import React, { useEffect, useState } from "react";

function FavoriteList() {
  const [favorites, setFavorites] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/favorites")
      .then((res) => res.json())
      .then((data) => setFavorites(data));
  }, []);

  return (
    <div>
      <h2>お気に入り一覧</h2>
      <ul>
        {favorites.map((fav) => (
          <li key={fav.spotId}>{fav.spotName}</li>
        ))}
      </ul>
    </div>
  );
}

export default FavoriteList;

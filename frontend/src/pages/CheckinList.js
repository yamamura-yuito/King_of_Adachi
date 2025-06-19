import React, { useEffect, useState } from "react";

function CheckinList() {
  const [checkins, setCheckins] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8080/api/checkins")
      .then((res) => res.json())
      .then((data) => setCheckins(data));
  }, []);

  return (
    <div>
      <h2>チェックイン履歴</h2>
      <ul>
        {checkins.map((checkin) => (
          <li key={checkin.id}>
            {checkin.spotName} - {checkin.checkinTime}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default CheckinList;

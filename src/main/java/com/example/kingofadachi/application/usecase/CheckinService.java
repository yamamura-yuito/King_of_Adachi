package com.example.kingofadachi.application.usecase;

import com.example.kingofadachi.domain.entity.Checkin;
import java.util.List;

public interface CheckinService {
    Checkin createCheckin(Long userId, Long spotId);
    List<Checkin> getCheckinsByUserId(Long userId);
}

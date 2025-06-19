package com.example.kingofadachi.infrastructure.db.mybatis;

import com.example.kingofadachi.domain.model.Checkin;
import com.example.kingofadachi.domain.repository.CheckinRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CheckinRepositoryImpl implements CheckinRepository {

    private final CheckinMapper checkinMapper;

    public CheckinRepositoryImpl(CheckinMapper checkinMapper) {
        this.checkinMapper = checkinMapper;
    }

    @Override
    public Checkin save(Checkin checkin) {
        // Assuming CheckinMapper.insert also handles ID generation if needed
        // and modifies the passed Checkin object or returns the generated ID.
        // The current CheckinMapper.insert returns void, so we'll assume
        // it populates the ID in the passed Checkin object.
        checkinMapper.insert(checkin);
        return checkin;
    }

    @Override
    public List<Checkin> findByUserId(Long userId) {
        return checkinMapper.findByUserId(userId);
    }
}

package com.example.kingofadachi.infrastructure.repository;

import com.example.kingofadachi.domain.entity.Checkin;
import com.example.kingofadachi.domain.repository.CheckinRepository;
import com.example.kingofadachi.infrastructure.mapper.CheckinMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CheckinRepositoryImpl implements CheckinRepository {

    private final com.example.kingofadachi.infrastructure.mapper.CheckinMapper checkinMapper;

    public CheckinRepositoryImpl(com.example.kingofadachi.infrastructure.mapper.CheckinMapper checkinMapper) {
        this.checkinMapper = checkinMapper;
    }

    @Override
    public Checkin save(Checkin checkin) {
        // CheckinMapper.insert が必要に応じてID生成も処理し、
        // 渡されたCheckinオブジェクトを変更するか、生成されたIDを返すと想定しています。
        // 現在の CheckinMapper.insert は void を返すため、
        // 渡されたCheckinオブジェクトにIDが設定されるものと仮定します。
        checkinMapper.insert(checkin);
        return checkin;
    }

    @Override
    public List<Checkin> findByUserId(Long userId) {
        return checkinMapper.findByUserId(userId);
    }
}

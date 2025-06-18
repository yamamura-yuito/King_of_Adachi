package com.example.kingofadachi.infrastructure.db.mybatis;

import com.example.kingofadachi.domain.model.Spot;
import com.example.kingofadachi.domain.repository.SpotRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class SpotRepositoryImpl implements SpotRepository {

    private final SpotMapper spotMapper;

    public SpotRepositoryImpl(SpotMapper spotMapper) {
        this.spotMapper = spotMapper;
    }

    @Override
    public Optional<Spot> findById(Long id) {
        return spotMapper.findById(id);
    }

    @Override
    public List<Spot> findAll() {
        return spotMapper.findAll();
    }

    @Override
    public Spot save(Spot spot) {
        if (spot.getId() == null) {
            spotMapper.insert(spot);
        } else {
            spotMapper.update(spot);
        }
        return spot;
    }

    @Override
    public void deleteById(Long id) {
        spotMapper.deleteById(id);
    }
}

package com.example.kingofadachi.infrastructure.mapper;

import com.example.kingofadachi.domain.entity.Spot;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Optional;

@Mapper
public interface SpotMapper {
    Optional<Spot> findById(Long id);
    List<Spot> findAll();
    int insert(Spot spot);
    int update(Spot spot);
    void deleteById(Long id);
}

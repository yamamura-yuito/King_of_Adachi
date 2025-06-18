package com.example.kingofadachi.infrastructure.db.mybatis;

import com.example.kingofadachi.domain.model.Spot;
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

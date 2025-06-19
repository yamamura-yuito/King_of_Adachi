package com.example.kingofadachi.infrastructure.db.mybatis;

import com.example.kingofadachi.domain.model.Checkin; // Corrected import
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckinMapper {
    List<Checkin> findByUserId(Long userId);
    void insert(Checkin checkin);
}

package com.example.kingofadachi.infrastructure.mapper;

import com.example.kingofadachi.domain.entity.Favorite; // Corrected import
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FavoriteMapper {
    List<Favorite> findByUserId(Long userId);
    void insert(Favorite favorite);
    void delete(@Param("userId") Long userId, @Param("spotId") Long spotId);
}

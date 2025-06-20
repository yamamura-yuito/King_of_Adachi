package com.example.kingofadachi.infrastructure.mapper;

import com.example.kingofadachi.domain.entity.Favorite;
import com.example.kingofadachi.domain.entity.Spot; // Spotも取得する場合に備えて
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param; // 複数のパラメータを渡す場合に必要

import java.util.List;
import java.util.Optional;

@Mapper
public interface FavoriteMapper {

    Optional<Favorite> findById(@Param("id") Long id);

    Optional<Favorite> findByUserIdAndSpotId(@Param("userId") Long userId, @Param("spotId") Long spotId);

    // ユーザーのお気に入りスポットの「情報」を取得する想定。FavoriteエンティティだけでなくSpotエンティティも結合して取得する。
    // そのため、戻り値は Favorite ではなく、Spot のリストや、FavoriteとSpotを組み合わせたDTOになる可能性がある。
    // ここではまず Favorite のリストを返す形にしておき、必要なら後で変更する。
    List<Favorite> findFavoritesByUserId(@Param("userId") Long userId);

    // Spotエンティティのリストを直接返す版（JOINしてSpotの情報を取得する想定）
    List<Spot> findFavoriteSpotsByUserId(@Param("userId") Long userId);

    int insert(Favorite favorite);

    int deleteById(@Param("id") Long id);

    int deleteByUserIdAndSpotId(@Param("userId") Long userId, @Param("spotId") Long spotId);

    boolean existsByUserIdAndSpotId(@Param("userId") Long userId, @Param("spotId") Long spotId);
}

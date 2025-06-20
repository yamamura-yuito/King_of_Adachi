package com.example.kingofadachi.domain.repository;

import com.example.kingofadachi.domain.entity.Favorite;
import com.example.kingofadachi.domain.entity.Spot; // Spotも取得する場合に備えて

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository {

    Optional<Favorite> findById(Long id);

    Optional<Favorite> findByUserIdAndSpotId(Long userId, Long spotId);

    // ユーザーのお気に入りスポットの「情報」を取得する。
    // FavoriteMapper.findFavoriteSpotsByUserId の結果（Spotのリスト）を返すことを想定。
    List<Spot> findFavoriteSpotsByUserId(Long userId);

    // Favoriteエンティティのリストを返す必要がある場合は、別途定義する。
    // List<Favorite> findFavoritesByUserId(Long userId);

    Favorite save(Favorite favorite);

    void deleteById(Long id);

    void deleteByUserIdAndSpotId(Long userId, Long spotId);

    boolean existsByUserIdAndSpotId(Long userId, Long spotId);
}

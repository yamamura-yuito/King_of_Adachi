package com.example.kingofadachi.infrastructure.repository;

import com.example.kingofadachi.domain.entity.Favorite;
import com.example.kingofadachi.domain.entity.Spot;
import com.example.kingofadachi.domain.repository.FavoriteRepository;
import com.example.kingofadachi.infrastructure.mapper.FavoriteMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteMapper favoriteMapper;

    public FavoriteRepositoryImpl(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    @Override
    public Optional<Favorite> findById(Long id) {
        return favoriteMapper.findById(id);
    }

    @Override
    public Optional<Favorite> findByUserIdAndSpotId(Long userId, Long spotId) {
        return favoriteMapper.findByUserIdAndSpotId(userId, spotId);
    }

    @Override
    public List<Spot> findFavoriteSpotsByUserId(Long userId) {
        return favoriteMapper.findFavoriteSpotsByUserId(userId);
    }

    @Override
    public Favorite save(Favorite favorite) {
        // MyBatisのinsertメソッドは通常、挿入した行数を返す。
        // オブジェクトにIDが自動採番される場合、<selectKey>などを使用するが、
        // FavoriteMapper.insert が Favorite オブジェクトを直接変更するか、
        // IDを返す設計になっていることを前提とする。
        // ここでは、insert処理が行われ、渡されたfavoriteオブジェクトが(ID等で)更新されるか、
        // あるいはそのまま返しても問題ないシンプルなケースを想定。
        // SpotRepositoryImplでは、insert後に引数のオブジェクトを返しているため、それに倣う。
        favoriteMapper.insert(favorite); // FavoriteMapper.insert が void でなく int を返す場合、戻り値のチェックも可能
        return favorite;
    }

    @Override
    public void deleteById(Long id) {
        favoriteMapper.deleteById(id);
    }

    @Override
    public void deleteByUserIdAndSpotId(Long userId, Long spotId) {
        favoriteMapper.deleteByUserIdAndSpotId(userId, spotId);
    }

    @Override
    public boolean existsByUserIdAndSpotId(Long userId, Long spotId) {
        return favoriteMapper.existsByUserIdAndSpotId(userId, spotId);
    }
}

package com.example.kingofadachi.domain.repository;

import com.example.kingofadachi.domain.entity.Spot;
import java.util.List;
import java.util.Optional;

public interface SpotRepository {

    /**
     * 指定されたIDのスポットを検索します。
     *
     * @param id スポットID
     * @return 見つかった場合は Spot オブジェクトを含む Optional、見つからない場合は空の Optional
     */
    Optional<Spot> findById(Long id);

    /**
     * すべてのスポットを検索します。
     *
     * @return Spot オブジェクトのリスト
     */
    List<Spot> findAll();

    /**
     * 新しいスポットを保存（作成または更新）します。
     *
     * @param spot 保存する Spot オブジェクト
     * @return 保存された Spot オブジェクト（通常はIDが採番される）
     */
    Spot save(Spot spot);

    /**
     * 指定されたIDのスポットを削除します。
     *
     * @param id 削除するスポットのID
     */
    void deleteById(Long id);
}

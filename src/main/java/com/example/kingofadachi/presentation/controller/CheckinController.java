package com.example.kingofadachi.presentation.controller;

import com.example.kingofadachi.application.service.impl.CheckinService;
import com.example.kingofadachi.domain.entity.Checkin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api") // チェックイン関連エンドポイントのベースパス
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    /**
     * 特定のスポットに対するユーザーの新しいチェックイン（スタンプ）を作成します。
     *
     * @param userId チェックインするユーザーのID。
     * @param payload "spotId" を含むマップ。
     * @return 作成されたCheckinオブジェクト。
     */
    @PostMapping("/users/{userId}/checkins")
    public ResponseEntity<?> createCheckin(@PathVariable Long userId, @RequestBody Map<String, Long> payload) {
        Long spotId = payload.get("spotId");
        if (spotId == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "spotId is required"));
        }
        try {
            Checkin checkin = checkinService.createCheckin(userId, spotId);
            return new ResponseEntity<>(checkin, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            // その他の予期せぬエラーに対する汎用エラーハンドラ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }

    /**
     * 特定のユーザーのすべてのチェックイン（スタンプ）を取得します。
     *
     * @param userId チェックインを取得するユーザーのID。
     * @return Checkinオブジェクトのリスト。
     */
    @GetMapping("/users/{userId}/checkins")
    public ResponseEntity<?> getCheckinsByUserId(@PathVariable Long userId) {
        try {
            List<Checkin> checkins = checkinService.getCheckinsByUserId(userId);
            if (checkins.isEmpty()) {
                // ユーザーが存在するがチェックインがない場合に NO_CONTENT がより適切かどうか、
                // ユーザーが見つからない場合と比較検討してください。現時点では、空のリストでOKを返す方針で問題ありません。
                // 現在、サービスはユーザーが見つからない場合に IllegalArgumentException をスローします。
                return new ResponseEntity<>(checkins, HttpStatus.OK);
            }
            return new ResponseEntity<>(checkins, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred."));
        }
    }
}

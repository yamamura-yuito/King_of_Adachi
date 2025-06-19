package com.example.kingofadachi.application.service.impl;

import com.example.kingofadachi.domain.entity.Checkin;
import com.example.kingofadachi.domain.entity.Spot;
import com.example.kingofadachi.domain.entity.User;
import com.example.kingofadachi.domain.repository.CheckinRepository;
import com.example.kingofadachi.domain.repository.SpotRepository;
import com.example.kingofadachi.domain.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final UserRepository userRepository; // ユーザー検証用
    private final SpotRepository spotRepository; // スポット検証用

    public CheckinService(CheckinRepository checkinRepository, UserRepository userRepository, SpotRepository spotRepository) {
        this.checkinRepository = checkinRepository;
        this.userRepository = userRepository;
        this.spotRepository = spotRepository;
    }

    @Transactional
    public Checkin createCheckin(Long userId, Long spotId) {
        // ユーザーを検証 (任意だが推奨される実践)
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // スポットを検証 (任意だが推奨される実践)
        Optional<Spot> spot = spotRepository.findById(spotId);
        if (spot.isEmpty()) {
            throw new IllegalArgumentException("Spot not found with ID: " + spotId);
        }

        // Checkinエンティティを作成
        // CheckinコンストラクタはIDを要求します。IDは自動生成されるため、
        // nullを渡すか、IDを要求しないコンストラクタが利用可能であればそれを使用します。
        // Checkinモデルに新しいチェックイン用のコンストラクタがあるか、
        // またはIDがマッパーによって設定されることを前提とします。
        // 現在のCheckinモデルのコンストラクタ `public Checkin(Long id, Long userId, Long spotId, LocalDateTime checkinTime)`
        // はIDを要求します。これを調整するか、新しいコンストラクタを追加する必要があります。
        // 現時点では、IDはデータベース/マッパーによって設定されると仮定します。
        Checkin newCheckin = new Checkin(null, userId, spotId, LocalDateTime.now());

        return checkinRepository.save(newCheckin);
    }

    public List<Checkin> getCheckinsByUserId(Long userId) {
        // ユーザーを検証 (任意)
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            // または空のリストを返すか、カスタム例外をスローします
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        return checkinRepository.findByUserId(userId);
    }
}

package com.example.kingofadachi.domain.repository;

import com.example.kingofadachi.domain.entity.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);
    User save(User user); // saveメソッドが必要になることを想定
}

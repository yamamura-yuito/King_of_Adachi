package com.example.kingofadachi.infrastructure.repository;

import com.example.kingofadachi.domain.entity.User;
import com.example.kingofadachi.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final com.example.kingofadachi.infrastructure.mapper.UserMapper userMapper;

    public UserRepositoryImpl(com.example.kingofadachi.infrastructure.mapper.UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User save(User user) {
        // UserMapper.insert が ID を設定することを想定
        userMapper.insert(user);
        return user;
    }
}

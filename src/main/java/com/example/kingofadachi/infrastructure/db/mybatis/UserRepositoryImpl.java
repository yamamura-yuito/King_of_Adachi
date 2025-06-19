package com.example.kingofadachi.infrastructure.db.mybatis;

import com.example.kingofadachi.domain.model.User;
import com.example.kingofadachi.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User save(User user) {
        // Assuming UserMapper.insert populates the ID.
        userMapper.insert(user);
        return user;
    }
}

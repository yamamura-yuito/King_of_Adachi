package com.example.kingofadachi.infrastructure.mapper;

import com.example.kingofadachi.domain.entity.User; // Corrected import
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findById(Long id);
    void insert(User user);
}

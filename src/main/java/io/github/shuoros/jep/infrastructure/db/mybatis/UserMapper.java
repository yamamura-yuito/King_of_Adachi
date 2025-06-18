package io.github.shuoros.jep.infrastructure.db.mybatis;

import com.example.kingofadachi.domain.model.User; // Corrected import
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    Optional<User> findById(Long id);
    void insert(User user);
}

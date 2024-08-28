package com.mkkg.user;

import com.mkkg.user.dto.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT new com.mkkg.user.dto.UserResponse(u.id, u.username, u.email) FROM UserEntity u")
    List<UserResponse> findAllUsersWithoutPassword();

    Optional<UserEntity> findByUsername(String username);
}

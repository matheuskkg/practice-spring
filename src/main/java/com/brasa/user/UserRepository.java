package com.brasa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query(value = "SELECT id, username, email FROM user_entity", nativeQuery = true)
    List<UserResponse> findAllUsersWithoutPassword();
}

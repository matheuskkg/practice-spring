package com.brasa.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT new com.brasa.user.UserResponse(u.id, u.username, u.email) FROM UserEntity u")
    List<UserResponse> findAllUsersWithoutPassword();
}

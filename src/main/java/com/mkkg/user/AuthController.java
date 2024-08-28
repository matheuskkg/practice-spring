package com.mkkg.user;

import com.mkkg.user.dto.UserLoginRequest;
import com.mkkg.user.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final UserRepository repository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginRequest request) {
        Optional<UserEntity> optionalUser = repository.findByUsername(request.username());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if (optionalUser.get().getPassword().equals(request.password())) {
            //generate and return token
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRegisterRequest request) {
        if (verifyUserAlreadyExists(request)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        UserEntity user = new UserEntity(request);
        repository.save(user);
        //generate and return token
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private boolean verifyUserAlreadyExists(UserRegisterRequest request) {
        //The @Column(unique = true) on the Entity declaration will check for uniqueness on the database,
        //but it may be bypassed, therefore the application should also check for it
        return repository.existsByEmail(request.email()) || repository.existsByUsername(request.username());
    }

}

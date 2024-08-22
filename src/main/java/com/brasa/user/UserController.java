package com.brasa.user;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUser);
        } else {
            throw new EntityNotFoundException("User with id " + id + " not found");
        }
    }

    private void verifyRequest(UserRequest request, BindingResult errors) {
        //The @Column(unique = true) on the Entity declaration will check for uniqueness on the database,
        //but it may be bypassed, therefore the application should also check for it
        if (userRepository.existsByUsername(request.username())) {
            errors.rejectValue("username", "Unique", "username in use");
        }

        if (userRepository.existsByEmail(request.email())) {
            errors.rejectValue("email", "Unique", "email in use");
        }
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid UserRequest request, BindingResult errors) {
        verifyRequest(request, errors);

        if (errors.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errors.getAllErrors());
        }

        UserEntity user = new UserEntity(request);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid UserRequest request, BindingResult errors) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        verifyRequest(request, errors);

        if (errors.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errors.getAllErrors());
        }

        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            user.setUsername(request.username());
            user.setPassword(request.password());
            user.setEmail(request.email());
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}

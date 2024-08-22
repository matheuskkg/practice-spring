package com.brasa.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    public record ResponseMsg(String defaultMessage) {}

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Integer id) {
        UserEntity user = this.userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMsg("User not found"));
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid UserEntity user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ResponseMsg(bindingResult.getFieldError().getDefaultMessage()));
        }

        //must find how to remove this
        if (this.userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ResponseMsg("Username already in use."));
        }

        this.userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity updatedUser) {
        UserEntity existingUser = this.userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found.");
        }

        //update info here


        this.userRepository.save(existingUser);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        this.userRepository.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}

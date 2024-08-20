package com.brasa.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userRepository.findAll());
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user) {
        if(this.userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists.");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username cannot be empty.");
        }

        if (user.getPassword().length() > 16) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username maximum length is 16.");
        }

        if (!Pattern.matches("^[a-zA-Z0-9]{1,16}$", user.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username contains invalid character.");
        }

        this.userRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PatchMapping("/updateuser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
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

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        this.userRepository.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}

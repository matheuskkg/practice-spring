package com.brasa.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        UserValidator validator = new UserValidator();

        if(this.userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists.");
        }

        if (!validator.isUsernameValid(user.getUsername())) {
            return validator.getResponse();
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
        //use validator

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

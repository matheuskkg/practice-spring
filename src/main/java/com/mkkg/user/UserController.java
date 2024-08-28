package com.mkkg.user;

import com.mkkg.user.dto.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAllUsersWithoutPassword());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUser);
        } else {
            //throw new EntityNotFoundException("User with id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    //not sure where this should be
    /*@PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody @Valid UserRegisterRequest request, BindingResult errors) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        verifyRequest(request, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errors.getAllErrors());
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
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}

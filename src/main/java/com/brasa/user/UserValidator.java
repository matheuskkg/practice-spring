package com.brasa.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.regex.Pattern;

@Getter
public class UserValidator {

    private ResponseEntity<String> response;

    public boolean isUsernameValid(String username) {
        //I find it easier to read it like this than to simply return the conditions
        if (isUsernameEmpty(username)
                || isUsernameOutOfBounds(username)
                || !usernameMatchesRegex(username)
        ) {
            return false;
        }

        return true;
    }

    private boolean isUsernameEmpty(String username) {
        if (username == null || username.isBlank()) {
            this.response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username cannot be empty.");

            return true;
        }

        return false;
    }

    private boolean isUsernameOutOfBounds(String username) {
        if (username.length() > 16) {
            this.response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username maximum length is 16.");

            return true;
        }

        return false;
    }

    private boolean usernameMatchesRegex(String username) {
        if (!Pattern.matches("^[a-zA-Z0-9]{1,16}$", username)) {
            this.response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username contains invalid character.");

            return false;
        }

        return true;
    }

}

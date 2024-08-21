package com.brasa.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.regex.Pattern;

@Getter
public class UserValidator {

    private ResponseEntity<String> response;

    public boolean isUsernameValid(String username) {
        if (!Pattern.matches("^[a-zA-Z0-9]{1,16}$", username)) {
            this.response = ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("The username must be alphanumeric, cannot contain spaces, and must be between 1 and 16 characters in length.");

            return false;
        }

        return true;
    }

}

package com.brasa.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import lombok.Data;

@Data
@Entity
public class UserEntity {

    @Id
    @SequenceGenerator(
            name = "user_entity_id_sequence",
            sequenceName = "user_entity_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_entity_id_sequence"
    )
    private Integer id;

    @NotNull(message = "Please provide a username.")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,16}$", message = "The username must be alphanumeric, cannot contain spaces, and must be between 1 and 16 characters in length.")
    private String username;

    @NotNull(message = "Please provide a password.")
    private String password;

    @NotBlank(message = "Please provide a email address.")
    @NotNull(message = "Please provide a email address.")
    @Email(message = "Please provide a valid email address.")
    private String email;

}

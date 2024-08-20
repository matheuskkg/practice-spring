package com.brasa.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String username;
    private String password;
    private String email;

}

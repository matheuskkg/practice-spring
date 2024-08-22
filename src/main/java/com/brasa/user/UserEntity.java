package com.brasa.user;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    public UserEntity(UserRequest request) {
        this.username = request.username();
        this.password = request.password();
        this.email = request.email();
    }

}

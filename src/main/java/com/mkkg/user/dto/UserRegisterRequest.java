package com.mkkg.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequest(

        @NotBlank
        String username,

        @NotBlank
        String password,

        @NotBlank
        @Email
        String email
) {}
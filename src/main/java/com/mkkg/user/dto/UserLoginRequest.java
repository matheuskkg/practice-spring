package com.mkkg.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(

        @NotBlank
        String username,

        @NotBlank
        String password
) {}

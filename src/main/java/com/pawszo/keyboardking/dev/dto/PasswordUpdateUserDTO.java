package com.pawszo.keyboardking.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PasswordUpdateUserDTO {

    private Long id;
    private String nickname;
    @NotBlank(message = "Password must have at least 8 characters including letters and digits.")
    @Size(min = 5)
    private String password;
    private String passwordConfirmation;
    private String previousPassword;

}

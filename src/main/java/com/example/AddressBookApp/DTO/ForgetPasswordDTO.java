package com.example.AddressBookApp.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPasswordDTO {
    @NotBlank(message = "Password cannot be empty")
    private String password;
}


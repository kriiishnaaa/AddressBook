package com.example.AddressBookApp.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookDTO implements Serializable {  // <-- Implement Serializable
    private static final long serialVersionUID = 1L;
    private Long id;

    @NotBlank(message = "Name is required")
    @Pattern(
            regexp = "^[A-Z][a-zA-Z\\s]*$",
            message = "Name must start with a capital letter and contain only letters and spaces"
    )
    private String name;

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\d{10}$",
            message = "Phone number must be exactly 10 digits"
    )
    private String phone;
}

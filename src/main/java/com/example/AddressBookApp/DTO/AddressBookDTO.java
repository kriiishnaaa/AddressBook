package com.example.AddressBookApp.DTO;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class AddressBookDTO {
    private Long id;
    private String name;
    private String phone;

    public AddressBookDTO(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }
}

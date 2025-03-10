package com.AddressBook.AdressBookApp.DTO;

public class ContactDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;

    // No-argument constructor (required for frameworks like Spring)
    public ContactDTO() {}

    // Parameterized constructor
    public ContactDTO(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public ContactDTO(Long id, String name, String phone) {
        this.id=id;
        this.name=name;
        this.phone=phone;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
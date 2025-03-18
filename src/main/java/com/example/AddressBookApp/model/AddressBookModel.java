package com.example.AddressBookApp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contacts") // Renamed for clarity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;

}

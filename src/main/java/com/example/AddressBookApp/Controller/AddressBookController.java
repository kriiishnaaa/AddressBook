package com.example.AddressBookApp.Controller;

import com.example.AddressBookApp.DTO.AddressBookDTO;
import com.example.AddressBookApp.Service.AddressBookService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@Setter
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;
    @GetMapping("/show")
    public ResponseEntity<List<AddressBookDTO>> getAllContacts() {
        return ResponseEntity.ok(addressBookService.getAllContacts());
    }
    @PostMapping
    public ResponseEntity<AddressBookDTO> addContact(@RequestBody AddressBookDTO dto) {
        return ResponseEntity.ok(addressBookService.saveContact(dto));
    }
}

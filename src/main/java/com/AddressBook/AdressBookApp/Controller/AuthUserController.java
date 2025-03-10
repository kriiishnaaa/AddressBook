package com.AddressBook.AdressBookApp.Controller;

import com.AddressBook.AdressBookApp.DTO.AuthUserDTO;
import com.AddressBook.AdressBookApp.DTO.ContactDTO;
import com.AddressBook.AdressBookApp.DTO.ResponseDTO;
import com.AddressBook.AdressBookApp.Interfaces.AddressBookServiceInterface;
import com.AddressBook.AdressBookApp.Model.AuthUser;
import com.AddressBook.AdressBookApp.Service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthUserController {
    @Autowired
    AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> resgister(@Valid @RequestBody AuthUserDTO userDTO) throws Exception{
        AuthUser user = authenticationService.register(userDTO);
        ResponseDTO responseDTO = new ResponseDTO("User details submitted!", user);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @RestController
    @RequestMapping("/contact")
    public static class AddressBookController {
        @Autowired
        AddressBookServiceInterface service;


        //Get all contacts
        @GetMapping("/showcontacts")
        public ResponseEntity<List<ContactDTO>> getAllContacts() {
            return ResponseEntity.ok(service.getAllContacts());
        }

        // Get a single contact by ID
        @GetMapping("/getbyid/{id}")
        public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {
            ContactDTO contact = service.getContactById(id);
            return (contact != null) ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
        }

        // Create a new contact
        @PostMapping("/create")
        public ResponseEntity<ContactDTO> createContact(@RequestBody ContactDTO dto) {
            return ResponseEntity.ok(service.saveContact(dto));
        }

        // Update an existing contact
        @PutMapping("/update/{id}")
        public ResponseEntity<ContactDTO> updateContact(@PathVariable Long id, @RequestBody ContactDTO dto) {
            ContactDTO updatedContact = service.updateContact(id, dto);
            return (updatedContact != null) ? ResponseEntity.ok(updatedContact) : ResponseEntity.notFound().build();
        }

        // Delete a contact
        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
            return (service.deleteContact(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
    }
}
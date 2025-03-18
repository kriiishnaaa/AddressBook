package com.example.AddressBookApp.Controller;
import com.example.AddressBookApp.DTO.AddressBookDTO;
import com.example.AddressBookApp.Service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookService service;

    //Get all contacts
    @GetMapping("/showcontacts")
    public ResponseEntity<List<AddressBookDTO>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    // Get a single contact by ID
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<AddressBookDTO> getContactById(@PathVariable Long id) {
        AddressBookDTO contact = service.getContactById(id);
        return (contact != null) ? ResponseEntity.ok(contact) : ResponseEntity.notFound().build();
    }

    // Create a new contact
    @PostMapping("/create")
    public ResponseEntity<AddressBookDTO> createContact(@RequestBody AddressBookDTO dto) {
        return ResponseEntity.ok(service.saveContact(dto));
    }

    // Update an existing contact
    @PutMapping("/update/{id}")
    public ResponseEntity<AddressBookDTO> updateContact(@PathVariable Long id, @RequestBody AddressBookDTO dto) {
        AddressBookDTO updatedContact = service.updateContact(id, dto);
        return (updatedContact != null) ? ResponseEntity.ok(updatedContact) : ResponseEntity.notFound().build();
    }

    // Delete a contact
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        return (service.deleteContact(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}


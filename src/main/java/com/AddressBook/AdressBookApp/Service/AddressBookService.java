package com.AddressBook.AdressBookApp.Service;

import com.AddressBook.AdressBookApp.DTO.ContactDTO;
import com.AddressBook.AdressBookApp.Interfaces.AddressBookServiceInterface;
import com.AddressBook.AdressBookApp.Model.Contact;
import com.AddressBook.AdressBookApp.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressBookService implements AddressBookServiceInterface {
    @Autowired
    private ContactRepository repository;

    // Retrieve all contacts
    @Override
    public List<ContactDTO> getAllContacts() {
        return repository.findAll().stream()
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getPhone()))
                .collect(Collectors.toList());
    }

    // Save a new contact
    @Override
    public ContactDTO saveContact(ContactDTO dto) {
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        Contact savedContact = repository.save(contact);
        return new ContactDTO(savedContact.getId(), savedContact.getName(), savedContact.getPhone());
    }

    // Retrieve a single contact by ID
    @Override
    public ContactDTO getContactById(Long id) {
        Optional<Contact> contact = repository.findById(id);
        return contact.map(c -> new ContactDTO(c.getId(), c.getName(), c.getPhone()))
                .orElse(null);  // Returns null if contact is not found
    }

    //  Update an existing contact by ID
    @Override
    public ContactDTO updateContact(Long id, ContactDTO dto) {
        Optional<Contact> existingContact = repository.findById(id);

        if (existingContact.isPresent()) {
            Contact contact = existingContact.get();
            contact.setName(dto.getName());
            contact.setPhone(dto.getPhone());
            Contact updatedContact = repository.save(contact);
            return new ContactDTO(updatedContact.getId(), updatedContact.getName(), updatedContact.getPhone());
        }
        return null;
    }


    @Override
    public boolean deleteContact(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
package com.AddressBook.AdressBookApp.Service;

import com.AddressBook.AdressBookApp.DTO.ContactDTO;
import com.AddressBook.AdressBookApp.Interfaces.AddressBookServiceInterface;
import com.AddressBook.AdressBookApp.Model.Contact;
import com.AddressBook.AdressBookApp.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class AddressBookService implements AddressBookServiceInterface {
    @Autowired
    private ContactRepository repository;

    // Retrieve all contacts
    @Override
    public List<ContactDTO> getAllContacts() {
        log.info("Fetching all contacts from database");
        return repository.findAll().stream()
                .map(contact -> new ContactDTO(contact.getId(), contact.getName(), contact.getPhone()))
                .collect(Collectors.toList());
    }

    // Save a new contact
    @Override
    public ContactDTO saveContact(ContactDTO dto) {
        log.info("Saving new contact: {}", dto);
        Contact contact = new Contact();
        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        Contact savedContact = repository.save(contact);
        log.info("Contact saved successfully with ID: {}", savedContact.getId());
        return new ContactDTO(savedContact.getId(), savedContact.getName(), savedContact.getPhone());
    }

    // Retrieve a single contact by ID
    @Override
    public ContactDTO getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);
        Optional<Contact> contact = repository.findById(id);
        if(contact.isPresent()){
            log.info("Contact found: {}", contact.get());
        }
        else {
            log.warn("Contact with ID {} not found.", id);
        }
        return contact.map(c -> new ContactDTO(c.getId(), c.getName(), c.getPhone()))
                .orElse(null);  // Returns null if contact is not found
    }

    //  Update an existing contact by ID
    @Override
    public ContactDTO updateContact(Long id, ContactDTO dto) {
        log.info("Updating contact with ID: {}", id);
        Optional<Contact> existingContact = repository.findById(id);

        if (existingContact.isPresent()) {
            Contact contact = existingContact.get();
            contact.setName(dto.getName());
            contact.setPhone(dto.getPhone());
            Contact updatedContact = repository.save(contact);
            log.info("Contact updated successfully: {}", updatedContact);
            return new ContactDTO(updatedContact.getId(), updatedContact.getName(), updatedContact.getPhone());
        }else {
            log.warn("Attempted to update non-existing contact with ID: {}", id);
        }
        return null;
    }


    @Override
    public boolean deleteContact(Long id) {
        log.info("Deleting contact with ID: {}", id);
        if (repository.existsById(id)) {
            repository.deleteById(id);
            log.info("Contact with ID {} deleted successfully.", id);
            return true;
        }else {
            log.warn("Attempted to delete non-existing contact with ID: {}", id);
        }
        return false;
    }
}
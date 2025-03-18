package com.example.AddressBookApp.Service;

import com.example.AddressBookApp.DTO.AddressBookDTO;
import com.example.AddressBookApp.Interface.AddressBookServiceInterface;
import com.example.AddressBookApp.Repository.AddressRepository;
import com.example.AddressBookApp.model.AddressBookModel;
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
    private AddressRepository repository;

    @Override
    public List<AddressBookDTO> getAllContacts() {
        log.info("Fetching all contacts from the database.");
        return repository.findAll().stream()
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhone()))
                .collect(Collectors.toList());
    }

    @Override
    public AddressBookDTO saveContact(AddressBookDTO dto) {
        log.info("Saving new contact: {}", dto);
        AddressBookModel contact = new AddressBookModel();
        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        AddressBookModel savedContact = repository.save(contact);
        log.info("Contact saved successfully with ID: {}", savedContact.getId());
        return new AddressBookDTO(savedContact.getId(), savedContact.getName(), savedContact.getPhone());
    }

    @Override
    public AddressBookDTO getContactById(Long id) {
        log.info("Fetching contact with ID: {}", id);
        Optional<AddressBookModel> contact = repository.findById(id);
        if (contact.isPresent()) {
            log.info("Contact found: {}", contact.get());
        } else {
            log.warn("Contact with ID {} not found.", id);
        }
        return contact.map(c -> new AddressBookDTO(c.getId(), c.getName(), c.getPhone()))
                .orElse(null);
    }

    @Override
    public AddressBookDTO updateContact(Long id, AddressBookDTO dto) {
        log.info("Updating contact with ID: {}", id);
        Optional<AddressBookModel> existingContact = repository.findById(id);

        if (existingContact.isPresent()) {
            AddressBookModel contact = existingContact.get();
            contact.setName(dto.getName());
            contact.setPhone(dto.getPhone());
            AddressBookModel updatedContact = repository.save(contact);
            log.info("Contact updated successfully: {}", updatedContact);
            return new AddressBookDTO(updatedContact.getId(), updatedContact.getName(), updatedContact.getPhone());
        } else {
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
        } else {
            log.warn("Attempted to delete non-existing contact with ID: {}", id);
        }
        return false;
    }
}

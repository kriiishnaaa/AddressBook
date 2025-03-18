package com.example.AddressBookApp.Service;

import com.example.AddressBookApp.DTO.AddressBookDTO;
import com.example.AddressBookApp.Exception.UserException;
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
        try {
            log.info("Fetching all contacts from the database.");
            return repository.findAll().stream()
                    .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhone()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error fetching contacts: {}", e.getMessage());
            throw new UserException("Failed to fetch contacts. Please try again.");
        }
    }

    @Override
    public AddressBookDTO saveContact(AddressBookDTO dto) {
        try {
            log.info("Saving new contact: {}", dto);
            AddressBookModel contact = new AddressBookModel();
            contact.setName(dto.getName());
            contact.setPhone(dto.getPhone());
            AddressBookModel savedContact = repository.save(contact);
            log.info("Contact saved successfully with ID: {}", savedContact.getId());
            return new AddressBookDTO(savedContact.getId(), savedContact.getName(), savedContact.getPhone());
        } catch (Exception e) {
            log.error("Error saving contact: {}", e.getMessage());
            throw new UserException("Failed to save contact. Please try again.");
        }
    }

    @Override
    public AddressBookDTO getContactById(Long id) {
        try {
            log.info("Fetching contact with ID: {}", id);
            Optional<AddressBookModel> contact = repository.findById(id);
            if (contact.isEmpty()) {
                log.warn("Contact with ID {} not found.", id);
                throw new UserException("Contact not found with ID: " + id);
            }
            return new AddressBookDTO(contact.get().getId(), contact.get().getName(), contact.get().getPhone());
        } catch (Exception e) {
            log.error("Error fetching contact by ID: {}", e.getMessage());
            throw new UserException("Failed to retrieve contact. Please try again.");
        }
    }

    @Override
    public AddressBookDTO updateContact(Long id, AddressBookDTO dto) {
        try {
            log.info("Updating contact with ID: {}", id);
            Optional<AddressBookModel> existingContact = repository.findById(id);

            if (existingContact.isEmpty()) {
                log.warn("Attempted to update non-existing contact with ID: {}", id);
                throw new UserException("Contact not found with ID: " + id);
            }

            AddressBookModel contact = existingContact.get();
            contact.setName(dto.getName());
            contact.setPhone(dto.getPhone());
            AddressBookModel updatedContact = repository.save(contact);

            log.info("Contact updated successfully: {}", updatedContact);
            return new AddressBookDTO(updatedContact.getId(), updatedContact.getName(), updatedContact.getPhone());

        } catch (Exception e) {
            log.error("Error updating contact: {}", e.getMessage());
            throw new UserException("Failed to update contact. Please try again.");
        }
    }

    @Override
    public boolean deleteContact(Long id) {
        try {
            log.info("Deleting contact with ID: {}", id);
            if (repository.existsById(id)) {
                repository.deleteById(id);
                log.info("Contact with ID {} deleted successfully.", id);
                return true;
            } else {
                log.warn("Attempted to delete non-existing contact with ID: {}", id);
                throw new UserException("Contact not found with ID: " + id);
            }
        } catch (Exception e) {
            log.error("Error deleting contact: {}", e.getMessage());
            throw new UserException("Failed to delete contact. Please try again.");
        }
    }
}

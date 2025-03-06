package com.AddressBook.AdressBookApp.Service;


import com.AddressBook.AdressBookApp.DTO.ContactDTO;
import com.AddressBook.AdressBookApp.Mapper.ContactMapper;
import com.AddressBook.AdressBookApp.Model.Contact;
import com.AddressBook.AdressBookApp.Repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper) {
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
    }

    public List<ContactDTO> getAllContacts() {
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ContactDTO> getContactById(Long id) {
        return contactRepository.findById(id).map(contactMapper::toDTO);
    }

    public ContactDTO addContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        return contactMapper.toDTO(contactRepository.save(contact));
    }

    public Optional<ContactDTO> updateContact(Long id, ContactDTO contactDTO) {
        return contactRepository.findById(id)
                .map(existingContact -> {
                    existingContact.setName(contactDTO.getName());
                    existingContact.setEmail(contactDTO.getEmail());
                    existingContact.setPhone(contactDTO.getPhone()); // ✅ Fix: Update Phone Number
                    return contactMapper.toDTO(contactRepository.save(existingContact));
                });
    }

    public boolean deleteContact(Long id) {
        if (contactRepository.existsById(id)) {
            contactRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
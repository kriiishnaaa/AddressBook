package com.example.AddressBookApp.Service;

import com.example.AddressBookApp.DTO.AddressBookDTO;
import com.example.AddressBookApp.Interface.AddressBookServiceInterface;
import com.example.AddressBookApp.Repository.AddressRepository;
import com.example.AddressBookApp.model.AddressBookModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressBookService implements AddressBookServiceInterface {

    @Autowired
    private AddressRepository repository;

    @Override
    public List<AddressBookDTO> getAllContacts() {
        return repository.findAll().stream()
                .map(contact -> new AddressBookDTO(contact.getId(), contact.getName(), contact.getPhone()))
                .collect(Collectors.toList());
    }

    @Override
    public AddressBookDTO saveContact(AddressBookDTO dto) {
        AddressBookModel contact = new AddressBookModel();
        contact.setName(dto.getName());
        contact.setPhone(dto.getPhone());
        AddressBookModel savedContact = repository.save(contact);
        return new AddressBookDTO(savedContact.getId(), savedContact.getName(), savedContact.getPhone());
    }
}

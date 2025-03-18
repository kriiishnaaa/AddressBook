package com.example.AddressBookApp.Interface;



import com.example.AddressBookApp.DTO.AddressBookDTO;

import java.util.List;

public interface AddressBookServiceInterface {
    List<AddressBookDTO> getAllContacts();
    AddressBookDTO saveContact(AddressBookDTO dto);
    AddressBookDTO getContactById(Long id);
    AddressBookDTO updateContact(Long id, AddressBookDTO dto);
    boolean deleteContact(Long id);
}
package com.AddressBook.AdressBookApp.Interfaces;


import com.AddressBook.AdressBookApp.DTO.ContactDTO;

import java.util.List;

public interface AddressBookServiceInterface {
    List<ContactDTO> getAllContacts();
    ContactDTO saveContact(ContactDTO dto);
    ContactDTO getContactById(Long id);
    ContactDTO updateContact(Long id, ContactDTO dto);
    boolean deleteContact(Long id);
}
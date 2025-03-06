package com.AddressBook.AdressBookApp.Repository;


import com.AddressBook.AdressBookApp.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {}
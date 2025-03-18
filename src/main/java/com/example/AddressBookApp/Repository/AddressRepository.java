package com.example.AddressBookApp.Repository;

import com.example.AddressBookApp.model.AddressBookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressBookModel,Long> {
}

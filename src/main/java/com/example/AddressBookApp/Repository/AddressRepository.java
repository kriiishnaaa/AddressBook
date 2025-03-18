package com.example.AddressBookApp.Repository;

import com.example.AddressBookApp.model.AddressBookModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressBookModel, Long> {
}

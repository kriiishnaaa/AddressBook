package com.example.AddressBookApp.Service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.AddressBookApp.DTO.AddressBookDTO;
import com.example.AddressBookApp.Exception.UserException;
import com.example.AddressBookApp.Repository.AddressRepository;
import com.example.AddressBookApp.model.AddressBookModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceTest {

    @Mock
    private AddressRepository repository;

    @InjectMocks
    private AddressBookService service;

    private AddressBookModel sampleContact;
    private AddressBookDTO sampleContactDTO;

    @BeforeEach
    void setUp() {
        sampleContact = new AddressBookModel(1L, "John Doe", "9876543210");
        sampleContactDTO = new AddressBookDTO(1L, "John Doe", "9876543210");
    }

    // ===================== GET ALL CONTACTS TESTS =====================

    @Test
    void getAllContacts_ShouldReturnContacts() {
        List<AddressBookModel> contactList = new ArrayList<>();
        contactList.add(sampleContact);

        when(repository.findAll()).thenReturn(contactList);

        List<AddressBookDTO> result = service.getAllContacts();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    void getAllContacts_ShouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<AddressBookDTO> result = service.getAllContacts();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllContacts_ShouldThrowExceptionOnFailure() {
        when(repository.findAll()).thenThrow(new RuntimeException("Database error"));

        UserException exception = assertThrows(UserException.class, () -> service.getAllContacts());
        assertEquals("Failed to fetch contacts. Please try again.", exception.getMessage());
    }

    // ===================== SAVE CONTACT TESTS =====================

    @Test
    void saveContact_ShouldSaveSuccessfully() {
        when(repository.save(any(AddressBookModel.class))).thenReturn(sampleContact);

        AddressBookDTO result = service.saveContact(sampleContactDTO);

        assertEquals("John Doe", result.getName());
        assertEquals("9876543210", result.getPhone());
    }

    @Test
    void saveContact_ShouldThrowExceptionOnFailure() {
        when(repository.save(any(AddressBookModel.class))).thenThrow(new RuntimeException("Save failed"));

        UserException exception = assertThrows(UserException.class, () -> service.saveContact(sampleContactDTO));
        assertEquals("Failed to save contact. Please try again.", exception.getMessage());
    }

    // ===================== GET CONTACT BY ID TESTS =====================

    @Test
    void getContactById_ShouldReturnContact() {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleContact));

        AddressBookDTO result = service.getContactById(1L);

        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
    }

    // ===================== GET CONTACT BY ID TESTS =====================

    @Test
    void getContactById_ShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> service.getContactById(1L));

        assertEquals("Contact not found with ID: 1", exception.getMessage());  // Fixed expected message
    }



    // ===================== UPDATE CONTACT TESTS =====================

    @Test
    void updateContact_ShouldUpdateSuccessfully() {
        AddressBookDTO updatedDTO = new AddressBookDTO(1L, "Jane Doe", "1234567890");

        when(repository.findById(1L)).thenReturn(Optional.of(sampleContact));
        when(repository.save(any(AddressBookModel.class))).thenReturn(new AddressBookModel(1L, "Jane Doe", "1234567890"));

        AddressBookDTO result = service.updateContact(1L, updatedDTO);

        assertEquals("Jane Doe", result.getName());
        assertEquals("1234567890", result.getPhone());
    }

    // ===================== UPDATE CONTACT TESTS =====================

    @Test
    void updateContact_ShouldThrowExceptionWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        UserException exception = assertThrows(UserException.class, () -> service.updateContact(1L, sampleContactDTO));
        assertEquals("Contact not found with ID: 1", exception.getMessage());  // Fixed expected message
    }


    // ===================== DELETE CONTACT TESTS =====================

    @Test
    void deleteContact_ShouldDeleteSuccessfully() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertTrue(service.deleteContact(1L));
    }
    @Test
    void deleteContact_ShouldThrowExceptionWhenNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        UserException exception = assertThrows(UserException.class, () -> service.deleteContact(1L));
        assertEquals("Contact not found with ID: 1", exception.getMessage());
    }
    @Test
    void deleteContact_ShouldThrowExceptionOnFailure() {
        when(repository.existsById(1L)).thenReturn(true);
        doThrow(new RuntimeException("Delete failed")).when(repository).deleteById(1L);

        UserException exception = assertThrows(UserException.class, () -> service.deleteContact(1L));
        assertEquals("Failed to delete contact. Please try again.", exception.getMessage());
    }
}

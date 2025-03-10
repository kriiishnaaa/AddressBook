package com.AddressBook.AdressBookApp.Interfaces;

import com.AddressBook.AdressBookApp.DTO.AuthUserDTO;
import com.AddressBook.AdressBookApp.Model.AuthUser;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;

}
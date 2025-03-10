package com.AddressBook.AdressBookApp.Interfaces;

import com.AddressBook.AdressBookApp.DTO.AuthUserDTO;
import com.AddressBook.AdressBookApp.DTO.LoginDTO;
import com.AddressBook.AdressBookApp.Model.AuthUser;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO);
    String forgotPassword(String email, String newPassword);
    String resetPassword(String email, String currentPassword, String newPassword);
}
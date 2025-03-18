package com.example.AddressBookApp.Interface;


import com.example.AddressBookApp.DTO.AuthUserDTO;
import com.example.AddressBookApp.DTO.LoginDTO;
import com.example.AddressBookApp.model.AuthUser;
public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;
    String login(LoginDTO loginDTO);
    String forgotPassword(String email, String newPassword);
    String resetPassword(String email, String currentPassword, String newPassword);
}
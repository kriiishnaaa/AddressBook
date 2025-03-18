package com.example.AddressBookApp.Interface;


import com.example.AddressBookApp.DTO.AuthUserDTO;
import com.example.AddressBookApp.DTO.JwtReponse;
import com.example.AddressBookApp.DTO.LoginDTO;
import com.example.AddressBookApp.model.AuthUser;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthenticationService {
    AuthUser register(AuthUserDTO userDTO) throws Exception;
    public String login(LoginDTO user, HttpServletResponse response);
    String forgotPassword(String email, String newPassword);
    String resetPassword(String email, String currentPassword, String newPassword);
}
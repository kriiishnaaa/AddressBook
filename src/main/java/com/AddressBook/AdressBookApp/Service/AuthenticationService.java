package com.AddressBook.AdressBookApp.Service;

import com.AddressBook.AdressBookApp.DTO.AuthUserDTO;
import com.AddressBook.AdressBookApp.Interfaces.IAuthenticationService;
import com.AddressBook.AdressBookApp.Model.AuthUser;
import com.AddressBook.AdressBookApp.Repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    AuthenticationRepository authUserRepository;

    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        AuthUser user = new AuthUser(userDTO);

        authUserRepository.save(user);

        return user;
    }


}
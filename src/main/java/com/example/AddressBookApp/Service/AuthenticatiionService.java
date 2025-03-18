package com.example.AddressBookApp.Service;

import com.example.AddressBookApp.DTO.AuthUserDTO;
import com.example.AddressBookApp.Interface.IAuthenticationService;
import com.example.AddressBookApp.Repository.AuthenticationRepository;
import com.example.AddressBookApp.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticatiionService implements IAuthenticationService {

    @Autowired
    AuthenticationRepository authUserRepository;

    @Override
    public AuthUser register(AuthUserDTO userDTO) throws Exception {
        AuthUser user = new AuthUser(userDTO);

        authUserRepository.save(user);

        return user;
    }


}
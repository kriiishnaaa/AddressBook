package com.example.AddressBookApp.Controller;

import com.example.AddressBookApp.DTO.AuthUserDTO;
import com.example.AddressBookApp.Exception.ResponseDTO;
import com.example.AddressBookApp.Service.AuthenticatiionService;
import com.example.AddressBookApp.model.AuthUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
//@RequestMapping("/auth")
public class AuthUserController {
    @Autowired
    AuthenticatiionService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) throws Exception{
        AuthUser user=authenticationService.register(userDTO);
        ResponseDTO responseUserDTO =new ResponseDTO("User details is submitted!",user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }


}

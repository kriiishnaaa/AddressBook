package com.example.AddressBookApp.Controller;

import com.example.AddressBookApp.DTO.*;
import com.example.AddressBookApp.Interface.IAuthenticationService;
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
    IAuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody AuthUserDTO userDTO) throws Exception{
        AuthUser user=authenticationService.register(userDTO);
        ResponseDTO responseUserDTO =new ResponseDTO("User details is submitted!",user);
        return new ResponseEntity<>(responseUserDTO, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        String result=authenticationService.login(loginDTO);
        ResponseDTO responseUserDTO=new ResponseDTO("Login successfully!!",result);
        return  new ResponseEntity<>(responseUserDTO,HttpStatus.OK);
    }
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<ResponseDTO> forgotPassword(@PathVariable String email,
                                                      @Valid @RequestBody ForgetPasswordDTO forgotPasswordDTO) {
        String responseMessage = authenticationService.forgotPassword(email, forgotPasswordDTO.getPassword());
        ResponseDTO responseDTO = new ResponseDTO(responseMessage, null);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<ResponseDTO> resetPassword(@PathVariable String email,
                                                     @Valid @RequestBody ResetPasswordDTO resetPasswordDTO) {
        String responseMessage = authenticationService.resetPassword(email,
                resetPasswordDTO.getCurrentPassword(),
                resetPasswordDTO.getNewPassword());
        return new ResponseEntity<>(new ResponseDTO(responseMessage, null), HttpStatus.OK);
    }

}

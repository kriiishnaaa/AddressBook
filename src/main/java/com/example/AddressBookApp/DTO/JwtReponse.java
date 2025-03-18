package com.example.AddressBookApp.DTO;

public class JwtReponse {
   String token;

    public String getToken() {
        return token;
    }
    public JwtReponse(String token) {
        this.token = token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}

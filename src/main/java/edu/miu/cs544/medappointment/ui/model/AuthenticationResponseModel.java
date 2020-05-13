package edu.miu.cs544.medappointment.ui.model;

public class AuthenticationResponseModel {

    private String token;

    public AuthenticationResponseModel() {
    }

    public AuthenticationResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

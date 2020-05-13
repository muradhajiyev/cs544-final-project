package edu.miu.cs544.medappointment.ui.model;

public class AuthenticationRequestModel {
    private String username;
    private String password;

    public AuthenticationRequestModel() {
    }

    public AuthenticationRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

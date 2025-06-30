package com.example.Models;

public class Login {
   private String username;
    private String password;

     public void setusername(String username) {
        this.username = username;
    }
    public String getusername(){
        return username;
    }

    // Getter for password
    public String getpassword() {
        return password;
    }

    // Setter for password
    public void setpassword(String password) {
        this.password = password;
    }
}

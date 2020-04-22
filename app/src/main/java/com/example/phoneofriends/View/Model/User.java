package com.example.phoneofriends.View.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String userId;
    private String userName;
    private String userEmail;
    private String userContact;
    private String userDateOfBirth;
    private String userGender;

    public User() {
    }

    public User(String userId, String userName, String userEmail, String userContact, String userDateOfBirth, String userGender) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContact = userContact;
        this.userDateOfBirth = userDateOfBirth;
        this.userGender = userGender;
    }

    public User(String userName, String userEmail, String userContact, String userDateOfBirth, String userGender) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userContact = userContact;
        this.userDateOfBirth = userDateOfBirth;
        this.userGender = userGender;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserDateOfBirth() {
        return userDateOfBirth;
    }

    public void setUserDateOfBirth(String userDateOfBirth) {
        this.userDateOfBirth = userDateOfBirth;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userDateOfBirth='" + userDateOfBirth + '\'' +
                ", userGender='" + userGender + '\'' +
                '}';
    }
}



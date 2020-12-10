package com.merobookstore.bookstore.model;

public class User {

private String fullName;
private String address;
private String username;
private String gender;
private String password;
private String image;

    public User(String fullName, String address, String username, String gender, String password, String image) {
        this.fullName = fullName;
        this.address = address;
        this.username = username;
        this.gender = gender;
        this.password = password;
        this.image = image;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

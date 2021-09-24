package com.example.techeye.subscriber.personal;

public class Login_ModelClass {



    String id,password,name,email,mobileno,aadhar,passport;

    public Login_ModelClass(String id, String password, String name, String email, String mobileno, String aadhar, String passport) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobileno = mobileno;
        this.aadhar = aadhar;
        this.passport = passport;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }
}

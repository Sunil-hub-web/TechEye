package com.example.techeye.subscriber.viewalldetails;

public class UserDetails_ModelClass {

    String id,name,mobileNumber,emailId,state,city,country,pincode,
            aadharNumber,passportNumber,aadharImage,passportImage,billingAddress1,billingAddress2,
            billingCountry,billingState,billingCity,billingPincode;

    public UserDetails_ModelClass(String id, String name, String mobileNumber,
                                  String emailId, String state, String city, String country, String pincode,
                                  String aadharNumber, String passportNumber, String aadharImage,
                                  String passportImage, String billingAddress1, String billingAddress2,
                                  String billingCountry, String billingState, String billingCity, String billingPincode) {
        this.id = id;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.emailId = emailId;
        this.state = state;
        this.city = city;
        this.country = country;
        this.pincode = pincode;
        this.aadharNumber = aadharNumber;
        this.passportNumber = passportNumber;
        this.aadharImage = aadharImage;
        this.passportImage = passportImage;
        this.billingAddress1 = billingAddress1;
        this.billingAddress2 = billingAddress2;
        this.billingCountry = billingCountry;
        this.billingState = billingState;
        this.billingCity = billingCity;
        this.billingPincode = billingPincode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getAadharImage() {
        return aadharImage;
    }

    public void setAadharImage(String aadharImage) {
        this.aadharImage = aadharImage;
    }

    public String getPassportImage() {
        return passportImage;
    }

    public void setPassportImage(String passportImage) {
        this.passportImage = passportImage;
    }

    public String getBillingAddress1() {
        return billingAddress1;
    }

    public void setBillingAddress1(String billingAddress1) {
        this.billingAddress1 = billingAddress1;
    }

    public String getBillingAddress2() {
        return billingAddress2;
    }

    public void setBillingAddress2(String billingAddress2) {
        this.billingAddress2 = billingAddress2;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public void setBillingCountry(String billingCountry) {
        this.billingCountry = billingCountry;
    }

    public String getBillingState() {
        return billingState;
    }

    public void setBillingState(String billingState) {
        this.billingState = billingState;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }

    public String getBillingPincode() {
        return billingPincode;
    }

    public void setBillingPincode(String billingPincode) {
        this.billingPincode = billingPincode;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", emailId='" + emailId + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", pincode='" + pincode + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", aadharImage='" + aadharImage + '\'' +
                ", passportImage='" + passportImage + '\'' +
                ", billingAddress1='" + billingAddress1 + '\'' +
                ", billingAddress2='" + billingAddress2 + '\'' +
                ", billingCountry='" + billingCountry + '\'' +
                ", billingState='" + billingState + '\'' +
                ", billingCity='" + billingCity + '\'' +
                ", billingPincode='" + billingPincode + '\'' +
                '}';
    }
}

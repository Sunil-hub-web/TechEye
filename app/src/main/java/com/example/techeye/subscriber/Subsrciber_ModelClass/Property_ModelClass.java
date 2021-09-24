package com.example.techeye.subscriber.Subsrciber_ModelClass;

public class Property_ModelClass {

    String id,email,reason_for_third_eye,plot_size,country,state,city,pincode;

    public Property_ModelClass(String id, String email, String reason_for_third_eye,
                               String plot_size, String country, String state, String city, String pincode) {
        this.id = id;
        this.email = email;
        this.reason_for_third_eye = reason_for_third_eye;
        this.plot_size = plot_size;
        this.country = country;
        this.state = state;
        this.city = city;
        this.pincode = pincode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason_for_third_eye() {
        return reason_for_third_eye;
    }

    public void setReason_for_third_eye(String reason_for_third_eye) {
        this.reason_for_third_eye = reason_for_third_eye;
    }

    public String getPlot_size() {
        return plot_size;
    }

    public void setPlot_size(String plot_size) {
        this.plot_size = plot_size;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}

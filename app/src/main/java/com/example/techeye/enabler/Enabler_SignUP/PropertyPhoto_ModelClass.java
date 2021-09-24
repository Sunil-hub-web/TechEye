package com.example.techeye.enabler.Enabler_SignUP;

import java.util.ArrayList;

public class PropertyPhoto_ModelClass {

    String EnablerId, ImageUri, Description, Name;

    public PropertyPhoto_ModelClass(String enablerId, String imageUri, String description, String name) {
        EnablerId = enablerId;
        ImageUri = imageUri;
        Description = description;
        Name = name;
    }

    public String getEnablerId() {
        return EnablerId;
    }

    public void setEnablerId(String enablerId) {
        EnablerId = enablerId;
    }

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "EnablerId='" + EnablerId + '\'' +
                ", ImageUri='" + ImageUri + '\'' +
                ", Description='" + Description + '\'' +
                ", Name='" + Name + '\'' +
                '}';
    }
}

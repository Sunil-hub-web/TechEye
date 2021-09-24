package com.example.techeye.enabler.Enabler_SignUP;

public class JobRequest_ModelClass {

    String propertyId,enablerId,assignDate,startDate,endDate,statues,id;

    public JobRequest_ModelClass(String propertyId, String enablerId, String assignDate,
                                 String startDate, String endDate, String statues,String id) {
        this.propertyId = propertyId;
        this.enablerId = enablerId;
        this.assignDate = assignDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statues = statues;
        this.id = id;
    }

    public String getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(String propertyId) {
        this.propertyId = propertyId;
    }

    public String getEnablerId() {
        return enablerId;
    }

    public void setEnablerId(String enablerId) {
        this.enablerId = enablerId;
    }

    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "propertyId='" + propertyId + '\'' +
                ", enablerId='" + enablerId + '\'' +
                ", assignDate='" + assignDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", statues='" + statues + '\'' +
                '}';
    }
}

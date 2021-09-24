package com.example.techeye.subscriber.viewalldetails;

public class PackagesDetails_ModelClass {

    String userid,paymentid,emailid,frequency,duration,statues,total;

    public PackagesDetails_ModelClass(String userid, String paymentid, String emailid,
                                      String frequency, String duration,String statues,String total) {
        this.userid = userid;
        this.paymentid = paymentid;
        this.emailid = emailid;
        this.frequency = frequency;
        this.duration = duration;
        this.statues = statues;
        this.total = total;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(String paymentid) {
        this.paymentid = paymentid;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStatues() {
        return statues;
    }

    public void setStatues(String statues) {
        this.statues = statues;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "{" +
                "userid='" + userid + '\'' +
                ", paymentid='" + paymentid + '\'' +
                ", emailid='" + emailid + '\'' +
                ", frequency='" + frequency + '\'' +
                ", duration='" + duration + '\'' +
                ", statues='" + statues + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}

package com.restartindia.naukri.main.model;

import java.util.ArrayList;

public class PostDetails {

    String name,phone,district,uid,pincode;
    boolean isEmployee ;
    ArrayList<String> skills;

    public PostDetails(String name, String phone, String district, String uid, String pincode, boolean isEmployee, ArrayList<String> skills) {
        this.name = name;
        this.phone = phone;
        this.district = district;
        this.uid = uid;
        this.pincode = pincode;
        this.isEmployee = isEmployee;
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public boolean isEmployee() {
        return isEmployee;
    }

    public void setEmployee(boolean employee) {
        isEmployee = employee;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<String> skills) {
        this.skills = skills;
    }
}


package com.restartindia.naukri.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class PostData {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("isEmployee")
    @Expose
    private Boolean isEmployee;
    @SerializedName("pincode")
    @Expose
    private Integer pincode;
    @SerializedName("skills")
    @Expose
    private List<String> skills = new ArrayList<>();

    public PostData(String name, String phone, String uid, String district, Boolean isEmployee, Integer pincode, List<String> skills) {
        this.name = name;
        this.phone = phone;
        this.uid = uid;
        this.district = district;
        this.isEmployee = isEmployee;
        this.pincode = pincode;
        this.skills = skills;
    }

    public PostData() {
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Boolean getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

}
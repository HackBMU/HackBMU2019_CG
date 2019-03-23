package com.example.sunejas.sihproject.Models;

public class PatientDetails {
    private String mName, Email, mCity, mPhone,mBloodGroup,mAge,mPassword;

    public PatientDetails() {
    }

    public PatientDetails(String mName, String email, String mCity, String mPhone, String mBloodGroup, String mAge) {
        this.mName = mName;
        Email = email;
        this.mCity = mCity;
        this.mPhone = mPhone;
        this.mBloodGroup = mBloodGroup;
        this.mAge = mAge;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmBloodGroup() {
        return mBloodGroup;
    }

    public void setmBloodGroup(String mBloodGroup) {
        this.mBloodGroup = mBloodGroup;
    }

    public String getmAge() {
        return mAge;
    }

    public void setmAge(String mAge) {
        this.mAge = mAge;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}

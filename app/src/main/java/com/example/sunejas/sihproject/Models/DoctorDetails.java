package com.example.sunejas.sihproject.Models;

public class DoctorDetails {
    private String mName, Email, mCity, mPhone,mField,mSpecialization,mLicenseNumber,mPassword;

    public DoctorDetails(){

    }

    public DoctorDetails(String mName, String email, String mCity, String mPhone, String mField, String mSpecialization, String mLicenseNumber) {
        this.mName = mName;
        Email = email;
        this.mCity = mCity;
        this.mPhone = mPhone;
        this.mField = mField;
        this.mSpecialization = mSpecialization;
        this.mLicenseNumber = mLicenseNumber;
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

    public String getmField() {
        return mField;
    }

    public void setmField(String mField) {
        this.mField = mField;
    }

    public String getmSpecialization() {
        return mSpecialization;
    }

    public void setmSpecialization(String mSpecialization) {
        this.mSpecialization = mSpecialization;
    }

    public String getmLicenseNumber() {
        return mLicenseNumber;
    }

    public void setmLicenseNumber(String mLicenseNumber) {
        this.mLicenseNumber = mLicenseNumber;
    }

    public String getmPassword() {
        return mPassword;
    }

    public void setmPassword(String mPassword) {
        this.mPassword = mPassword;
    }
}

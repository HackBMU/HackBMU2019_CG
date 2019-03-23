package com.example.sunejas.sihproject.Models;

public class EventDetails {
    String title, date, description, allergies, currentMed, userId, duration, sex,closeupURL,overviewURL;

    public EventDetails() {
    }

    public String getCloseupURL() {
        return closeupURL;
    }

    public void setCloseupURL(String closeupURL) {
        this.closeupURL = closeupURL;
    }

    public String getOverviewURL() {
        return overviewURL;
    }

    public void setOverviewURL(String overviewURL) {
        this.overviewURL = overviewURL;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getCurrentMed() {
        return currentMed;
    }

    public void setCurrentMed(String currentMed) {
        this.currentMed = currentMed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

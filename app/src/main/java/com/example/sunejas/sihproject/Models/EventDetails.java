package com.example.sunejas.sihproject.Models;

import java.util.ArrayList;

public class EventDetails {

    private String allergies, assignedDocName, closeupImage, comment, overviewImage, duration, name;
    private long date, assignedDoc, userId;
    private ArrayList<String> progress, selfMed, tags;
    private int priority;
    private DocMed docMed;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public EventDetails(String allergies, String assignedDocName, String closeupImage, String comment, String overviewImage, String duration, String name, long date, long assignedDoc, long userId, ArrayList<String> progress, ArrayList<String> selfMed, ArrayList<String> tags, int priority, DocMed docMed) {
        this.allergies = allergies;
        this.assignedDocName = assignedDocName;
        this.closeupImage = closeupImage;
        this.comment = comment;
        this.overviewImage = overviewImage;
        this.duration = duration;
        this.name = name;
        this.date = date;
        this.assignedDoc = assignedDoc;
        this.userId = userId;
        this.progress = progress;
        this.selfMed = selfMed;
        this.tags = tags;
        this.priority = priority;
        this.docMed = docMed;
    }

    public EventDetails() {
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getAssignedDocName() {
        return assignedDocName;
    }

    public void setAssignedDocName(String assignedDocName) {
        this.assignedDocName = assignedDocName;
    }

    public String getCloseupImage() {
        return closeupImage;
    }

    public void setCloseupImage(String closeupImage) {
        this.closeupImage = closeupImage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOverviewImage() {
        return overviewImage;
    }

    public void setOverviewImage(String overviewImage) {
        this.overviewImage = overviewImage;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getAssignedDoc() {
        return assignedDoc;
    }

    public void setAssignedDoc(long assignedDoc) {
        this.assignedDoc = assignedDoc;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public ArrayList<String> getProgress() {
        return progress;
    }

    public void setProgress(ArrayList<String> progress) {
        this.progress = progress;
    }

    public ArrayList<String> getSelfMed() {
        return selfMed;
    }

    public void setSelfMed(ArrayList<String> selfMed) {
        this.selfMed = selfMed;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public DocMed getDocMed() {
        return docMed;
    }

    public void setDocMed(DocMed docMed) {
        this.docMed = docMed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

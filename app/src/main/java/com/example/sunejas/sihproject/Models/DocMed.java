package com.example.sunejas.sihproject.Models;

public class DocMed {

    private String comment;
    private Med med;

    public DocMed(String comment, Med med) {
        this.comment = comment;
        this.med = med;
    }

    public DocMed() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Med getMed() {
        return med;
    }

    public void setMed(Med med) {
        this.med = med;
    }
}

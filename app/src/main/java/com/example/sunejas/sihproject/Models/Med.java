package com.example.sunejas.sihproject.Models;

public class Med {

    private int duration;
    private String name;
    private Time time;

    public Med(int duration, String name, Time time) {
        this.duration = duration;
        this.name = name;
        this.time = time;
    }

    public Med() {
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}

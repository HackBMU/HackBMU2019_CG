package com.example.sunejas.sihproject.Models;

public class Time {

    private long time1, time2, time3;

    public Time(long time1, long time2, long time3) {
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
    }

    public Time() {
    }

    public long getTime1() {
        return time1;
    }

    public void setTime1(long time1) {
        this.time1 = time1;
    }

    public long getTime2() {
        return time2;
    }

    public void setTime2(long time2) {
        this.time2 = time2;
    }

    public long getTime3() {
        return time3;
    }

    public void setTime3(long time3) {
        this.time3 = time3;
    }
}

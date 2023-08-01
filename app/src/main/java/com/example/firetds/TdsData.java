package com.example.firetds;

import com.google.firebase.database.Exclude;

public class TdsData {

    @Exclude
    String key;

    String date, insight;
    int ppm1, hour; // Add hour field

    //constructor
    public TdsData(String date, String insight, int ppm1, int hour){ // Include hour in the constructor
        this.date = date;
        this.insight = insight;
        this.ppm1 = ppm1;
        this.hour = hour; // Assign hour to the field
    }

    //empty constructor
    public TdsData(){

    }

    //set function
    public void setKey(String key) {
        this.key = key;
    }

    //get function
    public String getDate() {
        return date;
    }

    public String getInsight() {
        return insight;
    }

    public int getPpm1() {
        return ppm1;
    }

    public int getHour() { // Getter for hour
        return hour;
    }

    public String getKey() {
        return key;
    }
}

package com.example.firetds;

import com.google.firebase.database.Exclude;

public class TdsData {



    @Exclude
    String key;

    String date, insight;
    int ppm1;


    //constructor
    public TdsData(String date, String insight, int ppm1){
        this.date = date;
        this.insight = insight;
        this.ppm1 = ppm1;
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

    public String getKey() {
        return key;
    }
}

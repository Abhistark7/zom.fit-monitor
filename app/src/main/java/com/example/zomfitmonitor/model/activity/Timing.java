package com.example.zomfitmonitor.model.activity;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Timing {

    @SerializedName("id")
    public String timingId;

    @SerializedName("time")
    public String time;

    @SerializedName("date")
    public String date;

    @SerializedName("maxCount")
    public int maxCount;

    @SerializedName("bookedCount")
    public int bookedCount;

    @SerializedName("isAvailable")
    public boolean isAvailable;
}

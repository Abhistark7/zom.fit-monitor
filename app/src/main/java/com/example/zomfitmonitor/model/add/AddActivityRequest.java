package com.example.zomfitmonitor.model.add;

import com.example.zomfitmonitor.model.activity.Timing;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class AddActivityRequest {

    @SerializedName("activityName")
    public String activityName;

    @SerializedName("cost")
    public String cost;

    @SerializedName("activityImageUrl")
    public String activityImageUrl;

    @SerializedName("centerName")
    public String centerName;

    public List<Timing> timingList;
}

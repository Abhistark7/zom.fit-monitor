package com.example.zomfitmonitor.model.getcenters;

import com.example.zomfitmonitor.model.Center;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class GetCentersResponse {

    @SerializedName("centerList")
    public List<Center> centerList;
}

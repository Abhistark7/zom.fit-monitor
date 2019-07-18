package com.example.zomfitmonitor.model.getcenters;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

@Parcel
public class GetCentersRequest {

    @SerializedName("centerIdList")
    public ArrayList<String> centerIdList;
}

package com.example.zomfitmonitor.model.login;

import com.example.zomfitmonitor.model.User;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class LoginResponse {

    @SerializedName("status")
    public boolean status;

    @SerializedName("message")
    public String message;

    @SerializedName("user")
    public User user;
}

package com.example.zomfitmonitor.model.activity;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Timing implements Parcelable {

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

    public Timing() {
    }

    protected Timing(android.os.Parcel in) {
        timingId = in.readString();
        time = in.readString();
        date = in.readString();
        maxCount = in.readInt();
        bookedCount = in.readInt();
        isAvailable = in.readByte() != 0;
    }

    public static final Creator<Timing> CREATOR = new Creator<Timing>() {
        @Override
        public Timing createFromParcel(android.os.Parcel in) {
            return new Timing(in);
        }

        @Override
        public Timing[] newArray(int size) {
            return new Timing[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(timingId);
        parcel.writeString(time);
        parcel.writeString(date);
        parcel.writeInt(maxCount);
        parcel.writeInt(bookedCount);
    }
}

package com.example.android.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CookingStep implements Parcelable {

    public static final Parcelable.Creator<CookingStep> CREATOR = new Parcelable.Creator<CookingStep>(){

        @Override
        public CookingStep createFromParcel(Parcel source) {
            return new CookingStep(source);
        }

        @Override
        public CookingStep[] newArray(int size) {
            return new CookingStep[size];
        }
    };


    public final static String TAG = CookingStep.class.getSimpleName();
    public final static String POSITION_TAG = CookingStep.TAG + "_position";

    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private CookingStep(Parcel source){
        shortDescription = source.readString();
        description = source.readString();
        videoURL = source.readString();
        thumbnailURL = source.readString();
    }
    CookingStep(String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}

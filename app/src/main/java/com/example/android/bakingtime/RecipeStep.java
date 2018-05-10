package com.example.android.bakingtime;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeStep implements Parcelable {

    public static final Parcelable.Creator<RecipeStep> CREATOR = new Parcelable.Creator<RecipeStep>(){

        @Override
        public RecipeStep createFromParcel(Parcel source) {
            return new RecipeStep(source);
        }

        @Override
        public RecipeStep[] newArray(int size) {
            return new RecipeStep[size];
        }
    };


    final static String TAG = RecipeStep.class.getSimpleName();
    final static String POSITION_TAG = RecipeStep.TAG + TAG;

    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private RecipeStep(Parcel source){
        shortDescription = source.readString();
        description = source.readString();
        videoURL = source.readString();
        thumbnailURL = source.readString();
    }
    RecipeStep(String shortDescription, String description, String videoURL, String thumbnailURL) {
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

package com.example.android.bakingtime;

import android.os.Parcel;
import android.os.Parcelable;

class Recipe implements Parcelable {


    static final String TAG = Recipe.class.getSimpleName();
    static final String RECIPE_WIDGET_POSITION = TAG + "WidgetPosition";

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };


    private String name;

    //keeping these as strings for now to allow it to be used with parcel
    private String ingredientsJson;
    private String stepsJson;


    private Recipe(Parcel readFrom) {
        this.name = readFrom.readString();
        this.ingredientsJson = readFrom.readString();
        this.stepsJson = readFrom.readString();
    }

    Recipe(String name, String ingredientsJson, String stepsJson) {
        this.name = name;
        this.ingredientsJson = ingredientsJson;
        this.stepsJson = stepsJson;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getIngredientsJson() {
        return ingredientsJson;
    }

    public String getStepsJson() {
        return stepsJson;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(ingredientsJson);
        dest.writeString(stepsJson);
    }
}

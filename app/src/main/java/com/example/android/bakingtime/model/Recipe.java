package com.example.android.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Recipe implements Parcelable {

    public static final String TAG = Recipe.class.getSimpleName();

    public static final String NAME_TAG = "recipe_name";

    public static final String INGREDIENTS_TAG = "ingredients";



    public static final String RECIPE_WIDGET_POSITION = TAG + "WidgetPosition";

    private String name;

    //keeping these as strings for now to allow it to be used with parcel
    private String ingredientsJson;
    private String stepsJson;

    private String imageUrl;

    //---Parcelling-------------------------------------------
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

    private Recipe(Parcel readFrom) {
        this.name = readFrom.readString();
        this.ingredientsJson = readFrom.readString();
        this.stepsJson = readFrom.readString();
        this.imageUrl = readFrom.readString();
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
        dest.writeString(imageUrl);
    }
    //---------------------------------------------------------

    //----Regular constructors-------------
    private Recipe(String name, String ingredientsJson, String stepsJson,String imageUrl) {
        this.name = name;
        this.ingredientsJson = ingredientsJson;
        this.stepsJson = stepsJson;
        this.imageUrl = imageUrl;
    }

    public Recipe (JSONObject jsonRecipeObject) throws JSONException {
        this(
                jsonRecipeObject.optString("name"),
                jsonRecipeObject.getJSONArray("ingredients").toString(),
                jsonRecipeObject.getJSONArray("steps").toString(),
                jsonRecipeObject.optString("image")
        );
    }
    //--------------------------------------

    //-----Utils-----------------------------------
    //Turns JSONArray to recipe array.
    public static Recipe[] JSONToRecipeArray(JSONArray jsonRecipeArray) throws JSONException {

        Recipe[] recipes = new Recipe[jsonRecipeArray.length()];

        for (int i = 0; i < jsonRecipeArray.length(); i++) {
            recipes[i] = new Recipe(jsonRecipeArray.getJSONObject(i));
        }

        return recipes;
    }

    public static Recipe getRecipe(FragmentActivity fragmentActivity) {
        Recipe recipe = null;
        if (fragmentActivity!= null) {
            recipe = fragmentActivity.getIntent().getParcelableExtra(Recipe.TAG);
        }
        return recipe;
    }

    private CookingStep[] parseCookingSteps(String stepsJson) throws JSONException {
        JSONArray root = new JSONArray(stepsJson);
        CookingStep[] steps = new CookingStep[root.length()];
        for (int i = 0; i < root.length(); i++) {
            JSONObject obj = root.getJSONObject(i);
            steps[i] = new CookingStep(obj.getString("shortDescription"),
                    obj.getString("description"),
                    obj.getString("videoURL"),
                    obj.getString("thumbnailURL")
            );
        }
        return steps;
    }

    private String parseIngredients(String ingredientsJson) throws JSONException {
        JSONArray ingredientsArray = new JSONArray(ingredientsJson);
        StringBuilder builder = new StringBuilder("Ingredients:\n\n");
        for (int i = 0; i < ingredientsArray.length(); i++) {
            JSONObject ingredient = ingredientsArray.getJSONObject(i);
            builder.append("* ")
                    .append(ingredient.getString("quantity"))
                    .append(" ")
                    .append(ingredient.getString("measure"))
                    .append(" ")
                    .append(ingredient.getString("ingredient"))
                    .append("\n");
        }
        return builder.toString();
    }

    //--------------------------------------------------

    //----Setters and getters------
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public  String getIngredients() {
        String ingredients = null;
        try {
            ingredients = parseIngredients(ingredientsJson);
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return ingredients;
    }

    public  CookingStep[] getCookingSteps() {
        CookingStep[] steps = null;
        try {
            steps = parseCookingSteps(stepsJson);
        } catch (JSONException j) {
            j.printStackTrace();
        }
        return steps;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    //-------------------------------


}

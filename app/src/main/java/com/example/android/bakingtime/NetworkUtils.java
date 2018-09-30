package com.example.android.bakingtime;

import android.support.annotation.Nullable;

import com.example.android.bakingtime.model.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import okhttp3.ResponseBody;

public class NetworkUtils {

    /**
     * Downloads a URL and returns the contents as a string.
     *
     * @param url The download and return its string.
     * @return String from the provided url
     */
    @Nullable
    static private String getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        ResponseBody responseBody = client.newCall(request).execute().body();

        return responseBody != null ? responseBody.string() : null;
    }

    static private Recipe parseRecipe(JSONObject jsonRecipeObject) throws JSONException {
        return new Recipe(
                jsonRecipeObject.optString("name"),
                jsonRecipeObject.getJSONArray("ingredients").toString(),
                jsonRecipeObject.getJSONArray("steps").toString(),
                jsonRecipeObject.optString("image")
        );


    }

    static private Recipe[] parseRecipeList(JSONArray jsonRecipeList) throws JSONException {

        Recipe[] recipes = new Recipe[jsonRecipeList.length()];

        for (int i = 0; i < jsonRecipeList.length(); i++) {
            recipes[i] = parseRecipe(jsonRecipeList.getJSONObject(i));
        }

        return recipes;
    }

    public static Recipe[] getRecipeData() throws IOException, JSONException {
        String json = getResponse("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
        JSONArray root = new JSONArray(json);
        return parseRecipeList(root);

    }

    static RecipeStep[] parseCookingSteps(String stepsJson) throws JSONException {
        JSONArray root = new JSONArray(stepsJson);
        RecipeStep[] steps = new RecipeStep[root.length()];
        for (int i = 0; i < root.length(); i++) {
            JSONObject obj = root.getJSONObject(i);
            steps[i] = new RecipeStep(obj.getString("shortDescription"),
                    obj.getString("description"),
                    obj.getString("videoURL"),
                    obj.getString("thumbnailURL")
                    );
        }
        return steps;
    }

    static String parseIngredients(String ingredientsJson) throws JSONException {
        JSONArray root = new JSONArray(ingredientsJson);
        StringBuilder builder = new StringBuilder("Ingredients:\n\n");
        for (int i = 0; i < root.length(); i++) {
            JSONObject obj = root.getJSONObject(i);
            builder.append("* ")
                    .append(obj.getString("quantity"))
                    .append(" ")
                    .append(obj.getString("measure"))
                    .append(" ")
                    .append(obj.getString("ingredient"))
                    .append("\n");
        }
        return builder.toString();
    }


}

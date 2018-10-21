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

    public final static String RECIPES_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    /**
     * Returns the JSON string from a url.
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

    public static Recipe[] getRecipesFromURL(String url) throws IOException, JSONException {
        String json = getResponse(url);
        JSONArray root = new JSONArray(json);
        return Recipe.JSONToRecipeArray(root);
    }


}

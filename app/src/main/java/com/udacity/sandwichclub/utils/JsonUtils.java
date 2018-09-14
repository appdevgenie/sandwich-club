package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String TAG = "JsonUtils";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        String mainName = "";
        String image = "";
        String placeOfOrigin = "";
        String description = "";
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try{

            Log.d(TAG, "parseSandwichJson:");

            JSONObject rootObject = new JSONObject(json);
            image = rootObject.getString(KEY_IMAGE);
            placeOfOrigin = rootObject.getString(KEY_PLACE_OF_ORIGIN);
            description = rootObject.getString(KEY_DESCRIPTION);

            JSONObject nameObject = rootObject.getJSONObject(KEY_NAME);
            mainName = nameObject.getString(KEY_MAIN_NAME);

            JSONArray alsoKnownAsArray = nameObject.getJSONArray(KEY_ALSO_KNOW_AS);
            for (int i = 0; i < alsoKnownAsArray.length(); i++) {

                alsoKnownAs.add(alsoKnownAsArray.getString(i));

                Log.d(TAG, "populate aka array: " + alsoKnownAsArray.getString(i));
            }

            JSONArray ingredientsArray = rootObject.getJSONArray(KEY_INGREDIENTS);
            for (int i = 0; i < ingredientsArray.length(); i++) {

                ingredients.add(ingredientsArray.getString(i));

                Log.d(TAG, "populate ingredients array: " + ingredientsArray.getString(i));
            }

        } catch (Exception e){

            e.printStackTrace();
            Log.d(TAG, "json parsing error: " + e.toString());

        }

        Sandwich sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        sandwich.setMainName(mainName);
        sandwich.setImage(image);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setDescription(description);
        sandwich.setAlsoKnownAs(alsoKnownAs);
        sandwich.setIngredients(ingredients);

        return sandwich;
    }
}

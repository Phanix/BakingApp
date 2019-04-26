package hantaro.com.bakingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {


    public static String convertInputStream(InputStream inputStream){
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try {
            String line = bufferedReader.readLine();
            while(line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static List<Recipe> parserRecipes(String recipesJson){

        List<Recipe> recipes = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(recipesJson);

            for(int k = 0; k < jsonArray.length(); k++) {
                List<Ingredient> ingredients = new ArrayList<>();
                List<Step> steps = new ArrayList<>();
                JSONObject jsonObject1 = jsonArray.getJSONObject(k);
                String name = jsonObject1.getString("name");
                JSONArray jsonIngredients = jsonObject1.getJSONArray("ingredients");
                for (int i = 0; i < jsonIngredients.length(); i++) {
                    JSONObject ingredientJson = jsonIngredients.getJSONObject(i);
                    String quantity = ingredientJson.getString("quantity");
                    String measure = ingredientJson.getString("measure");
                    String ingredientName = ingredientJson.getString("ingredient");
                    ingredients.add(new Ingredient(quantity, measure, ingredientName));
                }
                JSONArray jsonSteps = jsonObject1.getJSONArray("steps");
                for (int i = 0; i < jsonSteps.length(); i++) {
                    JSONObject step = jsonSteps.getJSONObject(i);

                    String id = step.getString("id");
                    String shortDescription = step.getString("shortDescription");
                    String description = step.getString("description");
                    String videoUrl = step.getString("videoURL");
                    String thumbnailUrl = step.getString("thumbnailURL");

                    //Add a new Step to the list of steps
                    steps.add(new Step(shortDescription, description, videoUrl, thumbnailUrl, id));
                }
                String servings = jsonObject1.getString("servings");
                String imageURL = jsonObject1.getString("image");

                //Add the new Recipe to the list of recipes
                recipes.add(new Recipe(name, ingredients, steps, servings, imageURL));

            }

        }catch (JSONException e){
            e.printStackTrace();
        }
        return recipes;
    }

}

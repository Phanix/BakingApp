package hantaro.com.bakingapp;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {

    private String mName;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;
    private String mServings;
    private String mImageURL;

    public Recipe() {
    }

    public Recipe(String name, List<Ingredient> ingredients, List<Step> steps, String servings, String imageURL) {
        mName = name;
        mIngredients = ingredients;
        mSteps = steps;
        mServings = servings;
        mImageURL = imageURL;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public String getImageURL() {
        return mImageURL;
    }
}



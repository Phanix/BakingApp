package hantaro.com.bakingapp;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String mQuantity;
    private String mMeasure;
    private String mIngredient;

    public Ingredient() {
    }

    public Ingredient(String quantity, String measure, String ingredient) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mIngredient = ingredient;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }
}

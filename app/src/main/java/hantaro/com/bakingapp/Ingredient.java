package hantaro.com.bakingapp;

import java.io.Serializable;

public class Ingredient implements Serializable {

    public Ingredient() {
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

    public Ingredient(String quantity, String measure, String ingredient) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mIngredient = ingredient;
    }

    private String mQuantity;
    private String mMeasure;
    private String mIngredient;
}

package hantaro.com.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class IngredientsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_details);
        Intent intent = getIntent();
        Recipe recipe = (Recipe) intent.getSerializableExtra("recipe");
        TextView textView = findViewById(R.id.tv_ingredients);
        List<Ingredient> ingredients = recipe.getIngredients();
        for (Ingredient ingredient : ingredients) {
            textView.append("*" + ingredient.getIngredient() + "\n");
            textView.append(ingredient.getQuantity() + " : " + ingredient.getMeasure() + "\n\n\n");
        }
    }
}

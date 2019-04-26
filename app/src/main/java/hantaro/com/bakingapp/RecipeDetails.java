package hantaro.com.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Recipe recipe = new Recipe();
        final Intent intent = getIntent();
        if(intent.hasExtra("recipe")){
            recipe = (Recipe) intent.getSerializableExtra("recipe");
            setTitle(recipe.getName());
            Log.i("Name", recipe.getName());
        }
        final List<Step> stepList = recipe.getSteps();
        final List<String> stepsName = new ArrayList<>();
        final List<String> ingredients = new ArrayList<>();
        for(Step step : recipe.getSteps()){
            stepsName.add( step.getId() + " " + step.getShortDescription());
        }
        stepsName.remove(0);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stepsName);
        ListView listView = findViewById(R.id.simple_list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(RecipeDetails.this, RecipeStep.class);
                intent1.putExtra("step", stepList.get(i));
                startActivity(intent1);
            }
        });
        listView.setAdapter(adapter);

        final Recipe recipe1 = recipe;


        final TextView ingredientsTV = findViewById(R.id.ingredients_tv);
        ingredientsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call new intent
                Intent intent1 = new Intent(RecipeDetails.this, IngredientsDetails.class);
                intent1.putExtra("recipe", recipe1);
                startActivity(intent1);
            }
        });

    }


}

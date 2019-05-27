package hantaro.com.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeStep extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        Intent intent = getIntent();
        Step step;
        if(intent.hasExtra("recipe")){
            step = (Step) intent.getSerializableExtra("step");
            Intent intent1 = getIntent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("step", step);
            intent1.putExtras(bundle);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}

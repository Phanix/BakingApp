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

        MasterListFragment masterListFragment = new MasterListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);
        masterListFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.master_list_id, masterListFragment).commit();




    }


}

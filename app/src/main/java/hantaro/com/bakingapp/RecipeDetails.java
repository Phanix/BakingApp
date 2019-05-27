package hantaro.com.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RecipeDetails extends AppCompatActivity implements MasterListFragment.OnStepClickListener {
    public static boolean mTwoPane = false;
    Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        final Intent intent = getIntent();
        mRecipe = (Recipe) intent.getSerializableExtra("recipe");
        setTitle(mRecipe.getName());
        if (findViewById(R.id.step_id) != null) {
            mTwoPane = true;
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setStep(mRecipe.getSteps().get(0));
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.step_id, recipeStepFragment).commit();
        }

        Intent intent1 = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", mRecipe);
        bundle.putSerializable("step", mRecipe.getSteps().get(0));
        intent1.putExtras(bundle);
    }

    @Override
    public void onStepSelected(Step step) {
        if (mTwoPane) {
            RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
            recipeStepFragment.setStep(step);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_id, recipeStepFragment).commit();
        } else {
            Intent intent1 = new Intent(this, RecipeStep.class);
            intent1.putExtra("step", step);
            startActivity(intent1);
        }
    }
}

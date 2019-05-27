package hantaro.com.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>>, RecipeAdapter.RecipeClickListener {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    RecyclerView mRecyclerView;
    RecipeAdapter mRecipeAdapter;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIdlingResource();
        LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();
        mRecyclerView = findViewById(R.id.recipe_recycler_view);
        if (findViewById(R.id.tablet_id) != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager layout = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layout);
        }
        mRecyclerView.setHasFixedSize(true);
        mRecipeAdapter = new RecipeAdapter(null, this);
        mRecyclerView.setAdapter(mRecipeAdapter);
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            @Nullable
            @Override
            public List<Recipe> loadInBackground() {
                if (mIdlingResource != null) {
                    mIdlingResource.setIdleState(false);
                }
                List<Recipe> recipes = new ArrayList<>();
                try {

                    URL url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String result = JsonUtils.convertInputStream(inputStream);
                    recipes = JsonUtils.parserRecipes(result);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return recipes;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> recipes) {
        mRecipeAdapter = new RecipeAdapter(recipes, this);
        mRecyclerView.setAdapter(mRecipeAdapter);
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(true);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {

    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetails.class);
        saveIngredientsToTheWidget(recipe.getIngredients(), recipe.getName());
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }

    public void saveIngredientsToTheWidget(List<Ingredient> ingredients, String name) {
        String listIngredients = "";
        listIngredients += "*" + name + "\n\n";
        for (Ingredient ingredient : ingredients) {
            listIngredients += ingredient.getIngredient() + " : " + ingredient.getQuantity() + ingredient.getMeasure() + "\n";

        }
        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.ingredients_key), MODE_PRIVATE).edit();
        editor.putString(getString(R.string.ingredients_key), listIngredients);
        editor.apply();

        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), RecipeWidget.class));
        RecipeWidget myWidget = new RecipeWidget();
        myWidget.onUpdate(this, AppWidgetManager.getInstance(this), ids);
    }
}


package hantaro.com.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Recipe>>, RecipeAdapter.RecipeClickListener {

    RecyclerView mRecyclerView;
    RecipeAdapter mRecipeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();
        mRecyclerView = findViewById(R.id.recipe_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecipeAdapter = new RecipeAdapter(null,this);
        mRecyclerView.setAdapter(mRecipeAdapter);

    }


    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<List<Recipe>>(this) {
            @Nullable
            @Override
            public List<Recipe> loadInBackground() {
                List<Recipe>  recipes = new ArrayList<>();
                try {

                    URL url = new URL("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String result = JsonUtils.convertInputStream(inputStream);
                    recipes =  JsonUtils.parserRecipes(result);
                }catch(MalformedURLException e) {
                    e.printStackTrace();
                }catch (IOException e){                   e.printStackTrace();
                }
                return recipes;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> recipes) {
        for(Recipe recipe : recipes){
            Log.i("Name", recipe.getName());
        }
        mRecipeAdapter = new RecipeAdapter(recipes, this);
        mRecyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {

    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Toast.makeText(this, recipe.getName(),  Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RecipeDetails.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}


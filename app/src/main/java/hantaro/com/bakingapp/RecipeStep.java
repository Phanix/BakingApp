package hantaro.com.bakingapp;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;

public class RecipeStep extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        Intent intent = getIntent();
        Step step = new Step();
        if (intent.hasExtra("step")) {
            step = (Step) intent.getSerializableExtra("step");


        }

        RecipeStepFragment recipeStepFragment = new RecipeStepFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("step", step);
        recipeStepFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.recipe_id, recipeStepFragment).commit();


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

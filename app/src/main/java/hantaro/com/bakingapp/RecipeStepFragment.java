package hantaro.com.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class RecipeStepFragment extends Fragment {
    PlayerView mPlayerView;
    SimpleExoPlayer mSimpleExoPlayer;
    String mediaUrl = "";
    public RecipeStepFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step_fragment, container, false);

        //Do things here
        Bundle bundle = getArguments();
        Step step  = (Step) bundle.getSerializable("step");


            TextView recipeInstruction = view.findViewById(R.id.recipe_instruction);
            recipeInstruction.setText(step.getDescription());
            if (step.getVideoURL().isEmpty() || step.getVideoURL().length() < 1 || step.getVideoURL() == null) {
                mediaUrl = step.getThumbnailUrl();
            } else {
                mediaUrl = step.getVideoURL();
            }


            Log.i("Url", mediaUrl);


        mPlayerView = view.findViewById(R.id.exoplayer_id);




        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        if (mediaUrl.isEmpty() || mediaUrl.length() < 1 || mediaUrl == null) {
            //media player is empty
            mPlayerView.setVisibility(View.GONE);


        } else {

            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            mPlayerView.setPlayer(mSimpleExoPlayer);

            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "ExoPlayer"));

            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mediaUrl));

            mSimpleExoPlayer.prepare(mediaSource);

            mSimpleExoPlayer.setPlayWhenReady(true);

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mSimpleExoPlayer != null) {
            mPlayerView.setPlayer(null);
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }

    }
}

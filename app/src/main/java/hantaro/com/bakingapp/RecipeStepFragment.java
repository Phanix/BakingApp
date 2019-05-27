package hantaro.com.bakingapp;

import android.content.Intent;
import android.mtp.MtpStorageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class RecipeStepFragment extends Fragment {
    PlayerView mPlayerView;
    SimpleExoPlayer mSimpleExoPlayer;
    String mediaUrl = "";
    Step mStep = null;
    ImageView mImageView;
    public final String CURRENT_POSITION_KEY = "current_position";
    public void setStep(Step step) {
        mStep = step;
    }
    public Boolean hasVideo = false;
    long prevCurrentPosition;

    public RecipeStepFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_step_fragment, container, false);
        mImageView = view.findViewById(R.id.image_thumbnail);
        mPlayerView = view.findViewById(R.id.exoplayer_id);
        if (mStep == null) {
            Intent intent = getActivity().getIntent();
            mStep = (Step) intent.getExtras().getSerializable("step");
        }
        TextView recipeInstruction = view.findViewById(R.id.recipe_instruction);
        recipeInstruction.setText(mStep.getDescription());
        //If no Thumbnail and no VideoURL
        //display a default image and hide the ExoPlayerView
        if(TextUtils.isEmpty(mStep.getThumbnailUrl()) && TextUtils.isEmpty(mStep.getVideoURL())){
            mImageView.setImageResource(R.drawable.card_image);
            mPlayerView.setVisibility(View.GONE);
        }
        else if (TextUtils.isEmpty(mStep.getVideoURL())) {
            mediaUrl = mStep.getThumbnailUrl();
            //Check if ThumbnailUrl is video
            if(mediaUrl.contains(".mp4")){
                hasVideo = true;
                mImageView.setVisibility(View.GONE);
            }
            //if ThumbnailUrl is a image, use picasso to display and hide the video
            else{
                Picasso.with(getContext()).load(mediaUrl).into(mImageView);
                mPlayerView.setVisibility(View.GONE);
            }
        } else {
            mediaUrl = mStep.getVideoURL();
            mImageView.setVisibility(View.GONE);
            hasVideo = true;
        }
        if(savedInstanceState != null && savedInstanceState.containsKey(CURRENT_POSITION_KEY)){
            prevCurrentPosition = savedInstanceState.getLong(CURRENT_POSITION_KEY);
        }else{
            prevCurrentPosition = 0;
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button button = getActivity().findViewById(R.id.back_button);
        if (RecipeDetails.mTwoPane) {
            button.setVisibility(View.GONE);
        } else {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
        }

        if(hasVideo) {
            mSimpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
            mPlayerView.setPlayer(mSimpleExoPlayer);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "ExoPlayer"));
            ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mediaUrl));
            mSimpleExoPlayer.prepare(mediaSource);
            mSimpleExoPlayer.seekTo(prevCurrentPosition);
            mSimpleExoPlayer.setPlayWhenReady(true);
        }
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mSimpleExoPlayer != null) {
            mPlayerView.setPlayer(null);
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mSimpleExoPlayer != null) {
            outState.putLong(CURRENT_POSITION_KEY, mSimpleExoPlayer.getCurrentPosition());
        }
    }
}

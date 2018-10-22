package com.example.android.bakingtime.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingtime.R;
import com.example.android.bakingtime.model.CookingStep;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;


public class CookingStepDetailsFragment extends Fragment {

    public static final String TAG = CookingStepDetailsFragment.class.getSimpleName();

    static final String PLAYER_POSITION_TAG = TAG + "PLAYER_POSITION";
    static final String PLAY_WHEN_READY_TAG = TAG + "PLAY_WHEN_READY";

    boolean playWhenReady = true;
    private long last_player_position;

    boolean hasPlayer = true;
    PlayerView mPlayerView;
    ExoPlayer mPlayer;

    Parcelable[] cookingSteps;
    int cookingStepPosition;

    public static CookingStepDetailsFragment newInstance(Parcelable[] steps, int position){
        CookingStepDetailsFragment frag = new CookingStepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(CookingStep.TAG,steps);
        bundle.putInt(CookingStep.POSITION_TAG,position);
        bundle.putLong(PLAYER_POSITION_TAG,0);
        frag.setArguments(bundle);
        return frag;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cooking_step_details, container, false);

        cookingSteps = getArguments().getParcelableArray(CookingStep.TAG);
        cookingStepPosition = getArguments().getInt(CookingStep.POSITION_TAG);

        //If we already have a saved state, then restore the player at the old cookingStepPosition
        //and play the video when as soon it's ready to be played.
        if (savedInstanceState!=null){
            last_player_position = savedInstanceState.getLong(PLAYER_POSITION_TAG);
            playWhenReady= savedInstanceState.getBoolean(PLAY_WHEN_READY_TAG);
        }

        //initialize the bar used to navigate between cookingSteps.
        initNavBar(v);

        mPlayerView = v.findViewById(R.id.exo_PV);


        CookingStep step = (CookingStep) cookingSteps[cookingStepPosition];

        populateDescription((TextView)v.findViewById(R.id.step_description_TV),
                step.getDescription());

        String thumbURL = step.getThumbnailURL();

        //load the thumbnail if the url isn't empty.
        if(!step.getThumbnailURL().isEmpty()) {
            ImageView thumb = v.findViewById(R.id.thumbnail_IV);
            Picasso.get().load(thumbURL).into(thumb);
        }

        return v;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mPlayer!=null){
            playWhenReady = mPlayer.getPlayWhenReady();
            mPlayer.stop();
            last_player_position = mPlayer.getContentPosition();
            mPlayer.release();
            mPlayer=null;
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYER_POSITION_TAG, last_player_position);
        outState.putBoolean(PLAY_WHEN_READY_TAG,playWhenReady);

    }

    @Override
    public void onResume() {
        super.onResume();
        if( hasPlayer && mPlayer==null ) {
            populateMediaPlayer(
                    getContext(), (CookingStep) cookingSteps[cookingStepPosition]);
        }
    }

    void initNavBar(View buttonsContainer) {
        if(cookingStepPosition != cookingSteps.length-1) {
            Button b = buttonsContainer.findViewById(R.id.next_step_button);
            if (b!=null) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToStep(cookingStepPosition + 1);
                    }
                });
                b.setVisibility(View.VISIBLE);
            }
        }

        //If we are not at the first cookingStepPosition,
        //then find the previous button and set its click listener and visibility.
        if(cookingStepPosition !=0) {
            Button b = buttonsContainer.findViewById(R.id.previous_step_button);
            if (b!=null) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToStep(cookingStepPosition - 1);
                    }
                });
                b.setVisibility(View.VISIBLE);
            }
        }



    }

    void populateMediaPlayer( Context context,CookingStep step) {
        String stringUri  = step.getVideoURL();

        if (stringUri.isEmpty()){
            hasPlayer =false;
            return;
        }

        mPlayer = ExoPlayerFactory.newSimpleInstance(context, new DefaultTrackSelector());
        mPlayerView.setPlayer(mPlayer);

        MediaSource source = new ExtractorMediaSource.Factory(new DefaultDataSourceFactory(
                context, Util.getUserAgent(context, "BakingTime")
        )).createMediaSource(Uri.parse(stringUri));

        mPlayer.prepare(source);

        mPlayer.seekTo(last_player_position);

        mPlayer.setPlayWhenReady(playWhenReady);
        mPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        mPlayerView.setVisibility(View.VISIBLE);

    }

    void populateDescription(TextView view,String text){
        if (view!=null){
        view.setText(text);
        }
    }

    void goToStep(int position){
        FragmentActivity activity = getActivity();
        if (activity!=null){
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_cooking_step_details_fragment,
                            CookingStepDetailsFragment.newInstance(cookingSteps,position),
                    CookingStepDetailsFragment.TAG)
                    .commit();
        }
    }
}

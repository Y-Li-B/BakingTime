package com.example.android.bakingtime;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class RecipeStepDetailFragment extends Fragment {


    static final String TAG = RecipeStepDetailFragment.class.getSimpleName();
    static final String PLAYER_POSITION_TAG = TAG + "PLAYER_POSITION";
    static final String PLAY_WHEN_READY_TAG = TAG + "PLAY_WHEN_READY";

    boolean playWhenReady = true;
    private long last_player_position;
    boolean mHasPlayer = true;
    PlayerView mPlayerView;
    ExoPlayer mPlayer;
    Parcelable[] steps;
    int position;

    static RecipeStepDetailFragment newInstance(Parcelable[] steps,int position){
        RecipeStepDetailFragment frag = new RecipeStepDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(RecipeStep.TAG,steps);
        bundle.putInt(RecipeStep.POSITION_TAG,position);
        bundle.putLong(PLAYER_POSITION_TAG,0);
        frag.setArguments(bundle);
        return frag;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);

        if (savedInstanceState!=null){
            last_player_position = savedInstanceState.getLong(PLAYER_POSITION_TAG);
            playWhenReady= savedInstanceState.getBoolean(PLAY_WHEN_READY_TAG);
        }
        init(v);

        mPlayerView = v.findViewById(R.id.exo_PV);

        RecipeStep step = (RecipeStep) steps[position];

        populateDescription((TextView)v.findViewById(R.id.step_description_TV),
                step.getDescription());
        Log.v("VVV","returning view...");
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
        if( mHasPlayer && mPlayer==null ) {
            populateMediaPlayer(
                    getContext(), (RecipeStep) steps[position]);
        }
    }

    void init(View v) {
        Bundle args = getArguments();
        if(args!=null) {
            steps = args.getParcelableArray(RecipeStep.TAG);
            position = args.getInt(RecipeStep.POSITION_TAG);
        }

        if(position!=steps.length-1) {
            Button b = v.findViewById(R.id.next_step_button);
            if (b!=null) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToStep(position + 1);
                    }
                });
                b.setVisibility(View.VISIBLE);
            }
        }

        if(position!=0) {
            Button b = v.findViewById(R.id.previous_step_button);
            if (b!=null) {
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToStep(position - 1);
                    }
                });
                b.setVisibility(View.VISIBLE);
            }
        }



    }

    void populateMediaPlayer( Context context,RecipeStep step) {
        String stringUri  = step.getVideoURL();

        if (stringUri.isEmpty()){
            mHasPlayer=false;
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
                    .replace(R.id.container_recipe_step_details_fragment,
                            RecipeStepDetailFragment.newInstance(steps,position),
                    RecipeStepDetailFragment.TAG)
                    .commit();
        }
    }
}

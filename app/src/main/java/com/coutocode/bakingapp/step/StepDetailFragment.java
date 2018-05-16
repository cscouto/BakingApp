package com.coutocode.bakingapp.step;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coutocode.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    SimpleExoPlayer exoPlayer;
    RecipeStep step;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (savedInstanceState != null){
            step = savedInstanceState.getParcelable(getString(R.string.extra_step));
        }

        initializePlayer(step.videoURL);
        tvDescription.setText(step.description);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(getString(R.string.extra_step), step);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releasePLayer();
    }

    private void initializePlayer(String url){
        if (exoPlayer == null){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            playerView.setPlayer(exoPlayer);
            MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(url),
                   new DefaultDataSourceFactory(getContext(),Util.getUserAgent(getContext(),
                           "BakingApp")),
                    new DefaultExtractorsFactory(), null, null);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(false);
        }
    }

    private void releasePLayer(){
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }
}

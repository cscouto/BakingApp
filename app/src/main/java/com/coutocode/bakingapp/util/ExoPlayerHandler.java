package com.coutocode.bakingapp.util;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.coutocode.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerHandler {
    private static ExoPlayerHandler instance;

    public static ExoPlayerHandler getInstance(){
        if(instance == null){
            instance = new ExoPlayerHandler();
        }
        return instance;
    }
    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;

    private ExoPlayerHandler(){}

    public void prepareExoPlayerForUri(Context context, Uri uri,
                                       SimpleExoPlayerView exoPlayerView){
        if(context != null && uri != null && exoPlayerView != null){
            if(!uri.equals(playerUri) || player == null){
                playerUri = uri;
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();
                player = ExoPlayerFactory.newSimpleInstance(context,
                        trackSelector, loadControl);
                exoPlayerView.setPlayer(player);
                MediaSource mediaSource = new ExtractorMediaSource(uri,
                        new DefaultDataSourceFactory(context, Util.getUserAgent(context,
                                context.getString(R.string.baking_app))),
                        new DefaultExtractorsFactory(), null, null);
                player.prepare(mediaSource);
                player.setPlayWhenReady(false);
            }
            player.clearVideoSurface();
            player.setVideoSurfaceView(
                    (SurfaceView)exoPlayerView.getVideoSurfaceView());
            player.seekTo(player.getCurrentPosition() + 1);
            exoPlayerView.setPlayer(player);
        }
    }

    public void releaseVideoPlayer(){
        if(player != null) {
            player.release();
        }
        player = null;
    }

    public void goToBackground(){
        if(player != null){
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    public void goToForeground(){
        if(player != null){
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }
}

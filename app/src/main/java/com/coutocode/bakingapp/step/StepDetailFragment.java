package com.coutocode.bakingapp.step;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coutocode.bakingapp.R;
import com.coutocode.bakingapp.util.ExoPlayerHandler;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment {

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    RecipeStep step;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        ButterKnife.bind(this, rootView);


        if (savedInstanceState != null){
            step = savedInstanceState.getParcelable(getString(R.string.extra_step));
        }

        tvDescription.setText(step.description);
        if (!step.thumbnailURL.isEmpty()) {
            Picasso.with(getContext())
                    .load(step.thumbnailURL)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            playerView.setDefaultArtwork(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(getString(R.string.extra_step), step);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume(){
        super.onResume();
        if (!step.videoURL.isEmpty()) {
            ExoPlayerHandler.getInstance()
                    .prepareExoPlayerForUri(getContext(),
                            Uri.parse(step.videoURL), playerView);
            ExoPlayerHandler.getInstance().goToForeground();
        }else{
            playerView.setVisibility(View.GONE);
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        ExoPlayerHandler.getInstance().goToBackground();
    }


    public void pressedBack(){
        ExoPlayerHandler.getInstance().releaseVideoPlayer();
    }
}

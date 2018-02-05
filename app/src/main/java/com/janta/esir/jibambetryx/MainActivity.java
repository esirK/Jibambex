package com.janta.esir.jibambetryx;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private SimpleExoPlayer player;
    private ComponentListener componentListener;

    @BindView(R.id.video_view) SimpleExoPlayerView playerView;
    @BindView(R.id.loading_vid) ContentLoadingProgressBar videoLoadingPb;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Since this is a player, ensure that the screen does not go to sleep
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        url = getIntent().getExtras().getString("url");
        playerView = findViewById(R.id.video_view);
        componentListener = new ComponentListener();
        videoLoadingPb = findViewById(R.id.loading_vid);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Util.SDK_INT > 23){
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
        if(Util.SDK_INT <= 23 || player==null){
            initializePlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(Util.SDK_INT <= 23){
            releasePlayer();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT > 23){
            releasePlayer();
        }
    }

    private void initializePlayer(){
        if(player == null){
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(this),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.
                parse(url));
        player.prepare(mediaSource, true, false);
        player.addListener(componentListener);
    }

    private void releasePlayer(){
        if(player != null){
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.removeListener(componentListener);
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri){
        return new ExtractorMediaSource.Factory
                (new DefaultHttpDataSourceFactory("jibambe")).createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private class ComponentListener extends Player.DefaultEventListener {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            super.onPlayerStateChanged(playWhenReady, playbackState);
            switch(playbackState){
                case ExoPlayer.STATE_BUFFERING:
                    videoLoadingPb.show();
                    break;
                case  ExoPlayer.STATE_READY:
                    videoLoadingPb.hide();
                    break;
                case ExoPlayer.STATE_ENDED:
                    finish();
                    break;
                default:
                    break;
            }
        }
    }
}

package com.example.demoplayer;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.demoplayer.Utils.ApplicationContext;
import com.example.demoplayer.Utils.ConversionTimesUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoViewFragment extends Fragment implements View.OnKeyListener{

    private static String TAG="MainActivity";
    //VideoView videoView;
    //MediaController mediaController;
    private SurfaceView surfaceView;
    private MediaPlayer mPlayer;
    private SurfaceHolder holder;
    private ProgressBar loadBar;
    private SeekBar processBar;
    private MediaController mediaController;
    private TextView currentTime;
    private TextView totalTime;
    private LinearLayout linearLayout;
    private ViewUpdateHander handler = new ViewUpdateHander(this);

    private static final int MSG_PROCESS_DISAPPEAR = 0;
    private static final int MSG_UPDATE_TIME = 1;
    private static final int MSG_INIT_VIEW = 3;


    public VideoViewFragment() {
        // Required empty public constructor
    }


    public static VideoViewFragment newInstance() {
        VideoViewFragment fragment = new VideoViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_view, container, false);
        surfaceView = view.findViewById(R.id.surface_view);
        loadBar = view.findViewById(R.id.load_bar);
        processBar = view.findViewById(R.id.process_bar);
        view.findViewById(R.id.linearLayout).setOnKeyListener(this);
        view.findViewById(R.id.linearLayout).requestFocus();
        currentTime = view.findViewById(R.id.current_time);
        totalTime = view.findViewById(R.id.total_time);
        linearLayout = view.findViewById(R.id.process_label);
        linearLayout.setVisibility(View.INVISIBLE);
        initPlayer();
        return view;
    }
    private void initPlayer(){
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(getVideoPath());
            holder = surfaceView.getHolder();
            holder.addCallback(new surfaceCallback());
            mPlayer.prepare();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    loadBar.setVisibility(View.INVISIBLE);
                    //processBar.setVisibility(View.INVISIBLE);
                    mPlayer.start();
                    handler.sendEmptyMessage(MSG_INIT_VIEW);
                    handler.sendEmptyMessageDelayed(MSG_UPDATE_TIME,1000);
                    mPlayer.setLooping(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private String getVideoPath(){
        String path = Environment.getExternalStorageDirectory().getPath()+"/videodemo.mp4";
        return path;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d(TAG,"event.getAction():"+event.getAction());
        Log.d(TAG,"keyCode:"+keyCode);
        if (event.getAction()==KeyEvent.ACTION_DOWN){
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER){
                if (linearLayout.getVisibility()==View.VISIBLE){

                }else{
                    linearLayout.setVisibility(View.VISIBLE);
                    handler.sendEmptyMessageDelayed(VideoViewFragment.MSG_PROCESS_DISAPPEAR,5000);
                }
                return true;
            }
        }
        return false;
    }

    private class surfaceCallback implements SurfaceHolder.Callback{

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mPlayer.stop();
        }
    }

    class ViewUpdateHander extends Handler{
        private WeakReference<Fragment> weakReference;

        private ViewUpdateHander(Fragment fragment){
            super();
            weakReference = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Fragment fragment = weakReference.get();
            if (fragment instanceof VideoViewFragment){
                VideoViewFragment videoFragment = (VideoViewFragment) fragment;
                switch (msg.what){
                    case MSG_PROCESS_DISAPPEAR:
                        videoFragment.linearLayout.setVisibility(View.INVISIBLE);
                        break;
                    case MSG_UPDATE_TIME:
                        Log.d(TAG,"MSG_UPDATE_TIME");
                        String timeStr = (String) videoFragment.currentTime.getText();
                        currentTime.setText(ConversionTimesUtils.CheckTime(timeStr));
                        processBar.setProgress(mPlayer.getCurrentPosition());
                        videoFragment.handler.sendEmptyMessageDelayed(MSG_UPDATE_TIME,1000);
                        break;
                    case MSG_INIT_VIEW:
                        Log.d(TAG,"MSG_INIT_VIEW");
                        processBar.setMax(mPlayer.getDuration());
                        totalTime.setText(ConversionTimesUtils.ConversionTime(mPlayer.getDuration()));
                        currentTime.setText("00:00:00");
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
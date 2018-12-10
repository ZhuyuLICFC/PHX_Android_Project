package com.example.zhuyu.phx_final;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuyu.phx_final.model.Podcast;
import com.example.zhuyu.phx_final.model.PodcastLibrary;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AudioFragment extends Fragment {


    private List<Podcast> mPodcasts;
    private Podcast mPodcast;


    private ViewPager mViewPager;
    private TextView mPlayInfoTextView;
    private TextView mPlayTimeTextView;
    private SeekBar mPlayProgressBar;
    private ImageButton mPlayButton;
    private MediaPlayer mMediaPlayer;

    private int mCurrentPosition;
    private Timer mTimer;
    private boolean mIsSeekBarChanging;

    public static final int UPDATE_UI = 1;
    private static final String TAG = "AudioFragment";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_library_sub_audio, container, false);

        mMediaPlayer = new MediaPlayer();

        mViewPager = v.findViewById(R.id.audio_view_pager);
        mPlayButton = v.findViewById(R.id.audio_play_button);
        mPlayTimeTextView = v.findViewById(R.id.audio_play_time_text_view);
        mPlayInfoTextView = v.findViewById(R.id.audio_play_info_text_view);
        mPlayProgressBar = v.findViewById(R.id.audio_play_progress_progressbar);
        mPlayProgressBar.setProgress(0);

        mPodcasts = PodcastLibrary.get().getPodcasts();

        mPlayProgressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mIsSeekBarChanging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mIsSeekBarChanging = false;
                mMediaPlayer.seekTo(seekBar.getProgress());
            }
        });


        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mMediaPlayer.isPlaying()) {
                    try {
                        mMediaPlayer.start();
                        mPlayButton.setImageDrawable(getResources().getDrawable(R.drawable.audio_pause));
                    } catch (Exception e) {
                        Log.d(TAG, e.toString());
                    }

                } else {
                    mMediaPlayer.pause();
                    mPlayButton.setImageDrawable(getResources().getDrawable(R.drawable.audio_play));
                }
            }
        });
        FragmentManager fragmentManager = getChildFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                mPodcast = mPodcasts.get(i);
                return PodcastFragment.newInstance(mPodcast.getId());
            }

            @Override
            public int getCount() {
                return mPodcasts.size();
            }
        });
        UIhandle.sendEmptyMessage(UPDATE_UI);
        return v;
    }

    @Override
    public void onDestroy() {
        mMediaPlayer.release();
        mMediaPlayer = null;
        super.onDestroy();
    }

    public void play(String mp3URL) {
        try {
            mPlayButton.setImageDrawable(getResources().getDrawable(R.drawable.audio_pause));

            mMediaPlayer.reset();
            mCurrentPosition = 0;

            mMediaPlayer.setDataSource(mp3URL);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.seekTo(mCurrentPosition);
                    mPlayProgressBar.setMax(mMediaPlayer.getDuration());
                    //mPlayTimeTextView.setText(mMediaPlayer.getDuration());
                }
            });

        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),"playError", Toast.LENGTH_SHORT).show();
            Log.d(TAG, e.toString());
        }
    }

    public void updatePlayerInfo(String playInfo) {
        mPlayInfoTextView.setText(playInfo);
    }

    private Handler UIhandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == UPDATE_UI) {

                if (mMediaPlayer != null) {
                    if (mMediaPlayer.isPlaying()) {

                        int position = mMediaPlayer.getCurrentPosition();
                        int totalduration = mMediaPlayer.getDuration();

                        mPlayProgressBar.setMax(totalduration);
                        mPlayProgressBar.setProgress(position);

                        updateTime(position, totalduration);
                    }
                }
                UIhandle.sendEmptyMessageDelayed(UPDATE_UI, 500);
            }

        }
    };

    private void updateTime(int current, int total){
        int secondC = current/1000;
        int mmC = secondC / 60;
        int ssC = secondC % 60;

        int secondT = total/1000;
        int mmT = secondT / 60;
        int ssT = secondT % 60;

        String strC = String.format("%02d:%02d",mmC,ssC);
        String strT = String.format("%02d:%02d",mmT,ssT);

        mPlayTimeTextView.setText(strC + "/" + strT);

    }
}

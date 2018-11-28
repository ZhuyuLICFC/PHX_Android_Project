package com.bignerdranch.android.databaseupdation;


import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PodcastListFragment extends Fragment {

    private RecyclerView mPodcastRecycleView;
    private PodcastAdapter mPodcastAdapter;
    private PodcastLibrary podcastTotal;

    private ImageButton mPodcastPlayImageButton;
    private SeekBar mPodcastPlayProgressSeekBar;
    private boolean mIsSeekBarChanging;
    private Timer mTimer;
    private int mCurrentPosition;
    private boolean mIsPlaying;
    private MediaPlayer mMediaPlayer;

    private BottomNavigationView mBottomNavigationView;
   // private TabLayout mPodcastTabLayout;

    public static final String TAG = "DatabaseUpdation";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_podcast_list, container, false);

        Log.d(TAG, "Enter Fragment");


        mMediaPlayer = new MediaPlayer();

        mPodcastPlayImageButton = view.findViewById(R.id.audio_play_button);
        mPodcastPlayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mMediaPlayer.isPlaying()) {
                    try {
                        mMediaPlayer.start();
                    } catch (Exception e) {
                    }
                    mPodcastPlayImageButton.setImageDrawable(getResources().getDrawable(R.drawable.audio_pause));

                } else {
                    mMediaPlayer.pause();
                    mPodcastPlayImageButton.setImageDrawable(getResources().getDrawable(R.drawable.audio_play));
                }
            }
        });
        mPodcastPlayProgressSeekBar = view.findViewById(R.id.audio_play_seekbar);
        mPodcastPlayProgressSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        mPodcastRecycleView = view.findViewById(R.id.podcast_recycle_view);
        mPodcastRecycleView.setLayoutManager(new LinearLayoutManager((getActivity())));

        mBottomNavigationView = view.findViewById(R.id.main_bottom_navigation);


       /* mPodcastTabLayout = view.findViewById(R.id.podcast_top_tabs);
        mPodcastTabLayout.addTab(mPodcastTabLayout.newTab().setText("Podcast"), true);
        mPodcastTabLayout.addTab(mPodcastTabLayout.newTab().setText("Webinar"));
        mPodcastTabLayout.addTab(mPodcastTabLayout.newTab().setText("Speaking"));*/


        podcastTotal = PodcastLibrary.get(getActivity());

        Log.d(TAG, "Check Fragment OK " + podcastTotal.getPodcasts().size());
        updateUI();

        return view;

    }


    private void play(String mp3URL) {
        try {
            mPodcastPlayImageButton.setImageDrawable(getResources().getDrawable(R.drawable.audio_pause));

            mMediaPlayer.reset();
            mCurrentPosition = 0;

            mMediaPlayer.setDataSource(mp3URL);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepareAsync();

            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.seekTo(mCurrentPosition);
                    mPodcastPlayProgressSeekBar.setMax(mMediaPlayer.getDuration());
                }
            });

            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(!mIsSeekBarChanging){
                        mPodcastPlayProgressSeekBar.setProgress(mMediaPlayer.getCurrentPosition());
                    }
                }
            },0,50);
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(),"playError", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public void onDestroy() {
        mMediaPlayer.release();
        mTimer.cancel();
        mTimer = null;
        mMediaPlayer = null;
        super.onDestroy();
    }

    private void updateUI() {

        List<Podcast> podcasts = podcastTotal.getPodcasts();
        //Log.d(TAG, podcasts.get(10).getDescrip() + "of the view number");

        mPodcastAdapter = new PodcastAdapter(podcasts);
        mPodcastRecycleView.setAdapter(mPodcastAdapter);
    }

    private class PodcastHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPodcastBasicImageView;
        private TextView mPodacastTitleTextView;
        private TextView mPodacastTimeTextView;
        private ImageButton mPodcastDownloadImageButton;
        private ProgressBar mPodcastDownloadProgressBar;
        private Button mPodcastDetailButton;

        private Podcast mPodcast;

        public PodcastHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPodcastBasicImageView = itemView.findViewById(R.id.list_item_poadcast_image_view);
            mPodacastTitleTextView = itemView.findViewById(R.id.list_item_podcast_title_text_view);
            mPodacastTimeTextView = itemView.findViewById(R.id.list_item_podcast_time_text_view);
            mPodcastDownloadImageButton = itemView.findViewById(R.id.list_item_podcast_download_image_button);
            mPodcastDownloadImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mPodcast.getDownloaded()) {
                        mPodcast.setDownloaded(false);
                        mPodcastDownloadImageButton.setImageDrawable(getResources().getDrawable(R.drawable.download_done_button));
                    } else {
                        mPodcast.setDownloaded(true);
                        mPodcastDownloadImageButton.setImageDrawable(getResources().getDrawable(R.drawable.delete_ok_button));
                    }

                }
            });
            mPodcastDownloadProgressBar = itemView.findViewById(R.id.list_item_podcast_download_progress_bar);
            mPodcastDetailButton = itemView.findViewById(R.id.list_item_podcast_detail_button);
            mPodcastDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setTitle("Description").setMessage(mPodcast.getDescrip()).create();
                    alertDialog.show();
                    try {
                        Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                        mAlert.setAccessible(true);
                        Object mAlertController = mAlert.get(alertDialog);
                        Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                        mMessage.setAccessible(true);
                        TextView mMessageView = (TextView) mMessage.get(mAlertController);
                        mMessageView.setTextColor(Color.BLACK);

                        Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                        mTitle.setAccessible(true);
                        TextView mTitleView = (TextView) mTitle.get(mAlertController);
                        mTitleView.setTextColor(Color.RED);
                        mTitleView.setTextSize(20);
                        TextPaint tp = mTitleView.getPaint();
                        tp.setFakeBoldText(true);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        public void bindPodcast(Podcast podcast) {
            mPodcast = podcast;
            mPodacastTitleTextView.setText(podcast.getTitle());
            mPodacastTimeTextView.setText(podcast.getTime());
            if (podcast.getDownloaded()) {
                mPodcastDownloadImageButton.setImageDrawable(getResources().getDrawable(R.drawable.delete_ok_button));
            } else {
                mPodcastDownloadImageButton.setImageDrawable(getResources().getDrawable(R.drawable.download_done_button));
            }
            mPodcastBasicImageView.setImageDrawable(getResources().getDrawable(R.drawable.download_done_button));
            mPodcastBasicImageView.setImageDrawable(podcast.mImgShow);

        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(),
                    mPodcast.getMp3Url(), Toast.LENGTH_SHORT)
                    .show();
            play(mPodcast.getMp3Url());
        }


    }

    private class PodcastAdapter extends RecyclerView.Adapter<PodcastHolder> {

        private List<Podcast> mPodcasts;

        public PodcastAdapter(List<Podcast> podcasts) {
            mPodcasts = podcasts;
            Log.d(TAG, "final number check" + podcasts.get(20).getId());
        }

        @Override
        public PodcastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_podcast, parent, false);
            return new PodcastHolder(view);
        }

        @Override
        public void onBindViewHolder(PodcastHolder holder, int position) {
            Log.d(TAG, position + "");
            Podcast podcast = mPodcasts.get((position));
            holder.bindPodcast(podcast);
        }

        @Override
        public int getItemCount() {
            return mPodcasts.size();
        }
    }


}

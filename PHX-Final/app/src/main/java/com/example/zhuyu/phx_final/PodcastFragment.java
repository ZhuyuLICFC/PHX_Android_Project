package com.example.zhuyu.phx_final;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhuyu.phx_final.model.Podcast;
import com.example.zhuyu.phx_final.model.PodcastLibrary;

import org.w3c.dom.Text;

public class PodcastFragment extends Fragment {

    private ImageView mBackImageView;
    private TextView mTitleTextView;
    private TextView mTimeTextView;
    private TextView mDescripTextView;
    private ImageButton mPlayImageButton;
    private Podcast mPodcast;


    private static final String ARG_CRIME_ID = "pod_id";

    public static PodcastFragment newInstance(String podId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, podId);

        PodcastFragment fragment = new PodcastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String podId = (String) getArguments().getSerializable(ARG_CRIME_ID);
        mPodcast = PodcastLibrary.get().getPodcast(podId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_library_sub_audio_sub_podcast, container, false);

        mBackImageView = v.findViewById(R.id.podcast_back_image_view);
        mBackImageView.setImageDrawable(mPodcast.getImgShow());
        mTitleTextView = v.findViewById(R.id.podcast_title_text_view);
        mTitleTextView.setText(mPodcast.getTitle());
        mTimeTextView = v.findViewById(R.id.podcast_time_text_view);
        mTimeTextView.setText(mPodcast.getTime());
        mDescripTextView = v.findViewById(R.id.podcast_desc_text_view);
        mDescripTextView.setText(mPodcast.getDescrip());

        mPlayImageButton = v.findViewById(R.id.podcast_play_image_button);
        mPlayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AudioFragment)getParentFragment()).updatePlayerInfo(mPodcast.getTitle());
                ((AudioFragment)getParentFragment()).play(mPodcast.getMp3Url());
            }
        });
        return v;
    }
}

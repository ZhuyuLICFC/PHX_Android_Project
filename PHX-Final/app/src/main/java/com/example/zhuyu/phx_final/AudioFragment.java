package com.example.zhuyu.phx_final;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AudioFragment extends Fragment {

    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sub_library_sub_audio, container, false);
        mViewPager = v.findViewById(R.id.audio_view_pager);
        FragmentManager fragmentManager = getChildFragmentManager();
        final Fragment[] fragments = new Fragment[2];
        fragments[0] = new PodcastFragment();
        fragments[1] = new PodcastFragment();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return fragments[i];
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        return v;
    }
}

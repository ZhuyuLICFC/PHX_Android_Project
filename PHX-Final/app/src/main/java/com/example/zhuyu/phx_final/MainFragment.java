package com.example.zhuyu.phx_final;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener{

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewPager;
    private static final String TAG = "MainFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.main_main, container, false);
        mBottomNavigationView = v.findViewById(R.id.main_bottom_navigation_view);
        mViewPager = v.findViewById(R.id.main_view_pager);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        mViewPager.addOnPageChangeListener(this);
        FragmentManager fragmentManager = getChildFragmentManager();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(new EventFragment(), new LibraryFragment(), new DownloadFragment(), fragmentManager);
        mViewPager.setAdapter(viewPagerAdapter);

        return v;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.item_event:
                clickEvent();
                return true;
            case R.id.item_library:
                clickLibrary();
                return true;
            case R.id.item_download:
                clickDownload();
                return true;
            default:
                    break;
        }
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "selected page" + position);
        switch (position) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.item_event);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.item_library);
                break;
            case 2:
                mBottomNavigationView.setSelectedItemId(R.id.item_download);
                break;

            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    public void clickEvent() {
        mViewPager.setCurrentItem(0, false);
    }

    public void clickLibrary() {
        mViewPager.setCurrentItem(1, false);
    }

    public void clickDownload() {
        mViewPager.setCurrentItem(2, false);
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        private Fragment[] mFragments = new Fragment[4];

        public ViewPagerAdapter(Fragment f1, Fragment f2, Fragment f3, FragmentManager fm) {
            super(fm);
            mFragments[0] = f1;
            mFragments[1] = f2;
            mFragments[2] = f3;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return 3;
        }


    }
}

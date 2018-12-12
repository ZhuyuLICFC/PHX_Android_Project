package com.example.zhuyu.phx_final;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import com.example.zhuyu.phx_final.model.Podcast;
import com.example.zhuyu.phx_final.model.PodcastLibrary;
import com.example.zhuyu.phx_final.model.UserInfo;
import com.example.zhuyu.phx_final.utils.FileOperationUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class MainFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener, NavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView mBottomNavigationView;
    private NavigationView mSlideNavigationView;
    private View mHeaderView;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    private TextView mUserNameTextView;
    private TextView mUserEmailTextView;
    private UserInfo mUserInfo;
    private PodcastLibrary podcastsTotal;
    private ProgressDialog mProgressDialog;
    private View globalV;

    private String URLToAnalyse = "https://populationhealthexchange.org/library/podcasts/free-associations/";
    //private String URLToAnalyse = "https://populationhealthexchange.org/library/practically-speaking/";
    private String URLToAnalysePS = "https://populationhealthexchange.org/library/practically-speaking/";
    private String URLToAnalyseW = "https://populationhealthexchange.org/learning-opportunities/webinar/";
    private static final String TAG = "MainFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInfo();
        mUserInfo = UserInfo.sUserInfo;
        podcastsTotal = PodcastLibrary.get();

        mProgressDialog = ProgressDialog.show(getActivity(), "Please waiting", "Loading");
        ConnectPHX getPodcastNum = new ConnectPHX();
        getPodcastNum.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        globalV = inflater.inflate(R.layout.main_main, container, false);

        return globalV;
    }

    private void initView(View v) {
        mBottomNavigationView = v.findViewById(R.id.main_bottom_navigation_view);
        mSlideNavigationView = v.findViewById(R.id.mine_navi_view);
        mViewPager = v.findViewById(R.id.main_view_pager);
        mDrawerLayout = v.findViewById(R.id.mine_drawer_layout);
        mHeaderView = mSlideNavigationView.getHeaderView(0);
        mUserNameTextView = mHeaderView.findViewById(R.id.navi_header_user_name_text_view);
        mUserEmailTextView = mHeaderView.findViewById(R.id.navi_header_user_email_text_view);

        mBottomNavigationView.setOnNavigationItemSelectedListener(this);
        mSlideNavigationView.setNavigationItemSelectedListener(this);
        mUserNameTextView.setText(mUserInfo.getName());
        mUserEmailTextView.setText(mUserInfo.getEmail());

        mViewPager.addOnPageChangeListener(this);
        FragmentManager fragmentManager = getChildFragmentManager();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(new EventFragment(), new LibraryFragment(), new DownloadFragment(), fragmentManager);
        mViewPager.setAdapter(viewPagerAdapter);
    }

    private void initInfo() {
        String[] userInfo = FileOperationUtils.readFromFile(FileOperationUtils.getUserInfoFilePath()).split("/");
        UserInfo.setUserInfo(new UserInfo(userInfo[0], userInfo[1]));
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
            case R.id.item_mine:
                if (mDrawerLayout.isDrawerOpen(GravityCompat.END)) {
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.END);
                }
                return true;
            case R.id.slide_navi_feedback:
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
                mDrawerLayout.closeDrawer(GravityCompat.END);
                return true;
            default:
                    break;
        }
        return true;
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

        private ViewPagerAdapter(Fragment f1, Fragment f2, Fragment f3, FragmentManager fm) {
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


    public class ConnectPHX extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            Log.d(TAG, "Enter thread");
            int listNum = 0;

            try {
                Document doc = Jsoup.parse(new URL(URLToAnalyse), 5000);
                //finalLink = doc.select("p.powerpress_links").select("a[href]").get(0).attr("href");
                Elements podcastList = doc.select("div.related_block").get(1).select("div.related_items").select("article.related_item");
                listNum = podcastList.size();
                Log.d(TAG, "Total number of Podcasts is " + listNum);
                if (listNum != podcastsTotal.getPodcasts().size()) {
                    Log.d(TAG, "not equal " + podcastsTotal.getPodcasts().size());
                    updateNewPodcasts(podcastList, (listNum - podcastsTotal.getPodcasts().size()));
                }



            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }

            Log.d(TAG, "Data finished");
            return listNum + "";
        }

        @Override
        protected void onPostExecute(String finalLink) {

            //podcastsTotal.closeDatabase();
            Log.d(TAG, "closed");
            Log.d(TAG, "TestOK " + podcastsTotal.getPodcasts().size());

            mProgressDialog.dismiss();
            initView(globalV);

        }

        private void updateNewPodcasts(Elements podcastList, int numToAdd){


            Log.d(TAG, "num to add" + numToAdd);

            for (int i = 0; i < numToAdd; i++) {

                /*if (i < 5) {
                    continue;
                }*/

                String getSubPodcastLink = podcastList.get(i).select("a.related_thumbnail_link").attr("href");
                String mID;
                if (getSubPodcastLink.substring(getSubPodcastLink.length()-3, getSubPodcastLink.length()-1).equals("-5")) {
                    mID = getSubPodcastLink.substring(getSubPodcastLink.length()-5, getSubPodcastLink.length()-3) + "-5";
                } else {
                    mID = getSubPodcastLink.substring(getSubPodcastLink.length()-3, getSubPodcastLink.length()-1);
                }
                Log.d(TAG, "for " + mID);
                String mTitle = podcastList.get(i).select("a.related_heading_link").get(0).text();
                String mTime = podcastList.get(i).select("div.related_label").get(0).text();
                String mDescrip = podcastList.get(i).select("div.related_description").get(0).text();
                String mImgUrl = podcastList.get(i).select("img.related_image").attr("src");
                String mMP3UrlPre = "https://populationhealthexchange.org/wp-content/podcasts/fa/Free_Associations_Episode_";
                String mMP3Url = mMP3UrlPre + mID + ".mp3";

                Drawable imgShow = LoadImageFromWeb(mImgUrl);

                //podcastsTotal.addPodcast(new Podcast(mID, mTitle, mTime, mDescrip, mImgUrl, mMP3Url, false));
                podcastsTotal.addPodcast(new Podcast(mID, mTitle, mTime, mDescrip, mImgUrl, mMP3Url, false, imgShow));

            }


        }

        private Drawable LoadImageFromWeb(String url) {
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e){
                Log.d(TAG, e.toString());
                return null;
            }
        }

    }
}

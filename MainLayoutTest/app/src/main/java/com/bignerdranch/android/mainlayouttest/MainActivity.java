package com.bignerdranch.android.mainlayouttest;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private String URLToAnalyse = "https://populationhealthexchange.org/library/podcasts/free-associations/";

    private ListView mPodcastListView;
    private List<Map<String, Object>> mPoscastListInfo = new ArrayList<Map<String, Object>>();
    private ImageView mPodcastImage;
    private TextView mPodcastTimeText;
    private TextView mPodcastTitleText;
    private BottomNavigationView mBottomNavigationView;


    ArrayList<String> mPodcastTimeList = new ArrayList<>();
    ArrayList<String> mPodcastTitleList = new ArrayList<>();
    ArrayList<String> mPodcastURLList = new ArrayList<>();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
                case R.id.bottom_navigation_event:
                    //mBottomNavigationView.setBackgroundColor(Color.rgb(0,0,0));
                    return true;
                case R.id.bottom_navigation_library:
                    //mBottomNavigationView.setBackgroundColor(Color(design_default_color_primary_dark));
                    return true;
                case R.id.bottom_navigation_download:
                    return true;
                case R.id.bottom_navigation_mine:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        View testView = View.inflate(this, R.layout.podcast_list, null);
        mPodcastTimeText = testView.findViewById(R.id.podcast_time);
        mPodcastTitleText = testView.findViewById(R.id.podcast_title);
        mPodcastTitleText.setTextColor(Color.rgb(255,0,0));
        mPodcastTitleText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mPodcastImage = testView.findViewById(R.id.podcast_img);


        mPodcastListView = findViewById(R.id.podcast_list_view);



        ConnectPHX showList = new ConnectPHX();
        showList.execute();



        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);




    }

    public class ConnectPHX extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.d(TAG, "Start");
                Document doc = Jsoup.parse(new URL(URLToAnalyse), 5000);
                Log.d(TAG, "Doc finished");
                //finalLink = doc.select("p.powerpress_links").select("a[href]").get(0).attr("href");
                Elements podcastList = doc.select("div.related_block").get(1).select("div.related_items").select("article.related_item");
                Log.d(TAG, podcastList.size() + "");
                for (Element element:podcastList) {
                    mPodcastTimeList.add(element.select("div.related_label").get(0).text());
                    mPodcastTitleList.add(element.select("a.related_heading_link").get(0).text());
                    mPodcastURLList.add(element.select("picture.related_picture").select("img[src]").attr("src"));
                   // Log.d(TAG, mPodcastTimeList.get(0) + "\n" + mPodcastTitleList.get(0) + "\n" + mPodcastURLList.get(0));
                }
            } catch (Exception e) {
                Log.d(TAG, "Failed");
            }

            for (int i = 0; i < mPodcastURLList.size(); i++) {
                Map<String, Object> subMap = new HashMap<String, Object>();
                subMap.put("podImg", LoadImageFromWebOperations(mPodcastURLList.get(i)));
                subMap.put("podTime", mPodcastTimeList.get(i));
                subMap.put("podTitle", mPodcastTitleList.get(i));
                mPoscastListInfo.add(subMap);
            }
            if(mPoscastListInfo.get(0).get("img") instanceof Drawable) {
                Log.d(TAG, "first pass");
            }
            Log.d(TAG,"OOkkkk");
            return "OK";

        }

        @Override
        protected void onPostExecute(String finalLink) {


            SimpleAdapter mSubPodcast = new SimpleAdapter(MainActivity.this, mPoscastListInfo, R.layout.podcast_list, new String[] {"podImg", "podTime", "podTitle"}, new int[] {R.id.podcast_img, R.id.podcast_time, R.id.podcast_title});
            mSubPodcast.setViewBinder(new SimpleAdapter.ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data, String textRepresentation) {

                    if (view instanceof View) {
                    }

                    if (data instanceof Drawable) {
                    }

                    if (view instanceof View && data instanceof Drawable) {
                        ImageView iv = (ImageView) view;
                        iv.setImageDrawable((Drawable)data);
                        return true;
                    }
                    return false;
                }
            });

            mPodcastListView.setAdapter(mSubPodcast);
        }
    }

    private Drawable LoadImageFromWebOperations(String url)
    {
        try
        {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        }catch (Exception e) {
            Log.d(TAG, "Exc="+e);
            return null;
        }
    }

}

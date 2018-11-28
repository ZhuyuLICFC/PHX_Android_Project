package com.bignerdranch.android.databaseupdation;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;

public class DatabaseUpdationActivity extends FragmentActivity {

    public static final String TAG = "DatabaseUpdation";
    private String URLToAnalyse = "https://populationhealthexchange.org/library/podcasts/free-associations/";
    private PodcastLibrary podcastsTotal;
    private int updateLabel = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        podcastsTotal = PodcastLibrary.get(DatabaseUpdationActivity.this);

        ConnectPHX getPodcastNum = new ConnectPHX();
        getPodcastNum.execute();

       // Log.d(TAG, "Stop Waiting");

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


            FragmentManager fm = getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(R.id.fragment_container);

            if (fragment == null) {
                fragment = new PodcastListFragment();
                fm.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit();
            }

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

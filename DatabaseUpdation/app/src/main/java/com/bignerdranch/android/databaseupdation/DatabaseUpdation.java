package com.bignerdranch.android.databaseupdation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;

public class DatabaseUpdation extends AppCompatActivity {

    public static final String TAG = "DatabaseUpdation";
    private String URLToAnalyse = "https://populationhealthexchange.org/library/podcasts/free-associations/";
    private TextView showNumList;
    private PodcastLibrary podcastsTotal;
    private String letsURL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_updation);

        showNumList = findViewById(R.id.episoda_text_view);

        podcastsTotal = PodcastLibrary.get(DatabaseUpdation.this);

        ConnectPHX getPodcastNum = new ConnectPHX();
        getPodcastNum.execute();


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

            return listNum + "";
        }

        @Override
        protected void onPostExecute(String finalLink) {

            //podcastsTotal.closeDatabase();
            Log.d(TAG, "closed");
            showNumList.setText(letsURL);

        }

        private void updateNewPodcasts(Elements podcastList, int numToAdd){


            Log.d(TAG, "num to add" + numToAdd);

            for (int i = 0; i < numToAdd; i++) {

                if (i < 5) {
                    continue;
                }

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
                letsURL += mMP3Url + "/n";

                podcastsTotal.addPodcast(new Podcast(mID, mTitle, mTime, mDescrip, mImgUrl, mMP3Url, false));

            }

        }

    }
}

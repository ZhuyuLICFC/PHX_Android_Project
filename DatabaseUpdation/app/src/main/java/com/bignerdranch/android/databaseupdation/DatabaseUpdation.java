package com.bignerdranch.android.databaseupdation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;

public class DatabaseUpdation extends AppCompatActivity {

    public static final String TAG = "DatabaseUpdation";
    private String URLToAnalyse = "https://populationhealthexchange.org/library/podcasts/free-associations/";
    private TextView showNumList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_updation);

        showNumList = findViewById(R.id.episoda_text_view);

        ConnectPHX getPodcastNum = new ConnectPHX();
        getPodcastNum.execute();


    }


    public class ConnectPHX extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            int listNum = 0;

            try {
                Document doc = Jsoup.parse(new URL(URLToAnalyse), 5000);
                //finalLink = doc.select("p.powerpress_links").select("a[href]").get(0).attr("href");
                Elements podcastList = doc.select("div.related_block").get(1).select("div.related_items").select("article.related_item");
                listNum = podcastList.size();

                for (Element element: podcastList) {
                    String getString = element.select("a.related_thumbnail_link").attr("href");
                    String thisID = "NULL";
                    //Log.d(TAG, getString.substring(getString.length()-1, getString.length()));
                    //Log.d(TAG, ((getString.substring(getString.length()-1, getString.length()-1)).equals("-5")) + "ok")
                    //Log.d(TAG, "FUCK");
                    if (getString.substring(getString.length()-3, getString.length()-1).equals("-5")) {
                        thisID = getString.substring(getString.length()-5, getString.length()-3) + "05";
                    } else {
                        thisID = getString.substring(getString.length()-3, getString.length()-1) + "00";
                    }

                    Log.d(TAG, thisID);
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }

            return listNum + "";
        }

        @Override
        protected void onPostExecute(String finalLink) {

            showNumList.setText(finalLink);

        }

    }
}

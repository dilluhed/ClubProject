package com.example.clubproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class clubPage extends AppCompatActivity {
    String clubURL;
    TextView clubNameView;
    TextView recentPostDesc;
    TextView activityDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

        Intent openClubPage = getIntent();
        clubURL = openClubPage.getStringExtra("URL");

        clubNameView = findViewById(R.id.clubName);
        recentPostDesc = findViewById(R.id.recentPost);
        activityDate = findViewById(R.id.activityDate);

        new searchWeb("hhrv", clubURL).execute();
    }

    public class searchWeb extends AsyncTask<Void, Void, Void> {

        Document doc;
        public String directive = "";
        public String URL = "https://searchusers.com";
        ArrayList<Element> targets;

        String clubNameText = "";

        public searchWeb(String methodDirective, String methodURL)
        {
            directive = methodDirective;
            URL = methodURL;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try
            {
                doc = Jsoup.connect(URL).get();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            targets  = new ArrayList<>();
            Elements elements = doc.getElementsByClass(directive);

            for (Element element : elements)
            {
                targets.add(element);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(directive == "hhrv")
            {
                clubNameView.setText(targets.get(0).text());
                new searchWeb("showpics", clubURL).execute();
            }
            if(directive == "showpics")
            {
                activityDate.setText("Last Active: " + targets.get(1).attributes().get("alt"));
                String postLink = targets.get(0).attributes().get("href");
                new searchWeb("sb", postLink).execute();
            }
            if(directive == "sb")
            {
                recentPostDesc.setText(targets.get(0).text());
            }
        }

    }
}

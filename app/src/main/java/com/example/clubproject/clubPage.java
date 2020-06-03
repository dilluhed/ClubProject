package com.example.clubproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class clubPage extends AppCompatActivity//club page for user. Displays club name, recent post, and last date of recent activity
{
    String clubURL;
    TextView clubNameView;
    TextView recentPostDesc;
    TextView activityDate;

    Button saveButton;
    boolean willRemove;

    String clubName;

    user currentUser = MainActivity.currentUser;

    ArrayList<ClubSave> userClubs;
    ClubSave thisClub;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";

    @Override
    protected void onCreate(Bundle savedInstanceState) //runs when clubPage is started
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

        Intent openClubPage = getIntent(); //gets the intent from MainActivity
        clubURL = openClubPage.getStringExtra("URL");//gets the club page url under the key 'URL'

        clubNameView = findViewById(R.id.clubName);//The club name
        recentPostDesc = findViewById(R.id.recentPost);//the description of their recent post
        activityDate = findViewById(R.id.activityDate);//last activity date
        saveButton = findViewById(R.id.saveButton);

        saveButton.setVisibility(View.INVISIBLE);

        currentUser = MainActivity.currentUser;


        new searchWeb("hhrv", clubURL).execute(); //starts a new web search for any element under class 'hhrv' with the URL from MainActivity intent
    }//end method onCreate

    private void setUpSaveFunctionality() {
        thisClub = new ClubSave(clubName, clubURL);
        userClubs = currentUser.getUsersClubs();

        if(userClubs.contains(thisClub)) {
            //currentUser.removeClub(thisClub);
            saveButton.setText("remove club");
            willRemove = true;
        }
        else
        {
            //currentUser.addClub(thisClub);
            saveButton.setText("save club");
            willRemove = false;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClub();
            }
        });
    }

    private void saveClub()
    {
        if(willRemove == false) {
            currentUser.addClub(thisClub);
            saveButton.setText("remove club");
            willRemove = true;
        }
        else
        {
            currentUser.removeClub(thisClub);
            saveButton.setText("save club");
            willRemove = false;
        }
        doSaveData();
    }

    private void doSaveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(currentUser.getUsersClubs());
        editor.putString("task list", json);
        editor.apply();
    }

    public class searchWeb extends AsyncTask<Void, Void, Void> //web scraper
    {

        Document doc;//web page source code is put in here
        public String directive = "";//what scraper is searching for
        public String URL = "https://searchusers.com";//where scraper is searching
        ArrayList<Element> targets;//elements that we need

        public searchWeb(String methodDirective, String methodURL)
        {
            directive = methodDirective;
            URL = methodURL;
        }//end constructor

        @Override
        protected Void doInBackground(Void... voids)
        {

            try
            {
                doc = Jsoup.connect(URL).get();//connect to url; puts source code in the doc
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            targets  = new ArrayList<>(); //initializes new arraylist
            Elements elements = doc.getElementsByClass(directive);//List of elements of object Elements with the class of directive

            for (Element element : elements)//converting Elements into ArrayList
            {
                targets.add(element);
            }
            return null;
        }//end method doInBackground

        @Override
        protected void onPostExecute(Void aVoid) //This runs after doInBackground is finished
        {
            super.onPostExecute(aVoid);
            if(directive == "hhrv")//if scraper is searching for elements under class 'hhrv' --
            {
                clubName = targets.get(0).text();
                clubNameView.setText(clubName); //set the club name to the first element found
                new searchWeb("showpics", clubURL).execute(); //start another web search for class 'showpics' under clubURL
            }
            if(directive == "showpics")//if scraper is searching for elements under class 'showpics' --
            {
                activityDate.setText("Last Active: " + targets.get(1).attributes().get("alt"));//set the activityDate to the second element's attribute, 'alt'
                String postLink = targets.get(0).attributes().get("href");//gets the link to the recent post
                new searchWeb("sb", postLink).execute();//start another web search for class 'sb' under recent post link
            }
            if(directive == "sb")//if scraper is searching for elements under class 'sb' --
            {
                recentPostDesc.setText(targets.get(0).text());//set recentPostDesc to first element found
                saveButton.setVisibility(View.VISIBLE);
                setUpSaveFunctionality();
            }
        }//end method onPostExecute
    }//end class searchPage
}//end class clubPage

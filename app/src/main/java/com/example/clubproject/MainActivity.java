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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button search;
    LinearLayout layout;
    TableLayout table;
    //LinearLayout clubPage;

    TextView clubNameView;

    ArrayList<Element> clubs;

    public String directive = "SEARCH_CLUBS";
    public String URL = "https://searchusers.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text = findViewById(R.id.textView);
        search = findViewById(R.id.search);
        layout = findViewById(R.id.mainLayout);
        table = findViewById(R.id.table);

        //clubPage = findViewById(R.id.clubpageLayout);

        clubNameView = findViewById(R.id.clubName);
        /*
        result1 = findViewById(R.id.result1);
        result2 = findViewById(R.id.result2);
        result3 = findViewById(R.id.result3);
        result4 = findViewById(R.id.result4);
        result5 = findViewById(R.id.result5);
        result6 = findViewById(R.id.result6);
        */
        //Button testButton = new Button(this);
        //RelativeLayout myLayout = new RelativeLayout(this);
        //layout.addView(testButton);
        //setContentView(layout);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new searchWeb("SEARCH_CLUBS","https://searchusers.com/search/valley+view+high+school").execute();
            }
        });


    }
//change
    public class searchWeb extends AsyncTask<Void, Void, Void> {

    Document doc;


    String clubNameText = "";

    public searchWeb(String methodDirective, String methodURL)
    {
        directive = methodDirective;
        URL = methodURL;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {

            doc = Jsoup.connect(URL).get();

            //words = doc.text();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(directive == "SEARCH_CLUBS")
        {
            clubs  = new ArrayList<>();
            Elements elements = doc.getElementsByClass("timg");

            for (Element element : elements) {
                clubs.add(element);
            }
        }
        else if(directive == "POST_CLUB")
        {
            clubs  = new ArrayList<>();
            Elements elements = doc.getElementsByClass("hhrv");

            for (Element element : elements) {
                clubNameText = element.text();
            }

        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        //text.setText(words);
        if(directive == "SEARCH_CLUBS")
        {
            for (int i = 0; i < clubs.size(); i++) {
                createButton(clubs.get(i).text(), clubs.get(i).attributes().get("href"));
            }
        }
        else if(directive == "POST_CLUB")
        {
           clubNameView.getAutofillType();

        }
    }

    private Button createButton(String text, final String clubURL) {
        Button clubButton = new Button(getApplicationContext());
        //button.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
        clubButton.setText(text);
        //clubButton.setLayoutParams(new;
        clubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                prepareClubPage(clubURL);
            }
        });
        table.addView(clubButton);
        return clubButton;
    }

    public void prepareClubPage(String clubURL)
    {
        //new searchWeb("POST_CLUB", clubURL).execute();
        //setContentView(R.layout.club_page);
        Intent sendtoClubPage = new Intent(MainActivity.this,clubPage.class);
        sendtoClubPage.putExtra("URL", clubURL);
        startActivity(sendtoClubPage);
    }

}


}

package com.example.clubproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
    ScrollView scroll;
    //LinearLayout clubPage;

    TextView clubNameView;
    EditText schoolInput;

    final String BASE_URL = "https://searchusers.com/search/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //text = findViewById(R.id.textView);
        search = findViewById(R.id.search);
        layout = findViewById(R.id.mainLayout);
        scroll = findViewById(R.id.scroll);

        //clubPage = findViewById(R.id.clubpageLayout);

        clubNameView = findViewById(R.id.clubName);
        schoolInput = findViewById(R.id.schoolInput);
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

        table = findViewById(R.id.table);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = schoolInput.getText().toString();

                inputText.replace(" ", "+");

                scroll.removeView(table);
                table = new TableLayout(getApplicationContext());
                TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                );
                table.setLayoutParams(params);
                scroll.addView(table);

                new searchWeb("timg",BASE_URL + inputText + "+high+school").execute();
            }
        });


    }
//change
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
        for (int i = 0; i < targets.size(); i++) {
            createButton(targets.get(i).text(), targets.get(i).attributes().get("href"));
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

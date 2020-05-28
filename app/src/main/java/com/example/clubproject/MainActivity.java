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

public class MainActivity extends AppCompatActivity //Main search page
{
    Button search;
    LinearLayout layout;
    TableLayout table;
    ScrollView scroll;
    Button userButton;

    EditText schoolInput;

    final String BASE_URL = "https://searchusers.com/search/"; //website that app is scraping through

    public static user currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) //runs when MainActivity has started
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.search); //search button
        layout = findViewById(R.id.mainLayout); //main layout of the activity
        scroll = findViewById(R.id.scroll); //the scrollView that table are placed on
        userButton = findViewById(R.id.userButton);

        schoolInput = findViewById(R.id.schoolInput); //user inputs school name here

        table = findViewById(R.id.table); //the table view that is placed under scroll; buttons are put HERE

        currentUser = new user();

        search.setOnClickListener(new View.OnClickListener()  //sets method to fire when search is clicked

        {
            @Override
            public void onClick(View v) //method fired when search button is clicked
            {
                String inputText = schoolInput.getText().toString();

                inputText.replace(" ", "+"); //converts user input to url format

                scroll.removeView(table); //clears current results
                table = new TableLayout(getApplicationContext());
                TableLayout.LayoutParams params = new TableLayout.LayoutParams
                        (
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                );
                table.setLayoutParams(params);
                scroll.addView(table); //adds new table for new results

                new searchWeb("timg",BASE_URL + inputText + "+high+school").execute(); //starts a searchWeb object; search for class 'timg', under the url
            }//end method onClick
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendtoUserPage = new Intent(MainActivity.this,UserPage.class); //new intent to switch activities
                startActivity(sendtoUserPage);//begins clubPage.java activity
            }
        });


    }//end method onCreate
//change
    public class searchWeb extends AsyncTask<Void, Void, Void> //web scraper
    {

    Document doc; //web page source code is put in here
    public String directive = ""; //what scraper is searching for
    public String URL = "https://searchusers.com"; //where scraper is searching
    ArrayList<Element> targets; //elements that we need

    public searchWeb(String methodDirective, String methodURL)
    {
        directive = methodDirective;
        URL = methodURL;
    }//end constructor searchWeb

    @Override
    protected Void doInBackground(Void... voids)
    {

        try
        {
            doc = Jsoup.connect(URL).get();//connect to URL; puts source code in the doc
        }
        catch (Exception e)
        {
            e.printStackTrace();//in case it fails
        }
        targets  = new ArrayList<>(); // initializes new ArrayList
        Elements elements = doc.getElementsByClass(directive); //List of elements of object Elements with the class of directive

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
        for (int i = 0; i < targets.size(); i++)//creates a button for every user found
        {
            createButton(targets.get(i).text(), targets.get(i).attributes().get("href"));
        }
    }//end method onPostExecute

    private Button createButton(String text, final String clubURL) //creates a new button
    {
        Button clubButton = new Button(getApplicationContext());
        clubButton.setText(text);
        clubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                prepareClubPage(clubURL);//this method is fired when the new Button is clicked
            }
        });
        table.addView(clubButton); //adds button to the table
        return clubButton;
    }//end method createButton

    public void prepareClubPage(String clubURL)//prepares the club page
    {
        Intent sendtoClubPage = new Intent(MainActivity.this,clubPage.class); //new intent to switch activities
        sendtoClubPage.putExtra("URL", clubURL);//sends the url of the user's page under the key 'URL'
        startActivity(sendtoClubPage);//begins clubPage.java activity
    }//end method prepareClubPage

}//end class searchWeb
}//end class MainActivity

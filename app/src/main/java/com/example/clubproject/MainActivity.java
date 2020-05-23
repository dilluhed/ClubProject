package com.example.clubproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button button;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        layout = findViewById(R.id.mainLayout);

        //Button testButton = new Button(this);
        //RelativeLayout myLayout = new RelativeLayout(this);
        //layout.addView(testButton);
        //setContentView(layout);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new doIt().execute();
            }
        });


    }
//change
    public class doIt extends AsyncTask<Void, Void, Void>{

        String words = "";
        Document doc;

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                doc = Jsoup.connect("https://searchusers.com/search/valley+view+high+school").get();

                //words = doc.text();

            }
            catch(Exception e){e.printStackTrace();
            }

            Elements elements = doc.getElementsByClass("timg");

            for (Element element : elements)
            {
                String text = element.text();



                words += text;
                words += "\n";
               // createButton(text);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            text.setText(words);
        }
        /*
    private Button createButton(String text)
    {
        Button clubButton = new Button(getApplicationContext());
        //button.setLayoutParams(new RelativeLayout.LayoutParams(150,150));
        clubButton.setText(text);
        //clubButton.setLayoutParams(new;

        layout.addView(clubButton);

        return clubButton;
    }

         */
    }


}

package com.example.clubproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new doIt().execute();
            }
        });
    }
//change
    public class doIt extends AsyncTask<Void, Void, Void>{

        String words;

        @Override
        protected Void doInBackground(Void... voids) {

            try {

                Document doc = Jsoup.connect("https://www.imdb.com/name/nm0447695/").get();

                words = doc.text();

            }
            catch(Exception e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            text.setText(words);
        }
    }
}

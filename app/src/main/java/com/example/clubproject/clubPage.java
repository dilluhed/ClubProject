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

public class clubPage extends AppCompatActivity {
    String clubName;
    TextView clubNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_page);

        Intent openClubPage = getIntent();
        clubName = openClubPage.getStringExtra("URL");

        Toast toast=Toast. makeText(this,clubName,Toast. LENGTH_SHORT);
        toast.show();

        clubNameView = findViewById(R.id.clubName);
        clubNameView.setText(clubName);
    }
}

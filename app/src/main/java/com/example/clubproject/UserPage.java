package com.example.clubproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.ArrayList;

public class UserPage extends AppCompatActivity {
    TableLayout layout;
    user currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        layout = findViewById(R.id.clubPageTable);
        currentUser = MainActivity.currentUser;

        ArrayList<ClubSave> usersClubs = currentUser.getUsersClubs();

        if(usersClubs.size() > 0)
        {
                for (int i = 0; i < usersClubs.size(); i++) {
                    createButton(usersClubs.get(i).name, usersClubs.get(i).URL);
                }
        }
    }

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
        layout.addView(clubButton); //adds button to the table
        return clubButton;
    }//end method createButton

    public void prepareClubPage(String clubURL)//prepares the club page
    {
        Intent sendtoClubPage = new Intent(UserPage.this,clubPage.class); //new intent to switch activities
        sendtoClubPage.putExtra("URL", clubURL);//sends the url of the user's page under the key 'URL'
        startActivity(sendtoClubPage);//begins clubPage.java activity
    }//end method prepareClubPage
}

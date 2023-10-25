package com.example.findaseat;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.findaseat.widget.GridLayout;
import android.content.res.Resources;
import android.graphics.Color;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    String username = "";
    String password = "";
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        String message = "Please enter your login info";
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);

        tabLayout = findViewById(R.id.tab_layout_left);

        // Add an OnTabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Intent intent;
                switch (tab.getPosition()) {
                    case 0:
                        // Handle the first tab (Map) click
                        String message = " enter your login info";
                        TextView textView = (TextView) findViewById(R.id.textView);
                        textView.setText(message);
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        username = "guest";
                        intent.putExtra("com.example.username.MESSAGE", username);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        username = "guest";
                        intent.putExtra("com.example.username.MESSAGE", username);
                        startActivity(intent);
                        // Handle the second tab (Profile) click
                        break;
                    // Add more cases for additional tabs if needed
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // This method is called when a tab is unselected.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // This method is called when a tab is reselected.
                // You can choose to handle reselection differently if needed.
            }
        });
    }


    public void backToMain(View view) {

        EditText editView = (EditText) findViewById(R.id.enterUsername);
        username = editView.getText().toString();
        editView = (EditText) findViewById(R.id.enterPassword);
        password = editView.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("com.example.username.MESSAGE", username);
        startActivity(intent);


    }

    public void backToGuestMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        username = "guest";
        intent.putExtra("com.example.username.MESSAGE", username);
        startActivity(intent);
    }

    public void backToMap() {
        Intent intent = new Intent(this, MainActivity.class);
        username = "guest";
        intent.putExtra("com.example.username.MESSAGE", username);
        startActivity(intent);
    }
}

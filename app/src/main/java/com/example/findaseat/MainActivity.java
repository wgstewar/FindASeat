package com.example.findaseat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String username = intent.getStringExtra("com.example.username.MESSAGE");
        if (username == "guest" || username == null){
            username = "Hello, guest";
        }
        else {
            username = "Hello, " + username;
        }
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(username);

    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
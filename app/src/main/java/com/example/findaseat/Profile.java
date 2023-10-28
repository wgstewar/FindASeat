package com.example.findaseat;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.EditText;

public class Profile extends Fragment {
    /*
    String username = "";
    String password = "";
    */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    /*Old way of doing login, working on global variable thing*/
    public void onAttemptLogin(View view) {
/*
        EditText editView = (EditText) findViewById(R.id.enterUsername);
        username = editView.getText().toString();
        editView = (EditText) findViewById(R.id.enterPassword);
        password = editView.getText().toString();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("com.example.username.MESSAGE", username);
        startActivity(intent);
 */
    }


}
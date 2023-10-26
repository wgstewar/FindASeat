package com.example.findaseat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Map extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
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

         */

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }


}
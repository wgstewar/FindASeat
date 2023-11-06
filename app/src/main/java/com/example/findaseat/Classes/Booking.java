package com.example.findaseat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.findaseat.Adapters.IntervalListAdapter;
import com.example.findaseat.Classes.User;
import com.example.findaseat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.findaseat.Classes.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Booking extends Fragment {

    ArrayList<Reservation> resShoppingCart = new ArrayList<Reservation>();
    HashSet<Integer> shoppingCart;

    private FirebaseAuth auth;
    private FirebaseDatabase root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_booking, container, false);
        shoppingCart = new HashSet<>();

        ListView myList = inf.findViewById(R.id.intervalListView);
        Building b = new Building();
        ArrayList<Integer> a = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            a.add(20);
        }
        IntervalListAdapter adapter = new IntervalListAdapter(getActivity(), a, b, shoppingCart);
        myList.setAdapter(adapter);
        return inf;
    }
//    public void sortCart(){
//        Comparator<Reservation> startTimeComparator = Comparator.comparingInt(Reservation::getStartTime);
//        // Sort the list using startTime as comparator
//        Collections.sort(resShoppingCart, startTimeComparator);
//    }

    Button addButton = (Button)view.findViewById(R.id.addButton);
    addButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Reservation reservation =
            if (reservation == null){

            }
            else {
                Profile.currentUser.
            }
        }
    });


}


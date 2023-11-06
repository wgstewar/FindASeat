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
import android.widget.Toast;


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
    public static Weekday dayOfWeek = Weekday.MONDAY;
    ArrayList<Integer> a = new ArrayList<>();
    private FirebaseAuth auth;
    private FirebaseDatabase root;

    private IntervalListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_booking, container, false);
        shoppingCart = new HashSet<>();

        ListView myList = inf.findViewById(R.id.intervalListView);
        Building b = new Building();
        int buildingId = 1;
        /* use dayOfWeek to get correct ArrayList() */
        /* a = building.getAvailability[dayOfWeek]; */
        for (int i = 0; i < 20; i++) {
            a.add(20);
        }
        IntervalListAdapter adapter = new IntervalListAdapter(getActivity(), a, b, shoppingCart, buildingId);
        myList.setAdapter(adapter);

        Spinner selectWeekdaySpinner = inf.findViewById(R.id.selectWeekday);
        selectWeekdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                dayOfWeek = parent.getItemAtPosition(pos);
                a = building.getAvailability[dayOfWeek];
                updateAdapterData();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                a = building.getAvailability[Weekday.MONDAY];
                updateAdapterData();
            }
        });

        return inf;
    }

    private void updateAdapterData() {
        adapter.notifyDataSetChanged();
    }


    Button bookButton = (Button)view.findViewById(R.id.book);
    bookButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Reservation reservation = createReservation(buildingId, shoppingCart)
            if (reservation == null) {
                Toast.makeText(MainActivity.this, "Invalid Reservation: You may reserve only consecutive 30 min slots adding up to a total of no more than 2 hours.", Toast.LENGTH_LONG).show();
                return;
            }
            ArrayList<Reservation>() currUserReservations =
            if (Profile.currentUser.getActiveReservation() != null) {
                Toast.makeText(MainActivity.this, "Sorry, you may only hold one active reservation at a time.", Toast.LENGTH_LONG).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Make Reservation");
            builder.setMessage(reservation.intervalString());
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Profile.currentUser.addReservation(reservation);
                            String uid = auth.getCurrentUser().getUid();
                            DatabaseReference userRef = root.getReference("users/" + uid);
                            userRef.setValue(Profile.currentUser);
                        }
                    });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    });


}


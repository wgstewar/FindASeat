package com.example.findaseat;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.findaseat.Adapters.*;
import com.example.findaseat.Classes.*;
import com.example.findaseat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
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

    HashSet<Integer> shoppingCart;
    Building b;
    int buildingId;
    public static Weekday dayOfWeek = Weekday.MONDAY;
    ArrayList<Integer> a = new ArrayList<>();
    private Spinner selectWeekdaySpinner;
    private IntervalListAdapter adapter;
    private Context ctx;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bd = getArguments();
        buildingId = bd.getInt("buildingId");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.ctx = context;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View inf = inflater.inflate(R.layout.fragment_booking, container, false);

        FirebaseDatabase.getInstance().getReference("buildings/" + buildingId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                b = task.getResult().getValue(Building.class);
                a = b.getAvailability().get("MONDAY");
                ListView myList = inf.findViewById(R.id.intervalListView);
                adapter = new IntervalListAdapter(getActivity(), a, b, shoppingCart);
                myList.setAdapter(adapter);

                TextView nameView = (TextView) inf.findViewById(R.id.displayBuildingName);
                TextView descView = (TextView) inf.findViewById(R.id.displayDescription);
                nameView.setText(b.getName());
                descView.setText(b.getDescription());
            }
        });

        FirebaseDatabase.getInstance().getReference("buildings/" + buildingId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                b = snapshot.getValue(Building.class);
                a.clear();
                a.addAll(b.getAvailability().get(dayOfWeek.toString()));
                if (adapter != null) adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        shoppingCart = new HashSet<>();


        selectWeekdaySpinner = inf.findViewById(R.id.selectWeekday);
        selectWeekdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                dayOfWeek = Weekday.valueOf(parent.getItemAtPosition(pos).toString());
                if (b != null){
                    a.clear();
                    a.addAll(b.getAvailability().get(dayOfWeek.toString()));
                    shoppingCart.clear();
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button bookButton = (Button) inf.findViewById(R.id.bookButton);
        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null) bookButton.setEnabled(false);
                    else bookButton.setEnabled(true);
                });
        bookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Reservation r = Reservation.createReservation(buildingId, b.getOpenTime(), shoppingCart);
                if (r == null) {
                    TextView tv = (TextView) inf.findViewById(R.id.intervalTip);
                    tv.setTextColor(Color.RED);
                    tv.setText("Select up to 4 consecutive intervals!");
                    return;
                }
                if (Profile.currentUser.activeReservation() != null) {
                    Toast.makeText(ctx, "Sorry, you may only hold one active reservation at a time.", Toast.LENGTH_LONG).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Make Reservation?");
                builder.setMessage(Reservation.intervalString(
                        r.getStartTime(), r.getEndTime()));
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Profile.currentUser.addReservation(r);
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase.getInstance().getReference("users/" + uid).setValue(Profile.currentUser);

                                for (int j = r.getStartTime(); j < r.getEndTime(); j++) {
                                    b.removeSeat(dayOfWeek, j-b.getOpenTime());
                                }
                                FirebaseDatabase.getInstance().getReference("buildings/" + buildingId).setValue(b);
                                shoppingCart.clear();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(ctx, "Reservation created!", Toast.LENGTH_LONG).show();
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

        return inf;
    }
}


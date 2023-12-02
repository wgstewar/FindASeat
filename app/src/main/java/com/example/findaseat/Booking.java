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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.time.LocalDate;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Booking extends Fragment {

    HashSet<Integer> shoppingCart;
    Building b;
    int buildingId;
    public static Weekday dayOfWeek = Weekday.valueOf(LocalDate.now().getDayOfWeek().toString());
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
        dayOfWeek = Weekday.valueOf(LocalDate.now().getDayOfWeek().toString());
        FirebaseDatabase.getInstance().getReference("buildings/" + buildingId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            int id = buildingId;
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                b = task.getResult().getValue(Building.class);
                ArrayList<Integer> h = (ArrayList<Integer>) b.getAvailability().get(dayOfWeek.toString()).clone();

                a = h;
                ListView myList = inf.findViewById(R.id.intervalListView);
                adapter = new IntervalListAdapter(ctx, a, b, shoppingCart);
                myList.setAdapter(adapter);

                TextView nameView = (TextView) inf.findViewById(R.id.displayBuildingName);
                TextView descView = (TextView) inf.findViewById(R.id.displayDescription);
                nameView.setText(b.getName());
                descView.setText(b.getDescription());

                ImageView imageProfPic = (ImageView) inf.findViewById(R.id.buildingImageView);
                if (id==1) { //leavey library
                    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c3/USCLeavey2007.jpg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==2){ //doheny
                    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/91/Doheny_Library_interior.jpg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==3){ //leventhal
                    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/f/f8/Leventhal2012.JPG";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==4){ //olin hall
                    String imageUrl = "https://live.staticflickr.com/5545/11469647436_436e6a6fc1_5k.jpg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==5){ //annenberg hall
                    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/8/87/Wallis_Annenberg_Hall.jpeg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==6){ //IYA
                    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/49/Front_of_Iovine_and_Young_Academy.jpg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==7){ //Ronald Tutor Center
                    String imageUrl = "https://www.tutorperini.com/media/4221/campus-centerb.jpg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==8){ //Taper Hall
                    String imageUrl = "https://cms.concept3d.com/map/lib/image-cache/i.php?mapId=1928&image=1928/Taper-Hall_GR45205.jpg&w=900&h=508&r=1";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==9){ //Gould Law School
                    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c9/USC_Law.jpg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }
                if (id==10){ //Fertitta Hall
                    String imageUrl = "https://today.usc.edu/wp-content/uploads/2016/09/Fertitta_toned_web2.jpg";
                    Picasso.get().load(imageUrl).into(imageProfPic);
                }

            }
        });

        FirebaseDatabase.getInstance().getReference("buildings/" + buildingId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                b = snapshot.getValue(Building.class);

                a.clear();
                ArrayList<Integer> h = (ArrayList<Integer>) b.getAvailability().get(dayOfWeek.toString()).clone();

                a.addAll(h);
                if (adapter != null) adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        shoppingCart = new HashSet<>();


        selectWeekdaySpinner = inf.findViewById(R.id.selectWeekday);
        String[] wkdays = new String[] {
                "SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"
        };
        ArrayList<String> arraySpinner = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int day = dayOfWeek.ordinal() + i;
            day %= 7;
            arraySpinner.add(wkdays[day]);
        }
        ArrayAdapter<String> spAdapter = new ArrayAdapter<>(ctx,
                android.R.layout.simple_spinner_item, arraySpinner);
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectWeekdaySpinner.setAdapter(spAdapter);
        selectWeekdaySpinner.setSelection(0);

        selectWeekdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                dayOfWeek = Weekday.valueOf(parent.getItemAtPosition(pos).toString());
                if (b != null){
                    a.clear();
                    ArrayList<Integer> h = (ArrayList<Integer>) b.getAvailability().get(dayOfWeek.toString()).clone();
                    a.addAll(h);
                    Log.d("??", b.getAvailability().toString());
                    Log.d("??", dayOfWeek.toString());
                    shoppingCart.clear();
                    adapter.notifyDataSetChanged();
                    Log.d("umm", a.toString());
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
                int resWkday = Weekday.valueOf((String)selectWeekdaySpinner.getSelectedItem()).ordinal();
                int today = LocalDate.now().getDayOfWeek().getValue();
                resWkday = resWkday - today;
                resWkday = (resWkday < 0) ? resWkday + 7 : resWkday;
                Date d = new Date(LocalDate.now().plusDays(resWkday));
                Reservation r = Reservation.createReservation(d, buildingId, b.getOpenTime(), shoppingCart);
                if (r == null) {
                    TextView tv = (TextView) inf.findViewById(R.id.intervalTip);
                    tv.setTextColor(Color.RED);
                    tv.setText("Select up to 4 consecutive intervals!");
                    return;
                }
                if (Profile.currentUser==null){
                    Toast.makeText(getContext(), "User is null.", Toast.LENGTH_LONG).show();
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


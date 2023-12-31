package com.example.findaseat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findaseat.Adapters.*;
import com.example.findaseat.Classes.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    public static ViewPager2 viewPager2;
    public static ViewPagerAdapter viewPagerAdapter;
    private FirebaseDatabase root;
    private FirebaseAuth auth;
    private int open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.tab_layout);

        viewPager2 = findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.addFragment(new Map());
        viewPagerAdapter.addFragment(new Login());
        viewPagerAdapter.addFragment(new Profile());
        viewPagerAdapter.addFragment(new Register());

        viewPager2.setAdapter(viewPagerAdapter);
        viewPager2.setUserInputEnabled(false);

        root = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        auth.signOut();
        if (auth.getCurrentUser() != null) {
            String uid = auth.getCurrentUser().getUid();
            root.getReference("users/" + uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    Profile.currentUser = task.getResult().getValue(User.class);
                }
            });
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                while (viewPagerAdapter.getItemCount() > 4) {
                    viewPagerAdapter.destroyBookingPg();
                }
                int pos = tab.getPosition();
                if (pos == 1) {
                    FirebaseUser currentUser = auth.getCurrentUser();
                    if (currentUser != null) {
                        pos = 2;
                        if (Profile.currentUser != null) {
                            boolean update = Profile.currentUser.updateActiveReservation(LocalTime.now());
                            if (update) {
                                String uid = currentUser.getUid();
                                FirebaseDatabase.getInstance().getReference("users/" + uid).setValue(Profile.currentUser);
                            }
                        }
                    }
                }
                viewPager2.setCurrentItem(pos, false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (viewPagerAdapter.getItemCount() == 5) {
                    viewPagerAdapter.destroyBookingPg();
                }
                int pos = tab.getPosition();
                if (pos == 0) viewPager2.setCurrentItem(pos, false);
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        root = null;
    }

    public void attemptLogin(View v) {
        EditText enterEmail = (EditText) findViewById(R.id.enterEmail);
        String email = enterEmail.getText().toString();
        EditText enterPassword = (EditText) findViewById(R.id.enterPassword);
        String pwd = enterPassword.getText().toString();
        TextView loginTip = (TextView) findViewById(R.id.loginTip);
        if (email.isEmpty() || pwd.isEmpty()) {
            loginTip.setText("Please enter your email and password!");
            loginTip.setTextColor(Color.RED);
            return;
        }
        auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(MainActivity.this, "Successful sign-in.",
                                    Toast.LENGTH_SHORT).show();
                            viewPager2.setCurrentItem(2, false);
                            enterEmail.setText("");
                            enterPassword.setText("");
                        } else {
                            loginTip.setText("Invalid email/password combination.");
                            loginTip.setTextColor(Color.RED);
                            enterPassword.setText("");
                        }
                    }
                });
    }

    public void attemptRegister(View v) {
        boolean valid = true;
        TextView tv = (TextView) findViewById(R.id.registerTip);
        tv.setText("Please correct the following fields.");

        EditText usernameEditText = (EditText) findViewById(R.id.enterUsername);
        String username = usernameEditText.getText().toString();

        if (username.length() < 4 || username.length() > 12) {
            valid = false;
            tv.setText("Please enter a username between 4-12 characters.");
            usernameEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            usernameEditText.setBackgroundColor(Color.WHITE);
        }

        EditText passwordEditText = (EditText) findViewById(R.id.enterPassword);
        String pwd = passwordEditText.getText().toString();
        if (pwd.length() < 6 || pwd.length() > 20) {
            valid = false;
            tv.setText("Please enter a password between 6-20 characters.");
            passwordEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            passwordEditText.setBackgroundColor(Color.WHITE);

            EditText passwordCopyEditText = (EditText) findViewById(R.id.reenterPassword);
            String pwdCopy = passwordCopyEditText.getText().toString();
            if (!pwd.equals(pwdCopy)) {
                valid = false;
                tv.setText("Both passwords must match.");
                passwordCopyEditText.setBackgroundColor(Color.rgb(252, 174, 174));
            } else {
                passwordCopyEditText.setBackgroundColor(Color.WHITE);
            }
        }

        EditText fullNameEditText = (EditText) findViewById(R.id.enterFullName);
        String fullName = fullNameEditText.getText().toString();
        if (fullName.isEmpty()) {
            valid = false;
            tv.setText("Please enter your full name.");
            fullNameEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            fullNameEditText.setBackgroundColor(Color.WHITE);
        }

        EditText emailEditText = (EditText) findViewById(R.id.enterEmail);
        String email = emailEditText.getText().toString();
        if (email.isEmpty() || !email.contains("@usc.edu")) {
            valid = false;
            tv.setText("Please enter your @usc.edu address.");
            emailEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            emailEditText.setBackgroundColor(Color.WHITE);
        }

        EditText uscIdEditText = (EditText) findViewById(R.id.enterUscId);
        String uscId = uscIdEditText.getText().toString();
        if (uscId.length() != 10) {
            valid = false;
            tv.setText("Please enter your 10-digit USC ID.");
            uscIdEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            uscIdEditText.setBackgroundColor(Color.WHITE);
        }

        EditText imageUrlEditText = (EditText) findViewById(R.id.profimage);
        String imageUrl = imageUrlEditText.getText().toString();
        if (imageUrl.isEmpty()) {
            valid = false;
            imageUrlEditText.setBackgroundColor(Color.rgb(252, 174, 174));
        } else {
            imageUrlEditText.setBackgroundColor(Color.WHITE);
        }

        Spinner affiliationSpinner = (Spinner) findViewById(R.id.enterAffiliation);
        String affiliation = affiliationSpinner.getSelectedItem().toString();
        if (!valid) {
            tv.setTextColor(Color.RED);
            return;
        }

        auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            auth.getCurrentUser().updateProfile(profileUpdates);
                            String uid = auth.getCurrentUser().getUid();
                            DatabaseReference reference;
                            reference = root.getReference("users/" + uid);
                            User newUser = new User(fullName, uscId, username, email, affiliation, imageUrl);
                            reference.setValue(newUser);
                            Toast.makeText(MainActivity.this, "Account created !", Toast.LENGTH_SHORT).show();
                            viewPager2.setCurrentItem(2, false);
                        } else {
                            Toast.makeText(MainActivity.this, "Account with email already exists!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void attemptLogout(View view) {
        auth.signOut();
        viewPagerAdapter.notifyDataSetChanged();
        tabLayout.selectTab(tabLayout.getTabAt(0));
    }

    public void toRegister(View view) {
        viewPager2.setCurrentItem(3, false);
    }

    public void toLogin(View view) {
        viewPager2.setCurrentItem(1, false);
    }

    public void cancelActiveReservation(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cancel");
        builder.setMessage("Are you sure you would like to cancel your reservation?");
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Reservation r = Profile.currentUser.activeReservation();
                        root.getReference("buildings/" + r.getBuildingId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Building b = task.getResult().getValue(Building.class);
                                for (int i = r.getStartTime(); i < r.getEndTime(); i++) b.addSeat(r.getDate().getWeekday(), i-b.getOpenTime());
                                root.getReference("buildings/" + r.getBuildingId()).setValue(b);
                            }
                        });
                        Profile.currentUser.cancelActiveReservation();
                        String uid = auth.getCurrentUser().getUid();
                        root.getReference("users/" + uid).setValue(Profile.currentUser);
                        Toast.makeText(MainActivity.this, "Cancelled reservation", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void modifyActiveReservation(View view) {
        Reservation r = Profile.currentUser.activeReservation();
        Weekday oldDay = Booking.dayOfWeek;
        Booking.dayOfWeek = r.getDate().getWeekday();
        Dialog modifyDialog = new Dialog(this);
        modifyDialog.setContentView(R.layout.dialog_modify);
        modifyDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Booking.dayOfWeek = oldDay;
            }
        });
        HashSet<Integer> shoppingCart = new HashSet<>();
        root.getReference("buildings/" + r.getBuildingId())
                .get().addOnCompleteListener(
                        new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                Building b = task.getResult().getValue(Building.class);
                                ListView lv = modifyDialog.findViewById(R.id.modifyIntervalList);
                                ArrayList<Integer> avail = b.getAvailability().get(r.getDate().getWeekday().toString());
                                for (int i = r.getStartTime(); i < r.getEndTime(); i++) {
                                    shoppingCart.add(i-b.getOpenTime());
                                }
                                IntervalListAdapter adapter = new IntervalListAdapter(MainActivity.this, avail, b, shoppingCart);
                                lv.setAdapter(adapter);
                                open = b.getOpenTime();
                            }
                        });
        Button confirmModifyBtn = (Button) modifyDialog.findViewById(R.id.confirmModifyButton);
        confirmModifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reservation valid = Reservation.createReservation(r.getDate(), r.getBuildingId(), open, shoppingCart);
                if (valid == null) {
                    TextView tv = (TextView) modifyDialog.findViewById(R.id.modifyTip);
                    tv.setText("Select up to 4 consecutive intervals!");
                    tv.setTextColor(Color.RED);
                } else {
                    Log.d("hmm", valid.getStartTime() + " " + valid.getEndTime());
                    Profile.currentUser.modifyActiveReservation(valid);
                    String uid = auth.getCurrentUser().getUid();
                    root.getReference("users/" + uid).setValue(Profile.currentUser);
                    root.getReference("buildings/" + r.getBuildingId())
                            .get().addOnCompleteListener(
                                    new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            Building b = task.getResult().getValue(Building.class);
                                            for (int i = r.getStartTime(); i < r.getEndTime(); i++)
                                                b.addSeat(r.getDate().getWeekday(),i-b.getOpenTime());
                                            for (int i = valid.getStartTime(); i < valid.getEndTime(); i++)
                                                b.removeSeat(valid.getDate().getWeekday(),i-b.getOpenTime());
                                            root.getReference("buildings/" + r.getBuildingId()).setValue(b);
                                        }
                                    });


                    Toast.makeText(MainActivity.this, "Modified reservation", Toast.LENGTH_SHORT).show();
                    modifyDialog.dismiss();
                }
            }
        });
        modifyDialog.show();
    }

    public static void startBooking(int bid) {
        Booking fg = new Booking();
        Bundle bd = new Bundle();
        bd.putInt("buildingId", bid);
        fg.setArguments(bd);
        viewPagerAdapter.addFragment(fg);
        viewPager2.setCurrentItem(4, false);
    }
}
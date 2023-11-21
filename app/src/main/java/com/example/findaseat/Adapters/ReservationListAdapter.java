package com.example.findaseat.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.findaseat.*;
import com.example.findaseat.Classes.*;
import com.example.findaseat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReservationListAdapter extends ArrayAdapter<Reservation> {
    public ReservationListAdapter(Context context, ArrayList<Reservation> reservations) {
        super(context, 0, reservations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Reservation reservation = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_reservation, parent, false);
        }

        TextView buildingNameView = (TextView) convertView.findViewById(R.id.buildingName);
        TextView dateTimeView = (TextView) convertView.findViewById(R.id.dateTime);
        TextView statusView = (TextView) convertView.findViewById(R.id.status);
        Button modifyButton = (Button) convertView.findViewById(R.id.modifyButton);
        Button cancelButton = (Button) convertView.findViewById(R.id.cancelButton);
        int id = reservation.getBuildingId();

        FirebaseDatabase.getInstance().getReference("buildings/" + id).get().addOnCompleteListener(
                new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                Building b = task.getResult().getValue(Building.class);
                buildingNameView.setText(b.getName());

                String date = reservation.getDate().toString();
                String time = Reservation.intervalString(reservation.getStartTime(),
                        reservation.getEndTime());
                dateTimeView.setText(date + ", " + time);
            }
        });

        statusView.setText(reservation.getStatus().toString());
        if (reservation.getStatus() == ReservationStatus.ACTIVE) {
            convertView.setBackgroundColor(Color.rgb(73, 242, 92));
            modifyButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            switch (reservation.getStatus()) {
                case COMPLETED:
                    convertView.setBackgroundColor(Color.rgb(196,196,196));
                    break;
                case CANCELLED:
                    convertView.setBackgroundColor(Color.rgb(255,143,148));
                    break;
                default:
                    convertView.setBackgroundColor(Color.rgb(255, 255, 255));
            }
            modifyButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }

}

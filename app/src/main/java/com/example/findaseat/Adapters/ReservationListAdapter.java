package com.example.findaseat.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.findaseat.*;
import com.example.findaseat.Classes.*;
import com.example.findaseat.R;

import java.util.ArrayList;

public class ReservationListAdapter extends ArrayAdapter<Reservation> {
    private ArrayList<Reservation> dataSet;
    Context mContext;

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

        buildingNameView.setText("Leavey Library");
        String date = reservation.getDate().toString();
        String time = reservation.intervalString();
        dateTimeView.setText(date + ", " + time);

        statusView.setText(reservation.getStatus().toString());
        if (reservation.getStatus() == ReservationStatus.ACTIVE) {
            convertView.setBackgroundColor(Color.rgb(73, 242, 92));
            modifyButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
        } else {
            modifyButton.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }

}

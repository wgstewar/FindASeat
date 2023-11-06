package com.example.findaseat.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.findaseat.Classes.*;
import com.example.findaseat.R;

import java.util.ArrayList;
import java.util.HashSet;

public class IntervalListAdapter extends ArrayAdapter<Integer> {
    Building building;
    HashSet<Integer> shoppingCart;

    public IntervalListAdapter(Context context, ArrayList<Integer> avail, Building building, HashSet<Integer> shoppingCart) {
        super(context, 0, avail);
        this.building = building;
        this.shoppingCart = shoppingCart;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int numAvail = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_interval, parent, false);
        }

        double intervalStartTime;
        double intervalEndTime;
        //Getting accurate start time
        intervalStartTime = position/2.0 + building.getOpenTime();
        intervalEndTime = intervalStartTime + 0.5;
        /*set am/pm*/
        String startExtension;
        String endExtension;
        if (intervalStartTime < 12.0){
            startExtension = " am";
            if (intervalStartTime < 1){ intervalStartTime += 12; }
        } else {
            startExtension = " pm";
        }
        if ( intervalEndTime < 12.0){
            endExtension = " am";
            if (intervalEndTime < 1){intervalEndTime += 12;}
        } else {
            endExtension = " pm";
        }
        String intervalStartTimeString = Double.toString(intervalStartTime);
        String intervalEndTimeString = Double.toString(intervalEndTime);
        if (intervalStartTime%1 == 0) {
            intervalStartTimeString += ":00" + startExtension;
            intervalEndTimeString += ":30" + endExtension;
        } else { // half hour
            // Split the string at the decimal point
            String[] parts = intervalStartTimeString.split("\\.");
            // Extract the digits to the left of the decimal point
            intervalStartTimeString = parts[0];
            intervalStartTimeString += ":30" + startExtension;
            intervalEndTimeString += ":00" + endExtension;
        }

        TextView timeView = (TextView) convertView.findViewById(R.id.timeView);
        TextView seatsAvailView = (TextView) convertView.findViewById(R.id.seatsAvailView);
        Button addButton = (Button) convertView.findViewById(R.id.addButton);
        Button removeButton = (Button) convertView.findViewById(R.id.removeButton);

        seatsAvailView.setText(numAvail + " seats available.");
        timeView.setText(intervalStartTimeString + " - " + intervalEndTimeString);

        convertView.setBackgroundColor(Color.WHITE);
        if (shoppingCart.contains(position)) {
            addButton.setVisibility(View.INVISIBLE);
            removeButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.VISIBLE);
            removeButton.setVisibility(View.INVISIBLE);
        }

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.INVISIBLE);
                removeButton.setVisibility(View.VISIBLE);
                convertView.setBackgroundColor(Color.rgb(73, 242, 92));
                Date date = new Date(2023, 12, 10, MONDAY);
                Reservation reservation = new Reservation(bldgID, date, position + building.getOpenTime(), position + building.getOpenTime() + 1);
                Booking.resShoppingCart.add(reservation);
                Booking.shoppingCart.add(position);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.VISIBLE);
                removeButton.setVisibility(View.INVISIBLE);
                convertView.setBackgroundColor(Color.WHITE);
                Booking.shoppingCart.remove(position);
                Booking.resShoppingCart.remove(reservationToRemove)
            }
        });

        return convertView;
    }

}

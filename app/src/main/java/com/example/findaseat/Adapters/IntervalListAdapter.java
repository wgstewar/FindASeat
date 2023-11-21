package com.example.findaseat.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.findaseat.Classes.*;
import com.example.findaseat.R;

import java.time.LocalDateTime;
import java.time.LocalTime;
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

        TextView timeView = (TextView) convertView.findViewById(R.id.timeView);
        TextView seatsAvailView = (TextView) convertView.findViewById(R.id.seatsAvailView);
        Button addButton = (Button) convertView.findViewById(R.id.addButton);
        Button removeButton = (Button) convertView.findViewById(R.id.removeButton);

        seatsAvailView.setText(numAvail + " seats available.");

        LocalTime nowTime = LocalTime.now();
        int endInterval = position + 1 + building.getOpenTime();
        Log.d("tag", position + " " + building.getOpenTime() + " !!!");
        LocalTime endTime;
        if (endInterval == 48)
            endTime = LocalTime.of(23,59);
        else
            endTime = LocalTime.of(endInterval/2, endInterval%2 * 30);
        if (nowTime.isAfter(endTime)) {
            addButton.setEnabled(false);
        } else {
            addButton.setEnabled(true);
        }

        timeView.setText(Reservation.intervalString(position + building.getOpenTime(), position+1+ building.getOpenTime()));

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
                //convertView.setBackgroundColor(Color.rgb(73, 242, 92));
                shoppingCart.add(position);
            }
        });
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.VISIBLE);
                removeButton.setVisibility(View.INVISIBLE);
                //convertView.setBackgroundColor(Color.WHITE);
                shoppingCart.remove(position);
            }
        });

        return convertView;
    }

}

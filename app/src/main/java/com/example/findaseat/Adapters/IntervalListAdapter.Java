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

public class IntervalListAdapter extends ArrayAdapter<int> {
    private ArrayList<int> dataSet;
    int intervalsSize;
    Context mContext;

    public IntervalListAdapter(Context context, Building building) {
        ArrayList<int> intervals = building.getAvailability()[MONDAY];
        intervalsSize = intervals.size();
        super(context, 0, intervals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int intervalindex = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_interval, parent, false);
        }

        double intervalStartTime;
        double intervalEndTime;
        /*Getting accurate start time*/
        intervalStartTime = intervalindex/2.0 + building.getOpenTime();
        intervalEndTime = intervalStartTime + 0.5;
        /*set am/pm */
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
        String intervalStartTimeString;
        String intervalEndTimeString;
        intervalStartTimeString = Double.toString(intervalStartTime);
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

        TextView endTimeView = (TextView) convertView.findViewById(R.id.startTime);
        TextView startTimeView = (TextView) convertView.findViewById(R.id.endTime);
        Button addButton = (Button) convertView.findViewById(R.id.addButton);
        Button removeButton = (Button) convertView.findViewById(R.id.removeButton);

        endTimeView.setText(intervalEndTimeString);
        startTimeView.setText(intervalStartTimeString);
        Button addButton= (Button)view.findViewById(R.id.addButton);
        Button removeButton= (Button)view.findViewById(R.id.removeButton);

        convertView.setBackgroundColor(Color.WHITE);
        addButton.setVisibility(View.VISIBLE);
        removeButton.setVisibility(View.INVISIBLE);

        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.INVISIBLE);
                removeButton.setVisibility(View.VISIBLE);
                convertView.setBackgroundColor(Color.rgb(73, 242, 92));

            }
        });
        removeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addButton.setVisibility(View.VISIBLE);
                removeButton.setVisibility(View.INVISIBLE);
                convertView.setBackgroundColor(Color.WHITE);

            }
        });

        return convertView;
    }

}

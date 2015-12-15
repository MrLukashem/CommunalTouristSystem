package com.system.mrlukashem.communaltouristsystem;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.refbases.TrackingWayRefBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mrlukashem on 17.11.15.
 */
public class WaysListAdapter extends ArrayAdapter<TrackingWayRefBase> {

    private List<TrackingWayRefBase> mElements = new ArrayList<>();

    private Context mContext;

    public WaysListAdapter(Context context, int resource, TrackingWayRefBase[] elements) {
        super(context, resource, elements);

        mContext = context;
        mElements.addAll(Arrays.asList(elements));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(R.layout.ways_list_element, parent, false);
        } else {
            rowView = convertView;
        }

        final TrackingWayRefBase way = mElements.get(position);
        if(way.isNill()) {
            return rowView;
        }

        TextView title = (TextView)rowView.findViewById(R.id.way_title);
        TextView desc = (TextView)rowView.findViewById(R.id.way_desc);
        TextView dist = (TextView)rowView.findViewById(R.id.way_length);
        Button button = (Button)rowView.findViewById(R.id.showWayOnMapButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomMapManager
                        .getInstance()
                        .moveCamera(way.getPoints().get(0));
            }
        });

        title.setText(way.getTitle());
        desc.setText(way.getDescription());

        Location location = new Location("distance provider");
        Location previousLocation = new Location("distance provider");
        float result = .0f;
        previousLocation.setLongitude(way.getPoints().get(0).longitude);
        previousLocation.setLatitude(way.getPoints().get(0).latitude);

        for(int i = 1; i < way.getPoints().size(); i++) {
            location.setLatitude(way.getPoints().get(i).latitude);
            location.setLongitude(way.getPoints().get(i).longitude);

            result += previousLocation.distanceTo(location);

            previousLocation = location;
        }

        dist.setText(Float.toString(result) + " metrów");

        return rowView;
    }
}

package com.system.mrlukashem.communaltouristsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        TrackingWayRefBase way = mElements.get(position);
        if(way.isNill()) {
            return rowView;
        }

        TextView title = (TextView)rowView.findViewById(R.id.way_title);
        TextView desc = (TextView)rowView.findViewById(R.id.way_desc);

        title.setText(way.getTitle());
        desc.setText(way.getDescription());

        return rowView;
    }
}

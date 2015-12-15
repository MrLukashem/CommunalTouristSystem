package com.system.mrlukashem.communaltouristsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.system.mrlukashem.Interfaces.BitmapCallback;
import com.system.mrlukashem.refbases.BitmapLoader;
import com.system.mrlukashem.refbases.PlaceRefBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by mrlukashem on 17.11.15.
 */
public class PlacesListAdapter extends ArrayAdapter<PlaceRefBase> {

    private final Context mContext;

    private List<PlaceRefBase> mElements = new ArrayList<>();

    private BitmapLoader bitmapLoader = new CustomBitmapLoader();

    private class CallbackHandler implements BitmapCallback {

        private ImageView mImageView;

        public CallbackHandler(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        public void bmpCallback(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }

    public PlacesListAdapter(Context context, int resource, PlaceRefBase[] elements) throws NullPointerException {
        super(context, resource, elements);

        if (elements == null) {
            throw new NullPointerException();
        }

        mContext = context;

        for(PlaceRefBase place : elements) {
            mElements.add(place);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //TODO: check this! Inflates is really mandatory?

        View rowView;
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            rowView = inflater.inflate(R.layout.places_list_element, parent, false);
        } else {
            rowView = convertView;
        }

        final PlaceRefBase place = mElements.get(position);
        if(place.isNil()) {
            return rowView;
        }

        TextView title = (TextView)rowView.findViewById(R.id.place_title);
        TextView desc = (TextView)rowView.findViewById(R.id.place_desc);
        ImageView img = (ImageView)rowView.findViewById(R.id.place_img);
        Button button = (Button)rowView.findViewById(R.id.showPlaceOnMapButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomMapManager
                        .getInstance()
                        .moveCamera(place.getCords());

            }
        });


        title.setText(place.getName());
        desc.setText(place.getDescription());
        if(img.getDrawable() == null) {
            if(place.getPicPath().contains("http://")) {
                bitmapLoader.getBitmapFromURL(place.getPicPath(), new CallbackHandler(img));
            } else {
                Bitmap bitmap = bitmapLoader.getBitmapFromPath(place.getPicPath());
                img.setImageBitmap(bitmap);
            }
        }

        return rowView;
    }
}

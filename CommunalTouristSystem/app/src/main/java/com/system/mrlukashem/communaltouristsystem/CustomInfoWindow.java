package com.system.mrlukashem.communaltouristsystem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.system.mrlukashem.Interfaces.BitmapCallback;
import com.system.mrlukashem.refbases.BitmapLoader;
import com.system.mrlukashem.refbases.PlaceRefBase;

/**
 * Created by mrlukashem on 11.11.15.
 */
public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter, BitmapCallback {

    private final String HTTP = "http://";

    private Context mContext;

    private ImageView mImageView;

    private View fillDataContainers(View view, PlaceRefBase place) {
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView description = (TextView)view.findViewById(R.id.description);
        mImageView = (ImageView)view.findViewById(R.id.image);

        title.setText(place.getName());
        description.setText(place.getDescription());

        BitmapLoader bitmapLoader = new CustomBitmapLoader();
        Bitmap bitmap = null;
        if(place.getPicPath().contains(HTTP)) {
            BitmapCallback bitmapCallback = this;
            bitmapLoader.getBitmapFromURL(place.getPicPath(), bitmapCallback);
        } else {
            bitmap = bitmapLoader.getBitmapFromPath(place.getPicPath());
        }

        mImageView.setImageBitmap(bitmap);

        return view;
    }

    public CustomInfoWindow(Context context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        CustomMapManager customMapManager = CustomMapManager.getInstance();
        PlaceRefBase place = customMapManager.findElementByMarker(marker);

        View view = LayoutInflater.from(mContext).inflate(R.layout.info_window_layout, null);

        return fillDataContainers(view, place);
    }

    @Override
    public void bmpCallback(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }
}

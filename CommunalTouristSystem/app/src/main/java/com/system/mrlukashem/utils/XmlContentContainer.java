package com.system.mrlukashem.utils;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.communaltouristsystem.CommunalPlace;
import com.system.mrlukashem.communaltouristsystem.CommuneDescriptor;
import com.system.mrlukashem.refbases.PlaceRefBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 02.11.15.
 */
public class XmlContentContainer {

    private final String PLACE_NAME = "place_name";
    private final String DESCRIPTION = "description";
    private final String PATH_TO_IMG_FILE = "path_to_img_file";
    private final String HTTP_PATH = "http_path";
    private final String LAT = "Lat";
    private final String LNG = "Lng";
    private final String NAME = "name";
    private final String POPULATION = "population";
    private final String COMMUNAL_PLACE = "communal_place";
    private final String MAYOR = "major";
    private final String HISTORY = "history";
    private final String PLACEMENT = "placement";
    private final String OTHERS = "others";

    private static XmlContentContainer mInstance
            = new XmlContentContainer();

    private List<PlaceRefBase> mPlacesList
            = new ArrayList<>();

    private CommuneDescriptor mCommuneDesc
            = new CommuneDescriptor();

    private PlaceRefBase mNewPlaceInstance
            = new CommunalPlace();

    private String mNewPlaceLat = "";

    private String mNewPlaceLng = "";

    private void setNewPlaceInstanceCords() {
        double lat = Double.parseDouble(mNewPlaceLat);
        double lng = Double.parseDouble(mNewPlaceLng);

        mNewPlaceInstance.setCords(new LatLng(lat, lng));
    }

    private boolean checkNewPlaceInstanceCompleteness() {
        boolean completeness = true;

        if(mNewPlaceInstance.getCords() == null) {
            completeness = false;
        } else if(mNewPlaceInstance.getName().isEmpty()) {
            completeness = false;
        } else if(mNewPlaceInstance.getDescription().isEmpty()) {
            completeness = false;
        } else if(mNewPlaceInstance.getPicPath().isEmpty()) {
            completeness = false;
        }

        return completeness;
    }

    private XmlContentContainer() {}

    public static XmlContentContainer getInstance() {
        return mInstance;
    }

    public void pushData(String name, String data) {
        switch (name) {
            case PLACE_NAME:
                if(mNewPlaceInstance.getName().isEmpty()) {
                    mNewPlaceInstance.setName(data);
                }
                break;
            case DESCRIPTION:
                if(mNewPlaceInstance.getDescription().isEmpty()) {
                    mNewPlaceInstance.setDescription(data);
                }
                break;
            case PATH_TO_IMG_FILE:
                if(mNewPlaceInstance.getPicPath().isEmpty()) {
                    mNewPlaceInstance.setPicPath(data);
                }
                break;
            case HTTP_PATH:
                if(mNewPlaceInstance.getPicPath().isEmpty()) {
                    mNewPlaceInstance.setPicPath(data);
                }
                break;
            case LAT:
                if(mNewPlaceInstance.getCords() == null) {
                    mNewPlaceLat = data;
                    if(!mNewPlaceLat.isEmpty() && !mNewPlaceLng.isEmpty()) {
                        setNewPlaceInstanceCords();
                    }
                }
                break;
            case LNG:
                if(mNewPlaceInstance.getCords() == null) {
                    mNewPlaceLng = data;
                    if(!mNewPlaceLat.isEmpty() && !mNewPlaceLng.isEmpty()) {
                        setNewPlaceInstanceCords();
                    }
                }
                break;
            case NAME:
                mCommuneDesc.setName(data);
                break;
            case POPULATION:
                int population = Integer.parseInt(data);
                mCommuneDesc.setPopulation(population);
                break;
            case COMMUNAL_PLACE:
                mCommuneDesc.addPlace(data);
                break;
            case MAYOR:
                mCommuneDesc.setMajor(data);
                break;
            case HISTORY:
                mCommuneDesc.setHistory(data);
                break;
            case PLACEMENT:
                mCommuneDesc.setPlacement(data);
                break;
            case OTHERS:
                mCommuneDesc.setOthers(data);
                break;
        }

        if(checkNewPlaceInstanceCompleteness()) {
            mPlacesList.add(mNewPlaceInstance);

            mNewPlaceInstance = new CommunalPlace();
        }
    }

    public List<PlaceRefBase> getPlacesList() {
        return mPlacesList;
    }

    public CommuneDescriptor getCommuneDesc() {
        return mCommuneDesc;
    }
}

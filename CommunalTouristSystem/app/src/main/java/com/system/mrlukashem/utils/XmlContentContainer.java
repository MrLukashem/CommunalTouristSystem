package com.system.mrlukashem.utils;

import com.google.android.gms.maps.model.LatLng;
import com.system.mrlukashem.communaltouristsystem.CommunalPlace;
import com.system.mrlukashem.refbases.PlaceRefBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 02.11.15.
 */
public class XmlContentContainer {

    private static XmlContentContainer mInstance
            = new XmlContentContainer();

    private List<PlaceRefBase> mPlacesList
            = new ArrayList<>();

    private CommuneDesc mCommuneDesc
            = new CommuneDesc();

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
        }

        return completeness;
    }

    private XmlContentContainer() {}

    public static XmlContentContainer getInstance() {
        return mInstance;
    }

    public void pushData(String name, String data) {
        switch (name) {
            case "place_name":
                if(mNewPlaceInstance.getName().isEmpty()) {
                    mNewPlaceInstance.setName(data);
                }
                break;
            case "description":
                if(mNewPlaceInstance.getDescription().isEmpty()) {
                    mNewPlaceInstance.setDescription(data);
                }
                break;
            case "path_to_img_file":
                if(mNewPlaceInstance.getPicPath().isEmpty()) {
                    mNewPlaceInstance.setPicPath(data);
                }
                break;
            case "http_path":
                if(mNewPlaceInstance.getPicPath().isEmpty()) {
                    mNewPlaceInstance.setPicPath(data);
                }
                break;
            case "Lat":
                if(mNewPlaceInstance.getCords() == null) {
                    mNewPlaceLat = data;
                    if(!mNewPlaceLat.isEmpty() && !mNewPlaceLng.isEmpty()) {
                        setNewPlaceInstanceCords();
                    }
                }
                break;
            case "Lng":
                if(mNewPlaceInstance.getCords() == null) {
                    mNewPlaceLng = data;
                    if(!mNewPlaceLat.isEmpty() && !mNewPlaceLng.isEmpty()) {
                        setNewPlaceInstanceCords();
                    }
                }
                break;
            case "name":
                mCommuneDesc.setName(data);
                break;
            case "population":
                int population = Integer.parseInt(data);
                mCommuneDesc.setPopulation(population);
                break;
            case "communal_place":
                mCommuneDesc.addPlace(data);
                break;
            case "mayor":
                mCommuneDesc.setMajor(data);
                break;
            case "history":
                mCommuneDesc.setHistory(data);
                break;
            case "placement":
                mCommuneDesc.setPlacement(data);
                break;
            case "others":
                mCommuneDesc.setOthers(data);
                break;
        }

        if(checkNewPlaceInstanceCompleteness()) {
            mPlacesList.add(mNewPlaceInstance);

            mNewPlaceInstance = new CommunalPlace();
        }
    }

    public static class CommuneDesc {

        private String mName;

        private int mPopulation;

        private String mMajor;

        private String mHistory;

        private String mPlacement;

        private String mOthers;

        private List<String> mCommunalPlaces =
                new ArrayList<>();

        public CommuneDesc(
                String name,
                int population,
                String major,
                String history,
                String placement,
                String others) {
            mName = name;
            mPopulation = population;
            mMajor = major;
            mHistory = history;
            mPlacement = placement;
            mOthers = others;
        }

        public CommuneDesc() {}

        public void addPlace(String name) {
            mCommunalPlaces.add(name);
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public void setPopulation(int mPopulation) {
            this.mPopulation = mPopulation;
        }

        public void setMajor(String mMajor) {
            this.mMajor = mMajor;
        }

        public void setHistory(String mHistory) {
            this.mHistory = mHistory;
        }

        public void setPlacement(String mPlacement) {
            this.mPlacement = mPlacement;
        }

        public void setOthers(String mOthers) {
            this.mOthers = mOthers;
        }
    }
}

package com.example.mrlukashem.utils;

import android.os.Bundle;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrlukashem on 02.11.15.
 */
public class XmlContentContainer {

    private static XmlContentContainer mInstance =
            new XmlContentContainer();

    private List<Place> mPlacesList =
            new ArrayList<>();

    private CommuneDesc mCommuneDesc
            = new CommuneDesc();

    private Place mNewPlaceInstance =
            new Place();

    private String mNewPlaceLat;

    private String mNewPlaceLng;

    private void setNewPlaceInstanceCords() {
        double lat = Double.parseDouble(mNewPlaceLat);
        double lng = Double.parseDouble(mNewPlaceLng);

        mNewPlaceInstance.setCords(new LatLng(lat, lng));
    }

    private boolean checkNewPlaceInstanceCompleteness() {
        boolean completeness = true;

        if(mNewPlaceInstance.getCords() == null) {
            completeness = false;
        } else if(mNewPlaceInstance.getName() == null) {
            completeness = false;
        } else if(mNewPlaceInstance.getDesc() == null) {
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
                if(mNewPlaceInstance.getName() == null) {
                    mNewPlaceInstance.setName(data);
                }
                break;
            case "description":
                if(mNewPlaceInstance.getDesc() == null) {
                    mNewPlaceInstance.setDesc(data);
                }
                break;
            case "path_to_img_file":
                if(mNewPlaceInstance.getPath() == null) {
                    mNewPlaceInstance.setPath(data);
                }
                break;
            case "http_path":
                if(mNewPlaceInstance.getPath() == null) {
                    mNewPlaceInstance.setPath(data);
                }
                break;
            case "Lat":
                if(mNewPlaceInstance.getCords() == null) {
                    mNewPlaceLat = data;
                    if(mNewPlaceLat != null && mNewPlaceLng != null) {
                        setNewPlaceInstanceCords();
                    }
                }
                break;
            case "Lng":
                if(mNewPlaceInstance.getCords() == null) {
                    mNewPlaceLng = data;
                    if(mNewPlaceLat != null && mNewPlaceLng != null) {
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

            mNewPlaceInstance = new Place();
        }
    }

    public static class Place {

        private String mName;

        private String mDesc;

        private String mPath = "";

        private LatLng mCords;

        private Place(String name, String desc, String path, LatLng cords) {
            mName = name;
            mDesc = desc;
            mPath = path;
            mCords = cords;
        }

        private Place() {}

        public boolean isHttpPath() {
            return mPath.substring(0, 4).equals("http");
        }

        public String getName() {
            return mName;
        }

        public String getDesc() {
            return mDesc;
        }

        public String getPath() {
            return mPath;
        }

        public LatLng getCords() {
            return mCords;
        }

        public void setName(String mName) {
            this.mName = mName;
        }

        public void setDesc(String mDesc) {
            this.mDesc = mDesc;
        }

        public void setPath(String mPath) {
            this.mPath = mPath;
        }

        public void setCords(LatLng mCords) {
            this.mCords = mCords;
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

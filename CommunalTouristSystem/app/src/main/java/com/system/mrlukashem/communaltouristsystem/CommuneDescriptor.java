package com.system.mrlukashem.communaltouristsystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrlukashem on 11.11.15.
 */
public class CommuneDescriptor {
    private String mName;

    private int mPopulation;

    private String mMajor;

    private String mHistory;

    private String mPlacement;

    private String mOthers;

    private List<String> mCommunalPlaces =
            new ArrayList<>();

    public CommuneDescriptor() {
        mName = "";
        mMajor = "";
        mHistory = "";
        mPlacement = "";
        mOthers = "";
    }

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

    public String getName() {
        return mName;
    }

    public int getPopulation() {
        return mPopulation;
    }

    public String getMajor() {
        return mMajor;
    }

    public String getHistory() {
        return mHistory;
    }

    public String getPlacement() {
        return mPlacement;
    }

    public String getOthers() {
        return mOthers;
    }

    public List<String> getCommunalPlaces() {
        return mCommunalPlaces;
    }

}

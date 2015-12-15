package com.system.mrlukashem.communaltouristsystem;

import com.system.mrlukashem.nullobjects.NullPlace;
import com.system.mrlukashem.nullobjects.NullTrackingWay;
import com.system.mrlukashem.refbases.PlaceRefBase;
import com.system.mrlukashem.refbases.TrackingWayRefBase;

import junit.framework.TestCase;

/**
 * Created by mrlukashem on 21.11.15.
 */
public class MapsActivityTest extends TestCase {


    public void testNullPlaceBase() {
        PlaceRefBase placeRefBase =
                new NullPlace();

        assertTrue(placeRefBase.isNil());

        placeRefBase =
                new CommunalPlace();

        assertFalse(placeRefBase.isNil());
    }

    public void testNullTrackingWayBase() {
        TrackingWayRefBase wayRefBase =
                new NullTrackingWay();

        assertTrue(wayRefBase.isNill());

        wayRefBase =
                new TrackingWay();

        assertFalse(wayRefBase.isNill());
    }

    public void testMapManager() {
        assertTrue(CustomMapManager.getInstance()
                .getPlacesList().size() == 0);

        CustomMapManager
                .getInstance()
                .pushElement(new CommunalPlace(), "tagTest");
        assertTrue(CustomMapManager
                .getInstance()
                .findElementByTag("tagTest") != null);
    }
}
package com.example.mrlukashem.utils;

/**
 * Created by mrlukashem on 03.11.15.
 */
public class NullChecker {

    public static <T> T isNull(T value) throws NullPointerException {
        if(value == null) {
            throw new NullPointerException();
        }

        return value;
    }
}

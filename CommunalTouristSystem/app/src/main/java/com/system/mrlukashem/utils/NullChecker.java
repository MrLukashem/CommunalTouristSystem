package com.system.mrlukashem.utils;

/**
 * Created by mrlukashem on 03.11.15.
 */
public class NullChecker {

    private static final String ERROR_STRING = "Value can not be null!";

    public static <T> T isNull(T value) throws NullPointerException {
        if(value == null) {
            throw new NullPointerException(ERROR_STRING);
        }

        return value;
    }

    public static <T> T isNull(T value, String message) {
        if(value == null) {
            throw new NullPointerException(message);
        }

        return value;
    }
}

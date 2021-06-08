package com.cloud.meli.trace.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    public static double distanceCoord(double lat1, double lng1) {
        // argentina
        double lat2 = -34;
        double lng2 = -64;
        //double radioTierra = 3958.75;//en millas
        double ratioEarth = 6371;//en kilómetros
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double distance = ratioEarth * va2;

        return distance;
    }

    private static final String IP_FORMAT_PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    public static boolean validateIp(final String ip) {
        Pattern pattern = Pattern.compile(IP_FORMAT_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }


}

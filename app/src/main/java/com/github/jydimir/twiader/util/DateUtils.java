package com.github.jydimir.twiader.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static Date parseTwitterUTC(String date) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        Date parsedDate = null;
        try {
            parsedDate = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
}

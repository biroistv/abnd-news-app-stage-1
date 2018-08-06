package com.example.biro.abnd_news_app_s1.utils;

public class DateFormatter {
    // This method just remove the 'Z' at the and of the String and replace the 'T' to a Space
    public static String formatDate(String unformatted_date)
    {
        // JSON date: 2018-08-06T09:00:34Z

        String without_z = unformatted_date.subSequence(0, unformatted_date.length() - 1).toString();
        return without_z.replace('T', ' ');
    }
}

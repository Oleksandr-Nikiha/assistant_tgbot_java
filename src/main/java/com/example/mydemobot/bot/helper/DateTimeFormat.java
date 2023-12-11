package com.example.mydemobot.bot.helper;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;


public class DateTimeFormat {
    public String getDate(String textDate) {
        String outputFormat = "yyyy-MM-dd HH:mm";
        
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
            
            Date date = inputDateFormat.parse(textDate);
            String formattedDate = outputDateFormat.format(date);

            return formattedDate;
            
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}

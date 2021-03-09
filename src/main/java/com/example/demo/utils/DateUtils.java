package com.example.demo.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    public static String DbDateTimeString() {
        return ZonedDateTime.now(ZoneId.of("America/Phoenix"))
                .format(DateTimeFormatter.ofPattern("uuuu.MM.dd.HH.mm.ss"));
    }

    public static Date DbDateTimeDate() {
        return new Date();
    }

    public static String incrementHours(String dateTime, Integer hours) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("uuuu.MM.dd.HH.mm.ss");
        Date parsedDate = dateFormat.parse(dateTime);
        Timestamp timestamp = new Timestamp(parsedDate.getTime() + 3600L * hours);
        return new SimpleDateFormat("uuuu.MM.dd.HH.mm.ss").format(timestamp);
    }
}

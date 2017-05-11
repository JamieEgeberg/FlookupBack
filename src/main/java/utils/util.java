package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Niki on 2017-05-10.
 *
 * @author Niki
 */
public class util {
    public static String getToday() {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz);
        return df.format(Calendar.getInstance().getTime());
    }

    public static HashMap<String,String> urls() {
        HashMap<String,String> map = new HashMap<>();
        map.put("JENS air", "https://airline.skaarup.io/api/");
        //map.put("AngularJS Airline,
        //        "https://airline-plaul.rhcloud.com/api/flightinfo/");
        //map.put("AirWonDo", "https://vetterlain.dk/AirWonDo/api/");
        return map;
    }
}

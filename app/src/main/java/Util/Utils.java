package Util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joebreda on 11/20/17.
 */

/*
USEFUL GETTERS AND SETTERS + URL USED TO FETCH DATA FROM API
*/

//http://api.openweathermap.org/data/2.5/forecast/daily?q=Delhi,in&mode=json&cnt=7&units=metric&appid=yourapiid
//http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID={APIKEY}
    //061efd7648ef65ec44df8c43dd561793
    //api.openweathermap.org/data/2.5/weather?zip={zip code},{country code}
public class Utils {
    // q if city, zip if zipcode
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?zip=";

    public static final String ICON_URL = "http://api.openweathermap.org/image/w/";

    public static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException {
        JSONObject jObj = jsonObject.getJSONObject(tagName);
        return jObj;
    }

    public static String getString(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(tagName);
    }

    public static float getFloat(String tagName, JSONObject jsonObject) throws JSONException {
        return (float)jsonObject.getDouble(tagName);
    }

    public static double getDouble(String tagName, JSONObject jsonObject) throws JSONException {
        return (float)jsonObject.getDouble(tagName);
    }

    public static int getInt(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt(tagName);
    }

}

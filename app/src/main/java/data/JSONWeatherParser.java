package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Util.Utils;
import model.Place;
import model.Weather;

/**
 * Created by joebreda on 11/20/17.
 */

/*
STAGE 2 in data pull process
Weather data parser takes in string of weather data JSON format and returns a weather obkect
*/

public class JSONWeatherParser {
    public static Weather getWeather(String data) {
        Weather weather = new Weather();

        try {
            // Object that holds all JSON data
            JSONObject jsonObject = new JSONObject(data);

            Place place = new Place();

            JSONObject coordObj = Utils.getObject("coord", jsonObject);
            // coordObj holds longitude and latitude data
            place.setLat(Utils.getFloat("lat", coordObj));
            place.setLon(Utils.getFloat("lon", coordObj));

            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            // sysObj holds country, sunrise and sunset data, while other data is in larger JsonObj
            place.setCountry(Utils.getString("country", sysObj));
            place.setLastupdate(Utils.getInt("dt", jsonObject));
            place.setSunrise(Utils.getInt("sunrise", sysObj));
            place.setSunset(Utils.getInt("sunset", sysObj));
            place.setCity(Utils.getString("name", jsonObject));

            // now set to weather attribute
            weather.place = place;

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeathter = jsonArray.getJSONObject(0);
            // weather data stored within an array, first form JSONArray then JsonWeatherObj
            weather.currentCondition.setWeatherId(Utils.getInt("id", jsonWeathter));
            weather.currentCondition.setDescription(Utils.getString("description", jsonWeathter));
            weather.currentCondition.setCondition(Utils.getString("main", jsonWeathter));
            weather.currentCondition.setIcon(Utils.getString("icon", jsonWeathter));

            JSONObject mainObj = Utils.getObject("main", jsonObject);
            // mainObj holds most important weather specs
            weather.currentCondition.setHumidity(Utils.getInt("humidity", mainObj));
            weather.currentCondition.setPressure(Utils.getInt("pressure", mainObj));
            weather.currentCondition.setMinTemp(Utils.getFloat("temp_min", mainObj));
            weather.currentCondition.setMexTemp(Utils.getFloat("temp_max", mainObj));
            weather.currentCondition.setTemperature(Utils.getDouble("temp", mainObj));

            JSONObject windObj = Utils.getObject("wind", jsonObject);
            // windObj holds wind speed and direction
            weather.wind.setSpeed(Utils.getFloat("speed", windObj));
            weather.wind.setDeg(Utils.getFloat("deg", windObj));

            JSONObject cloudObj = Utils.getObject("clouds", jsonObject);
            // cloud object holds type of precipitation
            weather.clouds.setPrecipitation(Utils.getInt("all", cloudObj));

            // if data is found return weather
            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
            // and if nothing is found return null
            return null;
        }

    }
}

package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import Util.Utils;

/**
 * Created by joebreda on 11/20/17.
 */

/*
STAGE 2 in data pull process
pull data from web using URL and HttpURLConnection then fill stringbuffer and return as string obj
*/

public class WeatherHttpClient {

    public String getWeatherData(String place) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            // setting up connection to the internet
            URL url = new URL(Utils.BASE_URL + place);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            // Response
            StringBuffer stringBuffer = new StringBuffer();
            // Receive as stream of bits
            inputStream = connection.getInputStream();
            // Translates inputStream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            // Organize line by line into stringBuffer
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }
            // Close Connection
            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

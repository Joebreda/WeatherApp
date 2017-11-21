package com.example.joebreda.weatherapp;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import Util.Utils;
import data.CityPreferences;
import data.JSONWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView cityName;
    private TextView temp;
    private TextView minMaxTemp;
    //private ImageView iconView;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    //private TextView sunrise;
    //private TextView sunset;
    //private TextView updated;

    Weather weather = new Weather();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // links all attributes to XML tags by ID
        cityName = (TextView) findViewById(R.id.cityText);
        //iconView = (ImageView) findViewById(R.id.thumbnail);
        temp = (TextView) findViewById(R.id.tempText);
        minMaxTemp = (TextView) findViewById(R.id.minMaxTempText);
        description = (TextView) findViewById(R.id.cloudText);
        humidity = (TextView) findViewById(R.id.humidText);
        pressure = (TextView) findViewById(R.id.pressureText);
        wind = (TextView) findViewById(R.id.windText);
        //sunrise = (TextView) findViewById(R.id.riseText);
        //sunset = (TextView) findViewById(R.id.setText);
        //updated = (TextView) findViewById(R.id.updateText);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        // Renders default input option
        CityPreferences cityPreferences = new CityPreferences(MainActivity.this);
        renderWeatherData(cityPreferences.getCity());
    }


    public void renderWeatherData(String city) {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=imperial&APPID=061efd7648ef65ec44df8c43dd561793"});
    }

    // This method performs most of the meat of the applications fetching of data
    private class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... strings) {
            //initializing weather client and getting weather and passing into data
            String data = ( (new WeatherHttpClient()).getWeatherData(strings[0]));
            weather = JSONWeatherParser.getWeather(data);
            //weather.iconData = weather.currentCondition.getIcon();
            //new DownloadImageTask().execute(weather.iconData);
            return weather;

        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            // formatting dates from Unix time to readable time
            DateFormat df = DateFormat.getDateInstance();
            String sunriseDate = df.format(new Date(weather.place.getSunrise()));
            String sunsetDate = df.format(new Date(weather.place.getSunset()));
            String updatedDate = df.format(new Date(weather.place.getLastupdate()));
            // Round decimal or float to tenths position
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            // Strings used to set attribute text
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());
            String cityNameText = weather.place.getCity() + ", " + weather.place.getCountry();
            String tempText = "" + tempFormat + "°F";
            String minMaxTempText = "min: " + weather.currentCondition.getMinTemp() + "°F" +
                    "       Max: " + weather.currentCondition.getMexTemp() + "°F";
            String humidityText = "Humidity: " + weather.currentCondition.getHumidity() + "%";
            String pressureText = "Pressure: " + weather.currentCondition.getPressure() + "hPa";
            String windText = "Wind: " + weather.wind.getSpeed() + "mph";
            String sunRiseText = "Sunrise: " + sunriseDate;
            String sunsetText = "Sunset: " + sunsetDate;
            String lastUpdatedText = "last Updated: " + updatedDate;
            String descriptionText = "Condition: " + weather.currentCondition.getCondition() + " ("
                    + weather.currentCondition.getDescription() + ")";
            // setting of attribute text
            cityName.setText(cityNameText);
            temp.setText(tempText);
            minMaxTemp.setText(minMaxTempText);
            humidity.setText(humidityText);
            pressure.setText(pressureText);
            wind.setText(windText);
            //sunrise.setText(sunRiseText);
            //sunset.setText(sunsetText);
            //updated.setText(lastUpdatedText);
            description.setText(descriptionText);
        }
    }

    // button to change zip code
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change Zipcode");
        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("94040,US");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CityPreferences cityPreferences = new CityPreferences(MainActivity.this);
                cityPreferences.setCity(cityInput.getText().toString());
                String newCity = cityPreferences.getCity();
                // render new city preferences input from button
                renderWeatherData(newCity);
            }
        });
        builder.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_zipcode) {
            // if button is pressed -> show dialog and allow input of new zip code
            showInputDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    /*
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            return downloadImage(strings[0]);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            iconView.setImageBitmap(bitmap);
        }

        private Bitmap downloadImage(String code) {
            final DefaultHttpClient client = new DefaultHttpClient();
            final HttpGet getRequest = new HttpGet(Utils.ICON_URL + code + ".png");
            try {
                HttpResponse response = client.execute(getRequest);
                final int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.e("DownloadImage", "Error:" + statusCode);
                    return null;
                }
                final HttpEntity entity = response.getEntity();

                if (entity != null) {
                    InputStream inputStream = null;
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    */
}

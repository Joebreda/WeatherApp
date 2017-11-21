package data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by joebreda on 11/20/17.
 */

public class CityPreferences {
    SharedPreferences prefs;

    public CityPreferences(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    public String getCity() {
        return prefs.getString("city", "94040,US");
    }

    public void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }
}

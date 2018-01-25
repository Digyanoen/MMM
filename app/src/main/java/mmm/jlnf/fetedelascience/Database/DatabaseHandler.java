package mmm.jlnf.fetedelascience.Database;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import mmm.jlnf.fetedelascience.Pojos.EventPojo;
import mmm.jlnf.fetedelascience.R;

/**
 * Created by nicolas on 20/01/18.
 */

public class DatabaseHandler implements IDatabaseHandler{


    private DBManager dbManager;

    public DatabaseHandler(Context context) {
        DBManager.Init(context);
        this.dbManager = DBManager.getInstance();
    }

    @Override
    public List<EventPojo> getEventByCity(String city) {
        return dbManager.getPojosByCity(city);
    }

    @Override
    public List<EventPojo> getEventByCoordinates(LatLng center, double rayon) {
        return null;
    }




}

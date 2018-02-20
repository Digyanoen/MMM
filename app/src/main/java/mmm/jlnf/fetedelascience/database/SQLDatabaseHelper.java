package mmm.jlnf.fetedelascience.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import mmm.jlnf.fetedelascience.pojos.EventPojo;
import mmm.jlnf.fetedelascience.R;

/**
 * Created by nicolas on 23/01/18.
 */

public class SQLDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Context context;
    private Dao<EventPojo, String> eventDAO;
    private static String dbName = "events";
    private static int version = 1;


    public SQLDatabaseHelper(Context context) {
        super(context, dbName, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, EventPojo.class);
            initialize(context);
            Log.d("TAG", "Creation de la table");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, EventPojo.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Dao<EventPojo, String> getEventPojoDao() {
        if (eventDAO == null) {
            try {
                eventDAO = getDao(EventPojo.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eventDAO;
    }

    /**
     * Initialisation de la BDD
     * @param context
     */
    private void initialize(Context context){
        try {

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            InputStream resource = context.getResources().openRawResource(R.raw.data);
            JsonReader jsonReader = new JsonReader(new InputStreamReader(resource));
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(jsonReader).getAsJsonArray();
            for (JsonElement element : jsonArray){

                JsonObject object = element.getAsJsonObject().get("fields").getAsJsonObject();


                EventPojo eventPojo = gson.fromJson(object, EventPojo.class);
//                Log.e("TAGGGG", eventPojo.toString());
//                JsonElement jsonElement = object.get("geolocalisation");
//                JsonArray geo = jsonElement.getAsJsonArray();
//                Double[] geoPojo = gson.fromJson(geo, Double[].class);
                DBManager.getInstance().createEventPojo(eventPojo);
            }

            resource.close();
            jsonReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

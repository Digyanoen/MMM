package mmm.jlnf.fetedelascience.database;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 24/01/18.
 * DAO Permettant la récupération dans la base SQL
 */

public class DBManager {

    private  SQLDatabaseHelper helper;
    private static DBManager ourInstance;

    public static void Init(Context context) {
        if (ourInstance == null)
            ourInstance = new DBManager(context);
    }

    public static DBManager getInstance() {
        return ourInstance;
    }

    private DBManager(Context context) {
        helper = new SQLDatabaseHelper(context);
    }

    private SQLDatabaseHelper getHelper() {
        return helper;
    }

    /** Methods [EventPojo] **/

    public List<EventPojo> getAllEventPojo() {
        try {
            return getHelper().getEventPojoDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<EventPojo>();
        }
    }

    public int createEventPojo(EventPojo eventPojo) {
        try {
            getHelper().getEventPojoDao().create(eventPojo);
            return eventPojo.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void removeEventPojos(Collection<EventPojo> eventPojos) {
        try {
            getHelper().getEventPojoDao().delete(eventPojos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long updateEventPojo(EventPojo eventPojo) {
        try {
            getHelper().getEventPojoDao().update(eventPojo);
            return eventPojo.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Recher de pojos selon un type
     * @param type le type de la recherche
     * @param data le champ entré
     * @return
     */
    public List<EventPojo> getPojosByCriteria(String type, String data){
        List<EventPojo> eventList = null;
        Log.e("type", type);
        Log.e("criteria", data);
        try {
            if(type.equals("mots-clés")){
                QueryBuilder<EventPojo, String> builder = getHelper().getEventPojoDao().queryBuilder();
                builder.where().like("mots_cles_fr", "%" + data + "%");
                eventList = getHelper().getEventPojoDao().query(builder.prepare());
            }else {

                eventList = getHelper().getEventPojoDao().queryForEq(type, data);
            }
        } catch (SQLException e) {
            eventList =  new ArrayList<>();
        }
        Log.e("eventlist", eventList.toString());
        return eventList;
    }

    public List<EventPojo> getPojosByCoordinate(LatLng center, double rayon){
        List<EventPojo> eventList = null;
        try {
            QueryBuilder<EventPojo, String> builder = getHelper().getEventPojoDao().queryBuilder();
            builder.where().le("lat", center.latitude+rayon)
                    .and().ge("lat", center.latitude-rayon)
                    .and().le("lng", center.longitude+rayon)
                    .and().ge("lng", center.longitude-rayon);
            eventList = getHelper().getEventPojoDao().query(builder.prepare());
        } catch (SQLException e) {
            eventList =  new ArrayList<>();
        }
        return eventList;
    }


    public EventPojo getPojoByID(int eventID) {
        try {
            return getHelper().getEventPojoDao().queryForId(String.valueOf(eventID));
        } catch (SQLException e) {
            return null;
        }
    }
}

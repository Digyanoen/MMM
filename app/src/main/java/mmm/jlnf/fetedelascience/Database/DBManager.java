package mmm.jlnf.fetedelascience.Database;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mmm.jlnf.fetedelascience.Pojos.EventPojo;
import mmm.jlnf.fetedelascience.RecyclerViewAdapter;

/**
 * Created by nicolas on 24/01/18.
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

    public List<EventPojo> getPojosByCriteria(String type, String data){
        List<EventPojo> eventList = null;
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
        return eventList;
    }


}

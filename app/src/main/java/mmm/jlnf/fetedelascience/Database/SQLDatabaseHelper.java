package mmm.jlnf.fetedelascience.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 23/01/18.
 */

public class SQLDatabaseHelper extends OrmLiteSqliteOpenHelper {

    private Dao<EventPojo, String> eventDAO;
    private static String dbName = "events";
    private static int version = 1;


    public SQLDatabaseHelper(Context context) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, EventPojo.class);
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
}

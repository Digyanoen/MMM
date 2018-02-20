package mmm.jlnf.fetedelascience.database;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import mmm.jlnf.fetedelascience.pojos.EventPojo;

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
    public List<EventPojo> getEventByCriteria(String type, String data) {
        return dbManager.getPojosByCriteria(type, data);
    }

    @Override
    public List<EventPojo> getEventByCoordinates(LatLng center, double rayon) {
        return dbManager.getPojosByCoordinate(center,rayon);
    }




}

package mmm.jlnf.fetedelascience.database;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 23/01/18.
 */

public interface IDatabaseHandler {
    List<EventPojo> getEventByCriteria(String type, String data);
    List<EventPojo> getEventByCoordinates(LatLng center, double rayon);
}

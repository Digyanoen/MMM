package mmm.jlnf.fetedelascience.Database;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import mmm.jlnf.fetedelascience.Pojos.EventPojo;
import mmm.jlnf.fetedelascience.RecyclerViewAdapter;

/**
 * Created by nicolas on 23/01/18.
 */

interface IDatabaseHandler {
    List<EventPojo> getEventByCity(String city);
    List<EventPojo> getEventByCoordinates(LatLng center, double rayon);
    void initialize(Context context);
}

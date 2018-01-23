package mmm.jlnf.fetedelascience.Database;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import mmm.jlnf.fetedelascience.Pojos.EventPojo;
import mmm.jlnf.fetedelascience.RecyclerViewAdapter;

/**
 * Created by nicolas on 23/01/18.
 */

interface IDatabaseHandler {
    void getCityDescription(String city, List<EventPojo> eventPojos, RecyclerViewAdapter adapter);
    void getEventByCoordinates(LatLng coordinatesMin, LatLng coordinatesMax);
}

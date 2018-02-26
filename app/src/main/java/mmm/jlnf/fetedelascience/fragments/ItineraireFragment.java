package mmm.jlnf.fetedelascience.fragments;

import android.app.Fragment;

import java.util.List;

import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 26/02/18.
 */

public class ItineraireFragment extends Fragment {

    private List<EventPojo> pojoList;

    public boolean addToList(EventPojo eventPojo){
        return pojoList.add(eventPojo);
    }
}

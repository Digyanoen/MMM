package mmm.jlnf.fetedelascience.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.activity.MapsActivity;
import mmm.jlnf.fetedelascience.activity.SearchActivity;
import mmm.jlnf.fetedelascience.adapters.ItineraireAdapter;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 26/02/18.
 */

public class ItineraireFragment extends Fragment {

    private ArrayList<EventPojo> pojoList;

    @BindView(R.id.itineraireRecycler)
    RecyclerView recyclerView;

    public boolean addToList(EventPojo eventPojo){
        return pojoList.add(eventPojo);
    }

    public ItineraireFragment() {
        this.pojoList = new ArrayList<>();
        Log.e("fragment", "crée");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycleritinerairefragment, container, false);
        ButterKnife.bind(this, view);
        ItineraireAdapter itineraireAdapter = new ItineraireAdapter(pojoList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity().getApplication().getBaseContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(itineraireAdapter);

        return view;
    }


    @OnClick(R.id.mapItineraire)
    public void onClick() {
        Log.e("toto", "toto");
        if(pojoList.size() >= 2) {
            Log.e("tata", "tata");
            Intent i = getActivity().getIntent();
            i.setClass(getActivity(), MapsActivity.class);
            i.putExtra("itis", pojoList);
            i.putExtra("latitude", pojoList.get(0).getLat());
            i.putExtra("longitude", pojoList.get(0).getLng());
            startActivity(i);
        }
        else{
            Toast.makeText(getActivity(), "Pas d'itinéraire", Toast.LENGTH_SHORT).show();
        }

    }



}

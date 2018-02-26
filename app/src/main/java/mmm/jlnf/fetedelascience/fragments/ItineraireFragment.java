package mmm.jlnf.fetedelascience.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.R;
import mmm.jlnf.fetedelascience.adapters.ItineraireAdapter;
import mmm.jlnf.fetedelascience.pojos.EventPojo;

/**
 * Created by nicolas on 26/02/18.
 */

public class ItineraireFragment extends Fragment {

    private List<EventPojo> pojoList;

    @BindView(R.id.itineraireRecycler)
    RecyclerView recyclerView;

    public boolean addToList(EventPojo eventPojo){
        return pojoList.add(eventPojo);
    }

    public ItineraireFragment() {
        this.pojoList = new ArrayList<>();
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
        ItineraireAdapter itineraireAdapter = new ItineraireAdapter(pojoList, this.getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity().getApplication().getBaseContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(itineraireAdapter);

        return view;
    }
}

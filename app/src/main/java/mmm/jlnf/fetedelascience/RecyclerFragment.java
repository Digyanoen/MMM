package mmm.jlnf.fetedelascience;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.Database.DBManager;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

import static android.content.ContentValues.TAG;

/**
 * Created by julien on 07/02/18.
 */

public class RecyclerFragment extends Fragment {



    @BindView(R.id.event) RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<EventPojo> eventsList = new ArrayList<>();

    public RecyclerFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recyclerlistview, container, false);
        ButterKnife.bind(this, view);
        recyclerViewAdapter = new RecyclerViewAdapter(eventsList, this.getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity().getApplication().getBaseContext());

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);
        Log.e(TAG, "RecyclerFragment: "+ this.getActivity() );

        return view;
    }

    public RecyclerViewAdapter getAdapter() {
        return recyclerViewAdapter;
    }

    public void updateEventsList(List list) {
        eventsList.addAll(list);
    }
}

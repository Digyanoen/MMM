package mmm.jlnf.fetedelascience;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mmm.jlnf.fetedelascience.Database.DBManager;
import mmm.jlnf.fetedelascience.Database.DatabaseHandler;
import mmm.jlnf.fetedelascience.Pojos.EventPojo;

/**
 * Created by nicolas on 21/01/18.
 */

public class EventView extends Activity{

    @BindView(R.id.event) RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private List<EventPojo> eventsList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_view);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        DBManager dbmanager = DBManager.getInstance();
        Intent i = getIntent();
        recyclerViewAdapter = new RecyclerViewAdapter(eventsList);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(recyclerViewAdapter);


        eventsList.addAll(dbmanager.getPojosByCity(i.getStringExtra("ville")));
        Log.e("tag", "size of eventlist : "+eventsList.size());

        recyclerViewAdapter.notifyDataSetChanged();




    }

    private void init() {
        EventPojo eventPojo = new EventPojo();
        eventPojo.setDescription_fr("desc");
        eventPojo.setTitre_fr("title");
        for (int i=0; i<5; i++){
            eventsList.add(eventPojo);
        }
    }
}
